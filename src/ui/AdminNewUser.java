package ui;
import java.awt.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.Date;
import javax.swing.*;
import javax.swing.text.NumberFormatter;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import model.Staff;
import control.AdminController;

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
    JFormattedTextField zip;
    private final Color selectedColor = new Color(173, 216, 230);
    private AdminController controller;

    NumberFormat integerFormat;

    NumberFormatter numberFormatter;
    
    public AdminNewUser(AdminController controller){
        user = new Staff();
        this.controller = controller;
        setNumberFormatter();
        createUI();
    }

    public AdminNewUser (AdminController controller, int empID){
        this.controller = controller;
        user = controller.getStaffByID(empID);
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
        int selection = JOptionPane.showConfirmDialog(null, getPanel(), "New Staff : "
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
            if(Validate()) {
                if (user.getEmpID() == 0)
                    controller.addNewStaff(user);
                else
                    controller.updateStaff(user);
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
    }
    private JPanel getPanel()
    {
        JPanel basePanel = new JPanel();
        basePanel.setOpaque(true);
        basePanel.setBackground(selectedColor);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(10, 2, 5, 2));
        centerPanel.setBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5));
        centerPanel.setOpaque(true);
        centerPanel.setBackground(Color.WHITE);

       // JLabel idLabel = new JLabel("ID : ");
        id = new JTextField();
       // id.setEnabled(false);

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
        zip = new JFormattedTextField(numberFormatter);

        //centerPanel.add(idLabel);
       // centerPanel.add(id);

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
        }
        return true;

    }
}
