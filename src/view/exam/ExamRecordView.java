package view.exam;

import java.awt.*;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.TimePicker;

import control.ClientController;
import control.IClientView;
import model.*;
import view.treatment.TreatmentTable;

public class ExamRecordView extends JPanel implements IClientView {
    private class VetComboBoxRenderer extends JLabel implements ListCellRenderer<Vet> {
        @Override
        public Component getListCellRendererComponent(JList<? extends Vet> list, Vet value, int index,
                boolean isSelected, boolean cellHasFocus) {
            if (value != null) {
                setText(value.getName());
            }
            return this;
        }
    }

    private class TechComboBoxRenderer extends JLabel implements ListCellRenderer<Tech> {
        @Override
        public Component getListCellRendererComponent(JList<? extends Tech> list, Tech value, int index,
                boolean isSelected, boolean cellHasFocus) {
            if (value != null) {
                setText(value.getName());
            }
            return this;
        }
    }

    private class MedicationComboBoxRenderer extends JLabel implements ListCellRenderer<Medication>{
        @Override
        public Component getListCellRendererComponent(JList<? extends Medication> list, Medication value, int index,
                                                      boolean isSelected, boolean cellHasFocus){
            if(value != null){
                setText(value.getName());
            }else
                setText(" ");
            return this;
        }
    }

    ClientController clientController;
    JTextField petNameField;
    JTextField examIDField;
    JComboBox<Vet> examinerBox;
    JComboBox<Tech> techBox;
    JTextField locationField;
    DateTimePicker dateField;
    JFormattedTextField weightField;
    JTextArea descriptionField;
    JTextArea vitalsField;
    JButton detailsButton;
    JButton treatmentButton;

    JPanel examCardPanel;
    CardLayout cardLayout;
    JTextField treatmentIDField;
    JTextField medicationField;
    JComboBox<Medication> medicationBox;
    JComboBox treatTypeBox;
    DatePicker treatmentStartDateField;

    DatePicker treatmentEndDateField;
    JTextArea treatmentDirectionsField;
    JTable treatmentTable;

    JButton saveButton;

    JButton closeButton;

    NumberFormat numberFormat;

    public ExamRecordView() {
        clientController = ClientController.getInstance();
        clientController.registerView(this);
        configureFormatters();
        createUI();
    }

    public void refresh() {
        refreshExam();
        refreshTreatment();
    }

    private void configureFormatters() {
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(3);
    }

    private void createUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        JPanel header = createExamInfoSection();
        JPanel examInfoPanel = new JPanel();
        add(header, BorderLayout.NORTH);
        add(examInfoPanel, BorderLayout.CENTER);
        addActionListeners();
        refresh();
    }

    private JPanel createExamInfoSection() {
        JPanel examInfo = new JPanel();
        examInfo.setLayout(new BorderLayout());
        examInfo.setBackground(Color.WHITE);
        // Create the header panel
        JPanel header = new JPanel();
        header.setLayout(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(Color.WHITE);
        petNameField = new JTextField(20);
        petNameField.setEditable(false);

        saveButton = new JButton("Save");
        closeButton = new JButton("Close");

        header.add(petNameField);
        header.add(saveButton);
        header.add(closeButton);

        JPanel examDataFields = createExamDataFields();

        JPanel examDetails = createExamDetails();
        JPanel treatmentDetails = new TreatmentTable();

        JPanel examViewBottom = new JPanel();
        examViewBottom.setLayout(new BorderLayout());
        examViewBottom.setBackground(Color.WHITE);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(Color.WHITE);
        detailsButton = new JButton("Details");
        treatmentButton = new JButton("Treatment");
        buttonPanel.add(detailsButton);
        buttonPanel.add(treatmentButton);
        examViewBottom.add(buttonPanel, BorderLayout.NORTH);
        
        examCardPanel = new JPanel();
        cardLayout = new CardLayout();
        examCardPanel.setLayout(cardLayout);

        examCardPanel.add(examDetails, "examDetails");
        examCardPanel.add(treatmentDetails, "treatmentDetails");

        cardLayout.show(examCardPanel, "examDetails");

        examViewBottom.add(examCardPanel, BorderLayout.CENTER);

        examInfo.add(header, BorderLayout.NORTH);
        examInfo.add(examDataFields, BorderLayout.CENTER);
        examInfo.add(examViewBottom, BorderLayout.SOUTH);

        return examInfo;
    }

    private JPanel createExamDetails() {
        JPanel examDetails = new JPanel();
        examDetails.setLayout(new BoxLayout(examDetails, BoxLayout.Y_AXIS));
        examDetails.setBackground(Color.WHITE);
        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.Y_AXIS));
        descriptionPanel.setBackground(Color.WHITE);
        JLabel descriptionLabel = new JLabel("Description");
        descriptionField = new JTextArea(10, 50);
        descriptionPanel.add(descriptionLabel);
        descriptionPanel.add(descriptionField);

        JPanel vitalsPanel = new JPanel();
        vitalsPanel.setLayout(new BoxLayout(vitalsPanel, BoxLayout.Y_AXIS));
        vitalsPanel.setBackground(Color.WHITE);
        JLabel vitalsLabel = new JLabel("Vitals");
        vitalsField = new JTextArea(10, 50);
        vitalsPanel.add(vitalsLabel);
        vitalsPanel.add(vitalsField);

        examDetails.add(descriptionPanel);
        examDetails.add(vitalsPanel);

        return examDetails;
    }

    /*private JPanel createTreatmentDetails() {
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
        treatTypeBox = new JComboBox(Treatment.getTreatTypes());
        treatTypePanel.add(treatTypeLabel);
        treatTypePanel.add(treatTypeBox);

        JPanel medicationPanel = new JPanel();
        medicationPanel.setLayout(new BoxLayout(medicationPanel, BoxLayout.Y_AXIS));
        medicationPanel.setBackground(Color.WHITE);
        JLabel medicationLabel = new JLabel("Medication");

        //TODO: Make this a ComboBox that calls MedicationRepository.getAllInStock() to get the list of medications in stock
        //medicationField = new JTextField(10);
        medicationBox = new JComboBox<Medication>(clientController.getInStockMedications());
        medicationBox.insertItemAt(null, 0);
        medicationBox.setRenderer(new MedicationComboBoxRenderer());
        medicationPanel.add(medicationLabel);
        //medicationPanel.add(medicationField);
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
        directionsPanel.add(directionsLabel);
        directionsPanel.add(treatmentDirectionsField);

        treatmentDetails.add(treatmentHeader, BorderLayout.NORTH);
        treatmentDetails.add(directionsPanel, BorderLayout.CENTER);

        return treatmentDetails;
    }*/

    private JPanel createExamDataFields() {
        JPanel examDataFields = new JPanel();
        examDataFields.setLayout(new FlowLayout(FlowLayout.LEFT));
        examDataFields.setBackground(Color.WHITE);

        JPanel examIDPanel = new JPanel();
        examIDPanel.setLayout(new BoxLayout(examIDPanel, BoxLayout.Y_AXIS));
        examIDPanel.setBackground(Color.WHITE);
        JLabel examIDLabel = new JLabel("Exam ID");
        examIDField = new JTextField(10);
        examIDField.setEditable(false);
        examIDPanel.add(examIDLabel);
        examIDPanel.add(examIDField);

        JPanel examinerPanel = new JPanel();
        examinerPanel.setLayout(new BoxLayout(examinerPanel, BoxLayout.Y_AXIS));
        examinerPanel.setBackground(Color.WHITE);
        JLabel examinerLabel = new JLabel("Examiner");
        examinerBox = new JComboBox<Vet>(clientController.getVets());
        // set examinerBox to use the vets name as a label
        examinerBox.setRenderer(new VetComboBoxRenderer());

        examinerPanel.add(examinerLabel);
        examinerPanel.add(examinerBox);

        JPanel techPanel = new JPanel();
        techPanel.setLayout(new BoxLayout(techPanel, BoxLayout.Y_AXIS));
        techPanel.setBackground(Color.WHITE);
        JLabel techLabel = new JLabel("Tech");
        techBox = new JComboBox<Tech>(clientController.getTechs());
        // set techBox to use the techs name as a label
        techBox.setRenderer(new TechComboBoxRenderer());
        techPanel.add(techLabel);
        techPanel.add(techBox);

        JPanel locationPanel = new JPanel();
        locationPanel.setLayout(new BoxLayout(locationPanel, BoxLayout.Y_AXIS));
        locationPanel.setBackground(Color.WHITE);
        JLabel locationLabel = new JLabel("Location");
        locationField = new JTextField(10);
        locationPanel.add(locationLabel);
        locationPanel.add(locationField);

        JPanel datePanel = new JPanel();
        datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.Y_AXIS));
        datePanel.setBackground(Color.WHITE);
        JLabel dateTimeLabel = new JLabel("Date/Time");
        dateField = new DateTimePicker();
        datePanel.add(dateTimeLabel);
        datePanel.add(dateField);

        //JPanel timePanel = new JPanel();
        //timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.Y_AXIS));
        //timePanel.setBackground(Color.WHITE);
        //JLabel timeLabel = new JLabel("Time");
        //timeField = new TimePicker();
        //timePanel.add(timeLabel);
        //timePanel.add(timeField);

        JPanel weightPanel = new JPanel();
        weightPanel.setLayout(new BoxLayout(weightPanel, BoxLayout.Y_AXIS));
        weightPanel.setBackground(Color.WHITE);
        JLabel weightLabel = new JLabel("Weight");
        weightField = new JFormattedTextField(numberFormat);
        weightPanel.add(weightLabel);
        weightPanel.add(weightField);

        examDataFields.add(examIDPanel);
        examDataFields.add(examinerPanel);
        examDataFields.add(techPanel);
        examDataFields.add(locationPanel);
        examDataFields.add(datePanel);
        //examDataFields.add(timePanel);
        examDataFields.add(weightPanel);

        return examDataFields;
    }

    private void addActionListeners() {
        saveButton.addActionListener(e -> {
            updateExam();
            updateTreatment();
        });

        closeButton.addActionListener(e -> {
            // Close the exam info view
            clientController.closeExamInfoView();
        });

        detailsButton.addActionListener(e -> {
            cardLayout.show(examCardPanel, "examDetails");
        });

        treatmentButton.addActionListener(e -> {
            cardLayout.show(examCardPanel, "treatmentDetails");
        });
    }

    private void updateExam() {
        LocalDateTime date = dateField.getDateTimeStrict();
        int vetID = ((Vet) examinerBox.getSelectedItem()).getEmpID();
        int techID = ((Tech) techBox.getSelectedItem()).getEmpID();
        String description = descriptionField.getText();
        String vitals = vitalsField.getText();
        int weight = Integer.parseInt(weightField.getText());
        String location = locationField.getText();
        //LocalTime time = timeField.getTime();
        clientController.updateExam(clientController.getCurrentExamID(), date, vetID, techID, description, vitals,
                weight, location);
    }

    private void updateTreatment() {

        /*TODO: if medication is selected here, make sure we perform an addOrUpdatePrescription call to update
            Prescription table
        */
        /*
        Treatment treatment = clientController.getTreatmentFromExamID(clientController.getCurrentExamID());
        LocalDate startDate = treatmentStartDateField.getDate();
        LocalDate endDate = treatmentEndDateField.getDate();
        treatment.setMedicationID(medicationBox.getSelectedItem() != null ? ((Medication) medicationBox.getSelectedItem()).getItemID() : -1);
        treatment.setDirections(treatmentDirectionsField.getText());
        treatment.setStartDate(startDate);
        treatment.setEndDate(endDate);
        clientController.updateTreatment(treatment.getTreatmentID(), ((Vet) examinerBox.getSelectedItem()).getEmpID(), treatment);*/
    }

    private void refreshExam() {
        Exam exam = clientController.getExam(clientController.getCurrentExamID());
        if (exam != null) {
            petNameField.setText(clientController.getPet(exam.getPetID()).getName());
            examIDField.setText(Long.toString(exam.getExamID()));
            examinerBox.setSelectedItem(clientController.getVet(exam.getVetID()));
            techBox.setSelectedItem(clientController.getTech(exam.getTechID()));
            locationField.setText(exam.getLocation());
            dateField.setDateTimeStrict(exam.getDate());
            //timeField.setTime(exam.getTime());
            weightField.setText(Long.toString(exam.getWeight()));
            descriptionField.setText(exam.getDescription());
            vitalsField.setText(exam.getVitals());
        }
    }

    private void refreshTreatment() {
        Treatment treatment = clientController.getTreatmentFromExamID(clientController.getCurrentExamID());
        if (treatment != null) {
            /*treatmentIDField.setText(Integer.toString(treatment.getTreatmentID()));
            //medicationField.setText(String.valueOf(treatment.getMedicationID()));
            medicationBox.setSelectedItem(clientController.getInventoryNameByID(treatment.getMedicationID()));
            treatmentStartDateField.setDate(treatment.getStartDate());
            treatmentEndDateField.setDate(treatment.getEndDate());
            treatmentDirectionsField.setText(treatment.getDirections());*/
        }
    }
}