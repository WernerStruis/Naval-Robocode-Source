/**
 * Copyright (c) 2001-2017 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 */
package net.sf.robocode.ui.dialog;


import net.sf.robocode.repository.IRobotSpecItem;
import net.sf.robocode.ui.util.ShortcutUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings("serial")
public class ContestantRobotsPanel extends JPanel {

    private final List<ItemWrapper> availableRobots = new CopyOnWriteArrayList<ItemWrapper>();
    private List<IRobotSpecItem> robotList = new CopyOnWriteArrayList<IRobotSpecItem>();

    private JScrollPane availableRobotsScrollPane;
    private JList availableRobotsList;

    private JButton removeButton;

    private RobotNameCellRenderer robotNameCellRenderer;

    private final HostedContestantsPanel contestantsPanel;

    private final String title;

    public ContestantRobotsPanel(String title, HostedContestantsPanel contestantsPanel) {
        super();
        this.title = title;

        this.contestantsPanel = contestantsPanel;
        initialize();
    }

    private void initialize() {
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title));
        setLayout(new BorderLayout());

        JPanel top = new JPanel();

        top.setLayout(new GridLayout(1, 1));


        JPanel b = new JPanel();

        b.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Contestants"));
        b.setLayout(new BorderLayout());
        b.add(getAvailableRobotsScrollPane());
        b.setPreferredSize(new Dimension(120, 100));
        top.add(b);


        add(top, BorderLayout.CENTER);


        JPanel panel = new JPanel(new GridLayout(2, 1));
        removeButton = new JButton("Remove from game");
        removeButton.setEnabled(false);
        removeButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contestantsPanel.removeButtonActionPerformed();
            }
        });
        panel.add(removeButton);
        JLabel refreshLabel = new JLabel("Press " + ShortcutUtil.getModifierKeyText() + "+R to refresh");
        refreshLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(refreshLabel);

        add(panel, BorderLayout.SOUTH);
    }


    public List<ItemWrapper> getAvailableRobots() {
        return availableRobots;
    }

    //only for use by deleteContestants
    public List<String> getSelectedRobotsStringArray() {
        List<String> selected = new ArrayList<String>();

        for (int i : getAvailableRobotsList().getSelectedIndices()) {
            selected.add(availableRobots.get(i).getItem().getShortClassName());
        }
        return selected;
    }

    public List<ItemWrapper> getSelectedRobots() {
        List<ItemWrapper> selected = new ArrayList<ItemWrapper>();

        for (int i : getAvailableRobotsList().getSelectedIndices()) {
            selected.add(availableRobots.get(i));
        }
        return selected;
    }

    /**
     * Return the availableRobotsList.
     *
     * @return JList
     */
    public JList getAvailableRobotsList() {
        if (availableRobotsList == null) {
            availableRobotsList = new JList();
			availableRobotsList.setModel(new AvailableRobotsModel());
            availableRobotsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            availableRobotsList.setCellRenderer(getRobotNameCellRenderer());

            MouseListener mouseListener = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    removeButton.setEnabled(availableRobotsList.getSelectedIndices().length != 0);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    removeButton.setEnabled(availableRobotsList.getSelectedIndices().length != 0);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    removeButton.setEnabled(availableRobotsList.getSelectedIndices().length != 0);
                }
            };

            availableRobotsList.addMouseListener(mouseListener);
        }

        return availableRobotsList;
    }

    /**
     * Return the JScrollPane1 property value.
     *
     * @return JScrollPane
     */
    private JScrollPane getAvailableRobotsScrollPane() {
        if (availableRobotsScrollPane == null) {
            availableRobotsScrollPane = new JScrollPane();
            availableRobotsScrollPane.setViewportView(getAvailableRobotsList());

            // Bug fix [2975871] - Minor visual bug - Currently selected robot gets covered
            availableRobotsScrollPane.addComponentListener(new ComponentAdapter() {
                public void componentResized(ComponentEvent e) {
                    getAvailableRobotsList().ensureIndexIsVisible(getAvailableRobotsList().getSelectedIndex());
                }
            });
        }
        return availableRobotsScrollPane;
    }

    private RobotNameCellRenderer getRobotNameCellRenderer() {
        if (robotNameCellRenderer == null) {
            robotNameCellRenderer = new RobotNameCellRenderer();
        }
        return robotNameCellRenderer;
    }

    public void setContestantList(List<IRobotSpecItem> robotListList) {

        robotList = robotListList;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                availableRobots.clear();

                if (robotList == null) {
                    robotList = new CopyOnWriteArrayList<IRobotSpecItem>();
					((AvailableRobotsModel) getAvailableRobotsList().getModel()).changed();
                } else {
                    for (IRobotSpecItem robotSpec : robotList) {
                        if (!availableRobots.contains(new ItemWrapper(robotSpec))) {
                            availableRobots.add(new ItemWrapper(robotSpec));
                            contestantsPanel.addSubmittedRobot(new ItemWrapper(robotSpec));
                        }
                    }
					((AvailableRobotsModel) getAvailableRobotsList().getModel()).changed();
                }
                contestantsPanel.setFinishButtonEnabled(contestantsPanel.getSubmittedRobots().size() > 0);
            }
        });
    }


	private class AvailableRobotsModel extends AbstractListModel {
		public void changed() {
			fireContentsChanged(this, 0, getSize());
		}

		public int getSize() {
			return availableRobots.size();
		}

		public ItemWrapper getElementAt(int which) {
			return availableRobots.get(which);
		}
	}


    /**
     * Is there because of keyboard navigation is tied to toString() method
     */
    public static class ItemWrapper {
        private final IRobotSpecItem item;

        public ItemWrapper(IRobotSpecItem item) {
            this.item = item;
        }

        public IRobotSpecItem getItem() {
            return item;
        }

        // Used writing the robot name in JList. Is used for keyboard typing in JList to find robot. Bugfix for [2658090]
        public String toString() {
            return item.getUniqueShortClassNameWithVersion();
        }
    }


    /**
     * RobotNameCellRender, which renders the list cells with "Team: " prefix, and with or without package names.
     * This is cheating, as the ItemWrapper.toString() delivers the string used for keyboard navigation etc.
     */
    private static class RobotNameCellRenderer extends JLabel implements ListCellRenderer {
        private boolean useShortName = false;

        public RobotNameCellRenderer() {
            setOpaque(true);
        }

        public void setUseShortName(boolean useShortNames) {
            this.useShortName = useShortNames;
        }

        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

            setComponentOrientation(list.getComponentOrientation());

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            String text;

            if (value instanceof ItemWrapper) {
                IRobotSpecItem item = ((ItemWrapper) value).getItem();

                text = (item.isTeam() ? "Team: " : "");
                text += useShortName
                        ? item.getUniqueShortClassNameWithVersion()
                        : item.getUniqueFullClassNameWithVersion();
            } else {
                text = value.toString();
            }
            setText(text);

            setEnabled(list.isEnabled());
            setFont(list.getFont());

            return this;
        }
    }
}
