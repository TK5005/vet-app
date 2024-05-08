package view.treatment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.github.lgooddatepicker.components.DatePicker;

import control.ClientController;
import model.Medication;
import model.Treatment;


public class TreatmentInfo extends JPanel {

    int treatmentID;
    ClientController clientController;
    Treatment treatment;
    JTextField treatmentIDField;
    JTextField medicationField;
    JComboBox<Medication> medicationBox;
    JComboBox<String> treatTypeBox;
    DatePicker treatmentStartDateField;
    JButton saveButton;

    DatePicker treatmentEndDateField;
    JTextArea treatmentDirectionsField;

    public TreatmentInfo(int treatmentID) {
        this.treatmentID = treatmentID;
        clientController = ClientController.getInstance();
        treatment = clientController.getTreatment(treatmentID);
        createUI();
        createEventListeners();
    }

    private void createEventListeners() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTreatment();
            }
        });
    }

    private void saveTreatment()
    {
        Medication meds = (Medication) medicationBox.getSelectedItem();
        clientController.updateTreatment(this.treatmentID, clientController.getCurrentExamID(), meds == null ? null : meds.getItemID() , (String) treatTypeBox.getSelectedItem(),
                                        treatmentStartDateField.getDate(), treatmentEndDateField.getDate(), treatmentDirectionsField.getText());
    }

    private void createUI() {
        this.setLayout(new BorderLayout());
        saveButton = new JButton("Save");
        add(createTreatmentDetails(), BorderLayout.CENTER);
        add(saveButton, BorderLayout.SOUTH);
        loadData();
    }

    private void loadData()
    {
        treatmentIDField.setText(Integer.toString(treatment.getTreatmentID()));
        treatTypeBox.setSelectedItem(treatment.getTreatmentTypeString());
        Medication[] meds = clientController.getInStockMedications();
        medicationBox.removeAllItems();
        medicationBox.addItem(null);
        for(Medication med : meds){
            medicationBox.addItem(med);
            if(med.getItemID() == treatment.getMedicationID()){
                medicationBox.setSelectedItem(med);
            }
        }
        treatmentStartDateField.setDate(treatment.getStartDate());
        treatmentEndDateField.setDate(treatment.getEndDate());
        treatmentDirectionsField.setText(treatment.getDirections());
    }

    private JPanel createTreatmentDetails() {
        JPanel treatmentDetails = new JPanel();
        treatmentDetails.setLayout(new BorderLayout());
        treatmentDetails.setBackground(Color.WHITE);
        JPanel treatmentHeader = new JPanel();
        treatmentHeader.setLayout(new FlowLayout(FlowLayout.LEFT));
        treatmentHeader.setBackground(Color.WHITE);
        JPanel treatmentIDPanel = new JPanel();
        treatmentIDPanel.setLayout(new BoxLayout(treatmentIDPanel, BoxLayout.Y_AXIS));
        treatmentIDPanel.setBackground(Color.WHITE);
        JLabel treatmentIDLabel = new JLabel("Treatment ID");
        treatmentIDField = new JTextField(10);
        treatmentIDField.setEditable(false);
        treatmentIDPanel.add(treatmentIDLabel);
        treatmentIDPanel.add(treatmentIDField);

        JPanel treatTypePanel = new JPanel();
        treatTypePanel.setLayout(new BoxLayout(treatTypePanel, BoxLayout.Y_AXIS));
        treatTypePanel.setBackground(Color.WHITE);
        JLabel treatTypeLabel = new JLabel("Type");
        treatTypeBox = new JComboBox<String>(Treatment.getTreatTypes());
        treatTypePanel.add(treatTypeLabel);
        treatTypePanel.add(treatTypeBox);

        JPanel medicationPanel = new JPanel();
        medicationPanel.setLayout(new BoxLayout(medicationPanel, BoxLayout.Y_AXIS));
        medicationPanel.setBackground(Color.WHITE);
        JLabel medicationLabel = new JLabel("Medication");
        medicationBox = new JComboBox<Medication>(clientController.getInStockMedications());
        medicationBox.insertItemAt(null,0);
        medicationBox.setRenderer(new MedicationComboBoxRenderer());
        medicationPanel.add(medicationLabel);
        medicationPanel.add(medicationBox);

        JPanel treatmentStartDatePanel = new JPanel();
        treatmentStartDatePanel.setLayout(new BoxLayout(treatmentStartDatePanel, BoxLayout.Y_AXIS));
        treatmentStartDatePanel.setBackground(Color.WHITE);
        JLabel treatmentStartDateLabel = new JLabel("Start Date");
        treatmentStartDateField = new DatePicker();
        treatmentStartDatePanel.add(treatmentStartDateLabel);
        treatmentStartDatePanel.add(treatmentStartDateField);

        JPanel treatmentEndDatePanel = new JPanel();
        treatmentEndDatePanel.setLayout(new BoxLayout(treatmentEndDatePanel, BoxLayout.Y_AXIS));
        treatmentEndDatePanel.setBackground(Color.WHITE);
        JLabel treatmentEndDateLabel = new JLabel("End Date");
        treatmentEndDateField = new DatePicker();
        treatmentEndDatePanel.add(treatmentEndDateLabel);
        treatmentEndDatePanel.add(treatmentEndDateField);

        treatmentHeader.add(treatmentIDPanel);
        treatmentHeader.add(treatTypePanel);
        treatmentHeader.add(medicationPanel);
        treatmentHeader.add(treatmentStartDatePanel);
        treatmentHeader.add(treatmentEndDatePanel);

        JPanel directionsPanel = new JPanel();
        directionsPanel.setLayout(new BoxLayout(directionsPanel, BoxLayout.Y_AXIS));
        directionsPanel.setBackground(Color.WHITE);
        JLabel directionsLabel = new JLabel("Directions");
        treatmentDirectionsField = new JTextArea(10, 50);
        JScrollPane treatScroll = new JScrollPane(treatmentDirectionsField);
        directionsPanel.add(directionsLabel);
        directionsPanel.add(treatScroll);

        treatmentDetails.add(treatmentHeader, BorderLayout.NORTH);
        treatmentDetails.add(directionsPanel, BorderLayout.CENTER);

        return treatmentDetails;
    }

    private class MedicationComboBoxRenderer extends JLabel implements ListCellRenderer<Medication>{
        @Override
        public Component getListCellRendererComponent(JList<? extends Medication> list, Medication value, int index,
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
