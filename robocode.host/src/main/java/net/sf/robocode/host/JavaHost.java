/*******************************************************************************
 * Copyright (c) 2001-2014 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 *******************************************************************************/
package net.sf.robocode.host;


import net.sf.robocode.host.security.RobotClassLoader;
import net.sf.robocode.host.proxies.*;
import net.sf.robocode.io.FileUtil;
import net.sf.robocode.peer.IRobotStatics;
import net.sf.robocode.repository.IRobotItem;
import net.sf.robocode.repository.RobotType;
import static net.sf.robocode.io.Logger.logError;
import net.sf.robocode.io.Logger;
import net.sf.robocode.peer.IRobotPeer;
import net.sf.robocode.security.HiddenAccess;
import robocode.*;
import robocode.Robot;
import robocode.control.RobotSpecification;
import robocode.exception.RobotException;
import robocode.robotinterfaces.*;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.AccessControlException;
import java.lang.reflect.Method;


/**
 * @author Pavel Savara (original)
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class JavaHost implements IHost {

	public IRobotClassLoader createLoader(IRobotItem robotItem) {
		return new RobotClassLoader(robotItem.getClassPathURL(), robotItem.getFullClassName());
	}

	public IHostingRobotProxy createRobotProxy(IHostManager hostManager, RobotSpecification robotSpecification, IRobotStatics statics, IRobotPeer peer) {
		IHostingRobotProxy robotProxy;
		final IRobotItem specification = (IRobotItem) HiddenAccess.getFileSpecification(robotSpecification);

		if (specification.isShip()) {
			robotProxy = new ShipProxy(specification, hostManager, peer, (RobotStatics) statics);
		} else if (specification.isTeamRobot()) {
			robotProxy = new TeamRobotProxy(specification, hostManager, peer, (RobotStatics) statics);
		} else if (specification.isAdvancedRobot()) {
			robotProxy = new AdvancedRobotProxy(specification, hostManager, peer, (RobotStatics) statics);
		} else if (specification.isStandardRobot()) {
			robotProxy = new StandardRobotProxy(specification, hostManager, peer, (RobotStatics) statics);
		} else if (specification.isJuniorRobot()) {
			robotProxy = new JuniorRobotProxy(specification, hostManager, peer, (RobotStatics) statics);
		} else {
			throw new AccessControlException("Unknown robot type");
		}
		return robotProxy;
	}

	public String[] getReferencedClasses(IRobotItem robotItem) {
		IRobotClassLoader loader = null;

		try {
			loader = createLoader(robotItem);
			loader.loadRobotMainClass(true);
			return loader.getReferencedClasses();

		} catch (ClassNotFoundException e) {
			Logger.logError(e);
			return new String[0];
		} finally {
			if (loader != null) {
				loader.cleanup();
			}
		}
	}

	public RobotType getRobotType(IRobotItem robotItem, boolean resolve, boolean message) {
		IRobotClassLoader loader = null;

		try {
			loader = createLoader(robotItem);
			Class<?> robotClass = loader.loadRobotMainClass(resolve);

			if (robotClass == null || java.lang.reflect.Modifier.isAbstract(robotClass.getModifiers())) {
				// this class is not robot
				return RobotType.INVALID;
			}
			return checkInterfaces(robotClass, robotItem);

		} catch (Throwable t) {
			if (message) {
				logError("Got an error with " + robotItem.getFullClassName() + ": " + t); // just message here
				if (t.getMessage() != null && t.getMessage().contains("Bad version number in .class file")) {
					logError("Maybe you run robocode with Java 1.5 and robot was compiled for later Java version ?");
				}else{
//					try {
//						try {
							logError("Moving " + robotItem.getFullClassName() + " to: " + FileUtil.getExludedBotsDir());
////							Files.move(new File(robotItem.getClassPathURL().toURI()).toPath(), new File(FileUtil.getExludedBotsDir() + "/").toPath(), StandardCopyOption.REPLACE_EXISTING);
//						} catch (DirectoryNotEmptyException e) {
//							logError("Move failed, deleting file " + robotItem.getFullClassName());
////							Files.delete(new File(robotItem.getItemURL().toURI()).toPath());
//						}
//					}catch (IOException e) {
//						e.printStackTrace();
//					} catch (URISyntaxException e) {
//						e.printStackTrace();
//					}
				}
			}
			return RobotType.INVALID;
		} finally {
			if (loader != null) {
				loader.cleanup();
			}
		}
	}

	private RobotType checkInterfaces(Class<?> robotClass, IRobotItem robotItem) {
		boolean isJuniorRobot = false;
		boolean isStandardRobot = false;
		boolean isInteractiveRobot = false;
		boolean isPaintRobot = false;
		boolean isAdvancedRobot = false;
		boolean isTeamRobot = false;
		boolean isDroid = false;
		boolean isSentryRobot = false;
		boolean isShip = false;
		int shipFlag = RobotType.NOSHIP_FLAG;

		if (IShip.class.isAssignableFrom(robotClass)) {
			isShip = true;
		}
		if(CruiserShip.class.isAssignableFrom(robotClass)){
			shipFlag = RobotType.CRUISER_FLAG;
		}
		if(CorvetteShip.class.isAssignableFrom(robotClass)){
			shipFlag = RobotType.CORVETTE_FLAG;
		}
		if(CarrierShip.class.isAssignableFrom(robotClass)){
			shipFlag = RobotType.CARRIER_FLAG;
		}

		if(isShip && shipFlag == RobotType.NOSHIP_FLAG){
			throw new RobotException("Cheat detected: Directly extending Ship is forbidden.");
		}
		
		if (IAdvancedRobot.class.isAssignableFrom(robotClass)) { // Note: must be checked first
			isAdvancedRobot = true;
		}
		if (Robot.class.isAssignableFrom(robotClass) && !isAdvancedRobot) {
			isStandardRobot = true;
		}
		if (IJuniorRobot.class.isAssignableFrom(robotClass)) { // Note: Must be checked before checking for standard robot
			isJuniorRobot = true;
			if (isAdvancedRobot) {
				throw new AccessControlException(
						robotItem.getFullClassName() + ": Junior robot should not implement IAdvancedRobot interface.");
			}
		}
		if (IBasicRobot.class.isAssignableFrom(robotClass)) {
			if (!(isAdvancedRobot || isJuniorRobot)) {
				isStandardRobot = true;
			}
		}
		if (ITeamRobot.class.isAssignableFrom(robotClass)) {
			isTeamRobot = true;
		}
		if (Droid.class.isAssignableFrom(robotClass)) {
			isDroid = true;
		}
		if (BorderSentry.class.isAssignableFrom(robotClass)) {
			isSentryRobot = true;
		}

		if (IInteractiveRobot.class.isAssignableFrom(robotClass)) {
			// in this case we make sure that robot don't waste time
			if (checkMethodOverride(robotClass, Robot.class, "getInteractiveEventListener")
					|| checkMethodOverride(robotClass, Robot.class, "onKeyPressed", KeyEvent.class)
					|| checkMethodOverride(robotClass, Robot.class, "onKeyReleased", KeyEvent.class)
					|| checkMethodOverride(robotClass, Robot.class, "onKeyTyped", KeyEvent.class)
					|| checkMethodOverride(robotClass, Robot.class, "onMouseClicked", MouseEvent.class)
					|| checkMethodOverride(robotClass, Robot.class, "onMouseEntered", MouseEvent.class)
					|| checkMethodOverride(robotClass, Robot.class, "onMouseExited", MouseEvent.class)
					|| checkMethodOverride(robotClass, Robot.class, "onMousePressed", MouseEvent.class)
					|| checkMethodOverride(robotClass, Robot.class, "onMouseReleased", MouseEvent.class)
					|| checkMethodOverride(robotClass, Robot.class, "onMouseMoved", MouseEvent.class)
					|| checkMethodOverride(robotClass, Robot.class, "onMouseDragged", MouseEvent.class)
					|| checkMethodOverride(robotClass, Robot.class, "onMouseWheelMoved", MouseWheelEvent.class)
					) {
				isInteractiveRobot = true;
			}
		}

		if (IPaintRobot.class.isAssignableFrom(robotClass)) {
			if (checkMethodOverride(robotClass, Robot.class, "getPaintEventListener")
					|| checkMethodOverride(robotClass, Robot.class, "onPaint", Graphics2D.class)
					) {
				isPaintRobot = true;
			}
		}

		return new RobotType(isJuniorRobot, isStandardRobot, isInteractiveRobot, isPaintRobot, isAdvancedRobot,
				isTeamRobot, isDroid, isSentryRobot, isShip, shipFlag);
	}

	private boolean checkMethodOverride(Class<?> robotClass, Class<?> knownBase, String name, Class<?>... parameterTypes) {
		if (knownBase.isAssignableFrom(robotClass)) {
			final Method getInteractiveEventListener;

			try {
				getInteractiveEventListener = robotClass.getMethod(name, parameterTypes);
			} catch (NoSuchMethodException e) {
				return false;
			}
			if (getInteractiveEventListener.getDeclaringClass().equals(knownBase)) {
				return false;
			}
		}
		return true;
	}
	
}
