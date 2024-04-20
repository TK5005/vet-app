package view.invoice;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import control.IInvoiceView;
import control.InvoiceController;

public class InvoiceListView extends JPanel implements IInvoiceView {
    private InvoiceController controller;
    private JButton addInvoiceButton;
    private JTable invoiceTable;
    private DefaultTableModel tableModel;
    private int actionColumnIndex = 6;

    public InvoiceListView() {
        this.controller = InvoiceController.getInstance();
        controller.registerView(this);
        createUI();
    }

    public void refresh() {
        // Clear the table
        tableModel.setRowCount(0);

        Object[][] tableData = controller.getInvoiceTableData();

        for (Object[] rowData : tableData) {
            tableModel.addRow(rowData);
        }
    }

    private void createEventListeners() {
        addInvoiceButton.addActionListener(e -> {
            controller.addInvoice();
        });
    }

    private void createUI() {
        this.setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        JPanel header = createHeader();
        add(header, BorderLayout.NORTH);

        JScrollPane invoicePane = createInvoiceTable();
        add(invoicePane, BorderLayout.CENTER);

        createEventListeners();
        refresh();
    }

    private JPanel createHeader() {
        JPanel header = new JPanel();
        header.setBackground(Color.WHITE);
        header.setLayout(new FlowLayout(FlowLayout.LEFT));
        addInvoiceButton = new JButton("New Invoice");
        header.add(addInvoiceButton);
        return header;
    }

    private JScrollPane createInvoiceTable() {
        // Create the table with two columns
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make the action cell editable
                return column == actionColumnIndex;
            }
        };

        tableModel.addColumn("Invoice #");
        tableModel.addColumn("Owner Name");
        tableModel.addColumn("Pet Name");
        tableModel.addColumn("Date");
        tableModel.addColumn("Amount Due");
        tableModel.addColumn("Status");
        tableModel.addColumn("Actions");

        invoiceTable = new JTable(tableModel);
        invoiceTable.setRowHeight(50);
        invoiceTable.setBackground(Color.WHITE);
        invoiceTable.getTableHeader().setOpaque(false);
        invoiceTable.getTableHeader().setBackground(new Color(173, 216, 230));

        // Custom renderer and editor for the action column
        invoiceTable.getColumnModel().getColumn(actionColumnIndex).setCellRenderer(new ButtonRenderer());
        invoiceTable.getColumnModel().getColumn(actionColumnIndex).setCellEditor(new ButtonEditor());

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(invoiceTable);
        scrollPane.setBackground(Color.WHITE);
        return scrollPane;
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
        private JTable table;
        private int currentRow;

        public ButtonEditor() {
            super(new JTextField());

            panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panel.setBackground(Color.WHITE);
            viewButton = new JButton("View");
            JButton removeButton = new JButton("Remove");

            panel.add(viewButton);
            panel.add(removeButton);

            // Add action listener for the View button
            viewButton.addActionListener(e -> {
                int invoiceID = (int) tableModel.getValueAt(currentRow, 0);
                controller.showInvoiceDetail(invoiceID);
            });

            // Add action listener for the Remove button
            removeButton.addActionListener(e -> {
                int invoiceID = (int) tableModel.getValueAt(currentRow, 0);
                controller.removeInvoice(invoiceID);
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