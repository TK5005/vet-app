package src.ui;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import src.control.AppController;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.table.TableCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.table.TableColumnModel;
import java.awt.Dimension;


/**
 * ClientPatientPanel
 */
public class ClientPatientPanel extends JPanel
{
    private AppController controller;

    public ClientPatientPanel(AppController controller)
    {
        this.controller = controller;
        createUI();
    }

    private void createUI()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel clientPanel = createClientPanel();
        this.add(clientPanel);

        JPanel petInfoPanel = createPetPanel();
        JScrollPane scrollPane = new JScrollPane(petInfoPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPane);
    }

    private JPanel createClientPanel()
    {
        JPanel clientPanel = new JPanel();
        FlowLayout layout = new FlowLayout(FlowLayout.LEFT);
        clientPanel.setLayout(layout);
        
        JPanel clientIDPanel = new JPanel();
        clientIDPanel.setLayout(new BoxLayout(clientIDPanel, BoxLayout.Y_AXIS));
        clientIDPanel.add(new JLabel("Client ID"));
        clientIDPanel.add(new JTextField(10));

        JPanel firstNamePanel = new JPanel();
        firstNamePanel.setLayout(new BoxLayout(firstNamePanel, BoxLayout.Y_AXIS));
        firstNamePanel.add(new JLabel("First Name"));
        firstNamePanel.add(new JTextField(10));

        JPanel lastNamePanel = new JPanel();
        lastNamePanel.setLayout(new BoxLayout(lastNamePanel, BoxLayout.Y_AXIS));
        lastNamePanel.add(new JLabel("Last Name"));
        lastNamePanel.add(new JTextField(10));

        JPanel emailPanel = new JPanel();
        emailPanel.setLayout(new BoxLayout(emailPanel, BoxLayout.Y_AXIS));
        emailPanel.add(new JLabel("Email"));
        emailPanel.add(new JTextField(10));

        JPanel streetPanel = new JPanel();
        streetPanel.setLayout(new BoxLayout(streetPanel, BoxLayout.Y_AXIS));
        streetPanel.add(new JLabel("Street"));
        streetPanel.add(new JTextField(10));

        JPanel cityPanel = new JPanel();
        cityPanel.setLayout(new BoxLayout(cityPanel, BoxLayout.Y_AXIS));
        cityPanel.add(new JLabel("City"));
        cityPanel.add(new JTextField(10));

        JPanel statePanel = new JPanel();
        statePanel.setLayout(new BoxLayout(statePanel, BoxLayout.Y_AXIS));
        statePanel.add(new JLabel("State"));
        statePanel.add(new JTextField(10));

        JPanel zipPanel = new JPanel();
        zipPanel.setLayout(new BoxLayout(zipPanel, BoxLayout.Y_AXIS));
        zipPanel.add(new JLabel("Zip"));
        zipPanel.add(new JTextField(10));

        JPanel phonePanel = new JPanel();
        phonePanel.setLayout(new BoxLayout(phonePanel, BoxLayout.Y_AXIS));
        phonePanel.add(new JLabel("Phone"));
        phonePanel.add(new JTextField(10));

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

    private JPanel createPetPanel()
    {
        JPanel petPanel = new JPanel();
        petPanel.setLayout(new BorderLayout());
        
        PetTableModel petModel = new PetTableModel();
        JTable petTable = new JTable(petModel);

        // Custom renderer and editor for the "Actions" column
        petTable.getColumn("Actions").setCellRenderer(new ButtonRenderer());
        petTable.getColumn("Actions").setCellEditor(new ButtonEditor(new JTextField()));

        // Fill table with dummy data
        petModel.addPet(new Pet("Max", "View Info"));
        petModel.addPet(new Pet("Bella", "View Info"));
        petModel.addPet(new Pet("Charlie", "View Info"));
        petModel.addPet(new Pet("Lucy", "View Info"));
        petModel.addPet(new Pet("Cooper", "View Info"));
        petModel.addPet(new Pet("Cooper", "View Info"));
        petModel.addPet(new Pet("Cooper", "View Info"));
        petModel.addPet(new Pet("Cooper", "View Info"));
        petModel.addPet(new Pet("Cooper", "View Info"));
        petModel.addPet(new Pet("Cooper", "View Info"));
        petModel.addPet(new Pet("Cooper", "View Info"));
        petModel.addPet(new Pet("Cooper", "View Info"));
        petModel.addPet(new Pet("Cooper", "View Info"));
        petModel.addPet(new Pet("Cooper", "View Info"));

        JPanel petPanelHeader = new JPanel();
        petPanelHeader.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel petLabel = new JLabel("Pets");
        JButton addPetButton = new JButton("Add Pet");
        petPanelHeader.add(petLabel);
        petPanelHeader.add(Box.createHorizontalStrut(10));
        petPanelHeader.add(addPetButton);
        petPanel.add(petPanelHeader, BorderLayout.NORTH);
        petPanel.add(petTable, BorderLayout.CENTER);
        Dimension maxDimension = new Dimension(500, petPanel.getPreferredSize().height);
        petPanel.setMaximumSize(maxDimension);

        return petPanel;
    }
}

class Pet {
    String name;
    String action;

    public Pet(String name, String action) {
        this.name = name;
        this.action = action;
    }
}

class PetTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Pet Name", "Actions"};
    private final Class<?>[] columnClasses = {String.class, JButton.class};
    private java.util.List<Pet> pets = new java.util.ArrayList<>();

    public void addPet(Pet pet) {
        pets.add(pet);
        fireTableRowsInserted(pets.size() - 1, pets.size() - 1);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return pets.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Pet pet = pets.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return pet.name;
            case 1:
                return "View Info";
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 1; // Only the action column is editable
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 1) {
            // Here you can define what happens when the button is clicked
            System.out.println(pets.get(rowIndex).name + " Info");
        }
    }
}

class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? "" : value.toString());
        return this;
    }
}

class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private String label;
    private boolean isPushed;

    public ButtonEditor(JTextField checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> fireEditingStopped());
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        if (isSelected) {
            button.setForeground(table.getSelectionForeground());
            button.setBackground(table.getSelectionBackground());
        } else {
            button.setForeground(table.getForeground());
            button.setBackground(UIManager.getColor("Button.background"));
        }
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        return button;
    }

    public Object getCellEditorValue() {
        if (isPushed) {
            // Show a dialog with pet info or perform another action here
            JOptionPane.showMessageDialog(button, label + ": Placeholder for pet info");
        }
        isPushed = false;
        return label;
    }

    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}