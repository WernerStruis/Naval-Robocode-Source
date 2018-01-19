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
import net.sf.robocode.settings.SettingsManager;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
@SuppressWarnings("serial")
public class NewBattleConfigShipPanel extends JPanel {

	private final JSpinner crMaxVelocity = new JSpinner();
	private final JSpinner crMaxTurnSpeed = new JSpinner();
	private final JSpinner crEnergy = new JSpinner();

	private final JSpinner coMaxVelocity = new JSpinner();
	private final JSpinner coMaxTurnSpeed = new JSpinner();
	private final JSpinner coEnergy = new JSpinner();

	private final JSpinner caMaxVelocity = new JSpinner();
	private final JSpinner caMaxTurnSpeed = new JSpinner();
	private final JSpinner caEnergy = new JSpinner();


	public void setup(ISettingsManager manager) {
		GridBagLayout gbc = new GridBagLayout();
		setLayout(gbc);


		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_START;

		addCRPanel(c);
		addCOPanel(c);
		addCAPanel(c);

		setValues(manager.getShipConfigString());
	}


	protected void setValues(String configString){
		String[] parts = configString.split(",");
		for(int i = 0; i < parts.length; i++){
			parts[i] = parts[i].substring(1, parts[i].length() - 1);
		}
		String[] crValues = parts[0].split("-");
		String[] coValues = parts[1].split("-");
		String[] caValues = parts[2].split("-");

		crMaxVelocity.setModel(new SpinnerNumberModel(Double.parseDouble(crValues[0]), 1, Integer.MAX_VALUE, 0.1));
		crMaxTurnSpeed.setModel(new SpinnerNumberModel(Double.parseDouble(crValues[1]), 1, Integer.MAX_VALUE, 0.1));
		crEnergy.setModel(new SpinnerNumberModel(Double.parseDouble(crValues[2]), 1, Integer.MAX_VALUE, 0.1));

		coMaxVelocity.setModel(new SpinnerNumberModel(Double.parseDouble(coValues[0]), 1, Integer.MAX_VALUE, 0.1));
		coMaxTurnSpeed.setModel(new SpinnerNumberModel(Double.parseDouble(coValues[1]), 1, Integer.MAX_VALUE, 0.1));
		coEnergy.setModel(new SpinnerNumberModel(Double.parseDouble(coValues[2]), 1, Integer.MAX_VALUE, 0.1));

		caMaxVelocity.setModel(new SpinnerNumberModel(Double.parseDouble(caValues[0]), 1, Integer.MAX_VALUE, 0.1));
		caMaxTurnSpeed.setModel(new SpinnerNumberModel(Double.parseDouble(caValues[1]), 1, Integer.MAX_VALUE, 0.1));
		caEnergy.setModel(new SpinnerNumberModel(Double.parseDouble(caValues[2]), 1, Integer.MAX_VALUE, 0.1));
	}
	public String getValueString() {
		return String.format("[%s-%s-%s],[%s-%s-%s],[%s-%s-%s]",
				crMaxVelocity.getValue(),
				crMaxTurnSpeed.getValue(),
				crEnergy.getValue(),
				coMaxVelocity.getValue(),
				coMaxTurnSpeed.getValue(),
				coEnergy.getValue(),
				caMaxVelocity.getValue(),
				caMaxTurnSpeed.getValue(),
				caEnergy.getValue());
	}

	private void addCRPanel(GridBagConstraints c){
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.weightx = 0.5;
		add(getCruiserPanel(), c);
	}

	private void addCOPanel(GridBagConstraints c){
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		c.weightx = 0.5;
		add(getCorvettePanel(), c);
	}

	private void addCAPanel(GridBagConstraints c){
		c.gridx = 2;
		c.gridy = 0;
		c.gridwidth = 1;
		c.weightx = 0.5;
		add(getCarrierPanel(), c);
	}

	private JPanel getCruiserPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		Border border = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0),
				BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Cruiser"));
		panel.setBorder(border);

		panel.add(new JLabel("Max Velocity: "));
		panel.add(crMaxVelocity);
		panel.add(new JLabel("Max TurnRate: "));
		panel.add(crMaxTurnSpeed);
		panel.add(new JLabel("Energy: "));
		panel.add(crEnergy);

		return panel;
	}

	private JPanel getCorvettePanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		Border border = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0),
				BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Corvette"));
		panel.setBorder(border);

		panel.add(new JLabel("Max Velocity: "));
		panel.add(coMaxVelocity);
		panel.add(new JLabel("Max TurnRate: "));
		panel.add(coMaxTurnSpeed);
		panel.add(new JLabel("Energy: "));
		panel.add(coEnergy);

		return panel;
	}

	private JPanel getCarrierPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		Border border = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0),
				BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Carrier"));
		panel.setBorder(border);

		panel.add(new JLabel("Max Velocity: "));
		panel.add(caMaxVelocity);
		panel.add(new JLabel("Max TurnRate: "));
		panel.add(caMaxTurnSpeed);
		panel.add(new JLabel("Energy: "));
		panel.add(caEnergy);

		return panel;
	}
}
