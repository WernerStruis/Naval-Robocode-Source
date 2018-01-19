/**
 * Copyright (c) 2001-2017 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 */
package net.sf.robocode.ui.dialog;


import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.battle.peer.CannonTypePeer;
import net.sf.robocode.settings.ISettingsManager;
import robocode.naval.CannonType;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
@SuppressWarnings("serial")
public class NewBattleConfigCannonPanel extends JPanel {

	private final JLabel namePanel = new JLabel("Set Custom cannon values");

	private final JSpinner sbTurnRate = new JSpinner();
	private final JSpinner sbDamageMult = new JSpinner();
	private final JSpinner sbBonusMult = new JSpinner();
	private final JSpinner sbBulletSpeedMult = new JSpinner();
	private final JSpinner sbMaxBulletPower = new JSpinner();
	private final JSpinner sbBulletRange = new JSpinner();

	private final JSpinner dbTurnRate = new JSpinner();
	private final JSpinner dbDamageMult = new JSpinner();
	private final JSpinner dbBonusMult = new JSpinner();
	private final JSpinner dbBulletSpeedMult = new JSpinner();
	private final JSpinner dbMaxBulletPower = new JSpinner();
	private final JSpinner dbBulletRange = new JSpinner();


	public void setup(ISettingsManager manager) {
		GridBagLayout gbc = new GridBagLayout();
		setLayout(gbc);

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_START;

		addSingleBarrelPanel(c);
		addDoubleBarrelPanel(c);

		setValues(manager.getCannonConfigString());
	}

	public String getValueString(){
		//[8-4-2-1-3],[16-4-2-2-3],[8-5-2-1-6]
		return String.format("[%s-%s-%s-%s-%s-%s],[%s-%s-%s-%s-%s-%s],[8-5-2-1-6-700]",
				(sbTurnRate.getValue() == null? "16" + 0 : sbTurnRate.getValue()),
				(sbDamageMult.getValue() == null? "4" + 0 : sbDamageMult.getValue()),
				(sbBonusMult.getValue() == null? "2" + 0 : sbBonusMult.getValue()),
				(sbBulletSpeedMult.getValue() == null? "" + 0 : sbBulletSpeedMult.getValue()),
				(sbMaxBulletPower.getValue() == null? "" + 0 : sbMaxBulletPower.getValue()),
				(sbBulletRange.getValue() == null? "" + 0 : sbBulletRange.getValue()),
				(dbTurnRate.getValue() == null? "" + 0 : dbTurnRate.getValue()),
				(dbDamageMult.getValue() == null? "" + 0 : dbDamageMult.getValue()),
				(dbBonusMult.getValue() == null? "" + 0 : dbBonusMult.getValue()),
				(dbBulletSpeedMult.getValue() == null? "" + 0 : dbBulletSpeedMult.getValue()),
				(dbMaxBulletPower.getValue() == null? "" + 0 : dbMaxBulletPower.getValue()),
				(dbBulletRange.getValue() == null? "" + 0 : dbBulletRange.getValue()));
	}

	public void setValues(String configString){
		String[] parts = configString.split(",");
		for (int i = 0; i < parts.length; i++) {
			parts[i] = parts[i].substring(1, parts[i].length() - 1);
		}
		String[] sbValues = parts[0].split("-");
		String[] dbValues = parts[1].split("-");

		sbTurnRate.setModel(new SpinnerNumberModel(Double.parseDouble(sbValues[0]), 1, Integer.MAX_VALUE, 0.5));
		sbDamageMult.setModel(new SpinnerNumberModel(Double.parseDouble(sbValues[1]), 1, Integer.MAX_VALUE, 0.5));
		sbBonusMult.setModel(new SpinnerNumberModel(Double.parseDouble(sbValues[2]), 1, Integer.MAX_VALUE, 0.5));
		sbBulletSpeedMult.setModel(new SpinnerNumberModel(Double.parseDouble(sbValues[3]), 1, Integer.MAX_VALUE, 0.5));
		sbMaxBulletPower.setModel(new SpinnerNumberModel(Double.parseDouble(sbValues[4]), 1, Integer.MAX_VALUE, 1));
		sbBulletRange.setModel(new SpinnerNumberModel(Double.parseDouble(sbValues[5]), 1, Integer.MAX_VALUE, 1));

		dbTurnRate.setModel(new SpinnerNumberModel(Double.parseDouble(dbValues[0]), 1, Integer.MAX_VALUE, 0.5));
		dbDamageMult.setModel(new SpinnerNumberModel(Double.parseDouble(dbValues[1]), 1, Integer.MAX_VALUE, 0.5));
		dbBonusMult.setModel(new SpinnerNumberModel(Double.parseDouble(dbValues[2]), 1, Integer.MAX_VALUE, 0.5));
		dbBulletSpeedMult.setModel(new SpinnerNumberModel(Double.parseDouble(dbValues[3]), 1, Integer.MAX_VALUE, 0.5));
		dbMaxBulletPower.setModel(new SpinnerNumberModel(Double.parseDouble(dbValues[4]), 1, Integer.MAX_VALUE, 1));
		dbBulletRange.setModel(new SpinnerNumberModel(Double.parseDouble(dbValues[5]), 1, Integer.MAX_VALUE, 1));

	}

	private void addSingleBarrelPanel(GridBagConstraints c){
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.weightx = 0.5;
		add(getSingleBarrelPanel(), c);
	}
	private void addDoubleBarrelPanel(GridBagConstraints c){
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.weightx = 0.5;
		add(getDoubleBarrelPanel(), c);
	}

	public JPanel getSingleBarrelPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6,2));
		Border border = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0),
				BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Single-Barrel Cannon"));
		panel.setBorder(border);

		panel.add(new JLabel("Turn Rate: "));
		panel.add(sbTurnRate);
		panel.add(new JLabel("Damage Multiplier: "));
		panel.add(sbDamageMult);
		panel.add(new JLabel("Bonus Multiplier: "));
		panel.add(sbBonusMult);
		panel.add(new JLabel("Bullet Speed Multiplier: "));
		panel.add(sbBulletSpeedMult);
		panel.add(new JLabel("Max bullet power: "));
		panel.add(sbMaxBulletPower);
		panel.add(new JLabel("Max bullet range: "));
		panel.add(sbBulletRange);

		return panel;
	}

	public JPanel getDoubleBarrelPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6,2));
		Border border = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0),
				BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Double-Barrel Cannon"));
		panel.setBorder(border);

		panel.add(new JLabel("Turn Rate: "));
		panel.add(dbTurnRate);
		panel.add(new JLabel("Damage Multiplier: "));
		panel.add(dbDamageMult);
		panel.add(new JLabel("Bonus Multiplier: "));
		panel.add(dbBonusMult);
		panel.add(new JLabel("Bullet Speed Multiplier: "));
		panel.add(dbBulletSpeedMult);
		panel.add(new JLabel("Max bullet power: "));
		panel.add(dbMaxBulletPower);
		panel.add(new JLabel("Max bullet range: "));
		panel.add(dbBulletRange);

		return panel;
	}





}
