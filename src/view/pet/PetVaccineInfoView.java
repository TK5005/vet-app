package view.pet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.time.LocalDate;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.lgooddatepicker.components.DatePicker;

import control.ClientController;
import control.IClientView;
import model.Vaccination;

public class PetVaccineInfoView extends JPanel implements IClientView
{
    ClientController clientController;
    private JButton saveButton;
    private JButton closeButton;
    private JTextField vaccineIDField;
    private JTextField vaccineNameField;
    private DatePicker vaccineDateField;

    public PetVaccineInfoView()
    {
        clientController = ClientController.getInstance();
        clientController.registerView(this);
        createUI();
        createEventListeners();
    }

    public void refresh()
    {
        Vaccination vaccination = clientController.getVaccination(clientController.getCurrentVaccinationID());
        if(vaccination != null)
        {
            vaccineIDField.setText(String.valueOf(vaccination.getVaccinationId()));
            vaccineNameField.setText(vaccination.getName());
            vaccineDateField.setDate(vaccination.getDate());
        }
    }

    private void createEventListeners()
    {
        saveButton.addActionListener(e -> {
            // Save the vaccine information
            saveVaccineData();
        });

        closeButton.addActionListener(e -> {
            // Close the window
            clientController.closeVaccinationInfoView();
        });
    }

    private void saveVaccineData()
    {
        int vaccineID = Integer.parseInt(vaccineIDField.getText());
        String vaccineName = vaccineNameField.getText();
        LocalDate vaccineDate = vaccineDateField.getDate();
        clientController.updateVaccination(vaccineID, vaccineName, vaccineDate);
    }

    private void createUI()
    {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);

        JPanel header = createHeader();
        JPanel infoPanel = createInfoPanel();

        this.add(header, BorderLayout.NORTH);
        this.add(infoPanel, BorderLayout.CENTER);
    }

    private JPanel createHeader()
    {
        JPanel header = new JPanel();
        header.setLayout(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(Color.WHITE);

        saveButton = new JButton("Save");
        closeButton = new JButton("Close");

        header.add(saveButton);
        header.add(closeButton);

        return header;
    }

    private JPanel createInfoPanel()
    {
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JPanel vaccineIDPanel = new JPanel();
        vaccineIDPanel.setBackground(Color.WHITE);
        vaccineIDPanel.setLayout(new BoxLayout(vaccineIDPanel, BoxLayout.Y_AXIS));
        JLabel vaccineLabel = new JLabel("Vaccine ID:");
        vaccineIDField = new JTextField(20);
        vaccineIDField.setEditable(false);
        vaccineIDPanel.add(vaccineLabel);
        vaccineIDPanel.add(vaccineIDField);

        JPanel vaccineNamePanel = new JPanel();
        vaccineNamePanel.setBackground(Color.WHITE);
        vaccineNamePanel.setLayout(new BoxLayout(vaccineNamePanel, BoxLayout.Y_AXIS));
        JLabel vaccineNameLabel = new JLabel("Vaccine Name:");
        vaccineNameField = new JTextField(20);
        vaccineNamePanel.add(vaccineNameLabel);
        vaccineNamePanel.add(vaccineNameField);

        JPanel vaccineDatePanel = new JPanel();
        vaccineDatePanel.setBackground(Color.WHITE);
        vaccineDatePanel.setLayout(new BoxLayout(vaccineDatePanel, BoxLayout.Y_AXIS));
        JLabel vaccineDateLabel = new JLabel("Vaccine Date:");
        vaccineDateField = new DatePicker();
        vaccineDatePanel.add(vaccineDateLabel);
        vaccineDatePanel.add(vaccineDateField);

        infoPanel.add(vaccineIDPanel);
        infoPanel.add(vaccineNamePanel);
        infoPanel.add(vaccineDatePanel);

        return infoPanel;
    }
}