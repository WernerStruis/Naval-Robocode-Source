/**
 * Copyright (c) 2001-2017 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 */
package net.sf.robocode.test.robots;


import net.sf.robocode.test.helpers.RobocodeTestBed;
import org.junit.Assert;
import org.junit.Ignore;
import robocode.control.events.TurnEndedEvent;


/**
 * @author Flemming N. Larsen (original)
 */
@Ignore
public class TestReflectionAttack extends RobocodeTestBed {

	private boolean messagedAccessDenied;
	
	@Override
	public String getRobotNames() {
		return "tested.robots.ReflectionAttack,tested.sample.Target";
	}

	@Override
	public void onTurnEnded(TurnEndedEvent event) {
		super.onTurnEnded(event);

		final String out = event.getTurnSnapshot().getRobots()[0].getOutputStreamSnapshot();

		if (out.contains("access denied (java.lang.reflect.ReflectPermission")
				|| out.contains("access denied (\"java.lang.reflect.ReflectPermission\"")) {
			messagedAccessDenied = true;	
		}	
	}

	@Override
	protected void runTeardown() {
		Assert.assertTrue("Reflection is not allowed", messagedAccessDenied);
	}

	@Override
	protected int getExpectedErrors() {
		return 1; // Security error must be reported as an error
	}
}