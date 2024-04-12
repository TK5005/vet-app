package ui;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import control.AppController;
import view.clientPatient.ClientPageView;
import view.invoice.InvoiceView;

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
        ImageIcon menu = new ImageIcon(UIPanel.class.getResource("../menu.png"));
        ImageIcon armup = new ImageIcon(UIPanel.class.getResource("../armup.png"));
        ImageIcon appointment = new ImageIcon(UIPanel.class.getResource("../appointment.png"));
        ImageIcon invoice = new ImageIcon(UIPanel.class.getResource("../invoice.png"));
        ImageIcon inventory = new ImageIcon(UIPanel.class.getResource("../inventory.png"));
        ImageIcon admin = new ImageIcon(UIPanel.class.getResource("../lock.png"));

        for (String name : navButtons)
        {
            JButton button = null;
            if(name=="Dashboard"){
                button = new JButton(menu);
                loadImage(name, button, navigationPanel);
            }
            if(name=="Clients/Patients"){
                button = new JButton(armup);
                loadImage(name, button, navigationPanel);
            }
            if(name=="Appointments"){
                button = new JButton(appointment);
                loadImage(name, button, navigationPanel);
            }
            if(name=="Invoices"){
                button = new JButton(invoice);
                loadImage(name, button, navigationPanel);
            }
            if(name=="Inventory"){
                button = new JButton(inventory);
                loadImage(name, button, navigationPanel);
            }
            if(name=="Administration"){
                button = new JButton(admin);
                loadImage(name, button, navigationPanel);
            }
            
        }

        // Create and add application panels
        DashboardPanel dashboardPanel = new DashboardPanel(controller);
        dashboardPanel.setBackground(Color.WHITE);
        cards.add(dashboardPanel, "Dashboard");

        ClientPageView clientsView = new ClientPageView();
        clientsView.setBackground(Color.WHITE);
        cards.add(clientsView, "Clients/Patients");

        AppointmentPanel appointmentPanel = new AppointmentPanel(controller);
        appointmentPanel.setBackground(Color.WHITE);
        cards.add(appointmentPanel, "Appointments");

        InvoiceView invoiceView = new InvoiceView();
        invoiceView.setBackground(Color.WHITE);
        cards.add(invoiceView, "Invoices");

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

    private void loadImage(String name, JButton button,JPanel navigationPanel){
        button.setText(name);
        button.setContentAreaFilled(false);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getMinimumSize().height));
        button.addActionListener(this::navButtonPressed);
        navigationButtons.add(button);
        navigationPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        navigationPanel.add(button);
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