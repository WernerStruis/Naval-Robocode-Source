/**
 * Copyright (c) 2001-2017 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 */
package net.sf.robocode.test.robots;


import net.sf.robocode.test.helpers.RobocodeTestBed;
import org.junit.Ignore;
import org.junit.Test;


/**
 * @author Pavel Savara (original)
 */
@Ignore
public class TestIncludeNamespaceAttack extends RobocodeTestBed {
	@Test
	public void run() {
		super.run();
	}

	@Override
	protected int getExpectedErrors() {
		return 2;
	}

	@Override
	public int getExpectedRobotCount(String list) {
		return 1;
	}

	@Override
	public String getRobotNames() {
		return "tested.robots.DieFast,tested.robots.IncludeNamespaceAttack";
	}
}