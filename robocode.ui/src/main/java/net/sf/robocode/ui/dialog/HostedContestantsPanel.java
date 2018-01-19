/**
 * Copyright (c) 2001-2017 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 */
package net.sf.robocode.ui.dialog;


import net.sf.robocode.core.Container;
import net.sf.robocode.repository.ICompetitionManager;
import net.sf.robocode.repository.IRobotSpecItem;
import net.sf.robocode.security.HiddenAccess;
import net.sf.robocode.ui.IWindowManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class HostedContestantsPanel extends WizardPanel {
//    private final EventHandler eventHandler = new EventHandler();


    private int maxRobots = 1;
    private int minRobots = 1;


    private final ICompetitionManager competitionManager;
    private final List<ContestantRobotsPanel.ItemWrapper> submittedRobots = new ArrayList<ContestantRobotsPanel.ItemWrapper>();

    private WizardController controller;

    //buttons
    private JButton removeButton;

    //panels
    private JPanel mainPanel;
    private ContestantRobotsPanel contenstantRobotsPanel;

    private boolean onlyShowSource;
    private boolean onlyShowWithPackage;
    private boolean onlyShowRobots;
    private boolean onlyShowDevelopment;
    private boolean onlyShowInJar;
    private boolean ignoreTeamRobots;

    public void addSubmittedRobot(ContestantRobotsPanel.ItemWrapper e) {
        submittedRobots.add(e);
    }

    public HostedContestantsPanel(ICompetitionManager competitionManager) {
        super();
        this.competitionManager = competitionManager;
    }

    public void setup(int minRobots, int maxRobots, WizardController controller, boolean onlyShowSource, boolean onlyShowWithPackage,
                      boolean onlyShowRobots, boolean onlyShowDevelopment, boolean onlyShowInJar, boolean ignoreTeamRobots) {
        this.minRobots = minRobots;
        this.maxRobots = maxRobots;
        this.controller = controller;

        this.onlyShowSource = onlyShowSource;
        this.onlyShowWithPackage = onlyShowWithPackage;
        this.onlyShowRobots = onlyShowRobots;
        this.onlyShowDevelopment = onlyShowDevelopment;
        this.onlyShowInJar = onlyShowInJar;
        this.ignoreTeamRobots = ignoreTeamRobots;


        initialize();
    }

    private void initialize() {
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new BorderLayout());
        add(getMainPanel(), BorderLayout.NORTH);
        setVisible(true);
        refreshRobotList(false);
    }


    /**
     * JPanels
     */
    private JPanel getMainPanel() {
        if (mainPanel == null) {
            mainPanel = new JPanel();
            mainPanel.setPreferredSize(new Dimension(550, 300));
            GridBagLayout layout = new GridBagLayout();

            mainPanel.setLayout(layout);

            GridBagConstraints constraints = new GridBagConstraints();

            constraints.fill = GridBagConstraints.BOTH;
            constraints.weightx = 2;
            constraints.weighty = 1;
            constraints.anchor = GridBagConstraints.NORTHWEST;
            constraints.gridwidth = 2;
            constraints.gridheight = 1;
            layout.setConstraints(getSubmittedRobotsPanel(), constraints);
            mainPanel.add(getSubmittedRobotsPanel());

        }
        return mainPanel;
    }

    public ContestantRobotsPanel getSubmittedRobotsPanel() {
        if (contenstantRobotsPanel == null) {
            contenstantRobotsPanel = new ContestantRobotsPanel("Submitted Robots",
                    this);
        }
        return contenstantRobotsPanel;
    }

    public List<IRobotSpecItem> getSubmittedRobots() {
        List<IRobotSpecItem> res = new ArrayList<IRobotSpecItem>();

        for (ContestantRobotsPanel.ItemWrapper item : submittedRobots) {
            res.add(item.getItem());
        }
        return res;
    }

    public String getSubmittedRobotsAsString() {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < submittedRobots.size(); i++) {
            if (i != 0) {
                sb.append(',');
            }
            sb.append(submittedRobots.get(i).getItem().getUniqueFullClassNameWithVersion());
        }
        return sb.toString();
    }

    public void refreshRobotList(final boolean withClear) {

        final Runnable runnable = new Runnable() {
            public void run() {
                final IWindowManager windowManager = Container.getComponent(IWindowManager.class);

                try {
                    windowManager.setBusyPointer(true);
                    competitionManager.refresh(withClear);

                    List<IRobotSpecItem> robotList = competitionManager.getRepositoryItems(onlyShowSource,
                            onlyShowWithPackage, onlyShowRobots, onlyShowDevelopment, false, ignoreTeamRobots, onlyShowInJar, HiddenAccess.getNaval());


                    submittedRobots.clear();


                    getSubmittedRobotsPanel().setContestantList(robotList);


                } finally {
                    windowManager.setBusyPointer(false);
                }
            }
        };

        SwingUtilities.invokeLater(runnable);
    }

    public void setFinishButtonEnabled(boolean should) {

        controller.setFinishButtonEnabled(should);
    }

    @Override
    public boolean isReady() {
        return (getSubmittedRobotsCount() >= minRobots && getSubmittedRobotsCount() <= maxRobots);
    }

    public int getSubmittedRobotsCount() {
        return submittedRobots.size();
    }

    public void removeButtonActionPerformed() {
        if (contenstantRobotsPanel.getAvailableRobotsList().getSelectedIndices().length <= contenstantRobotsPanel.getAvailableRobots().size()) {
            List<String> toDelete = contenstantRobotsPanel.getSelectedRobotsStringArray();

            competitionManager.removeContestants(toDelete);
            submittedRobots.clear();
            refreshRobotList(false);
        }
    }
}
