/**
 * Copyright (c) 2001-2017 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 */
package net.sf.robocode.ui.dialog;


import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.settings.ISettingsManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;


/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
@SuppressWarnings("serial")
public class NewBattleConfigValuesPanel extends JPanel {

	private ISettingsManager settingsManager;
	private BattleProperties battleProperties;

	//tabs
	private NewBattleConfigShipPanel shipTab;
	private NewBattleConfigRadarPanel radarTab;
	private NewBattleConfigCannonPanel cannonTab;

	private final EventHandler eventHandler = new EventHandler();



	public void setup(ISettingsManager settingsManager, BattleProperties battleProperties) {
		GridBagLayout gbl = new GridBagLayout();
		gbl.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gbl);
		this.settingsManager = settingsManager;
		this.battleProperties = battleProperties;

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5,0,5,0);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.FIRST_LINE_START;

		addRadarPropertiesPanel(c);
		addCannonPropertiesPanel(c);
		addShipPropertiesPanel(c);

	}

	private void addRadarPropertiesPanel(GridBagConstraints c){
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.weightx = 1;
		add(getRadarTab(),c);
	};

	private void addCannonPropertiesPanel(GridBagConstraints c){
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		c.weightx = 1;
		add(getCannonTab(),c);
	};

	private void addShipPropertiesPanel(GridBagConstraints c){
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		c.weightx = 1;
		add(getShipTab(),c);
	}

	private NewBattleConfigShipPanel getShipTab() {
		if (shipTab == null) {
			shipTab = new NewBattleConfigShipPanel();
			shipTab.addAncestorListener(eventHandler);

			Border border = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0),
					BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Ship values"));
			shipTab.setBorder(border);

			shipTab.setup(settingsManager);
		}
		return shipTab;
	}

	private NewBattleConfigRadarPanel getRadarTab() {
		if (radarTab == null) {
			radarTab = new NewBattleConfigRadarPanel();
			radarTab.addAncestorListener(eventHandler);


			Border border = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0),
					BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Radar values"));
			radarTab.setBorder(border);

			radarTab.setup(settingsManager);
		}
		return radarTab;
	}

	private NewBattleConfigCannonPanel getCannonTab() {
		if (cannonTab == null) {
			cannonTab = new NewBattleConfigCannonPanel();
			cannonTab.addAncestorListener(eventHandler);

			Border border = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0),
					BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Cannon values"));
			cannonTab.setBorder(border);

			cannonTab.setup(settingsManager);
		}
		return cannonTab;
	}

	public String getRadarConfigString(){
		return radarTab.getValueString();
	}

	public String getCannonConfigString(){
		return cannonTab.getValueString();
	}

	public String getShipConfigString(){
		return shipTab.getValueString();
	}

	public void restoreDefaults(){
		getShipTab().setValues(settingsManager.getDefaultShipConfigString());
		getRadarTab().setValues(settingsManager.getDefaultRadarConfigString());
		getCannonTab().setValues(settingsManager.getDefaultCannonConfigString());
	}



	private class EventHandler implements AncestorListener, ActionListener, ChangeListener {

		@Override
		public void actionPerformed(ActionEvent e) {
		}

		@Override
		public void ancestorAdded(AncestorEvent event) {
		}

		@Override
		public void ancestorRemoved(AncestorEvent event) {
			settingsManager.setShipConfig(shipTab.getValueString());
			battleProperties.setShipConfiguration(shipTab.getValueString());

			settingsManager.setRadarConfig(radarTab.getValueString());
			battleProperties.setRadarConfiguration(radarTab.getValueString());

			settingsManager.setCannonConfig(cannonTab.getValueString());
			battleProperties.setCannonConfiguration(cannonTab.getValueString());

			settingsManager.saveProperties();
		}

		@Override
		public void ancestorMoved(AncestorEvent event) {
		}

		@Override
		public void stateChanged(ChangeEvent e) {

		}
	}

}
