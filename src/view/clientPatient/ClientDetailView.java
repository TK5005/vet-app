package view.clientPatient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import control.ClientController;
import control.IVetAppView;
import model.Client;
import model.Pet;

public class ClientDetailView extends JPanel implements IVetAppView {
    private ClientController clientController;

    private JButton saveButton;
    private JButton closeButton;
    private JTextField clientIDField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField streetField;
    private JTextField cityField;
    private JTextField stateField;
    private JFormattedTextField zipField;
    private JTextField phoneField;
    private JButton addPetButton;
    private DefaultTableModel tableModel;
    private JTable petTable;
    private NumberFormat zipFormat;

    public ClientDetailView() {
        clientController = ClientController.getInstance();
        clientController.registerView(this);
        configureFormatters();
        createUI();
    }

    private void configureFormatters() {
        zipFormat = NumberFormat.getIntegerInstance();
        zipFormat.setGroupingUsed(false);
        zipFormat.setMaximumIntegerDigits(5);
    }

    private void createUI() {
        // Set the layout of the panel
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Create the header of the panel
        JPanel panelHeader = new JPanel();
        panelHeader.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelHeader.setBackground(Color.WHITE);
        saveButton = new JButton("Save");
        closeButton = new JButton("Close");
        panelHeader.add(saveButton);
        panelHeader.add(closeButton);

        // Add the components to the panel
        add(panelHeader, BorderLayout.NORTH);

        JPanel clientInfoPanel = new JPanel();
        clientInfoPanel.setBackground(Color.WHITE);
        BoxLayout layout = new BoxLayout(clientInfoPanel, BoxLayout.Y_AXIS);
        clientInfoPanel.setLayout(layout);

        JPanel clientPanel = createClientPanel();
        clientPanel.setPreferredSize(new Dimension(clientInfoPanel.getWidth(), 100));
        clientInfoPanel.add(clientPanel);

        JPanel petPanel = createPetPanel();
        petPanel.setBackground(Color.WHITE);
        clientInfoPanel.add(petPanel);

        add(clientInfoPanel, BorderLayout.CENTER);

        createEventListeners();
    }

    private JPanel createClientPanel() {
        JPanel clientPanel = new JPanel();
        
        FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
        clientPanel.setLayout(layout);
        clientPanel.setBackground(Color.WHITE);

        JPanel clientIDPanel = new JPanel();
        clientIDPanel.setLayout(new BoxLayout(clientIDPanel, BoxLayout.Y_AXIS));
        clientIDPanel.setBackground(Color.WHITE);
        clientIDPanel.add(new JLabel("Client ID"));
        clientIDField = new JTextField(10);
        clientIDField.setEditable(false);
        clientIDPanel.add(clientIDField);

        JPanel firstNamePanel = new JPanel();
        firstNamePanel.setLayout(new BoxLayout(firstNamePanel, BoxLayout.Y_AXIS));
        firstNamePanel.setBackground(Color.WHITE);
        firstNamePanel.add(new JLabel("First Name"));
        firstNameField = new JTextField(10);
        firstNamePanel.add(firstNameField);

        JPanel lastNamePanel = new JPanel();
        lastNamePanel.setLayout(new BoxLayout(lastNamePanel, BoxLayout.Y_AXIS));
        lastNamePanel.setBackground(Color.WHITE);
        lastNamePanel.add(new JLabel("Last Name"));
        lastNameField = new JTextField(10);
        lastNamePanel.add(lastNameField);

        JPanel emailPanel = new JPanel();
        emailPanel.setLayout(new BoxLayout(emailPanel, BoxLayout.Y_AXIS));
        emailPanel.setBackground(Color.WHITE);
        emailPanel.add(new JLabel("Email"));
        emailField = new JTextField(10);
        emailPanel.add(emailField);

        JPanel streetPanel = new JPanel();
        streetPanel.setLayout(new BoxLayout(streetPanel, BoxLayout.Y_AXIS));
        streetPanel.setBackground(Color.WHITE);
        streetPanel.add(new JLabel("Street"));
        streetField = new JTextField(10);
        streetPanel.add(streetField);

        JPanel cityPanel = new JPanel();
        cityPanel.setLayout(new BoxLayout(cityPanel, BoxLayout.Y_AXIS));
        cityPanel.setBackground(Color.WHITE);
        cityPanel.add(new JLabel("City"));
        cityField = new JTextField(10);
        cityPanel.add(cityField);

        JPanel statePanel = new JPanel();
        statePanel.setLayout(new BoxLayout(statePanel, BoxLayout.Y_AXIS));
        statePanel.setBackground(Color.WHITE);
        statePanel.add(new JLabel("State"));
        stateField = new JTextField(10);
        statePanel.add(stateField);

        JPanel zipPanel = new JPanel();
        zipPanel.setLayout(new BoxLayout(zipPanel, BoxLayout.Y_AXIS));
        zipPanel.setBackground(Color.WHITE);
        zipPanel.add(new JLabel("Zip"));
        zipField = new JFormattedTextField(zipFormat);
        zipPanel.add(zipField);

        JPanel phonePanel = new JPanel();
        phonePanel.setLayout(new BoxLayout(phonePanel, BoxLayout.Y_AXIS));
        phonePanel.setBackground(Color.WHITE);
        phonePanel.add(new JLabel("Phone"));
        phoneField = new JTextField(10);
        phonePanel.add(phoneField);

        clientPanel.add(clientIDPanel);
        clientPanel.add(firstNamePanel);
        clientPanel.add(lastNamePanel);
        clientPanel.add(emailPanel);
        clientPanel.add(streetPanel);
        clientPanel.add(cityPanel);
        clientPanel.add(statePanel);
        clientPanel.add(zipPanel);
        clientPanel.add(phonePanel);

        return clientPanel;
    }

    private JPanel createPetPanel() {
        JPanel petPanel = new JPanel();
        petPanel.setLayout(new BorderLayout());
        petPanel.setBackground(Color.WHITE);

        JPanel petPanelHeader = new JPanel();
        petPanelHeader.setLayout(new FlowLayout(FlowLayout.LEFT));
        petPanelHeader.setBackground(Color.WHITE);
        addPetButton = new JButton("Add Pet");
        petPanelHeader.add(addPetButton);
        
        // Create the table with two columns
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make the action cell editable
                return column == 1;
            }
        };

        tableModel.addColumn("Pets");
        tableModel.addColumn("Actions");

        petTable = new JTable(tableModel);
        petTable.setBackground(Color.WHITE);
        petTable.setRowHeight(50);
        petTable.getTableHeader().setOpaque(false);
        petTable.getTableHeader().setBackground(new Color(173, 216, 230));

        // Custom renderer for the pet column
        petTable.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(Pet.class.cast(value).getName());
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
        });

        // Custom renderer and editor for the action column
        petTable.getColumnModel().getColumn(1).setCellRenderer(new ButtonRenderer());
        petTable.getColumnModel().getColumn(1).setCellEditor(new ButtonEditor());

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(petTable);
        scrollPane.setBackground(Color.WHITE);

        petPanel.add(petPanelHeader, BorderLayout.NORTH);
        petPanel.add(scrollPane, BorderLayout.CENTER);

        return petPanel;
    }

    private void createEventListeners() {
        saveButton.addActionListener(e -> {
            updateClient();
        });

        closeButton.addActionListener(e -> {
            clientController.closeClientInfoView();
        });

        addPetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientController.addPet();
            }
        });
    }

    public void refresh() {
        Client client = clientController.getClient(clientController.getCurrentClientID());

        if (client != null) {
            clientIDField.setText(Integer.toString(client.getClientID()));
            firstNameField.setText(client.getFirstName());
            lastNameField.setText(client.getLastName());
            emailField.setText(client.getEmail());
            streetField.setText(client.getStreet());
            cityField.setText(client.getCity());
            stateField.setText(client.getState());
            zipField.setText(Integer.toString(client.getZip()));
            phoneField.setText(client.getPhone());
        }

        refreshPetTable();
    }

    private void updateClient() {
        String fName = firstNameField.getText();
        String lName = lastNameField.getText();
        String email = emailField.getText();
        String street = streetField.getText();
        String city = cityField.getText();
        String state = stateField.getText();
        int zip = Integer.parseInt(zipField.getText());
        String phone = phoneField.getText();

        clientController.updateClient(clientController.getCurrentClientID(), fName, lName, phone, email, street, city,
                state, zip);
    }

    private void refreshPetTable() {
        // Clear the table
        tableModel.setRowCount(0);

        // Get the pets data from ClientController
        Pet[] pets = clientController.getPets(clientController.getCurrentClientID());

        // Populate the table with pet data
        for (Pet pet : pets) {
            Object[] rowData = { pet, "" };
            tableModel.addRow(rowData);
        }
    }

    // Custom renderer
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton viewButton;
        private JButton removeButton;

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER));
            setBackground(Color.WHITE);
            viewButton = new JButton("View");
            removeButton = new JButton("Remove");
            add(viewButton);
            add(removeButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            // You can customize the buttons further here, such as setting a different text
            // based on the cell value
            return this;
        }
    }

    // Custom editor
    class ButtonEditor extends DefaultCellEditor {
        protected JPanel panel;
        protected JButton viewButton;
        protected JButton removeButton;
        private JTable table;
        private int currentRow;

        public ButtonEditor() {
            super(new JTextField());

            panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panel.setBackground(Color.WHITE);
            viewButton = new JButton("View");
            removeButton = new JButton("Remove");

            panel.add(viewButton);
            panel.add(removeButton);

            // Add action listener for the Remove button
            removeButton.addActionListener(e -> {
                Pet pet = (Pet) table.getModel().getValueAt(currentRow, 0);
                clientController.deletePet(pet.getPetID());
            });

            // Add action listener for the View button
            viewButton.addActionListener(e -> {
                Pet pet = (Pet) table.getModel().getValueAt(currentRow, 0);
                clientController.showPetInfo(pet.getPetID());
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            this.table = table; // Capture the table
            this.currentRow = row; // Capture the current row
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }
}