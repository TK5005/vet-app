package view.appointmentView;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.*;

import com.github.lgooddatepicker.components.DateTimePicker;
import model.Appointment;
import control.AppController;

public class NewAppointment {
    Appointment appt;
    JTextField appID;
    JTextField clientID;
    JTextField petID;
    JTextField staffID;
    DateTimePicker datePicker;
    DateTimePicker checkinPicker;
    JTextField description;

    private final Color selectedColor = new Color(173, 216, 230);
    private AppController controller;

    public NewAppointment(AppController controller){
        appt = new Appointment();
        this.controller = controller;
        createUI();
    }
    private void createUI(){
        int selection = JOptionPane.showConfirmDialog(null, getPanel(), "New Appointment : "
                                , JOptionPane.OK_CANCEL_OPTION
                                , JOptionPane.PLAIN_MESSAGE);
        if (selection == JOptionPane.OK_OPTION) 
        { 
            appt.setAppointmentID(Integer.parseInt(appID.getText())); 
            appt.setClientID(Integer.parseInt(clientID.getText()));
            appt.setStaffID(Integer.parseInt(staffID.getText()));
            appt.setPetID(Integer.parseInt(petID.getText()));

            //Switched Separate Date and Time fields to be 1 DateTime field to line up with the DB  - Dan;
            LocalDateTime startTime = datePicker.getDateTimeStrict();
            appt.setAppointmentDate(startTime);
            LocalDateTime checkIn = checkinPicker.getDateTimeStrict();
            appt.setCheckInTime(checkIn);
            appt.setDescription(description.getText());
            //controller.
           
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

        JLabel clientIDLabel = new JLabel("Client ID : ");
        clientID = new JTextField();

        JLabel petIDLabel = new JLabel("Pet ID : ");
        petID = new JTextField();

        JLabel staffIDLabel = new JLabel("Staff ID : ");
        staffID = new JTextField();

        JLabel dateLabel = new JLabel("Start Time : ");
        datePicker = new DateTimePicker();

        JLabel checkinLabel = new JLabel("Check-in Time :");
        checkinPicker = new DateTimePicker();

        JLabel descriptionLabel = new JLabel("Description :");
        description = new JTextField();

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
        centerPanel.add(checkinPicker);

        centerPanel.add(descriptionLabel);
        centerPanel.add(description);

        basePanel.add(centerPanel);

        return basePanel;
    }
}
