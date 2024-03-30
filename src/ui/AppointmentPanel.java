package src.ui;
import javax.swing.JPanel;

import src.control.AppController;

import javax.swing.JLabel;

/**
 * AppointmentPanel
 */
public class AppointmentPanel extends JPanel
{
    private AppController controller;

    public AppointmentPanel(AppController controller)
    {
        this.controller = controller;
        createUI();
    }

    private void createUI()
    {
        add(new JLabel("Content for Appointment Panel"));
    }
}