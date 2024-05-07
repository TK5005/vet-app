package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import control.DashboardController;
import control.IVetAppView;
import model.Appointment;
import model.Client;
import model.Pet;
import model.Staff;

/**
 * DashboardPanel
 */
public class DashboardPanel extends JPanel implements IVetAppView
{
    private DashboardController controller;

    private JButton apptButton;
    private JButton patientButton;
    private JPanel contentPane;
    private CardLayout cardLayout;

    private DefaultTableModel appointmentModel;
    private DefaultTableModel patientModel;
   
    public DashboardPanel() {
        controller = DashboardController.getInstance();
        controller.registerView(this);
        createUI();
        createActionListeners();
        refresh();
    }

    private void createUI() {
        setLayout(new BorderLayout());
       this.setBackground(Color.WHITE);
       JPanel header = new JPanel();
       header.setBackground(Color.WHITE);
       
       apptButton = new JButton("Today's Appointments");
       apptButton.setContentAreaFilled(false);
       apptButton.setBorderPainted(true);
       header.add(apptButton);

       patientButton = new JButton("Active Patients");
       patientButton.setContentAreaFilled(false);
       patientButton.setBorderPainted(true);
       header.add(patientButton);

       contentPane = new JPanel();
       cardLayout = new CardLayout();
       contentPane.setLayout(cardLayout);
       contentPane.setBackground(Color.WHITE);

       JPanel apptTable = createAppointmentTable();
       JPanel patientTable = createPatientTable();
       contentPane.add(apptTable, "apptTable");
       contentPane.add(patientTable, "patientTable");

       add(header, BorderLayout.NORTH);
       add(contentPane, BorderLayout.CENTER);
       
    }

    private void createActionListeners() {
        apptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setApptView();
            }
        });
        patientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPatientView();
            }
        });
    }

    public void refresh() {
        refreshApptTable();
        refreshPatientTable();
    }

    private void refreshApptTable() {
        appointmentModel.setRowCount(0);
        Appointment[] appointments = controller.getAppointments();
        for (Appointment appointment : appointments) {
            if(appointment.getAppointmentDate().getMonth().equals(LocalDate.now().getMonth()) && appointment.getAppointmentDate().getDayOfMonth() == LocalDate.now().getDayOfMonth())
            {
                Client client = controller.getClientByID(appointment.getClientID());
                Pet pet = controller.getPetByID(appointment.getPetID());
                if(client != null && pet != null)
                {
                    appointmentModel.addRow(new Object[]{client.getName(),pet.getName(), client.getPhone(), appointment.getAppointmentDate()});
                }
            }   
        }
    }

    private void refreshPatientTable() {
        patientModel.setRowCount(0);
        Appointment[] appointments = controller.getAppointments();
        for (Appointment appointment : appointments) {
            Pet pet = controller.getPetByID(appointment.getPetID());
            Staff staff = controller.getStaffByID(appointment.getStaffID());
            if(pet != null && staff != null)
            {
                patientModel.addRow(new Object[]{pet.getName(), appointment.getAppointmentDate(), staff.getName(), appointment.getDescription()});
            }
        }
    }

    public void setApptView() {
        cardLayout.show(contentPane, "apptTable");
    }

    public void setPatientView()
    {
        cardLayout.show(contentPane, "patientTable");
    }

    private JPanel createAppointmentTable()
    {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel("Today's Appointments");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        appointmentModel = new DefaultTableModel();
        appointmentModel.addColumn("Owner Name");
        appointmentModel.addColumn("Pet Name");
        appointmentModel.addColumn("Phone Number");
        appointmentModel.addColumn("Appointment");

        JTable apptTable = new JTable(appointmentModel);
        apptTable.setBackground(Color.WHITE);
        apptTable.setRowHeight(50);
        apptTable.getTableHeader().setBackground(new Color(173, 216, 230));

        JScrollPane scrollPane = new JScrollPane(apptTable);

        panel.add(label, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createPatientTable()
    {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BorderLayout());
        JLabel label = new JLabel("Active Patients");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        patientModel = new DefaultTableModel();
        patientModel.addColumn("Patient");
        patientModel.addColumn("Check In");
        patientModel.addColumn("Doctor/Tech");
        patientModel.addColumn("Reason for Visit");

        JTable patientTable = new JTable(patientModel);
        patientTable.setBackground(Color.WHITE);
        patientTable.setRowHeight(50);
        patientTable.getTableHeader().setBackground(new Color(173, 216, 230));

        JScrollPane scrollPane = new JScrollPane(patientTable);

        panel.add(label, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
}