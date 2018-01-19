package robocode.control.snapshot;

import robocode.naval.ShipType;

import java.util.ArrayList;
/**
 * Interface for the ShipSnapshots. 
 * Basically just adds a list with ComponentSnapshots to the RobotSnapshot.
 * @author Thales B.V. / Thomas Hakkers
 *
 */
public interface IShipSnapshot extends IRobotSnapshot{
	/**
	 * @returns a list of ComponentSnapshots relevant to this ShipSnapshot
	 */
	ArrayList<IComponentSnapshot> getComponents();

	/**
	 * @returns the chosen ship type
	 */
	ShipType getType();
}
