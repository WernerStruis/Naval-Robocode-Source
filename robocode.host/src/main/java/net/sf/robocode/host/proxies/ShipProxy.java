package net.sf.robocode.host.proxies;

import static java.lang.Math.PI;
import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.robocode.peer.*;
import robocode.Bullet;
import robocode.Missile;
import robocode.naval.*;
import robocode.naval.Components.CannonComponents.CannonComponent;
import robocode.naval.Components.ComponentBase;
import robocode.naval.Components.MineComponent;
import robocode.naval.Components.MissileComponent;
import robocode.naval.interfaces.componentInterfaces.IComponent;
import robocode.robotinterfaces.peer.IBasicShipPeer;
import net.sf.robocode.host.IHostManager;
import net.sf.robocode.host.RobotStatics;
import net.sf.robocode.repository.IRobotItem;
import net.sf.robocode.security.HiddenAccess;
import robocode.Mine;
import robocode.ShipStatus;

/**
 * Handles the game mechanics of a ship. (Delegates the commands of a Ship.)
 * @author Thales B.V. / Jiri Waning / Thomas Hakkers
 * @author Thales B.V. /Werner Struis (contributor naval)
 * @since 1.9.2.0 
 * @version 0.2
 */
public class ShipProxy extends AdvancedRobotProxy implements IBasicShipPeer{
	protected int nextMineId = 1;
	protected final Map<Integer, Mine> mines = new ConcurrentHashMap<Integer, Mine>();

	public ShipProxy(IRobotItem specification, IHostManager hostManager, IRobotPeer peer, RobotStatics statics) {
		super(specification, hostManager, peer, statics);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getSlotOffset(int slotIndex) {
		return ComponentManager.getSlotOffset(slotIndex, getShipType());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void rotate(int index, double angle) {
		setCall();
		commands.setComponentsTurnRemaining(index, Math.toRadians(angle));
	}

	@Override
	public double getAngleRemainingComponent(int index) {
		return commands.getComponentsTurnRemaining(index);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setSlot(int slotIndex, IComponent component) {
		if(component != null) {
//		System.err.println("ShipProxy.java setSlot() at: " + System.currentTimeMillis());
			ComponentManager manager = ((ShipStatus) status).getComponents();
			component.setUpComponent(slotIndex, this);
			manager.setSlot(slotIndex, component);
//
//		System.out.println("ShipProxy.java setSlot() at: " + System.currentTimeMillis());
//		System.out.println("--> this needs to be called after RobotPeer.java startRound()");
		}
	}

	@Override
	public ComponentBase getSlot(int slotIndex) {
		ComponentManager manager = ((ShipStatus)status).getComponents();
		return (ComponentBase) manager.getSlot(slotIndex);
	}

	@Override
	public int getMaxComponents() {
		return ShipType.getTypeByID(statics.getShipFlag()).getMaxSlots();
	}

	/**
	 * Returns the turnRemaining for the component at the given index in degrees.
	 *
	 * @param index The index of the component you want to know the turnRemaining of
	 * @return The amount the given component still needs to turn in degrees.
	 */
	public final double getComponentTurnRemainingDegrees(int index) {
		return getComponentTurnRemainingRadians(index) * 180 / Math.PI;
	}

	/**
	 * Returns the turnRemaining for the component at the given index in radians.
	 *
	 * @param index The index of the component you want to know the turnRemaining of
	 * @return The amount the given component still needs to turn in radians.
	 */
	public final double getComponentTurnRemainingRadians(int index) {
		return getAngleRemainingComponent(index);
	}

	@Override
	public double getComponentHeadingDegrees(int index) {
		return Math.toDegrees(getComponentHeadingRadians(index));
	}

	@Override
	public double getComponentHeadingRadians(int index) {
		ComponentManager manager = ((ShipStatus)status).getComponents();
		return manager.getSlot(index).getAngle();
	}

	@Override
	public void setAdjustComponentForShipTurn(int index, boolean independent) {
		setCall();
		commands.setAdjustComponentForShip(index, independent);
	}

	/**
	 * returns the body heading in degrees
	 * @return body heading in degrees
	 */
	public double getBodyHeadingDegrees() {
		return Math.toDegrees(getBodyHeadingRadians());
	}

	/**
	 * returns the body heading in degrees
	 * @return body heading in degrees
	 */
	public double getBodyHeadingRadians() {
		getCall();
		return status.getHeadingRadians();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Bullet fireComponent(int index) {
		Bullet bullet = setFireCannonImpl(index);
		execute();
		return bullet;
	}

	@Override
	public Missile fireComponentMissile(int index) {
		Missile missile = setLaunchImpl(index);
		execute();
		return missile;
	}

	protected Missile setLaunchImpl(int index) {
		ComponentManager manager = ((ShipStatus)status).getComponents();
		MissileComponent missileComponent = (MissileComponent)manager.getSlot(index);
		if(missileComponent == null){
			return null;
		}
		if (Double.isNaN(missileComponent.getPower())) {
			println("SYSTEM: You cannot call fire(NaN)");
			return null;
		}
		if (missileComponent.getGunHeat() > 0 || getEnergyImpl() == 0) {
			return null;
		}

		Missile missile;
		MissileCommand wrapper;
		nextMissileId++;
		double offsetY = missileComponent.getPivot().getY();
		missile = new Missile(PI - missileComponent.getAngle() - getBodyHeading(),
				getX() + (offsetY * Math.cos(getBodyHeading()+ Math.PI/2)),
				getBattleFieldHeight() - (getY() + (offsetY * Math.sin(getBodyHeading() + Math.PI/2))),
				missileComponent.getPower(), statics.getName(), null, true, nextMissileId);
		wrapper = new MissileCommandShip(missileComponent.getPower(), false, 0, nextMissileId, index);

		commands.getMissiles().add(wrapper);

		missiles.put(nextMissileId, missile);
		return missile;
	}

	/**
	 * Basically puts a few BulletCommandShips in the ExecCommands object.
	 * Updated to now use bulletIds.
	 */
	private Bullet setFireCannonImpl(int index) {
		ComponentManager manager = ((ShipStatus)status).getComponents();
		CannonComponent weaponComponent = (CannonComponent)manager.getSlot(index);
		if(weaponComponent == null){
			return null;
		}
		if (Double.isNaN(weaponComponent.getPower())) {
			println("SYSTEM: You cannot call fire(NaN)");
			return null;
		}
		if (weaponComponent.getGunHeat() > 0 || getEnergyImpl() == 0) {
			return null;
		}

		Bullet bullet;
		BulletCommand wrapper;
		nextBulletId++;
		double offsetY = weaponComponent.getPivot().getY();
		bullet = new Bullet(PI - weaponComponent.getAngle() - getBodyHeading(),
				getX() + (offsetY * Math.cos(getBodyHeading()+ Math.PI/2)),
				getBattleFieldHeight() - (getY() + (offsetY * Math.sin(getBodyHeading() + Math.PI/2))),
				weaponComponent.getPower(),
				weaponComponent.getBulletSpeed(weaponComponent.getPower()),
				statics.getName(), null, true, nextBulletId);
		wrapper = new BulletCommandShip(weaponComponent.getPower(), weaponComponent.getType(), false, 0, nextBulletId, index);

		commands.getBullets().add(wrapper);

		bullets.put(nextBulletId, bullet);

		return bullet;
	}

	/**
	 * {@inheritDoc}
	 */
	public Mine mine(double power, int index) {
		Mine mine = setMine(power, index);
		execute();
		return mine;
	}

	/**
	 * {@inheritDoc}
	 */
	public Mine setMine(double power, int index) {
		setCall();
		return setMineImpl(power, index);
	}

	protected final Mine setMineImpl(double power, int index) {
		if (Double.isNaN(power)) {
			println("SYSTEM: You cannot call mine(NaN)");
			return null;
		}
		ComponentManager manager = ((ShipStatus)status).getComponents();
		MineComponent mineComponent = (MineComponent)manager.getSlot(index);

		if(mineComponent == null){
			return null;
		}

		if (mineComponent.getMineRecharge() > 0 || getEnergyImpl() == 0) {
			return null;
		}
		//Make sure the power is within the boundries
		power = min(getEnergyImpl(), min(max(power, NavalRules.MIN_MINE_POWER), NavalRules.MAX_MINE_POWER));

		Mine mine;
		MineCommand wrapper;

		nextMineId++;

		double offsetY = mineComponent.getPivot().getY();
		mine = new Mine(getX() + (offsetY * Math.cos(getBodyHeading()+ Math.PI/2)),
				getBattleFieldHeight() - (getY() + (offsetY * Math.sin(getBodyHeading() + Math.PI/2))),
				power, statics.getName(), null, true, nextMineId);
		wrapper = new MineCommand(power, nextMineId);

		commands.getMines().add(wrapper);

		mines.put(nextMineId, mine);

		return mine;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public ShipType getShipType() {
		return ShipType.getTypeByID(statics.getShipFlag());
	}
	
	@Override
	protected final void executeImpl() {
		super.executeImpl();
		if (execResults.getMineUpdates() != null) {
			for (MineStatus mineStatus : execResults.getMineUpdates()) {
				final Mine mine = mines.get(mineStatus.mineId);
		
				if (mine != null) {
					//x and y don't need to be update since a mine doesn't move
					HiddenAccess.update(mine, mineStatus.victimName,
							mineStatus.isActive);
					if (!mineStatus.isActive) {
						mines.remove(mineStatus.mineId);
					}
				}
			}
		}
	}
}
