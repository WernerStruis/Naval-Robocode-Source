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
import net.sf.robocode.repository.IRepositoryManager;
import net.sf.robocode.repository.IRobotSpecItem;
import net.sf.robocode.security.HiddenAccess;
import net.sf.robocode.settings.ISettingsManager;
import net.sf.robocode.ui.IWindowManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import static net.sf.robocode.ui.util.ShortcutUtil.MENU_SHORTCUT_KEY_MASK;


/**
 * @author Mathew A. Nelson (original)
 * @author Matthew Reeder (contributor)
 * @author Flemming N. Larsen (contributor)
 * @author Robert D. Maupin (contributor)
 */
@SuppressWarnings("serial")
public class TransferRobotDialog extends JDialog{


	private final EventHandler eventHandler = new EventHandler();

	private ISettingsManager settingsManager;
	private IWindowManager windowManager;
	private IRepositoryManager repositoryManager;

	private AvailableRobotsPanel availableRobotsPanel;
	private SelectedPanel selectedPanel;


	private JTextField ipField;
	private JTextField portField;

	private JButton sendButton;
	private JButton cancelButton;


	public TransferRobotDialog(IWindowManager windowManager, IRepositoryManager repositoryManager) {
		super(windowManager.getRobocodeFrame(), true);
		this.windowManager = windowManager;
		this.repositoryManager = repositoryManager;
	}

	public void setup(ISettingsManager settingsManager){
		this.settingsManager = settingsManager;
		availableRobotsPanel = null;

		setLayout(new BorderLayout());

		setTitle("Export robot to another application");
		setPreferredSize(new Dimension(850, 650));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		addCancelByEscapeKey();

		add(getAvailableRobotsPanel(), BorderLayout.CENTER);
		add(getRobotInfoPanel(), BorderLayout.SOUTH);
	}

	public AvailableRobotsPanel getAvailableRobotsPanel(){
		if (availableRobotsPanel == null) {
			availableRobotsPanel = new AvailableRobotsPanel("available robots", this);
			List<IRobotSpecItem> robotList = repositoryManager.getRepositoryItems(false,
					false, false, false, false, false, false, HiddenAccess.getNaval());

			getAvailableRobotsPanel().setRobotList(robotList);
			availableRobotsPanel.add(getSelectedPanel(), BorderLayout.SOUTH);
		}
		return availableRobotsPanel;
	}

	private SelectedPanel getSelectedPanel() {
		if(selectedPanel ==  null) {
			selectedPanel = new SelectedPanel();
			selectedPanel.setup();
		}
		return selectedPanel;
	}

	public JPanel getRobotInfoPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		panel.add(getRobotDestinationPanel(), BorderLayout.NORTH);
		panel.add(getButtonPanel(), BorderLayout.SOUTH);

		return panel;
	}

	public JPanel getRobotDestinationPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		Border border = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0),
				BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Publish to"));
		panel.setBorder(border);

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_START;

		c.gridy = 0;
		addIPPanel(panel, c);

		addPortPanel(panel, c);
		return panel;
	}

	public void addIPPanel(JPanel panel, GridBagConstraints c){
		c.gridx = 0;
		c.weightx = 1;
		panel.add(new JLabel("IP:", SwingConstants.CENTER), c);

		c.gridx = 1;
		c.weightx = 15;
		ipField = new JTextField();
		panel.add(ipField, c);

	}

	public void addPortPanel(JPanel panel, GridBagConstraints c){
		c.gridx = 2;
		c.weightx = 1;
		panel.add(new JLabel("Port:", SwingConstants.CENTER), c);

		c.gridx = 3;
		c.weightx = 5;
		portField = new JTextField();
		panel.add(portField, c);
	}

	private JPanel getButtonPanel(){
		JPanel panel = new JPanel();
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(eventHandler);
		sendButton = new JButton("Send");
		sendButton.addActionListener(eventHandler);
		panel.add(cancelButton);
		panel.add(sendButton);

		return panel;
	}

	public class SelectedPanel extends JPanel{
		private JLabel robotNameLabel;
		private JLabel robotPathLabel;
		private IRobotSpecItem currentRobotSpecification;

		public SelectedPanel(){
			robotNameLabel = new JLabel();
			robotPathLabel = new JLabel();
		}
		public void setup(){
			setLayout(new GridLayout(2,0));
			Border border = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0),
					BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Selected robot:"));
			setBorder(border);

			add(robotNameLabel);
			add(robotPathLabel);
		}

		public IRobotSpecItem getSelectedRobot(){
			return currentRobotSpecification;
		}

		public void showDescription(IRobotSpecItem robotSpecification){
			currentRobotSpecification = robotSpecification;
			robotNameLabel.setText(currentRobotSpecification.getFullClassName());
			robotPathLabel.setText(currentRobotSpecification.getItemURL().getFile());
		}
	}

	public boolean isValidIP(String ip){
		return ip.matches("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");
	}

	public boolean isValidPort(String port){
		return port.matches("^([0-9]{1,4}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$");
	}



	private class EventHandler implements AncestorListener, ActionListener {
		@Override
		public void ancestorAdded(AncestorEvent event) {}

		@Override
		public void ancestorRemoved(AncestorEvent event) {
		}

		@Override
		public void ancestorMoved(AncestorEvent event) {}

		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == sendButton){
				boolean isValidIP = isValidIP(ipField.getText());
				boolean isValidPort = isValidPort(portField.getText());
				if(!isValidIP || !isValidPort){
					JOptionPane.showMessageDialog(getOwner(), String.format("Invalid destination: \n IP: %s  [%b]\n Port: %s [%b]", ipField.getText(), isValidIP, portField.getText(), isValidPort), "Destination error", JOptionPane.ERROR_MESSAGE);
				}else {
					System.out.printf("Sending robot [%s] to [%s:%s]\n", getSelectedPanel().getSelectedRobot().getFullClassName(), ipField.getText(), portField.getText());
					windowManager.sendRobot(ipField.getText(), Integer.parseInt(portField.getText()), getSelectedPanel().getSelectedRobot());
					dispose();
				}
			}else if(event.getSource() == cancelButton){
				cancelButtonActionPerformed();
			}
		}
	}

	public void showDescription(IRobotSpecItem robotSpecification) {
		getSelectedPanel().showDescription(robotSpecification);
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

	public void cancelButtonActionPerformed() {
		dispose();
	}

}
