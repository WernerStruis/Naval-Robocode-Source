package net.sf.robocode.battle.snapshot.components;

import robocode.naval.Components.CannonComponents.BlindSpot;
import robocode.util.Utils;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class BlindSpotSnapshot {
	protected double start;
	protected double extent;
	protected boolean hasSecondBlindSpot;
	protected double secondstart;
	
	/**
	 * Creates a Snapshot for a BlindSpot based on the given BlindSpot
	 * @param blindspot The BlindSpot the snapshot needs to be based on.
	 */
	public BlindSpotSnapshot(BlindSpot blindspot){
		start = blindspot.getStart();
		extent = Utils.normalAbsoluteAngle(blindspot.getEnd() - start);
		this.hasSecondBlindSpot = blindspot.hasSecondBlindSpot();
	}
	
	/**
	 * Constructor that is made for serialization purposes.
	 * @param start The start of the blindspot.
	 * @param extent The extent of the blindspot.
	 */
	public BlindSpotSnapshot(double start, double extent, boolean hasSecondBlindSpot, double secondstart){
		this.start = start;
		this.extent = extent;
		this.hasSecondBlindSpot = hasSecondBlindSpot;
		this.secondstart = secondstart;
	}

	public double getSecondstart() {
		return secondstart;
	}

	public BlindSpotSnapshot(){
	}
	
	public double getStart(){
		return start;
	}
	
	public double getExtent(){
		return extent;
	}

	public boolean hasSecondBlindSpot() {
		return hasSecondBlindSpot;
	}
}
