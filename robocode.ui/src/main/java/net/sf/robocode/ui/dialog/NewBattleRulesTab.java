/**
 * Copyright (c) 2001-2017 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 */
package net.sf.robocode.ui.dialog;


import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.core.*;
import net.sf.robocode.settings.ISettingsManager;
import net.sf.robocode.ui.IWindowManager;
import net.sf.robocode.ui.WindowManager;
import net.sf.robocode.ui.battleview.BattleField;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
@SuppressWarnings("serial")
public class NewBattleRulesTab extends JPanel {

    private final static int MIN_BATTLEFIELD_SIZE = 400;
    private final static int MAX_BATTLEFIELD_SIZE = 5000;
    private final static int BATTLEFIELD_STEP_SIZE = 100;

    private ISettingsManager settingsManager;
    private BattleProperties battleProperties;

    private final EventHandler eventHandler = new EventHandler();

//	private SizeButton[] predefinedSizeButtons = {
//		new SizeButton(400, 400), new SizeButton(600, 400), new SizeButton(600, 600), new SizeButton(800, 600),
//		new SizeButton(800, 800), new SizeButton(1000, 800), new SizeButton(1000, 1000), new SizeButton(1200, 1200),
//		new SizeButton(2000, 2000), new SizeButton(5000, 5000)
//	};

    private final JLabel numberOfRoundsLabel = new JLabel("Number of Rounds:");
    private final JLabel gunCoolingRateLabel = new JLabel("Gun Cooling Rate:");
    private final JLabel inactivityTimeLabel = new JLabel("Inactivity Time:");
    private final JLabel sentryBorderSizeLabel = new JLabel("Sentry Border Size");
    private final JLabel hideEnemyNamesLabel = new JLabel("Hide Enemy Names:");
    private final JLabel battleFieldWidth = new JLabel("Width:");
    private final JLabel battleFieldHeight = new JLabel("Height:");

    private final JButton restoreDefaultsButton = new JButton("Restore Defaults");
    private final JButton battleFieldSizeButton = new JButton("Set battlefield size");

    private JSpinner numberOfRoundsTextField;
    private JSpinner gunCoolingRateTextField;
    private JSpinner inactivityTimeTextField;
    private JSpinner sentryBorderSizeTextField;
    private final JCheckBox hideEnemyNamesCheckBox = new JCheckBox();
    private JTextField battleFieldWidthField;
    private JTextField battleFieldHeightField;

    private BattleFieldSizeDialog battleFieldSizeDialog;

    private NewBattleConfigValuesPanel shipConfigPanel;

    public NewBattleRulesTab() {
        super();
    }

    public void setup(ISettingsManager settingsManager, BattleProperties battleProperties) {
        this.settingsManager = settingsManager;
        this.battleProperties = battleProperties;

        EventHandler eventHandler = new EventHandler();

        JPanel rulesPanel = createRulesContainerPanel();
        rulesPanel.addAncestorListener(eventHandler);

        restoreDefaultsButton.addActionListener(eventHandler);

        setLayout(new BorderLayout());

        add(rulesPanel, BorderLayout.WEST);
        add(restoreDefaultsButton, BorderLayout.SOUTH);
        add(getShipConfigPanel(), BorderLayout.CENTER);

    }

    public void battleFieldSizeChanged(){
        battleFieldHeightField.setText("" + battleProperties.getBattlefieldHeight());
        battleFieldWidthField.setText("" + battleProperties.getBattlefieldWidth());
    }

    private JPanel getShipConfigPanel() {
        if (shipConfigPanel == null) {
            shipConfigPanel = new NewBattleConfigValuesPanel();
            shipConfigPanel.addAncestorListener(eventHandler);

            shipConfigPanel.setup(settingsManager, battleProperties);
        }
        return shipConfigPanel;
    }

    public String getRadarConfigString(){
        String radarConfigString = shipConfigPanel.getRadarConfigString();

        settingsManager.setRadarConfig(radarConfigString);
        battleProperties.setRadarConfiguration(radarConfigString);
        settingsManager.saveProperties();

        return radarConfigString;
    }

    public String getCannonConfigString(){
        String cannonConfigString = shipConfigPanel.getCannonConfigString();

        settingsManager.setCannonConfig(cannonConfigString);
        battleProperties.setCannonConfiguration(cannonConfigString);
        settingsManager.saveProperties();

        return cannonConfigString;
    }

    public String getShipConfigString(){
        String shipConfigString = shipConfigPanel.getShipConfigString();

        settingsManager.setShipConfig(shipConfigString);
        battleProperties.setShipConfiguration(shipConfigString);
        settingsManager.saveProperties();

        return shipConfigString;
    }

    private JPanel createRulesContainerPanel() {
        JPanel panel = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        gbl.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gbl);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 0, 5, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.FIRST_LINE_START;

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.weightx = 1;
        panel.add(createRulesPanel(), c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.weightx = 1;
        battleFieldSizeButton.addActionListener(eventHandler);
        panel.add(battleFieldSizeButton, c);

        return panel;
    }

    private JPanel createRulesPanel() {
        JPanel panel = new JPanel();

        panel.addAncestorListener(new EventHandler());
        panel.setBorder(BorderFactory.createEtchedBorder());

        GroupLayout layout = new GroupLayout(panel);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        panel.setLayout(layout);

        GroupLayout.SequentialGroup leftToRight = layout.createSequentialGroup();

        GroupLayout.ParallelGroup left = layout.createParallelGroup();
        left.addComponent(numberOfRoundsLabel);
        left.addComponent(gunCoolingRateLabel);
        left.addComponent(inactivityTimeLabel);
        left.addComponent(sentryBorderSizeLabel);
        left.addComponent(hideEnemyNamesLabel);
        left.addComponent(battleFieldWidth);
        left.addComponent(battleFieldHeight);
        leftToRight.addGroup(left);

        GroupLayout.ParallelGroup right = layout.createParallelGroup();
        right.addComponent(getNumberOfRoundsTextField());
        right.addComponent(getGunCoolingRateTextField());
        right.addComponent(getInactivityTimeTextField());
        right.addComponent(getSentryBorderSizeTextField());
        right.addComponent(hideEnemyNamesCheckBox);
        right.addComponent(getBattleFieldWidthField());
        right.addComponent(getBattleFieldHeightField());
        leftToRight.addGroup(right);

        GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();

        GroupLayout.ParallelGroup row0 = layout.createParallelGroup(Alignment.BASELINE);
        row0.addComponent(numberOfRoundsLabel);
        row0.addComponent(numberOfRoundsTextField);
        topToBottom.addGroup(row0);

        GroupLayout.ParallelGroup row1 = layout.createParallelGroup(Alignment.BASELINE);
        row1.addComponent(gunCoolingRateLabel);
        row1.addComponent(getGunCoolingRateTextField());
        topToBottom.addGroup(row1);

        GroupLayout.ParallelGroup row2 = layout.createParallelGroup(Alignment.BASELINE);
        row2.addComponent(inactivityTimeLabel);
        row2.addComponent(inactivityTimeTextField);
        topToBottom.addGroup(row2);

        GroupLayout.ParallelGroup row3 = layout.createParallelGroup(Alignment.BASELINE);
        row3.addComponent(sentryBorderSizeLabel);
        row3.addComponent(sentryBorderSizeTextField);
        topToBottom.addGroup(row3);

        GroupLayout.ParallelGroup row4 = layout.createParallelGroup(Alignment.BASELINE);
        row4.addComponent(hideEnemyNamesLabel);
        row4.addComponent(hideEnemyNamesCheckBox);
        topToBottom.addGroup(row4);

        GroupLayout.ParallelGroup row5 = layout.createParallelGroup(Alignment.BASELINE);
        row5.addComponent(battleFieldWidth);
        row5.addComponent(battleFieldWidthField);
        topToBottom.addGroup(row5);

        GroupLayout.ParallelGroup row6 = layout.createParallelGroup(Alignment.BASELINE);
        row6.addComponent(battleFieldHeight);
        row6.addComponent(battleFieldHeightField);
        topToBottom.addGroup(row6);


        layout.setHorizontalGroup(leftToRight);
        layout.setVerticalGroup(topToBottom);

        return panel;
    }

    private JTextField getBattleFieldHeightField() {
        if (battleFieldHeightField == null) {
            battleFieldHeightField = new JTextField(5);
            battleFieldHeightField.setEditable(false);
            battleFieldHeightField.setText("" + battleProperties.getBattlefieldHeight());
            battleFieldHeightField.setInputVerifier(
                    new InputVerifier() {
                        @Override
                        public boolean verify(JComponent input) {
                            boolean isValid = false;

                            String text = ((JTextField) input).getText();
                            if (text != null && text.matches("\\d+")) {
                                int width = Integer.parseInt(text);
                                isValid = (width > 0 && width <= 5000);
                            }
                            if (!isValid) {
                                WindowUtil.messageError(
                                        "'BattleField Height' must be an integer value > 0 and < 5000\n" + "Default value is 2000.");
                                battleFieldHeightField.setText("" + settingsManager.getBattleFieldHeight());
                            }
                            return isValid;
                        }
                    });
        }
        return battleFieldHeightField;
    }

    private JTextField getBattleFieldWidthField() {
        if (battleFieldWidthField == null) {
            battleFieldWidthField = new JTextField(5);
            battleFieldWidthField.setEditable(false);
            battleFieldWidthField.setText("" + battleProperties.getBattlefieldWidth());
            battleFieldWidthField.setInputVerifier(
                    new InputVerifier() {
                        @Override
                        public boolean verify(JComponent input) {
                            boolean isValid = false;

                            String text = ((JTextField) input).getText();
                            if (text != null && text.matches("\\d+")) {
                                int width = Integer.parseInt(text);
                                isValid = (width > 0 && width <= 5000);
                            }
                            if (!isValid) {
                                WindowUtil.messageError(
                                        "'BattleField Width' must be an integer value > 0 and < 5000\n" + "Default value is 2000.");
                                battleFieldWidthField.setText("" + settingsManager.getBattlefieldWidth());
                            }
                            return isValid;
                        }
                    });
        }
        return battleFieldWidthField;
    }

    private JSpinner getNumberOfRoundsTextField() {
        if (numberOfRoundsTextField == null) {
            numberOfRoundsTextField = new JSpinner();
            numberOfRoundsTextField.setModel(new SpinnerNumberModel(battleProperties.getNumRounds(), 1, Integer.MAX_VALUE, 1));
//            numberOfRoundsTextField.setInputVerifier(
//                    new InputVerifier() {
//                        @Override
//                        public boolean verify(JComponent input) {
//                            boolean isValid = false;
//
//                            String text = ((JTextField) input).getText();
//                            if (text != null && text.matches("\\d+")) {
//                                int numRounds = Integer.parseInt(text);
//                                isValid = (numRounds > 0);
//                            }
//                            if (!isValid) {
//                                WindowUtil.messageError(
//                                        "'Number of Rounds' must be an integer value > 0.\n" + "Default value is 10.");
//                                numberOfRoundsTextField.setValue("" + battleProperties.getNumRounds());
//                            }
//                            return isValid;
//                        }
//                    });
        }
        return numberOfRoundsTextField;
    }

    private JSpinner getGunCoolingRateTextField() {
        if (gunCoolingRateTextField == null) {
            gunCoolingRateTextField = new JSpinner();
            gunCoolingRateTextField.setModel(new SpinnerNumberModel(battleProperties.getGunCoolingRate(), 0.1, 0.7, 0.1));
//            gunCoolingRateTextField.setInputVerifier(
//                    new InputVerifier() {
//                        @Override
//                        public boolean verify(JComponent input) {
//                            boolean isValid = false;
//
//                            String text = ((JTextField) input).getText();
//                            if (text != null && text.matches("\\d*(\\.\\d+)?")) {
//                                double gunCoolingRate = Double.parseDouble(text);
//                                isValid = (gunCoolingRate > 0 && gunCoolingRate <= 0.7);
//                            }
//                            if (!isValid) {
//                                WindowUtil.messageError(
//                                        "'Gun Cooling Rate' must be a floating point number > 0 and <= 0.7.\n"
//                                                + "Default value is 0.1.");
//                                gunCoolingRateTextField.setText("" + battleProperties.getGunCoolingRate());
//                            }
//                            return isValid;
//                        }
//                    });
        }
        return gunCoolingRateTextField;
    }

    private JSpinner getInactivityTimeTextField() {
        if (inactivityTimeTextField == null) {
            inactivityTimeTextField = new JSpinner();
            inactivityTimeTextField.setModel(new SpinnerNumberModel((int) battleProperties.getInactivityTime(), 0, Integer.MAX_VALUE, 1));
//            inactivityTimeTextField.setInputVerifier(
//                    new InputVerifier() {
//                        @Override
//                        public boolean verify(JComponent input) {
//                            boolean isValid = false;
//
//                            String text = ((JTextField) input).getText();
//                            if (text != null && text.matches("\\d+")) {
//                                int inactivityTime = Integer.parseInt(text);
//                                isValid = (inactivityTime >= 0);
//                            }
//                            if (!isValid) {
//                                WindowUtil.messageError(
//                                        "'Inactivity Time' must be an integer value >= 0.\n" + "Default value is 450.");
//                                inactivityTimeTextField.setText("" + battleProperties.getInactivityTime());
//                            }
//                            return isValid;
//                        }
//                    });
        }
        return inactivityTimeTextField;
    }

    private JSpinner getSentryBorderSizeTextField() {
        if (sentryBorderSizeTextField == null) {
            sentryBorderSizeTextField = new JSpinner();
            sentryBorderSizeTextField.setModel(new SpinnerNumberModel(battleProperties.getSentryBorderSize(), 50, Integer.MAX_VALUE, 1));
//            sentryBorderSizeTextField.setInputVerifier(
//                    new InputVerifier() {
//                        @Override
//                        public boolean verify(JComponent input) {
//                            boolean isValid = false;
//
//                            String text = ((JTextField) input).getText();
//                            if (text != null && text.matches("\\d+")) {
//                                int borderSize = Integer.parseInt(text);
//                                isValid = (borderSize >= 50);
//                            }
//                            if (!isValid) {
//                                WindowUtil.messageError(
//                                        "'Sentry Border Size' must be an integer value >= 50.\n" + "Default value is 100.");
//                                sentryBorderSizeTextField.setText("" + battleProperties.getSentryBorderSize());
//                            }
//                            return isValid;
//                        }
//                    });
        }
        return sentryBorderSizeTextField;
    }

    private class EventHandler implements AncestorListener, ActionListener, ChangeListener {
        @Override
        public void ancestorAdded(AncestorEvent event) {
            pushBattlePropertiesToUIComponents();
        }

        @Override
        public void ancestorRemoved(AncestorEvent event) {
            Integer numberOfRounds;
            try {
                numberOfRounds = (Integer) getNumberOfRoundsTextField().getValue();
            } catch (NumberFormatException e) {
                numberOfRounds = null;
            }
            if (numberOfRounds != null) {
                settingsManager.setBattleNumberOfRounds(numberOfRounds);
                battleProperties.setNumRounds(numberOfRounds);
            }
            Double gunCoolingRate;
            try {
                gunCoolingRate = (Double) getGunCoolingRateTextField().getValue();
            } catch (NumberFormatException e) {
                gunCoolingRate = null;
            }
            if (gunCoolingRate != null) {
//                settingsManager.setBattleDefaultGunCoolingRate(gunCoolingRate);
                battleProperties.setGunCoolingRate(gunCoolingRate);
            }
            Integer inactivityTime;
            try {
                inactivityTime = (Integer) getInactivityTimeTextField().getValue();
            } catch (NumberFormatException e) {
                inactivityTime = null;
            }
            if (inactivityTime != null) {
                battleProperties.setInactivityTime(inactivityTime);
            }
            Integer sentryBorderSize;
            try {
                sentryBorderSize = (Integer) getSentryBorderSizeTextField().getValue();
            } catch (NumberFormatException e) {
                sentryBorderSize = null;
            }
            if (sentryBorderSize != null) {
                battleProperties.setSentryBorderSize(sentryBorderSize);
            }
            boolean hideEnemyNames = hideEnemyNamesCheckBox.isSelected();
            settingsManager.setBattleHideEnemyNames(hideEnemyNames);
            battleProperties.setHideEnemyNames(hideEnemyNames);

            int weight = Integer.parseInt(battleFieldWidthField.getText());
            int height = Integer.parseInt(battleFieldHeightField.getText());

            battleProperties.setBattlefieldWidth(weight);
            battleProperties.setBattlefieldHeight(height);

            settingsManager.setBattlefieldWidth(weight);
            settingsManager.setBattlefieldHeight(height);
        }

        @Override
        public void ancestorMoved(AncestorEvent event) {
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == restoreDefaultsButton) {
                battleProperties.setBattlefieldWidth(settingsManager.getDefaultBattlefieldWidth());
                battleProperties.setBattlefieldHeight(settingsManager.getDefaultBattlefieldHeight());
                battleProperties.setNumRounds(settingsManager.getBattleDefaultNumberOfRounds());
                battleProperties.setGunCoolingRate(settingsManager.getBattleDefaultGunCoolingRate());
                battleProperties.setInactivityTime(settingsManager.getBattleDefaultInactivityTime());
                battleProperties.setHideEnemyNames(settingsManager.getBattleDefaultHideEnemyNames());
                battleProperties.setSentryBorderSize(settingsManager.getBattleDefaultSentryBorderSize());

                shipConfigPanel.restoreDefaults();
                pushBattlePropertiesToUIComponents();

            } else if (event.getSource() == battleFieldSizeButton) {
                battleFieldSizeDialog = net.sf.robocode.core.Container.createComponent(BattleFieldSizeDialog.class);
                battleFieldSizeDialog.setup(settingsManager, battleProperties, NewBattleRulesTab.this);
                WindowUtil.packCenterShow(battleFieldSizeDialog);
            }
        }

        @Override
        public void stateChanged(ChangeEvent event) {
        }

        private void pushBattlePropertiesToUIComponents() {

            battleFieldWidthField.setText("" + battleProperties.getBattlefieldWidth());
            battleFieldHeightField.setText("" + battleProperties.getBattlefieldHeight());

            getNumberOfRoundsTextField().setModel(new SpinnerNumberModel(battleProperties.getNumRounds(), 1, Integer.MAX_VALUE, 1));
            getGunCoolingRateTextField().setModel(new SpinnerNumberModel(battleProperties.getGunCoolingRate(), 0.1, 0.7, 0.1));
            getInactivityTimeTextField().setModel(new SpinnerNumberModel((int) battleProperties.getInactivityTime(), 0, Integer.MAX_VALUE, 1));
            getSentryBorderSizeTextField().setModel(new SpinnerNumberModel(battleProperties.getSentryBorderSize(), 50, Integer.MAX_VALUE, 1));
            hideEnemyNamesCheckBox.setSelected(battleProperties.getHideEnemyNames());

        }
    }
}
