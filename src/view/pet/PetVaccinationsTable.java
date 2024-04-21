package view.pet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import control.ClientController;
import control.IClientView;
import model.Vaccination;

public class PetVaccinationsTable extends JPanel implements IClientView {
    private ClientController clientController;
    private DefaultTableModel tableModel;
    private JButton addVaccinationButton;

    public PetVaccinationsTable() {
        clientController = ClientController.getInstance();
        clientController.registerView(this);
        createUI();
    }

    public void createUI() {
        setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());
        JPanel vaccinationTable = createVaccinationTable();
        this.add(vaccinationTable, BorderLayout.CENTER);

        createEventListeners();
    }

    private void createEventListeners() {
        addVaccinationButton.addActionListener(e -> {
            clientController.addVaccination(clientController.getCurrentClientID());
        });
    }

    private JPanel createVaccinationTable() {
        JPanel vaccinationPanel = new JPanel();
        vaccinationPanel.setBackground(Color.WHITE);
        vaccinationPanel.setLayout(new BorderLayout());

        JPanel vaccinationHeader = new JPanel();
        vaccinationHeader.setLayout(new FlowLayout(FlowLayout.LEFT));
        vaccinationHeader.setBackground(Color.WHITE);
        addVaccinationButton = new JButton("Add Vaccination");
        vaccinationHeader.add(addVaccinationButton);

        // Create the table with two columns
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make the action cell editable
                return column == 2;
            }
        };

        tableModel.addColumn("Name");
        tableModel.addColumn("Date");
        tableModel.addColumn("Actions");

        JTable vaccinationTable = new JTable(tableModel);
        vaccinationTable.setBackground(Color.WHITE);
        vaccinationTable.setRowHeight(50);
        vaccinationTable.getTableHeader().setOpaque(false);
        vaccinationTable.getTableHeader().setBackground(new Color(173, 216, 230));

        // Custom renderer for the pet column
        vaccinationTable.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(Vaccination.class.cast(value).getName());
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
        });

        // Custom renderer and editor for the action column
        vaccinationTable.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
        vaccinationTable.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor());

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(vaccinationTable);
        scrollPane.setBackground(Color.WHITE);
        vaccinationPanel.add(vaccinationHeader, BorderLayout.NORTH);
        vaccinationPanel.add(scrollPane, BorderLayout.CENTER);

        return vaccinationPanel;
    }

    public void refresh() {
        // Clear the table
        tableModel.setRowCount(0);

        // Get the pets data from ClientController
        Vaccination[] vaccinations = clientController.getVaccinationsFromPetID(clientController.getCurrentPetID());

        // Populate the table with pet data
        for (Vaccination vaccination : vaccinations) {
            tableModel.addRow(new Object[] { vaccination, vaccination.getDate(), "" });
        }
    }

    // Custom renderer
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton editButton;
        private JButton removeButton;

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER));
            setBackground(Color.WHITE);
            editButton = new JButton("Edit");
            add(editButton);
            removeButton = new JButton("Remove");
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
        protected JButton editButton;
        protected JButton removeButton;
        private JTable table;
        private int currentRow;

        public ButtonEditor() {
            super(new JTextField());

            panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panel.setBackground(Color.WHITE);
            editButton = new JButton("Edit");
            removeButton = new JButton("Remove");
            panel.add(editButton);
            panel.add(removeButton);

            // Add action listener for the View button
            editButton.addActionListener(e -> {
                int vaccinationID = Vaccination.class.cast(tableModel.getValueAt(currentRow, 0)).getVaccinationId();
                clientController.showVaccinationInfo(vaccinationID);
            });

            removeButton.addActionListener(e -> {
                int vaccinationID = Vaccination.class.cast(tableModel.getValueAt(currentRow, 0)).getVaccinationId();
                clientController.removeVaccination(vaccinationID);
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