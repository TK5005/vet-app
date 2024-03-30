package src.ui;
import javax.swing.JPanel;

import src.control.AppController;

import javax.swing.JLabel;

/**
 * InvoicePanel
 */
public class InvoicePanel extends JPanel
{
    private AppController controller;

    public InvoicePanel(AppController controller)
    {
        this.controller = controller;
        createUI();
    }

    private void createUI()
    {
        add(new JLabel("Content for Invoice Panel"));
    }
}