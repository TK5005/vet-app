package view.pet;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.ClientController;
import control.IClientView;
import model.Client;
import model.Pet;
import view.exam.ExamTable;

import java.text.NumberFormat;

import java.time.format.DateTimeFormatter;

import java.awt.Dimension;

import java.text.ParsePosition;

public class PetInfoView extends JPanel implements IClientView {
    private JTextField nameField;
    private JTextField patientIDField;
    private JTextField clientNameField;
    private JTextField sexField;
    private JTextField colorField;
    private JTextField speciesField;
    private JTextField breedField;
    private JFormattedTextField dateOfBirthField;
    private JFormattedTextField microchipField;
    private JFormattedTextField rabiesTagField;
    private JFormattedTextField weightField;
    private JButton saveButton;
    private JButton closeButton;
    private ClientController clientController;
    private JPanel examPanel;
    private JPanel cardPanel;
    private JButton examButton;
    private JButton appointmentsButton;
    private JButton vaccinationsButton;
    private JButton invoicesButton;
    private CardLayout cardLayout;

    private PetAppointmentsTable appointmentsPanel;
    private PetVaccinationsTable vaccinationsPanel;
    private PetInvoiceTable invoicesPanel;
    private NumberFormat numberFormat;
    private DateTimeFormatter dateFormat;

    public PetInfoView() {
        clientController = ClientController.getInstance();
        clientController.registerView(this);
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(false);
        dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        createUI();
    }

    private void createUI() {
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        nameField = new JTextField(20);
        saveButton = new JButton("Save");
        closeButton = new JButton("Close");

        headerPanel.add(nameField);
        headerPanel.add(saveButton);
        headerPanel.add(closeButton);

        JPanel petInfoPanel = createPetInfoSection();

        JPanel contentPanel = new JPanel();
        BoxLayout layout = new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS);
        petInfoPanel.setPreferredSize(new Dimension(contentPanel.getWidth(), 100));
        contentPanel.setLayout(layout);

        contentPanel.add(headerPanel);
        contentPanel.add(petInfoPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        examButton = new JButton("Exams");
        buttonPanel.add(examButton);
        appointmentsButton = new JButton("Appointments");
        buttonPanel.add(appointmentsButton);
        vaccinationsButton = new JButton("Vaccinations");
        buttonPanel.add(vaccinationsButton);
        invoicesButton = new JButton("Invoices");
        buttonPanel.add(invoicesButton);

        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        examPanel = new ExamTable();
        appointmentsPanel = new PetAppointmentsTable();
        vaccinationsPanel = new PetVaccinationsTable();
        invoicesPanel = new PetInvoiceTable();
        cardPanel.add(examPanel, "examPanel");
        cardPanel.add(appointmentsPanel, "appointmentsPanel");
        cardPanel.add(vaccinationsPanel, "vaccinationsPanel");
        cardPanel.add(invoicesPanel, "invoicesPanel");
        cardLayout.show(cardPanel, "examPanel");

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.NORTH);
        bottomPanel.add(cardPanel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);

        createEventListeners();
    }

    private JPanel createPetInfoSection() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel patientIDPanel = new JPanel();
        patientIDPanel.setLayout(new BoxLayout(patientIDPanel, BoxLayout.Y_AXIS));
        JLabel patientIDLabel = new JLabel("Patient ID:");
        patientIDField = new JTextField(20);
        patientIDField.setEditable(false);
        patientIDPanel.add(patientIDLabel);
        patientIDPanel.add(patientIDField);
        contentPanel.add(patientIDPanel);

        JPanel clientNamePanel = new JPanel();
        clientNamePanel.setLayout(new BoxLayout(clientNamePanel, BoxLayout.Y_AXIS));
        JLabel clientNameLabel = new JLabel("Client Name:");
        clientNameField = new JTextField(20);
        clientNameField.setEditable(false);
        clientNamePanel.add(clientNameLabel);
        clientNamePanel.add(clientNameField);
        contentPanel.add(clientNamePanel);

        JPanel sexPanel = new JPanel();
        sexPanel.setLayout(new BoxLayout(sexPanel, BoxLayout.Y_AXIS));
        JLabel sexLabel = new JLabel("Sex:");
        sexField = new JTextField(20);
        sexPanel.add(sexLabel);
        sexPanel.add(sexField);
        contentPanel.add(sexPanel);

        JPanel colorPanel = new JPanel();
        colorPanel.setLayout(new BoxLayout(colorPanel, BoxLayout.Y_AXIS));
        JLabel colorLabel = new JLabel("Color:");
        colorField = new JTextField(20);
        colorPanel.add(colorLabel);
        colorPanel.add(colorField);
        contentPanel.add(colorPanel);

        JPanel speciesPanel = new JPanel();
        speciesPanel.setLayout(new BoxLayout(speciesPanel, BoxLayout.Y_AXIS));
        JLabel speciesLabel = new JLabel("Species:");
        speciesField = new JTextField(20);
        speciesPanel.add(speciesLabel);
        speciesPanel.add(speciesField);
        contentPanel.add(speciesPanel);

        JPanel breedPanel = new JPanel();
        breedPanel.setLayout(new BoxLayout(breedPanel, BoxLayout.Y_AXIS));
        JLabel breedLabel = new JLabel("Breed:");
        breedField = new JTextField(20);
        breedPanel.add(breedLabel);
        breedPanel.add(breedField);
        contentPanel.add(breedPanel);

        JPanel dateOfBirthPanel = new JPanel();
        dateOfBirthPanel.setLayout(new BoxLayout(dateOfBirthPanel, BoxLayout.Y_AXIS));
        JLabel dateOfBirthLabel = new JLabel("Date of Birth:");
        dateOfBirthField = new JFormattedTextField(dateFormat);
        dateOfBirthPanel.add(dateOfBirthLabel);
        dateOfBirthPanel.add(dateOfBirthField);
        contentPanel.add(dateOfBirthPanel);

        JPanel microchipPanel = new JPanel();
        microchipPanel.setLayout(new BoxLayout(microchipPanel, BoxLayout.Y_AXIS));
        JLabel microchipLabel = new JLabel("Microchip:");
        microchipField = new JFormattedTextField(numberFormat);
        microchipPanel.add(microchipLabel);
        microchipPanel.add(microchipField);
        contentPanel.add(microchipPanel);

        JPanel rabiesTagPanel = new JPanel();
        rabiesTagPanel.setLayout(new BoxLayout(rabiesTagPanel, BoxLayout.Y_AXIS));
        JLabel rabiesTagLabel = new JLabel("Rabies Tag:");
        rabiesTagField = new JFormattedTextField(numberFormat);
        rabiesTagPanel.add(rabiesTagLabel);
        rabiesTagPanel.add(rabiesTagField);
        contentPanel.add(rabiesTagPanel);

        JPanel weightPanel = new JPanel();
        weightPanel.setLayout(new BoxLayout(weightPanel, BoxLayout.Y_AXIS));
        JLabel weightLabel = new JLabel("Weight (lbs):");
        weightField = new JFormattedTextField(numberFormat);
        weightPanel.add(weightLabel);
        weightPanel.add(weightField);
        contentPanel.add(weightPanel);

        return contentPanel;
    }

    private void createEventListeners() {
        saveButton.addActionListener(e -> {
            updatePet();
        });

        closeButton.addActionListener(e -> {
            clientController.closePetInfoView();
        });

        examButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "examPanel");
        });

        appointmentsButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "appointmentsPanel");
        });

        vaccinationsButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "vaccinationsPanel");
        });

        invoicesButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "invoicesPanel");
        });
    }

    private void updatePet()
    {
        String petBirthDate = dateOfBirthField.getText();
        String name = nameField.getText();
        String species = speciesField.getText();
        String breed = breedField.getText();
        String color = colorField.getText();
        String sex = sexField.getText();
        long weight = numberFormat.parse(weightField.getText(), new ParsePosition(0)).longValue();
        long microchipNumber = numberFormat.parse(microchipField.getText(), new ParsePosition(0)).longValue();
        long rabiesTag = numberFormat.parse(rabiesTagField.getText(), new ParsePosition(0)).longValue();

        clientController.updatePet(name, sex, color, species, breed, petBirthDate, weight, microchipNumber, rabiesTag);
    }

    public void refresh() {
        Pet pet = clientController.getPet(this.clientController.getCurrentPetID());
        if (pet != null) {
            Client client = clientController.getClient(pet.getOwnerID());
            nameField.setText(pet.getName());
            patientIDField.setText(Long.toString(pet.getPetID()));
            clientNameField.setText(client.getName());
            sexField.setText(pet.getSex());
            colorField.setText(pet.getColor());
            speciesField.setText(pet.getSpecies());
            breedField.setText(pet.getBreed());
            microchipField.setText(Long.toString(pet.getMicrochipNumber()));
            rabiesTagField.setText(Long.toString(pet.getRabiesTag()));
            weightField.setText(Long.toString(pet.getWeight()));
            if (pet.getBirthdate() != null) {
                String dob = pet.getBirthdate().format(dateFormat);
                dateOfBirthField.setText(dob);
            }
        }
    }
}