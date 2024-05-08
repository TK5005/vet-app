package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JScrollPane;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import com.github.lgooddatepicker.components.DatePicker;

import control.AdminController;
import model.Staff;
import model.Tech;
import model.Vet;


public class AdminViewDetails extends JFrame {
    
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
    JTextArea certification;
    private AdminController adminController;
    private final Color selectedColor = new Color(173, 216, 230);
    
    public AdminViewDetails(int empID){
        super("User Details");
        Staff staff = adminController.getStaffByID(empID);
        Vet vet = adminController.getVetByID(empID);
        Tech tech = adminController.getTechByID(empID);
        createUI(staff,vet,tech);
    }
     private void createUI(Staff staff, Vet vet, Tech tech){
        int selection = JOptionPane.showConfirmDialog(null, getPanel(staff,vet,tech), "New Staff : "
                                , JOptionPane.OK_CANCEL_OPTION
                                , JOptionPane.PLAIN_MESSAGE);
        if (selection == JOptionPane.OK_OPTION) 
        { 
            //controller.updateStaff(fName.getText(), lName.getText(),Date.valueOf(datePicker.getText()), street.getText(), city.getText(), state.getText(), Integer.parseInt(zip.getText()), phone.getText(), sex.getText(), ssn.getText());
        }
    }
    public LocalDate convertToLocalDate(java.util.Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    private JPanel getPanel(Staff staff, Vet vet, Tech tech)
    {
        JPanel basePanel = new JPanel();
        basePanel.setOpaque(true);
        basePanel.setBackground(selectedColor);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(13, 2, 5, 2));
        centerPanel.setBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5));
        centerPanel.setOpaque(true);
        centerPanel.setBackground(Color.WHITE);

        JLabel fNameLabel = new JLabel("First Name : ");
        fName = new JTextField();
        fName.setText(staff.getFirstName());

        JLabel lNameLabel = new JLabel("Last Name : ");
        lName = new JTextField();
        lName.setText(staff.getLastName());

        JLabel sexLabel = new JLabel("Sex :");
        sex = new JTextField();
        sex.setText(staff.getSex());

        JLabel dateLabel = new JLabel("DOB :");
        datePicker = new DatePicker();

        JLabel ssnLabel = new JLabel("SSN :");
        ssn = new JTextField();
        ssn.setText(staff.getSsn());

        JLabel phoneLabel = new JLabel("Phone :");
        phone = new JTextField();
        phone.setText(staff.getPhone());

        JLabel streetLabel = new JLabel("Street :");
        street = new JTextField();
        street.setText(staff.getStreet());


        JLabel cityLabel = new JLabel("City :");
        city = new JTextField();
        city.setText(staff.getCity());

        JLabel stateLabel = new JLabel("State :");
        state = new JTextField();
        state.setText(staff.getState());

        JLabel zipLabel = new JLabel("Zip :");
        zip = new JTextField();
        zip.setText(Integer.toString(staff.getZip()));

        JLabel licenseLabel = new JLabel("License # :");
        licenseNo = new JTextField();
        licenseNo.setText(vet.getLicenseNumber());

        JLabel specialtiesLabel = new JLabel("Specialties : ");
        specialties = new JTextArea();
        specialties.setToolTipText("New line to add many specialties");
        specialties.append(vet.getSpecialty()+"\n");

        JLabel certLabel = new JLabel("Certifications :");
        certification = new JTextArea();
        certification.setToolTipText("New line to add many certifications");
        certification.append(tech.getCertification()+"\n"); 
        
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
        centerPanel.add(specialties);

        centerPanel.add(certLabel);
        centerPanel.add(certification);

        basePanel.add(centerPanel);

        return basePanel;
    }
}
