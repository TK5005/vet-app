package ui;
import java.awt.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import model.Certification;
import model.Specialty;
import model.Staff;
import control.AdminController;
import model.Vet;

import java.sql.Date;

public class NewVet {
    Vet user;
    Specialty[] specs;
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
    JFormattedTextField zip;
    JTextField licenseNo;
    JTextArea specialties;
    private final Color selectedColor = new Color(173, 216, 230);
    private AdminController controller;

    NumberFormat integerFormat;
    NumberFormatter numberFormatter;
    
    public NewVet(AdminController controller){
        user = new Vet();
        this.controller = controller;
        setNumberFormatter();
        createUI();
    }
    public NewVet(AdminController controller, int empID){
        this.controller = controller;
        user = controller.getVetByID(empID);
        specs = controller.getSpecialtiesByVetID(empID);
        setNumberFormatter();
        createUI();
    }

    private void setNumberFormatter(){
        integerFormat = NumberFormat.getIntegerInstance();
        integerFormat.setGroupingUsed(false);
        numberFormatter = new NumberFormatter(integerFormat) {
            @Override
            public Object stringToValue(String string) throws ParseException {
                Number number = (Number) super.stringToValue(string);
                return number.intValue(); // Always return an Integer
            }
        };
    }
    private void createUI(){
        int selection = JOptionPane.showConfirmDialog(null, getPanel(), "New Vet : "
                                , JOptionPane.OK_CANCEL_OPTION
                                , JOptionPane.PLAIN_MESSAGE);
        if (selection == JOptionPane.OK_OPTION) 
        {
            user.setEmpID(Integer.parseInt(id.getText()));
            user.setFirstName(fName.getText());
            user.setLastName(lName.getText());
            user.setDob(datePicker.getDate());
            user.setStreet(street.getText());
            user.setCity(city.getText());
            user.setState(state.getText());
            user.setZip((Integer)zip.getValue());
            user.setPhone(phone.getText());
            user.setSex(sex.getText());
            user.setSsn(ssn.getText());
            user.setLicenseNumber(licenseNo.getText());
            String[] item = specialties.getText().split("\n");
            if(Validate()) {
                if (user.getEmpID() == 0)
                    controller.addNewVet(user, item);
                else
                    controller.updateVet(user, item);
            }else{
                createUI();
            }
        }
    }
    public LocalDate convertToLocalDate(java.util.Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private void loadData(){
        id.setText(String.valueOf(user.getEmpID()));
        fName.setText(user.getFirstName());
        lName.setText(user.getLastName());
        sex.setText(user.getSex());
        datePicker.setDate(user.getDob());
        ssn.setText(user.getSsn());
        phone.setText(user.getPhone());
        street.setText(user.getStreet());
        city.setText(user.getCity());
        state.setText(user.getState());
        zip.setValue(user.getZip());
        licenseNo.setText(user.getLicenseNumber());
        if(specs != null)
            for(Specialty spec : specs){
                specialties.append(spec.getSpecialty()+"\n");
            }
    }
    private JPanel getPanel()
    {
        JPanel basePanel = new JPanel();
        basePanel.setOpaque(true);
        basePanel.setBackground(selectedColor);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel,BoxLayout.Y_AXIS));
        centerPanel.setBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5));
        centerPanel.setOpaque(true);
        centerPanel.setBackground(Color.WHITE);

        id = new JTextField();

        JPanel fnamePanel = new JPanel();
        fnamePanel.setLayout(new GridLayout(1,2));
        fnamePanel.setBackground(Color.WHITE);
        JLabel fNameLabel = new JLabel("First Name : ");
        fName = new JTextField();
        fnamePanel.add(fNameLabel);
        fnamePanel.add(fName);

        JPanel lnamePanel = new JPanel();
        lnamePanel.setLayout(new GridLayout(1,2));
        lnamePanel.setBackground(Color.WHITE);
        JLabel lNameLabel = new JLabel("Last Name : ");
        lName = new JTextField();
        lnamePanel.add(lNameLabel);
        lnamePanel.add(lName);

        JPanel sexPanel = new JPanel();
        sexPanel.setLayout(new GridLayout(1,2));
        sexPanel.setBackground(Color.WHITE);
        JLabel sexLabel = new JLabel("Sex :");
        sex = new JTextField();
        sexPanel.add(sexLabel);
        sexPanel.add(sex);

        JPanel datePanel = new JPanel();
        datePanel.setLayout(new GridLayout(1,2));
        datePanel.setBackground(Color.WHITE);
        JLabel dateLabel = new JLabel("DOB :");
        datePicker = new DatePicker();
        datePanel.add(dateLabel);
        datePanel.add(datePicker);

        JPanel ssnPanel = new JPanel();
        ssnPanel.setLayout(new GridLayout(1,2));
        ssnPanel.setBackground(Color.WHITE);
        JLabel ssnLabel = new JLabel("SSN :");
        ssn = new JTextField();
        ssnPanel.add(ssnLabel);
        ssnPanel.add(ssn);

        JPanel phonePanel = new JPanel();
        phonePanel.setLayout(new GridLayout(1,2));
        phonePanel.setBackground(Color.WHITE);
        JLabel phoneLabel = new JLabel("Phone :");
        phone = new JTextField();
        phonePanel.add(phoneLabel);
        phonePanel.add(phone);

        JPanel streetPanel = new JPanel();
        streetPanel.setLayout(new GridLayout(1,2));
        streetPanel.setBackground(Color.WHITE);
        JLabel streetLabel = new JLabel("Street :");
        street = new JTextField();
        streetPanel.add(streetLabel);
        streetPanel.add(street);

        JPanel cityPanel = new JPanel();
        cityPanel.setLayout(new GridLayout(1,2));
        cityPanel.setBackground(Color.WHITE);
        JLabel cityLabel = new JLabel("City :");
        city = new JTextField();
        cityPanel.add(cityLabel);
        cityPanel.add(city);

        JPanel statePanel = new JPanel();
        statePanel.setLayout(new GridLayout(1,2));
        statePanel.setBackground(Color.WHITE);
        JLabel stateLabel = new JLabel("State :");
        state = new JTextField();
        statePanel.add(stateLabel);
        statePanel.add(state);

        JPanel zipPanel = new JPanel();
        zipPanel.setLayout(new GridLayout(1,2));
        zipPanel.setBackground(Color.WHITE);
        JLabel zipLabel = new JLabel("Zip :");
        zip = new JFormattedTextField(numberFormatter);
        zipPanel.add(zipLabel);
        zipPanel.add(zip);

        JPanel licensePanel = new JPanel();
        licensePanel.setLayout(new GridLayout(1,2));
        licensePanel.setBackground(Color.WHITE);
        JLabel licenseLabel = new JLabel("License # :");
        licenseNo = new JTextField();
        licensePanel.add(licenseLabel);
        licensePanel.add(licenseNo);

        JPanel specPanel = new JPanel();
        specPanel.setLayout(new GridLayout(1,2));
        specPanel.setBackground(Color.WHITE);
        JLabel specialtiesLabel = new JLabel("Specialties : ");
        specialties = new JTextArea(5,5);
        specialties.setToolTipText("One Specialty Per Line");
        JScrollPane panel = new JScrollPane(specialties);
        specPanel.add(specialtiesLabel);
        specPanel.add(panel);

        centerPanel.add(fnamePanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0,5)));
        //centerPanel.add(fName);

        centerPanel.add(lnamePanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0,5)));
        //centerPanel.add(lName);

        centerPanel.add(sexPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0,5)));
        //centerPanel.add(sex);

        centerPanel.add(datePanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0,5)));
        //centerPanel.add(datePicker);

        centerPanel.add(ssnPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0,5)));
        //centerPanel.add(ssn);

        centerPanel.add(phonePanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0,5)));
        //centerPanel.add(phone);

        centerPanel.add(streetPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0,5)));
        //centerPanel.add(street);

        centerPanel.add(cityPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0,5)));
        //centerPanel.add(city);

        centerPanel.add(statePanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0,5)));
        //centerPanel.add(state);

        centerPanel.add(zipPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0,5)));
        //centerPanel.add(zip);

        centerPanel.add(licensePanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0,5)));


        centerPanel.add(specPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0,5)));
        //centerPanel.add(panel);

        basePanel.add(centerPanel);

        loadData();

        return basePanel;
    }
    private boolean Validate(){
        if(fName.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "First Name is required");
            return false;
        }else if(lName.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Last Name is required");
            return false;
        }else if(sex.getText().length() != 1){
            JOptionPane.showMessageDialog(null, "Sex needs to be 1 Character");
            return false;
        }else if(datePicker.getDate() == null){
            JOptionPane.showMessageDialog(null, "DOB is required");
            return false;
        }else if(ssn.getText().isEmpty() || ssn.getText().length() > 9){
            JOptionPane.showMessageDialog(null, "SSN is required and cannot be longer than 9 characters");
            return false;
        }else if(phone.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Phone is required");
            return false;
        }else if(street.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Street is required");
            return false;
        }else if(city.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "City is required");
            return false;
        }else if(state.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "State is required");
            return false;
        }else if(zip.getText().isEmpty() || (Integer)zip.getValue() > 99999){
            JOptionPane.showMessageDialog(null, "Zip is required and must be less than 5 digits");
            return false;
        }else if(licenseNo.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Certification Number is required");
            return false;
        }
        return true;

    }
}
