package view;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;

import model.Appointment;

import control.IClientView;
import control.ClientController;



public class PetAppointmentsTable extends JPanel implements IClientView
{
    private ClientController clientController;
    private DefaultTableModel tableModel;

    public PetAppointmentsTable()
    {
        clientController = ClientController.getInstance();
        clientController.registerView(this);
        createUI();
    }

    public void createUI()
    {
        JPanel appointmentTable = createAppointmentTable();
        this.add(appointmentTable, BorderLayout.CENTER);
    }

    private JPanel createAppointmentTable()
    {
        JPanel appointmentPanel = new JPanel();
        appointmentPanel.setLayout(new BorderLayout());

        JLabel appointmentLabel = new JLabel("Appointments");

        // Create the table with two columns
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make the action cell editable
                return column == 1;
            }
        };

        tableModel.addColumn("Date");
        tableModel.addColumn("Description");

        JTable appointmentTable = new JTable(tableModel);
        appointmentTable.setRowHeight(50);
        appointmentTable.getTableHeader().setOpaque(false);
        appointmentTable.getTableHeader().setBackground(new Color(173, 216, 230));

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(appointmentTable);

        appointmentPanel.add(appointmentLabel, BorderLayout.NORTH);
        appointmentPanel.add(scrollPane, BorderLayout.CENTER);

        return appointmentPanel;
    }

    public void refresh()
    {
        // Clear the table
        tableModel.setRowCount(0);

        // Get the pets data from ClientController
        Appointment[] appointments = clientController.getAppointments(clientController.getCurrentPetID());

        // Populate the table with pet data
        for (Appointment appointment : appointments) {
            Object[] rowData = {appointment.getAppointmentDate().toString(), appointment.getDescription()};
            tableModel.addRow(rowData);
        }
    }
}