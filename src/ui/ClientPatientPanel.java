package src.ui;
import javax.swing.JPanel;

import src.control.AppController;

import javax.swing.JLabel;

/**
 * ClientPatientPanel
 */
public class ClientPatientPanel extends JPanel
{
    private AppController controller;

    public ClientPatientPanel(AppController controller)
    {
        this.controller = controller;
        createUI();
    }

    private void createUI()
    {
        add(new JLabel("Content for Client/Patient Panel"));
    }
}