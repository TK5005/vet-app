package ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import control.AppController;

/**
 * The AppWindow class represents the main window of the application.
 */
public class AppWindow extends JFrame {
    AppController controller;

    public AppWindow() {
        super("Warm & Fuzzy Veterinary Center Enterprise Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void start(AppController controller) {
        this.controller = controller;

        // Add content to the window
        UIPanel uiPanel = new UIPanel(controller);

        // Load the app logo image
        ImageIcon appLogoIcon = new ImageIcon(AppWindow.class.getResource("../appLogo.jpg"));

        // Load the header image
        ImageIcon headerImage = new ImageIcon(AppWindow.class.getResource("../fuzzyLogo.png"));
        JLabel headerLabel = new JLabel(headerImage);

        // Create the header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE); // Set the background color of the header
        headerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        headerPanel.add(headerLabel, BorderLayout.CENTER);

        // Add the header panel to the top of the userManagementPanel
        uiPanel.add(headerPanel, BorderLayout.NORTH);

        this.add(uiPanel);

        this.setIconImage(appLogoIcon.getImage());

        // Display the window
        this.pack();
        this.setSize(1920, 1080);
        this.setVisible(true);
    }
}