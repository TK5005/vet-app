package ui;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.swing.*;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import model.Staff;
import control.AdminController;
import java.sql.Date;

public class NewVet {
    Staff user;
    JTextField id;
    JTextField fName;
    JTextField lName;
    JTextField sex;
    DatePicker datePicker;
    JTextField ssn;
    JTextField phone;
    JTextField street;
    JTextField city;
    JTextField state;
    JTextField zip;
    JTextField licenseNo;
    JTextArea specialties;
    private final Color selectedColor = new Color(173, 216, 230);
    private AdminController controller;
    
    public NewVet(AdminController controller){
        user = new Staff();
        this.controller = controller;
        createUI();
    }
    private void createUI(){
        int selection = JOptionPane.showConfirmDialog(null, getPanel(), "New Vet : "
                                , JOptionPane.OK_CANCEL_OPTION
                                , JOptionPane.PLAIN_MESSAGE);
        if (selection == JOptionPane.OK_OPTION) 
        { 
            //java.util.Date date = java.sql.Date.valueOf(datePicker.getText());
            //LocalDate date2 = LocalDate.parse(datePicker.getText(),formatter);
            String[] item = specialties.getText().split("\n");
            controller.addNewVet(fName.getText(), lName.getText(), Date.valueOf(datePicker.getText()), street.getText(), city.getText(), state.getText(), Integer.parseInt(zip.getText()), phone.getText(), sex.getText(), ssn.getText(),licenseNo.getText(),item);
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
        centerPanel.setLayout(new GridLayout(12, 2, 5, 2));
        centerPanel.setBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5));
        centerPanel.setOpaque(true);
        centerPanel.setBackground(Color.WHITE);

        JLabel fNameLabel = new JLabel("First Name : ");
        fName = new JTextField();

        JLabel lNameLabel = new JLabel("Last Name : ");
        lName = new JTextField();

        JLabel sexLabel = new JLabel("Sex :");
        sex = new JTextField();

        JLabel dateLabel = new JLabel("DOB :");
        DatePickerSettings dateSetting = new DatePickerSettings();
        datePicker = new DatePicker(dateSetting);
        dateSetting.setFormatForDatesCommonEra("yyyy-MM-dd");

        JLabel ssnLabel = new JLabel("SSN :");
        ssn = new JTextField();

        JLabel phoneLabel = new JLabel("Phone :");
        phone = new JTextField();

        JLabel streetLabel = new JLabel("Street :");
        street = new JTextField();

        JLabel cityLabel = new JLabel("City :");
        city = new JTextField();

        JLabel stateLabel = new JLabel("State :");
        state = new JTextField();

        JLabel zipLabel = new JLabel("Zip :");
        zip = new JTextField();

        JLabel licenseLabel = new JLabel("License # :");
        licenseNo = new JTextField();

        JLabel specialtiesLabel = new JLabel("Specialties : ");
        specialties = new JTextArea();
        specialties.setToolTipText("New line to add many specialties");
        JScrollPane panel = new JScrollPane(specialties);
        panel.getViewport().setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(30, 30));

        centerPanel.add(fNameLabel);
        centerPanel.add(fName);

        centerPanel.add(lNameLabel);
        centerPanel.add(lName);

        centerPanel.add(sexLabel);
        centerPanel.add(sex);

        centerPanel.add(dateLabel);
        centerPanel.add(datePicker);

        centerPanel.add(ssnLabel);
        centerPanel.add(ssn);

        centerPanel.add(phoneLabel);
        centerPanel.add(phone);

        centerPanel.add(streetLabel);
        centerPanel.add(street);

        centerPanel.add(cityLabel);
        centerPanel.add(city);

        centerPanel.add(stateLabel);
        centerPanel.add(state);

        centerPanel.add(zipLabel);
        centerPanel.add(zip);

        centerPanel.add(licenseLabel);
        centerPanel.add(licenseNo);
        
        centerPanel.add(specialtiesLabel);
        centerPanel.add(panel);

        basePanel.add(centerPanel);

        return basePanel;
    }
}
