package view.exam;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.text.NumberFormat;
import java.time.LocalDateTime;

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

import control.ClientController;
import control.IVetAppView;
import model.Exam;
import model.Medication;
import model.Tech;
import model.Vet;
import view.treatment.TreatmentTable;

public class ExamRecordView extends JPanel implements IVetAppView {
    private class VetComboBoxRenderer extends JLabel implements ListCellRenderer<Vet> {
        @Override
        public Component getListCellRendererComponent(JList<? extends Vet> list, Vet value, int index,
                boolean isSelected, boolean cellHasFocus) {
            if (value != null) {
                setText(value.getName());
            }else
                setText(" ");
            return this;
        }
    }

    private class TechComboBoxRenderer extends JLabel implements ListCellRenderer<Tech> {
        @Override
        public Component getListCellRendererComponent(JList<? extends Tech> list, Tech value, int index,
                boolean isSelected, boolean cellHasFocus) {
            if (value != null) {
                setText(value.getName());
            }else
                setText(" ");
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
        examDataFields.add(weightPanel);

        return examDataFields;
    }

    private void addActionListeners() {
        saveButton.addActionListener(e -> {
            updateExam();
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
        Vet vet = (Vet) examinerBox.getSelectedItem();
        Tech tech = (Tech) techBox.getSelectedItem();
        String description = descriptionField.getText();
        String vitals = vitalsField.getText();
        int weight = Integer.parseInt(weightField.getText());
        String location = locationField.getText();
        //LocalTime time = timeField.getTime();
        clientController.updateExam(clientController.getCurrentExamID(), date,
                vet == null ? null : vet.getEmpID(), tech == null ? null : tech.getEmpID(), description, vitals,
                weight, location);
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
       /* Treatment treatment = clientController.getTreatmentFromExamID(clientController.getCurrentExamID());
        if (treatment != null) {
            treatmentIDField.setText(Integer.toString(treatment.getTreatmentID()));
            //medicationField.setText(String.valueOf(treatment.getMedicationID()));
            medicationBox.setSelectedItem(clientController.getInventoryNameByID(treatment.getMedicationID()));
            treatmentStartDateField.setDate(treatment.getStartDate());
            treatmentEndDateField.setDate(treatment.getEndDate());
            treatmentDirectionsField.setText(treatment.getDirections());
        }*/
    }
}