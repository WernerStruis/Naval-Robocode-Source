/**
 * Copyright (c) 2001-2017 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 */
package net.sf.robocode.ui.dialog;


import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.battle.peer.RadarTypePeer;
import net.sf.robocode.settings.ISettingsManager;
import robocode.naval.RadarType;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
@SuppressWarnings("serial")
public class NewBattleConfigRadarPanel extends JPanel {

	private final JSpinner lrScanRadiusSpinner = new JSpinner();
	private final JSpinner lrTurnSpeedSpinner = new JSpinner();

	private final JSpinner srScanRadiusSpinner = new JSpinner();
	private final JSpinner srTurnSpeedSpinner= new JSpinner();

	private final SpinnerNumberModel scanRadiusModel = new SpinnerNumberModel(1,1,100,1);
	private final SpinnerNumberModel numberModel = new SpinnerNumberModel(1,1,Integer.MAX_VALUE, 1);


	public void setup(ISettingsManager manager) {
		GridBagLayout gbc = new GridBagLayout();
		setLayout(gbc);


		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_START;


		addSRPanel(c);
		addLRPanel(c);

		setValues(manager.getRadarConfigString());
	}


	protected void setValues(String configString){
		String[] parts = configString.split(",");
		for(int i = 0; i < parts.length; i++){
			parts[i] = parts[i].substring(1, parts[i].length() - 1);
		}
		String[] lrValues = parts[0].split("-");
		String[] srValues = parts[1].split("-");


		lrScanRadiusSpinner.setModel(new SpinnerNumberModel(Integer.parseInt(lrValues[0]), 1, 100, 1));
		lrTurnSpeedSpinner.setModel(new SpinnerNumberModel(Integer.parseInt(lrValues[1]), 1, Integer.MAX_VALUE, 1));

		srScanRadiusSpinner.setModel(new SpinnerNumberModel(Integer.parseInt(srValues[0]), 1, 100, 1));
		srTurnSpeedSpinner.setModel(new SpinnerNumberModel(Integer.parseInt(srValues[1]), 1, Integer.MAX_VALUE, 1));
	}
	public String getValueString() {
		return String.format("[%s-%s],[%s-%s]",
				lrScanRadiusSpinner.getValue(),
				lrTurnSpeedSpinner.getValue(),
				srScanRadiusSpinner.getValue(),
				srTurnSpeedSpinner.getValue());
	}


	private void addSRPanel(GridBagConstraints c){
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.weightx = 0.5;
		add(getShortRangePanel(), c);
	}

	private void addLRPanel(GridBagConstraints c){
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.weightx = 0.5;
		add(getLongRangePanel(), c);
	}

	private JPanel getLongRangePanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		Border border = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0),
				BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Long-Range radar"));
		panel.setBorder(border);

		panel.add(new JLabel("Scan Radius (%): "));
		lrScanRadiusSpinner.setModel(scanRadiusModel);
		panel.add(lrScanRadiusSpinner);

		panel.add(new JLabel("Turn Speed: "));
		lrTurnSpeedSpinner.setModel(numberModel);
		panel.add(lrTurnSpeedSpinner);

		return panel;
	}

	private JPanel getShortRangePanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		Border border = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0),
				BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Short-Range radar"));
		panel.setBorder(border);

		panel.add(new JLabel("Scan Radius (%): "));
		srScanRadiusSpinner.setModel(scanRadiusModel);
		panel.add(srScanRadiusSpinner);

		panel.add(new JLabel("Turn Speed: "));
		srTurnSpeedSpinner.setModel(numberModel);
		panel.add(srTurnSpeedSpinner);

		return panel;
	}




}
