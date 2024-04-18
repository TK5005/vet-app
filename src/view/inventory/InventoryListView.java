package view.inventory;

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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import control.IInventoryView;
import control.InventoryController;
import model.Inventory;

public class InventoryListView extends JPanel implements IInventoryView{
    private InventoryController controller;

    private JButton newItemButton;
    private DefaultTableModel tableModel;

    public InventoryListView() {
        controller = InventoryController.getInstance();
        controller.registerView(this);
        createUI();
        createEventListeners();
    }

    public void refresh() {
        // Clear the table
        tableModel.setRowCount(0);

        // Get the inventory data from InventoryController
        Inventory[] inventory = controller.getInventory();

        // Populate the table with inventory data
        for (Inventory inventoryItem : inventory) {
            Object[] rowData = {inventoryItem.getItemID(), inventoryItem.getName(), inventoryItem.getQuantity()};
            tableModel.addRow(rowData);
        }
    }

    private void createUI() {
        this.setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setLayout(new FlowLayout(FlowLayout.LEFT));
        newItemButton = new JButton("New Item");
        header.add(newItemButton);
        this.add(header, BorderLayout.NORTH);

        JPanel inventoryTable = createInventoryTable();
        this.add(inventoryTable, BorderLayout.CENTER);
    }

    private void createEventListeners() {
        newItemButton.addActionListener(e -> {
            controller.addNewItem();
        });
    }

    private JPanel createInventoryTable() {
        JPanel inventoryList = new JPanel();
        inventoryList.setLayout(new BorderLayout());

        // Create the table with two columns
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make the action cell editable
                return column == 3;
            }
        };

        tableModel.addColumn("Item #");
        tableModel.addColumn("Item Name");
        tableModel.addColumn("Item Quantity");
        tableModel.addColumn("Actions");

        JTable inventoryTable = new JTable(tableModel);
        inventoryTable.setRowHeight(50);
        inventoryTable.getTableHeader().setOpaque(false);
        inventoryTable.getTableHeader().setBackground(new Color(173, 216, 230));

        // Custom renderer and editor for the action column
        inventoryTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
        inventoryTable.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor());

        refresh();

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(inventoryTable);

        inventoryList.add(scrollPane, BorderLayout.CENTER);

        return inventoryList;
    }

    // Custom renderer
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton viewButton;
        private JButton removeButton;

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER));
            viewButton = new JButton("View Details");
            add(viewButton);
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

            viewButton = new JButton("View Details");

            panel.add(viewButton);

            // Add action listener for the View button
            viewButton.addActionListener(e -> {
                // TODO: Open Inventory item details view
                System.out.println("View button clicked");
                //Client client = (Client) table.getModel().getValueAt(currentRow, 0);
                //clientController.showClientInfo(client.getClientID());
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
