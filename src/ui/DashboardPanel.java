package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import control.AppController;

/**
 * DashboardPanel
 */
public class DashboardPanel extends JPanel {
    private AppController controller;
    private final JPanel topPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel bottomJPanel = new JPanel(cardLayout);
    private final List<JButton> navigationButtons = new ArrayList<>();

    public DashboardPanel(AppController controller) {
        this.controller = controller;
        createUI();
    }

    private void createUI() {
        setLayout(new BorderLayout());
        String[] navButtons = { "Active Patients", "In Review", "Medication Order", "Today's Appointment" };
        for (String name : navButtons) {
            if (name == "Active Patients") {
                loadPatients(name);
            }
            if (name == "In Review") {
                loadReview(name);
            }
            if (name == "Medication Order") {
                loadMedication(name);
            }
            if (name == "Today's Appointment") {
                loadAppointments(name);
            }
        }
        add(topPanel, BorderLayout.NORTH);
    }

    private void loadPatients(String name) {
        Object[] columns = { "Patient", "Check In", "Location", "Doctor/Tech", "Reason for Visit" };
        Object[][] returnedData = controller.loadActivePatient();
        JButton loadDataButton = new JButton(name);
        loadDataButton.setContentAreaFilled(false);
        loadDataButton.setBorderPainted(false);
        loadDataButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setTable(returnedData, columns, name);
                navButtonPressed(e);
            }
        });
        topPanel.add(loadDataButton);
        setTable(returnedData, columns, name);
        cardLayout.show(bottomJPanel, "Active Patients");
    }

    private void loadAppointments(String name) {
        Object[] columns = { "Owner Name", "Pet Name", "Phone Numer", "Appointment", "Action" };
        Object[][] returnedData = controller.loadAppointments();
        JButton loadAppButton = new JButton(name);
        navigationButtons.add(loadAppButton);
        loadAppButton.setContentAreaFilled(false);
        loadAppButton.setBorderPainted(false);
        loadAppButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setTable(returnedData, columns, name);
                navButtonPressed(e);
            }
        });
        topPanel.add(loadAppButton);
    }

    private void loadReview(String name) {
        Object[] columns = { "Owner Name", "Pet Name", "Phone Numer", "Appointment", "Action" };
        Object[][] returnedData = controller.loadReview();
        JButton loadDataButton = new JButton(name);
        navigationButtons.add(loadDataButton);
        loadDataButton.setContentAreaFilled(false);
        loadDataButton.setBorderPainted(false);
        loadDataButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setTable(returnedData, columns, name);
                navButtonPressed(e);
            }
        });
        topPanel.add(loadDataButton);
    }

    private void loadMedication(String name) {
        Object[] columns = { "Owner Name", "Pet Name", "Date", "Order Number", "Refill" };
        Object[][] returnedData = controller.loadMedication();
        JButton loadDataButton = new JButton(name);
        navigationButtons.add(loadDataButton);
        loadDataButton.setContentAreaFilled(false);
        loadDataButton.setBorderPainted(false);
        loadDataButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setTable(returnedData, columns, name);
                navButtonPressed(e);
            }
        });
        topPanel.add(loadDataButton);
    }

    private void setTable(Object[][] data, Object[] col, String name) {

        JTable table = new JTable(data, col);
        setCellsAlignment(table, SwingConstants.CENTER);
        Dimension d = table.getPreferredSize();
        table.setPreferredScrollableViewportSize(d);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new Color(173, 216, 230));
        table.setVisible(true);

        JScrollPane panel = new JScrollPane(table);
        panel.getViewport().setBackground(Color.WHITE);
        bottomJPanel.add(panel, name);
        add(bottomJPanel, BorderLayout.CENTER);
        repaint();
        revalidate();
    }

    // center text class
    private void setCellsAlignment(JTable table, int alignment) {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(alignment);

        TableModel tableModel = table.getModel();

        for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
            table.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer);
        }
    }

    private void navButtonPressed(ActionEvent e) {
        JButton pressedButton = (JButton) e.getSource();
        cardLayout.show(bottomJPanel, pressedButton.getText());
    }
}