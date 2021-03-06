/**
 * Copyright (c) 2001-2017 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 */
package net.sf.robocode.battle.peer;


import robocode.BattleRules;
import robocode.control.snapshot.BulletState;
import robocode.naval.CannonType;

import java.util.List;


/**
 * @author Mathew A. Nelson (original)
 * @author Luis Crespo (contributor)
 * @author Flemming N. Larsen (contributor)
 * @author Titus Chen (contributor)
 */
public class ExplosionPeer extends BulletPeer {

	private static final int EXPLOSION_LENGTH = 71;

	ExplosionPeer(RobotPeer owner, BattleRules battleRules) {
		super(owner, null, battleRules, 0);
		frame = 0;
		x = owner.getX();
		y = owner.getY();
		victim = owner;
		power = 3;
		state = BulletState.EXPLODED;
		explosionImageIndex = 1;
	}

	@Override
	public final void update(List<RobotPeer> robots, List<BulletPeer> bullets, List<MissilePeer> missiles) {
		frame++;

		x = owner.getX();
		y = owner.getY();

		updateBulletState();
	}

	@Override
	public int getExplosionLength() {
		return EXPLOSION_LENGTH;
	}
}
