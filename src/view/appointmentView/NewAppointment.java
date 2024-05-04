package view.appointmentView;

import java.awt.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.swing.*;
import com.github.lgooddatepicker.components.DatePicker;

import model.Appointment;
import control.AppController;

public class NewAppointment {
    Appointment appt;
    JTextField appID;
    JTextField clientID;
    JTextField petID;
    JTextField staffID;
    DatePicker datePicker;
    JTextField time;
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
            java.util.Date date = java.sql.Date.valueOf(datePicker.getText());
            LocalDate date2 = convertToLocalDate(date);
            appt.setAppointmentDate(date2);
            appt.setAppointmentTime(time.getText());
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

        JLabel clientIDLabel = new JLabel("Client ID : ");
        clientID = new JTextField();

        JLabel petIDLabel = new JLabel("Pet ID : ");
        petID = new JTextField();

        JLabel staffIDLabel = new JLabel("Staff ID : ");
        staffID = new JTextField();

        JLabel dateLabel = new JLabel("Date : ");
        datePicker = new DatePicker();

        JLabel timeLabel = new JLabel("Time : ");
        time = new JTextField();

        JLabel discriptionLabel = new JLabel("Discription :");
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

        centerPanel.add(timeLabel);
        centerPanel.add(time);

        centerPanel.add(discriptionLabel);
        centerPanel.add(description);

        basePanel.add(centerPanel);

        return basePanel;
    }
}
