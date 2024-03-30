package src.ui;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import src.control.AppController;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.Component;
import java.awt.Dimension;

/**
 * The main panel of the application that contains the navigation panel and the content panels.
 */
public class UIPanel extends JPanel
{
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cards = new JPanel(cardLayout);
    private final List<JButton> navigationButtons = new ArrayList<>();
    private final Color selectedColor = new Color(173, 216, 230); // A light blue color for selection
    private final Color defaultColor = UIManager.getColor("Button.background"); // Default color for buttons
    private AppController controller;

    public UIPanel(AppController controller)
    {
        this.controller = controller;
        createUI();
    }

    private void createUI()
    {
        setLayout(new BorderLayout());

        // Navigation panel
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.Y_AXIS));
        navigationPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        navigationPanel.setBackground(Color.WHITE); // Set the background color of the navigation panel

        // Navigation buttons
        String[] navButtons = {"Dashboard", "Clients/Patients", "Appointments", "Invoices", "Inventory", "Administration"};

        for (String name : navButtons)
        {
            JButton button = new JButton(name);
            button.setAlignmentX(Component.LEFT_ALIGNMENT);
            button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getMinimumSize().height));
            button.addActionListener(this::navButtonPressed);
            navigationButtons.add(button);
            navigationPanel.add(button);
        }

        // Create and add application panels
        DashboardPanel dashboardPanel = new DashboardPanel(controller);
        dashboardPanel.setBackground(Color.WHITE);
        cards.add(dashboardPanel, "Dashboard");

        ClientPatientPanel clientPatientPanel = new ClientPatientPanel(controller);
        clientPatientPanel.setBackground(Color.WHITE);
        cards.add(clientPatientPanel, "Clients/Patients");

        AppointmentPanel appointmentPanel = new AppointmentPanel(controller);
        appointmentPanel.setBackground(Color.WHITE);
        cards.add(appointmentPanel, "Appointments");

        InvoicePanel invoicePanel = new InvoicePanel(controller);
        invoicePanel.setBackground(Color.WHITE);
        cards.add(invoicePanel, "Invoices");

        InventoryPanel inventoryPanel = new InventoryPanel(controller);
        inventoryPanel.setBackground(Color.WHITE);
        cards.add(inventoryPanel, "Inventory");
        
        AdminPanel adminPanel = new AdminPanel(controller);
        adminPanel.setBackground(Color.WHITE);
        cards.add(adminPanel, "Administration");


        // Adding components to the main panel
        add(navigationPanel, BorderLayout.WEST);
        add(cards, BorderLayout.CENTER);
    }

    private void navButtonPressed(ActionEvent e)
    {
        JButton pressedButton = (JButton) e.getSource();
        cardLayout.show(cards, pressedButton.getText());
        updateButtonSelection(pressedButton);
    }

    private void updateButtonSelection(JButton selectedButton)
    {
        for (JButton button : navigationButtons)
        {
            if (button == selectedButton)
            {
                button.setBackground(selectedColor);
            }
            else
            {
                button.setBackground(defaultColor);
            }
        }
    }
}