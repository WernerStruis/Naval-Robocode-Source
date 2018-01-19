/**
 * Copyright (c) 2001-2017 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 */
package net.sf.robocode.ui.dialog;


import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.battle.IBattleManager;
import net.sf.robocode.core.Container;
import net.sf.robocode.repository.ICompetitionManager;
import net.sf.robocode.repository.IRobotSpecItem;
import net.sf.robocode.settings.ISettingsManager;
import net.sf.robocode.ui.IWindowManager;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import static net.sf.robocode.ui.util.ShortcutUtil.MENU_SHORTCUT_KEY_MASK;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
@SuppressWarnings("serial")
public class NewCompetitionDialog extends JDialog implements WizardListener {

	private final static int MAX_ROBOTS = 256; // 64;
	private final static int MIN_ROBOTS = 1;

	private final EventHandler eventHandler = new EventHandler();

	private ISettingsManager settingsManager;
	private BattleProperties battleProperties;

	private JTextField ipLabel;

	private WizardTabbedPane tabbedPane;
	private NewBattleRulesTab battleFieldTab;
//	private NewBattleConfigValuesPanel battleConfigValuesTab;

	private WizardController wizardController;

	private HostedContestantsPanel contestantsPanel;

	private final IBattleManager battleManager;
	private final IWindowManager windowManager;


	public NewCompetitionDialog(IWindowManager windowManager, IBattleManager battleManager) {
		super(windowManager.getRobocodeFrame(), true);
		this.windowManager = windowManager;
		this.battleManager = battleManager;
	}

	public void setup(ISettingsManager settingsManager, BattleProperties battleProperties) { // XXX
		this.settingsManager = settingsManager;
		this.battleProperties = battleProperties;

		contestantsPanel = null;

		setTitle("New Battle");
		setPreferredSize(new Dimension(850, 650));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		addCancelByEscapeKey();

		add(createNewHostedBattleDialogPanel());
	}

	@Override
	public void dispose() {
		windowManager.closeCompetitionServer();
		super.dispose();
	}

	public void cancelButtonActionPerformed() {
		dispose();
	}

	public void finishButtonActionPerformed() {
		if (contestantsPanel.getSubmittedRobotsCount() > 24) {
			if (JOptionPane.showConfirmDialog(this,
					"Warning:  The battle you are about to start (" + contestantsPanel.getSubmittedRobotsCount()
					+ " robots) " + " is very large and will consume a lot of CPU and memory.  Do you wish to proceed?",
					"Large Battle Warning",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.WARNING_MESSAGE)
					== JOptionPane.NO_OPTION) {
				return;
			}
		}
		if (contestantsPanel.getSubmittedRobotsCount() == 1) {
			if (JOptionPane.showConfirmDialog(this,
					"You have only selected one robot.  For normal battles you should select at least 2.\nDo you wish to proceed anyway?",
					"Just one robot?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
					== JOptionPane.NO_OPTION) {
				return;
			}
		}

		battleProperties.setRadarConfiguration(battleFieldTab.getRadarConfigString());
		battleProperties.setCannonConfiguration(battleFieldTab.getCannonConfigString());
		battleProperties.setShipConfiguration(battleFieldTab.getShipConfigString());

		settingsManager.setShipConfig(battleFieldTab.getShipConfigString());
		settingsManager.setRadarConfig(battleFieldTab.getRadarConfigString());
		settingsManager.setCannonConfig(battleFieldTab.getCannonConfigString());
		settingsManager.saveProperties();



		// Dispose this dialog before starting the battle due to pause/resume battle state
		dispose();

		//set right repository manager
		battleManager.setRepository(Container.cache.getComponent(ICompetitionManager.class));
		// Start new battle after the dialog has been disposed and hence has called resumeBattle()
		battleManager.startNewBattle(battleProperties, false, false);
	}

//	private NewBattleConfigValuesPanel getBattleConfigTab() {
//		if (battleConfigValuesTab == null) {
//			battleConfigValuesTab = new NewBattleConfigValuesPanel();
//			battleConfigValuesTab.setup(settingsManager, battleProperties);
//		}
//		return battleConfigValuesTab;
//	}

	private NewBattleRulesTab getBattleFieldTab() {
		if (battleFieldTab == null) {
			battleFieldTab = new NewBattleRulesTab();
			battleFieldTab.setup(settingsManager, battleProperties);
		}
		return battleFieldTab;
	}

	private JPanel createNewHostedBattleDialogPanel() {
		JPanel panel = new JPanel();

		ipLabel = new JTextField();
		ipLabel.setEditable(false);
		ipLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
		ipLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.setLayout(new BorderLayout());
		panel.add(ipLabel, BorderLayout.NORTH);
		panel.add(getWizardController(), BorderLayout.SOUTH);
		panel.add(getTabbedPane(), BorderLayout.CENTER);
		panel.registerKeyboardAction(eventHandler, "Refresh",
				KeyStroke.getKeyStroke(KeyEvent.VK_R, MENU_SHORTCUT_KEY_MASK), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		return panel;
	}

	public List<IRobotSpecItem> getSelectedRobots() {
		return getContestantsPanel().getSubmittedRobots();
	}

	private void addCancelByEscapeKey() {
		String CANCEL_ACTION_KEY = "CANCEL_ACTION_KEY";
		int noModifiers = 0;
		KeyStroke escapeKey = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, noModifiers, false);
		InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

		inputMap.put(escapeKey, CANCEL_ACTION_KEY);
		AbstractAction cancelAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				cancelButtonActionPerformed();
			}
		};

		getRootPane().getActionMap().put(CANCEL_ACTION_KEY, cancelAction);
	}

	/**
	 * Return the wizardController
	 *
	 * @return JButton
	 */
	private WizardController getWizardController() {
		if (wizardController == null) {
			wizardController = getTabbedPane().getWizardController();
			wizardController.setFinishButtonTextAndMnemonic("Start Battle", 'S', 0);
			wizardController.setFocusOnEnabled(true);
		}
		return wizardController;
	}

	/**
	 * Return the Page property value.
	 *
	 * @return JPanel
	 */
	private HostedContestantsPanel getContestantsPanel() {
		if (contestantsPanel == null) {
			contestantsPanel = Container.createComponent(HostedContestantsPanel.class);
			contestantsPanel.addAncestorListener(eventHandler);

			contestantsPanel.setup(MIN_ROBOTS, MAX_ROBOTS, getWizardController(), false, false, false,
					false, false, false);
		}
		return contestantsPanel;
	}

	/**
	 * Return the tabbedPane.
	 *
	 * @return JTabbedPane
	 */
	private WizardTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new WizardTabbedPane(this);
			tabbedPane.insertTab("Contestants", null, getContestantsPanel(), null, 0);
			tabbedPane.setMnemonicAt(0, KeyEvent.VK_0);
			tabbedPane.setDisplayedMnemonicIndexAt(0, 0);
			tabbedPane.insertTab("Rules", null, getBattleFieldTab(), null, 1);
			tabbedPane.setMnemonicAt(1, KeyEvent.VK_1);
			tabbedPane.setDisplayedMnemonicIndexAt(1, 1);
//			tabbedPane.insertTab("Ship Config", null, getBattleConfigTab(), null, 2);
//			tabbedPane.setMnemonicAt(2, KeyEvent.VK_2);
//			tabbedPane.setDisplayedMnemonicIndexAt(2, 2);
		}
		return tabbedPane;
	}

	private class EventHandler implements AncestorListener, ActionListener {
		@Override
		public void ancestorAdded(AncestorEvent event) {}

		@Override
		public void ancestorRemoved(AncestorEvent event) {
			battleProperties.setSelectedRobots(getContestantsPanel().getSubmittedRobotsAsString());
		}

		@Override
		public void ancestorMoved(AncestorEvent event) {}

		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getActionCommand().equals("Refresh")) {
				refreshContestantList();
			}
		}
	}

	public void setAdress(String ip, Integer port){
		ipLabel.setText(String.format("[ %s : %d ]", ip, port));
	}

	public void refreshContestantList(){
		getContestantsPanel().refreshRobotList(true);
	}
}
