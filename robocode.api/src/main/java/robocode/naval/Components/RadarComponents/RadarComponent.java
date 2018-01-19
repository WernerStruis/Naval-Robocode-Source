package robocode.naval.Components.RadarComponents;

import robocode.ScannedShipEvent;
import robocode.exception.RobotException;
import robocode.naval.Components.ComponentBase;
import robocode.naval.RadarType;
import robocode.naval.interfaces.componentInterfaces.IRadarFunctions;
import robocode.robotinterfaces.ITransformable;
import robocode.robotinterfaces.peer.IBasicShipPeer;
import robocode.util.Collision;
import robocode.util.Coordinates;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;

import static java.lang.Math.PI;

/**
 * A class that resembles a radar component for on a ship.
 * @author Thales B.V. / Jiri Waning
 * @since 1.8.3.0 Alpha 1
 * @version 0.1
 */
public abstract class RadarComponent extends ComponentBase implements IRadarFunctions {
	private static final long serialVersionUID = 2L;

	protected Arc2D.Double scanArc = new Arc2D.Double();
	private Color scanColor = Color.WHITE;

	private final RadarType type;

	private int RADAR_SCAN_RADIUS;
	private double RADAR_TURN_RATE_DEG;

	protected RadarComponent(RadarType type) {
		this(new Point2D.Double(0.0d, 0.0d), type);
	}

	/**
	 * Creates a new radar component for on the robot.
	 * @param pivot The pivot point of the component.
	 * @param type The type of radar component.
	 */
	public RadarComponent(Point2D pivot, RadarType type) {
		super(pivot, 0.0d);
		this.type = type;

		this.RADAR_SCAN_RADIUS = type.getScanRadius();
		this.RADAR_TURN_RATE_DEG = type.getTurnRate();
	}

	@Override
	public void setUpComponent(int slotIndex, IBasicShipPeer owner) {
		this.slotIndex = slotIndex;
		this.owner = owner;
		setPivot(new Point2D.Double(0, owner.getSlotOffset(slotIndex)));

		//TODO if radars need to face outwards, like cannons, uncomment below
		this.angle = getTypeOffset();

	}

	public RadarType getType() {
		return type;
	}

	public Color getScanColor() {
		return scanColor;
	}

	public void setAttributeValues(int altScanRadius, double altTurnRate){
		checkCaller();
		RADAR_SCAN_RADIUS = altScanRadius;
		RADAR_TURN_RATE_DEG = altTurnRate;
	}


	private void checkCaller(){
		try
		{
			throw new Exception("Who called me?");
		}
		catch( Exception e )
		{
			if(!e.getStackTrace()[2].getClassName().equals("net.sf.robocode.battle.peer.ShipPeer") && !e.getStackTrace()[2].getClassName().equals("net.sf.robocode.test.naval.RadarComponents.RadarComponentTest")){
				throw new RobotException("Cheat detected: Calling setAttributeValues from ship is forbidden.");
			}
		}
	}


	@Override
	public final void setScanColor(Color color) {
		this.scanColor = color;
	}

	@Override
	public final void setTurnLeftDegrees(double angle) {
		owner.rotate(slotIndex, -angle);
	}

	@Override
	public final void setTurnRightDegrees(double angle) {
		owner.rotate(slotIndex, angle);
	}

	@Override
	public final void setTurnLeftRadians(double angle) {
		setTurnLeftDegrees(Math.toDegrees(angle));
	}

	@Override
	public final void setTurnRightRadians(double angle) {
		setTurnRightDegrees(Math.toDegrees(angle));
	}

	@Override
	public double turnRadians(double turnRemaining) {
		double turnRate = getTurnRateRadians();
		if (turnRemaining > 0) {
			if (turnRemaining < turnRate) {
				angle += turnRemaining;
				return 0;
			} else {
				angle += turnRate;
				return turnRemaining - turnRate;
			}
		} else if (turnRemaining < 0) {
			if (turnRemaining > -turnRate) {
				angle += turnRemaining;
				return 0;
			} else {
				angle += -turnRate;
				return turnRemaining + turnRate;
			}
		} else{
			return 0;
		}

	}


	/**
	 * Getters
	 */
	@Override
	public final int getScanRadius() {
		return RADAR_SCAN_RADIUS;
	}

	public Arc2D.Double getScanArc() {
		return scanArc;
	}

	@Override
	public final double getTurnRateDegrees() {
		return RADAR_TURN_RATE_DEG;
	}

	@Override
	public final double getTurnRateRadians() {
		return Math.toRadians(RADAR_TURN_RATE_DEG);
	}

	@Override
	public double getTurnRemainingDegrees() {
		return owner.getComponentTurnRemainingDegrees(slotIndex);
	}

	@Override
	public double getTurnRemainingRadians() {
		return owner.getComponentTurnRemainingRadians(slotIndex);

	}

	@Override
	public double getHeadingDegrees() {
		double bodyHeadingDeg = owner.getBodyHeadingDegrees() * 180 / Math.PI;
		return (owner.getComponentHeadingDegrees(slotIndex) + bodyHeadingDeg) % 360;
	}

	@Override
	public double getHeadingRadians() {
		return Math.toRadians(getHeadingDegrees());
	}

	@Override
	public double getAngle() {
		return angle;
	}

	@Override
	public void setAdjustComponentForShipTurn(boolean independent) {
		owner.setAdjustComponentForShipTurn(slotIndex, independent);
	}

	/**
	 * Update the scan arc of the radar.
	 * @param shipPeer The peer to whom the radar belongs.
	 */
	public void updateScanArc(ITransformable shipPeer) {
		Point2D pivot = getPivot(); // relative
		Point2D point = Coordinates.getPivot(shipPeer, pivot.getX(), pivot.getY()); // absolute

		scanArc.setArc(
					/* x */point.getX() - RADAR_SCAN_RADIUS,
					/* y */point.getY() - RADAR_SCAN_RADIUS,
					/* w */2 * RADAR_SCAN_RADIUS,
					/* h */2 * RADAR_SCAN_RADIUS,
					/* s */180.0 * ((PI/2) - (shipPeer.getBodyHeading() + (getAngle() + (PI/2)))) / PI,
					/* e */ Math.toDegrees(angle - lastHeading),
					/* t */Arc2D.PIE);
	}

	/**
	 * Get the angular offset in radians based upon the weapon type.
	 * THOMA_NOTE: Basically makes sure that back cannons are facing south and that front cannons are facing north. (Returns PI/2 for front cannon and 3PI/2 for back cannon)
	 *
	 * @return The angular offset in radians based upon the weapon type.
	 */
	public double getTypeOffset() {
		//TODO THIS ONE PROBABLY IS NOT RIGHT
//        return 0;
		if (slotIndex < (owner.getMaxComponents() / 2)) {
			return 3*PI/2 ;
		} else {
			return PI/2;
		}
	}

	@Override
	public double getBearingRadians(ScannedShipEvent event) {

		if (slotIndex < event.getSlotBearings().length) {
			return event.getSlotBearings()[slotIndex] - (PI / 2);
		}
		return -1;
	}


	/**
	 * Determines whether or not the {@code peer} is inside the scan arc.
	 * @param peer The peer to determine from if it is inside the scan arc.
	 * @return {@code true} when the peer is in the scan arc; {@code false} otherwise.
	 */
	public boolean insideScanArc(ITransformable peer) {
		return Collision.insideScan(scanArc, peer);
	}



}
