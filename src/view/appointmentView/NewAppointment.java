package view.appointmentView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;

import javax.swing.*;

import com.github.lgooddatepicker.components.DateTimePicker;
import control.AppointmentController;
import model.*;

public class NewAppointment {
    Appointment appt;
    Client client;
    Pet pet;
    Vet vet;
    JTextField appID;
    JTextField clientID;
    JComboBox<Client> clientBox;
    JTextField petID;
    JComboBox<Pet> petBox;
    JTextField staffID;

    JComboBox<Vet> vetBox;
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
        client = controller.getClient(appt.getClientID());
        pet = controller.getPet(appt.getPetID());
        vet = controller.getVet(appt.getStaffID());

        createUI();

    }
    private void createUI(){
        int selection = JOptionPane.showConfirmDialog(null, getPanel(), "Appointment : "
                                , JOptionPane.OK_CANCEL_OPTION
                                , JOptionPane.PLAIN_MESSAGE);
        if (selection == JOptionPane.OK_OPTION)
        {

                appt.setAppointmentID(Integer.parseInt(appID.getText()));
                //appt.setClientID(Integer.parseInt(clientID.getText()));
                if(clientBox.getSelectedItem()!= null){appt.setClientID(((Client) clientBox.getSelectedItem()).getClientID());}
                //appt.setStaffID(Integer.parseInt(staffID.getText()));
                appt.setStaffID(vetBox.getSelectedItem() == null ? null : ((Vet) vetBox.getSelectedItem()).getEmpID());
                //appt.setPetID(Integer.parseInt(petID.getText()));
                if(petBox.getSelectedItem() != null){appt.setPetID(((Pet) petBox.getSelectedItem()).getPetID());}
                appt.setAppointmentDate(datePicker.getDateTimeStrict());
                appt.setCheckInTime(checkInTime.getDateTimeStrict());
                appt.setDescription(description.getText());
            if(validate()) {
                if (appt.getAppointmentID() == 0)
                    controller.addNewAppointment(appt);
                else
                    controller.updateAppointment(appt);
            }else{
                JOptionPane.showMessageDialog(null, "One or More required fields are missing");
                createUI();
            }
        }
    
    }
    private void createActionListeners() {
        clientBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadPetDropDown();
            }
        });
    }
    public LocalDate convertToLocalDate(java.util.Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private void loadData(){
        appID.setText(String.valueOf(appt.getAppointmentID()));
        //clientID.setText(String.valueOf(appt.getClientID()));
        //staffID.setText(String.valueOf(appt.getStaffID()));
        //petID.setText(String.valueOf(appt.getPetID()));
        datePicker.setDateTimeStrict(appt.getAppointmentDate());
        description.setText(appt.getDescription());
        checkInTime.setDateTimeStrict(appt.getCheckInTime());

        Client[] clients = controller.getClients();
        clientBox.removeAllItems();
        clientBox.addItem(null);
        for(Client cl : clients){
            clientBox.addItem(cl);
            if(cl.getClientID() == appt.getClientID()){
                clientBox.setSelectedItem(cl);
            }
        }
        loadPetDropDown();

        Vet[] vets = controller.getVets();
        vetBox.removeAllItems();
        vetBox .addItem(null);
        for(Vet vet: vets){
            vetBox.addItem(vet);
            if(appt.getAppointmentID() != 0 && vet.getEmpID() == appt.getStaffID()){
                vetBox.setSelectedItem(vet);
            }
        }

    }

    private void loadPetDropDown(){
        if(clientBox.getSelectedIndex() > 0){
            Pet[] pets = controller.getPetsByClientID(((Client)clientBox.getSelectedItem()).getClientID());
            petBox.removeAllItems();
            for(Pet pet: pets){
                petBox.addItem(pet);
                if(pet.getPetID() == appt.getPetID()){
                    petBox.setSelectedItem(pet);
                }
            }
        }
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

        JLabel clientIDLabel = new JLabel("Client : ");
        clientBox = new JComboBox<Client>();
        clientBox.setRenderer(new ClientComboBoxRenderer());

        JLabel petIDLabel = new JLabel("Pet : ");
        petBox = new JComboBox<Pet>();
        petBox.setRenderer(new PetComboBoxRenderer());

        JLabel staffIDLabel = new JLabel("Vet : ");
        vetBox = new JComboBox<Vet>();
        vetBox.setRenderer(new VetComboBoxRenderer());

        JLabel dateLabel = new JLabel("Date : ");
        datePicker = new DateTimePicker();

        JLabel checkinLabel = new JLabel("Check-in Time : ");
        checkInTime = new DateTimePicker();

        JLabel descriptionLabel = new JLabel("Description :");
        description = new JTextField();

        centerPanel.add(appIDLabel);
        centerPanel.add(appID);

        centerPanel.add(clientIDLabel);
        centerPanel.add(clientBox);

        centerPanel.add(petIDLabel);
        centerPanel.add(petBox);

        centerPanel.add(staffIDLabel);
        centerPanel.add(vetBox);

        centerPanel.add(dateLabel);
        centerPanel.add(datePicker);

        centerPanel.add(checkinLabel);
        centerPanel.add(checkInTime);

        centerPanel.add(descriptionLabel);
        centerPanel.add(description);

        basePanel.add(centerPanel);

        loadData();
        createActionListeners();
        return basePanel;
    }
    private boolean validate(){

        if(datePicker.getDateTimeStrict()==null)
            return false;
        if(clientBox.getSelectedItem() == null)
            return false;
        if(petBox.getSelectedItem() == null)
            return false;

        return true;
    }

    private class ClientComboBoxRenderer extends JLabel implements ListCellRenderer<Client>{
        @Override
        public Component getListCellRendererComponent(JList<? extends Client> list, Client value, int index,
                                                      boolean isSelected, boolean cellHasFocus){
            if(value != null){
                setText(value.getName());
            }else{
                setText(" ");
            }
            return this;
        }
    }

    private class PetComboBoxRenderer extends JLabel implements ListCellRenderer<Pet>{
        @Override
        public Component getListCellRendererComponent(JList<? extends Pet> list, Pet value, int index,
                                                      boolean isSelected, boolean cellHasFocus){
            if(value != null){
                setText(value.getName());
            }
            return this;
        }
    }

    private class VetComboBoxRenderer extends JLabel implements ListCellRenderer<Vet>{
        @Override
        public Component getListCellRendererComponent(JList<? extends Vet> list, Vet value, int index,
                                                      boolean isSelected, boolean cellHasFocus){
            if(value != null){
                setText(value.getName());
            }else{
                setText(" ");
            }
            return this;
        }
    }
}
