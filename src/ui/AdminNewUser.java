package ui;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.swing.*;
import com.github.lgooddatepicker.components.DatePicker;

import model.Staff;
import control.AppController;

public class AdminNewUser {
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
    private final Color selectedColor = new Color(173, 216, 230);
    private AppController controller;
    
    public AdminNewUser(AppController controller){
        user = new Staff();
        this.controller = controller;
        createUI();
    }
    private void createUI(){
        int selection = JOptionPane.showConfirmDialog(null, getPanel(), "New User : "
                                , JOptionPane.OK_CANCEL_OPTION
                                , JOptionPane.PLAIN_MESSAGE);
        if (selection == JOptionPane.OK_OPTION) 
        { 
            user.setEmpID(Integer.parseInt(id.getText())); 
            user.setFirstName(fName.getText());
            user.setLastName(lName.getText());
            user.setSex(sex.getText());
            java.util.Date date = java.sql.Date.valueOf(datePicker.getText());
            LocalDate date2 = convertToLocalDate(date);
            user.setDob(date2);
            user.setSsn(ssn.getText());
            user.setPhone(phone.getText());
            user.setStreet(street.getText());
            user.setCity(city.getText());
            user.setState(state.getText());
            user.setZip(Integer.parseInt(zip.getText()));
            controller.addStaff(user);
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
        centerPanel.setLayout(new GridLayout(11, 2, 5, 2));
        centerPanel.setBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5));
        centerPanel.setOpaque(true);
        centerPanel.setBackground(Color.WHITE);

        JLabel idLabel = new JLabel("ID : ");
        id = new JTextField();

        JLabel fNameLabel = new JLabel("First Name : ");
        fName = new JTextField();

        JLabel lNameLabel = new JLabel("Last Name : ");
        lName = new JTextField();

        JLabel sexLabel = new JLabel("Sex :");
        sex = new JTextField();

        JLabel dateLabel = new JLabel("DOB :");
        datePicker = new DatePicker();

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

        centerPanel.add(idLabel);
        centerPanel.add(id);

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

        basePanel.add(centerPanel);

        return basePanel;
    }
}
