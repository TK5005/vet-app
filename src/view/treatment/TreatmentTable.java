package view.treatment;

import control.ClientController;
import control.IClientView;
import model.Client;
import model.Treatment;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;

public class TreatmentTable extends JPanel implements IClientView {
    private JTable treatmentTable;
    private DefaultTableModel tableModel;
    private JButton addTreatmentButton;
    private ClientController clientController;
    
    public TreatmentTable() {
        clientController = ClientController.getInstance();
        clientController.registerView(this);
        createUI();
        createEventListeners();
        refresh();
    }

    public void refresh() {
        // Clear the table
        tableModel.setRowCount(0);

        Treatment[] treatments = clientController.getTreatments();
        for (Treatment treatment : treatments) {
            tableModel.addRow(new Object[] {
                treatment.getTreatmentID(),
                treatment.getTreatmentTypeString(),
                treatment.getStartDate(),
                treatment.getEndDate()
            });
        }
    }

    public void createEventListeners() {
        addTreatmentButton.addActionListener(e -> {

        });
    }

    public void createUI() {
        this.setLayout(new BorderLayout());

        // Create the table with two columns
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make the action cell editable
                return column == 4;
            }
        };

        tableModel.addColumn("ID");
        tableModel.addColumn("Type");
        tableModel.addColumn("Start Date");
        tableModel.addColumn("End Date");
        tableModel.addColumn("");

        treatmentTable = new JTable(tableModel);
        treatmentTable.setBackground(Color.WHITE);
        treatmentTable.setRowHeight(50);
        treatmentTable.getTableHeader().setOpaque(false);
        treatmentTable.getTableHeader().setBackground(new Color(173, 216, 230));

        // Custom renderer and editor for the action column
        treatmentTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        treatmentTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor());

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(treatmentTable);
        scrollPane.setBackground(Color.WHITE);

        addTreatmentButton = new JButton("Add Treatment");

        this.add(addTreatmentButton, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
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
                
            });

            // Add action listener for the View button
            viewButton.addActionListener(e -> {
                
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
