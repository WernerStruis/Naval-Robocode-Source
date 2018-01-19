package net.sf.robocode.ui.dialog;

import net.sf.robocode.battle.BattleProperties;
import net.sf.robocode.settings.ISettingsManager;
import net.sf.robocode.ui.IWindowManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class BattleFieldSizeDialog extends JDialog {

    private final EventHandler eventHandler = new EventHandler();
    private NewBattleRulesTab parent;

    private final static int MIN_BATTLEFIELD_SIZE = 400;
    private final static int MAX_BATTLEFIELD_SIZE = 5000;
    private final static int BATTLEFIELD_STEP_SIZE = 100;

    private ISettingsManager settingsManager;
    private BattleProperties battleProperties;

    private SizeButton[] predefinedSizeButtons = {
            new SizeButton(400, 400), new SizeButton(600, 400), new SizeButton(600, 600), new SizeButton(800, 600),
            new SizeButton(800, 800), new SizeButton(1000, 800), new SizeButton(1000, 1000), new SizeButton(1200, 1200),
            new SizeButton(2000, 2000), new SizeButton(5000, 5000)
    };

    private JSlider battlefieldWidthSlider;
    private JSlider battlefieldHeightSlider;
    private JLabel battlefieldSizeLabel;

    private JButton saveButton = new JButton("Save");
    private JButton cancelButton = new JButton("Cancel");

    public BattleFieldSizeDialog(IWindowManager windowManager) {
        super(windowManager.getRobocodeFrame(), true);
    }


    public void setup(ISettingsManager settingsManager, BattleProperties battleProperties, NewBattleRulesTab parent) {
        this.settingsManager = settingsManager;
        this.battleProperties = battleProperties;
        this.parent = parent;

        EventHandler eventHandler = new EventHandler();

        setTitle("Set Battlefield Size");
        setPreferredSize(new Dimension(2000, 650));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        battlefieldWidthSlider = createBattlefieldSizeSlider();
        battlefieldWidthSlider.setOrientation(SwingConstants.HORIZONTAL);

        battlefieldHeightSlider = createBattlefieldSizeSlider();
        battlefieldHeightSlider.setOrientation(SwingConstants.VERTICAL);
        battlefieldHeightSlider.setInverted(true);

        battlefieldSizeLabel = new BattlefieldSizeLabel();
        battlefieldSizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        battlefieldSizeLabel.setMinimumSize(new Dimension(50, 50));
        battlefieldSizeLabel.setMaximumSize(new Dimension(500, 500));

        battlefieldWidthSlider.addChangeListener(eventHandler);
        battlefieldHeightSlider.addChangeListener(eventHandler);

        add(createBattlefieldSizePanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createButtonPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        cancelButton.addActionListener(eventHandler);
        saveButton.addActionListener(eventHandler);

        panel.add(cancelButton);
        panel.add(saveButton);

        return panel;
    }

    private JPanel createBattlefieldSizePanel() {
        JPanel panel = new JPanel();
        panel.addAncestorListener(eventHandler);

        Border border = BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Battlefield Size"),
                BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBorder(border);

        panel.setLayout(new BorderLayout());

        JPanel sliderPanel = createBattlefieldSlidersPanel();
        panel.add(sliderPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = createPredefinedSizesPanel();
        panel.add(buttonsPanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createBattlefieldSlidersPanel() {
        JPanel panel = new JPanel();

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        GroupLayout.SequentialGroup leftToRight = layout.createSequentialGroup();

        GroupLayout.ParallelGroup left = layout.createParallelGroup();
        left.addComponent(battlefieldSizeLabel);
        left.addComponent(battlefieldWidthSlider);
        leftToRight.addGroup(left);

        GroupLayout.ParallelGroup right = layout.createParallelGroup();
        right.addComponent(battlefieldHeightSlider);
        leftToRight.addGroup(right);

        GroupLayout.SequentialGroup topToBottom = layout.createSequentialGroup();

        GroupLayout.ParallelGroup top = layout.createParallelGroup();
        top.addComponent(battlefieldSizeLabel);
        top.addComponent(battlefieldHeightSlider);
        topToBottom.addGroup(top);

        GroupLayout.ParallelGroup bottom = layout.createParallelGroup();
        bottom.addComponent(battlefieldWidthSlider);
        topToBottom.addGroup(bottom);

        layout.setHorizontalGroup(leftToRight);
        layout.setVerticalGroup(topToBottom);

        return panel;
    }

    private JPanel createPredefinedSizesPanel() {
        JPanel panel = new JPanel();


        Border border = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0),
                BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Predefined Sizes"));
        panel.setBorder(border);

        panel.setLayout(new GridLayout(predefinedSizeButtons.length, 1));

        for (SizeButton button : predefinedSizeButtons) {
            panel.add(button);
        }
        return panel;
    }

    private JSlider createBattlefieldSizeSlider() {
        JSlider slider = new JSlider();
        slider.setMinimum(MIN_BATTLEFIELD_SIZE);
        slider.setMaximum(MAX_BATTLEFIELD_SIZE);
        slider.setMajorTickSpacing(BATTLEFIELD_STEP_SIZE);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        return slider;
    }

    private void updateBattlefieldSizeLabel() {
        int w = battlefieldWidthSlider.getValue();
        int h = battlefieldHeightSlider.getValue();
        battlefieldSizeLabel.setText(w + " x " + h);
    }

    private class SizeButton extends JButton {
        final int width;
        final int height;

        public SizeButton(int width, int height) {
            super(width + "x" + height);
            this.width = width;
            this.height = height;
            addActionListener(eventHandler);
        }
    }


    private class EventHandler implements AncestorListener, ActionListener, ChangeListener {
        @Override
        public void ancestorAdded(AncestorEvent event) {
            pushBattlePropertiesToUIComponents();
        }

        @Override
        public void ancestorRemoved(AncestorEvent event) {
//            int weight = battlefieldWidthSlider.getValue();
//            int height = battlefieldHeightSlider.getValue();
//
//            battleProperties.setBattlefieldWidth(weight);
//            battleProperties.setBattlefieldHeight(height);
//
//            parent.battleFieldSizeChanged();
        }

        @Override
        public void ancestorMoved(AncestorEvent event) {}

        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() instanceof SizeButton) {
                SizeButton button = (SizeButton) event.getSource();
                battlefieldWidthSlider.setValue(button.width);
                battlefieldHeightSlider.setValue(button.height);
                updateBattlefieldSizeLabel();
            }else if(event.getSource() == saveButton){
                int weight = battlefieldWidthSlider.getValue();
                int height = battlefieldHeightSlider.getValue();

                battleProperties.setBattlefieldWidth(weight);
                battleProperties.setBattlefieldHeight(height);

                settingsManager.setBattlefieldWidth(weight);
                settingsManager.setBattlefieldHeight(height);
                settingsManager.saveProperties();

                parent.battleFieldSizeChanged();

                dispose();
            }else if(event.getSource() == cancelButton){
                dispose();
            }
        }

        @Override
        public void stateChanged(ChangeEvent event) {
            if ((event.getSource() == battlefieldWidthSlider) || (event.getSource() == battlefieldHeightSlider)) {
                updateBattlefieldSizeLabel();
            }
        }

        private void pushBattlePropertiesToUIComponents() {
            battlefieldWidthSlider.setValue(battleProperties.getBattlefieldWidth());
            battlefieldHeightSlider.setValue(battleProperties.getBattlefieldHeight());
            updateBattlefieldSizeLabel();
        }
    }

    private class BattlefieldSizeLabel extends JLabel {
        @Override
        public void paintComponent(Graphics g) {
            g.setColor(SystemColor.activeCaption);

            int width = getWidth() * battlefieldWidthSlider.getValue() / MAX_BATTLEFIELD_SIZE;
            int height = getHeight() * battlefieldHeightSlider.getValue() / MAX_BATTLEFIELD_SIZE;

            g.fillRect(0, 0, width, height);
            super.paintComponent(g);
        }
    }
}
