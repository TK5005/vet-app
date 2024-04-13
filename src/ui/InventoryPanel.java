package ui;

import javax.swing.JLabel;
import javax.swing.JPanel;

import control.AppController;

/**
 * InventoryPanel
 */
public class InventoryPanel extends JPanel {
    private AppController controller;

    public InventoryPanel(AppController controller) {
        this.controller = controller;
        createUI();
    }

    private void createUI() {
        add(new JLabel("Content for Inventory Panel"));
    }
}