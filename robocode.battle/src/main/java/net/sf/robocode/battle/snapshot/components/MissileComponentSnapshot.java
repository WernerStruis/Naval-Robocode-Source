package net.sf.robocode.battle.snapshot.components;


import net.sf.robocode.serialization.IXmlSerializable;
import net.sf.robocode.serialization.SerializableOptions;
import net.sf.robocode.serialization.XmlReader;
import net.sf.robocode.serialization.XmlWriter;
import robocode.naval.CannonType;
import robocode.naval.Components.CannonComponents.CannonComponent;
import robocode.naval.Components.MissileComponent;
import robocode.naval.interfaces.IPaint;
import robocode.naval.interfaces.IRenderImages;
import robocode.robotinterfaces.ITransformable;
import robocode.util.Utils;

import java.awt.*;
import java.io.IOException;

//import robocode.naval.ComponentType;

/**
 * Snapshot for a WeaponComponent.
 * @author Thales B.V. / Thomas Hakkers
 * @since 1.9.2.2
 */
public class MissileComponentSnapshot extends BaseComponentSnapshot{
	private static final long serialVersionUID = -358308844160820664L;


	/**
	 * The current (over)heat of the gun.
	 */
	protected double gunHeat;

	/**
	 * The fire power of the weapon.
	 */
	protected double power;


	public MissileComponentSnapshot(){
		super();
	}

	public MissileComponentSnapshot(MissileComponent missileComponent){
		super(missileComponent);

		gunHeat = missileComponent.getGunHeat();
		power = missileComponent.getPower();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getGunHeat() {
		return gunHeat;
	}
	public double getPower(){
		return power;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IPaint getImage(IRenderImages manager) {
		return manager.getColoredMissileComponentRenderNavalImage(getColor());
	}

	/**
	 * {@inheritDoc}
	 */
	public void render(Graphics2D g, ITransformable peer, boolean scanArcs) {
		// Render weapon related items... (Which it does not have ATM.)
	}


	@Override
	public void writeXml(XmlWriter writer, SerializableOptions options) throws IOException {
		super.writeXml(writer, options);
			writer.writeAttribute("gunheat", gunHeat, options.trimPrecision);
			writer.writeAttribute("power", power, options.trimPrecision);
		writer.writeAttribute("type", "MISSILE");

		writer.endElement();
	}
	
	@Override
	public XmlReader.Element readXml(XmlReader reader) {
		return reader.expect("minecomponent", "mc", new XmlReader.Element() {
			public IXmlSerializable read(final XmlReader reader) {
				final MissileComponentSnapshot snapshot = new MissileComponentSnapshot();
			
//				reader.expect("type", new XmlReader.Attribute() {
//					public void read(String value) {
//						snapshot.type = ComponentType.getValue(Integer.parseInt(value));
//					}
//				});

				reader.expect("x", new XmlReader.Attribute() {
					public void read(String value) {
						snapshot.pivot.setLocation(Double.parseDouble(value), 0);
					}
				});

				reader.expect("y", new XmlReader.Attribute() {
					public void read(String value) {
						snapshot.pivot.setLocation(snapshot.pivot.getX(), Double.parseDouble(value));
					}
				});

				reader.expect("color", new XmlReader.Attribute() {
					public void read(String value) {
						snapshot.color = (Long.valueOf(value.toUpperCase(), 16).intValue());
					}
				});
				
				reader.expect("angle", new XmlReader.Attribute() {
					public void read(String value) {
						snapshot.angle = Double.parseDouble(value);
					}
				});

				reader.expect("lastangle", new XmlReader.Attribute() {
					public void read(String value) {
						snapshot.lastHeading = Double.parseDouble(value);
					}
				});

				reader.expect("serializetype", new XmlReader.Attribute() {
					public void read(String value) {
						snapshot.serializeType = value.getBytes()[0];
					}
				});
				
				reader.expect("gunheat", new XmlReader.Attribute() {
					public void read(String value) {
						snapshot.gunHeat = Double.parseDouble(value);
					}
				});
				
				reader.expect("power", new XmlReader.Attribute() {
					public void read(String value) {
						snapshot.power = Double.parseDouble(value);
					}
				});

				reader.expect("slotindex", new XmlReader.Attribute() {
					public void read(String value) {
						snapshot.slotIndex = Integer.parseInt(value);
					}
				});
				
				return snapshot;
			}
		});
	}
}
