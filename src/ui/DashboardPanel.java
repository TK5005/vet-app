package src.ui;
import javax.swing.JPanel;

import src.control.AppController;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 * DashboardPanel
 */
public class DashboardPanel extends JPanel
{
    private AppController controller;

    public DashboardPanel(AppController controller)
    {
        this.controller = controller;
        createUI();
    }

    private void createUI()
    {
        add(new JLabel("Content for Dashboard Panel"));
        JLabel dataLabel = new JLabel();
        add(dataLabel);
        JButton loadDataButton = new JButton("Load Test Data");
        loadDataButton.addActionListener(e -> {
            // Call the controller method to load test data
            String returnedData = controller.loadTestData();
            // Update the label with the returned data
            dataLabel.setText(returnedData);
        });
        add(loadDataButton);
    }
}