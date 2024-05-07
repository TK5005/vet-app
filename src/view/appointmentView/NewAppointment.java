package view.appointmentView;

import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.swing.*;

import com.github.lgooddatepicker.components.DateTimePicker;
import control.AppointmentController;
import model.Appointment;

public class NewAppointment {
    Appointment appt;
    JTextField appID;
    JTextField clientID;
    JTextField petID;
    JTextField staffID;
    DateTimePicker datePicker;
    DateTimePicker checkInTime;
    JTextField description;

    private final Color selectedColor = new Color(173, 216, 230);
    private AppointmentController controller;

    public NewAppointment(AppointmentController controller){
        appt = new Appointment();
        this.controller = controller;
        createUI();
    }

    public NewAppointment(AppointmentController controller, int appointmentID){
        this.controller = controller;
        appt = controller.getAppointment(appointmentID);
        createUI();

    }
    private void createUI(){
        int selection = JOptionPane.showConfirmDialog(null, getPanel(), "Appointment : "
                                , JOptionPane.OK_CANCEL_OPTION
                                , JOptionPane.PLAIN_MESSAGE);
        if (selection == JOptionPane.OK_OPTION) 
        {
              appt.setAppointmentID(Integer.parseInt(appID.getText()));
              appt.setClientID(Integer.parseInt(clientID.getText()));
              appt.setStaffID(Integer.parseInt(staffID.getText()));
              appt.setPetID(Integer.parseInt(petID.getText()));
              appt.setAppointmentDate(datePicker.getDateTimeStrict());
              appt.setDescription(description.getText());
        }
    
    }
    public LocalDate convertToLocalDate(java.util.Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    private JPanel getPanel()
    {
        JPanel basePanel = new JPanel();
        basePanel.setOpaque(true);
        basePanel.setBackground(selectedColor);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(7, 2, 5, 5));
        centerPanel.setBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5));
        centerPanel.setOpaque(true);
        centerPanel.setBackground(Color.WHITE);

        JLabel appIDLabel = new JLabel("Appointment ID : ");
        appID = new JTextField();
        appID.setEditable(false);

        JLabel clientIDLabel = new JLabel("Client ID : ");
        clientID = new JTextField();

        JLabel petIDLabel = new JLabel("Pet ID : ");
        petID = new JTextField();

        JLabel staffIDLabel = new JLabel("Staff ID : ");
        staffID = new JTextField();

        JLabel dateLabel = new JLabel("Date : ");
        datePicker = new DateTimePicker();

        JLabel checkinLabel = new JLabel("Check-in Time : ");
        checkInTime = new DateTimePicker();

        JLabel descriptionLabel = new JLabel("Description :");
        description = new JTextField();

        appID.setText(String.valueOf(appt.getAppointmentID()));
        clientID.setText(String.valueOf(appt.getClientID()));
        staffID.setText(String.valueOf(appt.getStaffID()));
        petID.setText(String.valueOf(appt.getPetID()));
        datePicker.setDateTimeStrict(appt.getAppointmentDate());
        description.setText(appt.getDescription());
        checkInTime.setDateTimeStrict(appt.getCheckInTime());

        centerPanel.add(appIDLabel);
        centerPanel.add(appID);

        centerPanel.add(clientIDLabel);
        centerPanel.add(clientID);

        centerPanel.add(petIDLabel);
        centerPanel.add(petID);

        centerPanel.add(staffIDLabel);
        centerPanel.add(staffID);

        centerPanel.add(dateLabel);
        centerPanel.add(datePicker);

        centerPanel.add(checkinLabel);
        centerPanel.add(checkInTime);

        centerPanel.add(descriptionLabel);
        centerPanel.add(description);

        basePanel.add(centerPanel);

        return basePanel;
    }
}
