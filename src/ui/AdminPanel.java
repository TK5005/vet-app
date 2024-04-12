package ui;

import javax.swing.JPanel;

import control.AppController;

import javax.swing.JLabel;

/**
 * AdminPanel
 */
public class AdminPanel extends JPanel
{
    private AppController controller;

    public AdminPanel(AppController controller)
    {
        this.controller = controller;
        createUI();
    }

    private void createUI()
    {
        add(new JLabel("Content for Admin Panel"));
    }
}