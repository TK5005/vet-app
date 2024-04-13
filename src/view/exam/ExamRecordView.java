package view.exam;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import control.ClientController;
import control.IClientView;
import model.Exam;
import model.Tech;
import model.Treatment;
import model.Vet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.ZoneId;

import javax.swing.JList;
import javax.swing.ListCellRenderer;
import java.awt.Component;

public class ExamRecordView extends JPanel implements IClientView
{
    ClientController clientController;

    Treatment treatment;
    JTextField petNameField;
    JTextField examIDField;
    JComboBox<Vet> examinerBox;
    JComboBox<Tech> techBox;
    JTextField locationField;
    JFormattedTextField dateTimeField;
    JFormattedTextField weightField;
    JTextArea descriptionField;
    JTextArea vitalsField;
    JButton detailsButton;
    JButton treatmentButton;
    JPanel examCardPanel;
    CardLayout cardLayout;

    JTextField treatmentIDField;
    JTextField medicationField;
    JFormattedTextField treatmentStartDateField;
    JFormattedTextField treatmentEndDateField;
    JTextArea treatmentDirectionsField;

    JButton saveButton;
    JButton closeButton;

    DateFormat dateFormat;
    NumberFormat numberFormat;


    public ExamRecordView()
    {
        clientController = ClientController.getInstance();
        clientController.registerView(this);
        configureFormatters();
        createUI();
    }

    private void configureFormatters()
    {
        dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(3);
    }

    private void createUI()
    {
        setLayout(new BorderLayout());
        JPanel header = createExamInfoSection();
        JPanel examInfoPanel = new JPanel();
        add(header, BorderLayout.NORTH);
        add(examInfoPanel, BorderLayout.CENTER);
        addActionListeners();
        refresh();
    }

    private JPanel createExamInfoSection()
    {
        JPanel examInfo = new JPanel();
        examInfo.setLayout(new BorderLayout());

        // Create the header panel
        JPanel header = new JPanel();
        header.setLayout(new FlowLayout(FlowLayout.LEFT));

        petNameField = new JTextField(20);
        petNameField.setEditable(false);

        saveButton = new JButton("Save");
        closeButton = new JButton("Close");

        header.add(petNameField);
        header.add(saveButton);
        header.add(closeButton);

        JPanel examDataFields = createExamDataFields();

        JPanel examDetails = createExamDetails();
        JPanel treatmentDetails = createTreatmentDetails();

        JPanel examViewBottom = new JPanel();
        examViewBottom.setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
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

    private JPanel createExamDetails()
    {
        JPanel examDetails = new JPanel();
        examDetails.setLayout(new BoxLayout(examDetails, BoxLayout.Y_AXIS));

        JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.Y_AXIS));
        JLabel descriptionLabel = new JLabel("Description");
        descriptionField = new JTextArea(10, 50);
        descriptionPanel.add(descriptionLabel);
        descriptionPanel.add(descriptionField);

        JPanel vitalsPanel = new JPanel();
        vitalsPanel.setLayout(new BoxLayout(vitalsPanel, BoxLayout.Y_AXIS));
        JLabel vitalsLabel = new JLabel("Vitals");
        vitalsField = new JTextArea(10, 50);
        vitalsPanel.add(vitalsLabel);
        vitalsPanel.add(vitalsField);

        examDetails.add(descriptionPanel);
        examDetails.add(vitalsPanel);

        return examDetails;
    }

    private JPanel createTreatmentDetails()
    {
        JPanel treatmentDetails = new JPanel();
        treatmentDetails.setLayout(new BorderLayout());

        JPanel treatmentHeader = new JPanel();
        treatmentHeader.setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel treatmentIDPanel = new JPanel();
        treatmentIDPanel.setLayout(new BoxLayout(treatmentIDPanel, BoxLayout.Y_AXIS));
        JLabel treatmentIDLabel = new JLabel("Treatment ID");
        treatmentIDField = new JTextField(10);
        treatmentIDField.setEditable(false);
        treatmentIDPanel.add(treatmentIDLabel);
        treatmentIDPanel.add(treatmentIDField);

        JPanel medicationPanel = new JPanel();
        medicationPanel.setLayout(new BoxLayout(medicationPanel, BoxLayout.Y_AXIS));
        JLabel medicationLabel = new JLabel("Medication");
        medicationField = new JTextField(10);
        medicationPanel.add(medicationLabel);
        medicationPanel.add(medicationField);

        JPanel treatmentStartDatePanel = new JPanel();
        treatmentStartDatePanel.setLayout(new BoxLayout(treatmentStartDatePanel, BoxLayout.Y_AXIS));
        JLabel treatmentStartDateLabel = new JLabel("Start Date");
        treatmentStartDateField = new JFormattedTextField(dateFormat);
        treatmentStartDatePanel.add(treatmentStartDateLabel);
        treatmentStartDatePanel.add(treatmentStartDateField);

        JPanel treatmentEndDatePanel = new JPanel();
        treatmentEndDatePanel.setLayout(new BoxLayout(treatmentEndDatePanel, BoxLayout.Y_AXIS));
        JLabel treatmentEndDateLabel = new JLabel("End Date");
        treatmentEndDateField = new JFormattedTextField(dateFormat);
        treatmentEndDatePanel.add(treatmentEndDateLabel);
        treatmentEndDatePanel.add(treatmentEndDateField);

        treatmentHeader.add(treatmentIDPanel);
        treatmentHeader.add(medicationPanel);
        treatmentHeader.add(treatmentStartDatePanel);
        treatmentHeader.add(treatmentEndDatePanel);

        JPanel directionsPanel = new JPanel();
        directionsPanel.setLayout(new BoxLayout(directionsPanel, BoxLayout.Y_AXIS));
        JLabel directionsLabel = new JLabel("Directions");
        treatmentDirectionsField = new JTextArea(10, 50);
        directionsPanel.add(directionsLabel);
        directionsPanel.add(treatmentDirectionsField);

        treatmentDetails.add(treatmentHeader, BorderLayout.NORTH);
        treatmentDetails.add(directionsPanel, BorderLayout.CENTER);

        return treatmentDetails;
    }

    private JPanel createExamDataFields()
    {
        JPanel examDataFields = new JPanel();
        examDataFields.setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel examIDPanel = new JPanel();
        examIDPanel.setLayout(new BoxLayout(examIDPanel, BoxLayout.Y_AXIS));
        JLabel examIDLabel = new JLabel("Exam ID");
        examIDField = new JTextField(10);
        examIDField.setEditable(false);
        examIDPanel.add(examIDLabel);
        examIDPanel.add(examIDField);

        JPanel examinerPanel = new JPanel();
        examinerPanel.setLayout(new BoxLayout(examinerPanel, BoxLayout.Y_AXIS));
        JLabel examinerLabel = new JLabel("Examiner");
        examinerBox = new JComboBox<Vet>(clientController.getVets());
        // set examinerBox to use the vets name as a label
        examinerBox.setRenderer(new VetComboBoxRenderer());

        examinerPanel.add(examinerLabel);
        examinerPanel.add(examinerBox);

        JPanel techPanel = new JPanel();
        techPanel.setLayout(new BoxLayout(techPanel, BoxLayout.Y_AXIS));
        JLabel techLabel = new JLabel("Tech");
        techBox = new JComboBox<Tech>(clientController.getTechs());
        // set techBox to use the techs name as a label
        techBox.setRenderer(new TechComboBoxRenderer());
        techPanel.add(techLabel);
        techPanel.add(techBox);

        JPanel locationPanel = new JPanel();
        locationPanel.setLayout(new BoxLayout(locationPanel, BoxLayout.Y_AXIS));
        JLabel locationLabel = new JLabel("Location");
        locationField = new JTextField(10);
        locationPanel.add(locationLabel);
        locationPanel.add(locationField);

        JPanel dateTimePanel = new JPanel();
        dateTimePanel.setLayout(new BoxLayout(dateTimePanel, BoxLayout.Y_AXIS));
        JLabel dateTimeLabel = new JLabel("Date/Time");
        dateTimeField = new JFormattedTextField(dateFormat);
        dateTimePanel.add(dateTimeLabel);
        dateTimePanel.add(dateTimeField);

        JPanel weightPanel = new JPanel();
        weightPanel.setLayout(new BoxLayout(weightPanel, BoxLayout.Y_AXIS));
        JLabel weightLabel = new JLabel("Weight");
        weightField = new JFormattedTextField(numberFormat);
        weightPanel.add(weightLabel);
        weightPanel.add(weightField);

        examDataFields.add(examIDPanel);
        examDataFields.add(examinerPanel);
        examDataFields.add(techPanel);
        examDataFields.add(locationPanel);
        examDataFields.add(dateTimePanel);
        examDataFields.add(weightPanel);

        return examDataFields;
    }

    private void addActionListeners()
    {
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

    private void updateExam()
    {
        String dateTime = dateTimeField.getText();
        int vetID = ((Vet)examinerBox.getSelectedItem()).getEmpID();
        int techID = ((Tech)techBox.getSelectedItem()).getEmpID();
        String description = descriptionField.getText();
        String vitals = vitalsField.getText();
        int weight = Integer.parseInt(weightField.getText());
        String location = locationField.getText();

        clientController.updateExam(clientController.getCurrentExamID(), dateTime, vetID, techID, description, vitals, weight, location);
    }

    private void updateTreatment()
    {
        Date startDate = (Date)treatmentStartDateField.getValue();
        Date endDate = (Date)treatmentEndDateField.getValue();

        treatment.setMedication(medicationField.getText());
        treatment.setDirections(treatmentDirectionsField.getText());
        if (startDate != null)
        {
            LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if(start != null)
            {
                treatment.setStartDate(start);
            
            }
        }

        if (endDate != null)
        {
            LocalDate end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if(end != null)
            {
                treatment.setEndDate(end);
            }
        }

        clientController.updateTreatment(treatment.getTreatmentID(), treatment);
    }

    public void refresh()
    {
        refreshExam();
        refreshTreatment();
    }

    private void refreshExam()
    {
        Exam exam = clientController.getExam(clientController.getCurrentExamID());
        if(exam != null)
        {
            petNameField.setText(clientController.getPet(exam.getPetID()).getName());
            examIDField.setText(Long.toString(exam.getExamID()));
            examinerBox.setSelectedItem(clientController.getVet(exam.getVetID()));
            techBox.setSelectedItem(clientController.getTech(exam.getTechID()));
            locationField.setText(exam.getLocation());
            try
            {
                dateTimeField.setText(exam.getDateTime().toString());
            }
            catch(Exception e){}
            weightField.setText(Long.toString(exam.getWeight()));
            descriptionField.setText(exam.getDescription());
            vitalsField.setText(exam.getVitals());
        }
    }

    private void refreshTreatment()
    {
        treatment = clientController.getTreatmentFromExamID(clientController.getCurrentExamID());
        if(treatment != null)
        {
            treatmentIDField.setText(Integer.toString(treatment.getTreatmentID()));
            medicationField.setText(treatment.getMedication());
            try
            {
                treatmentStartDateField.setText(treatment.getStartDate().toString());
                treatmentEndDateField.setText(treatment.getEndDate().toString());
            }
            catch(Exception e){}
            treatmentDirectionsField.setText(treatment.getDirections());
        }
    }

    private class VetComboBoxRenderer extends JLabel implements ListCellRenderer<Vet>
    {
        @Override
        public Component getListCellRendererComponent(JList<? extends Vet> list, Vet value, int index, boolean isSelected, boolean cellHasFocus)
        {
            if(value != null)
            {
                setText(value.getName());
            }
            return this;
        }
    }

    private class TechComboBoxRenderer extends JLabel implements ListCellRenderer<Tech>
    {
        @Override
        public Component getListCellRendererComponent(JList<? extends Tech> list, Tech value, int index, boolean isSelected, boolean cellHasFocus)
        {
            if(value != null)
            {
                setText(value.getName());
            }
            return this;
        }
    }
}