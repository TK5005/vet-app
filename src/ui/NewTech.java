package ui;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.swing.*;
import com.github.lgooddatepicker.components.DatePicker;

import model.Certification;
import model.Staff;
import control.AdminController;
import model.Tech;

public class NewTech {
    Tech user;
    Certification[] certs;
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
    JTextArea certification;
    private final Color selectedColor = new Color(173, 216, 230);
    private AdminController controller;
    
    public NewTech(AdminController controller){
        user = new Tech();
        this.controller = controller;
        createUI();
    }

    public NewTech(AdminController controller, int empID){
        this.controller = controller;
        user = controller.getTechByID(empID);
        certs = controller.getCertificationsByTechID(empID);
        createUI();

    }
    private void createUI(){
        int selection = JOptionPane.showConfirmDialog(null, getPanel(), "New Tech : "
                                , JOptionPane.OK_CANCEL_OPTION
                                , JOptionPane.PLAIN_MESSAGE);
        if (selection == JOptionPane.OK_OPTION) 
        { 
            java.util.Date date = java.sql.Date.valueOf(datePicker.getText());
            LocalDate date2 = convertToLocalDate(date);
            String[] item = certification.getText().split("\n");
            controller.addNewTech(fName.getText(), lName.getText(), date2, street.getText(), city.getText(), state.getText(), Integer.parseInt(zip.getText()), phone.getText(), sex.getText(), ssn.getText(),item);
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
        centerPanel.setLayout(new BoxLayout(centerPanel,BoxLayout.Y_AXIS));
//        centerPanel.setBorder(
//            BorderFactory.createEmptyBorder(5, 5, 5, 5));
        centerPanel.setOpaque(true);
        centerPanel.setBackground(Color.WHITE);

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

        JLabel certLabel = new JLabel("Certifications :");
        certification = new JTextArea(5,5);
        certification.setToolTipText("Once Certification Per Line");
        JScrollPane panel = new JScrollPane(certification);

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

        centerPanel.add(certLabel);
        centerPanel.add(panel);

        basePanel.add(centerPanel);

        return basePanel;
    }
}
