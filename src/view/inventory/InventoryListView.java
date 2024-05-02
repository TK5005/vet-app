package view.inventory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import control.IVetAppView;
import control.InventoryController;
import model.Inventory;
import view.treatment.TreatmentInfo;

public class InventoryListView extends JPanel implements IVetAppView{
    private InventoryController controller;

    private JButton newItemButton;
    private DefaultTableModel tableModel;
    private InventoryInfo inventoryInfo;

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
        setBackground(Color.WHITE);
        JPanel header = new JPanel();
        header.setLayout(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(Color.WHITE);
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
        inventoryList.setBackground(Color.WHITE);
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
        inventoryTable.setBackground(Color.WHITE);
        inventoryTable.setRowHeight(50);
        inventoryTable.getTableHeader().setOpaque(false);
        inventoryTable.getTableHeader().setBackground(new Color(173, 216, 230));

        // Custom renderer and editor for the action column
        inventoryTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
        inventoryTable.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor());

        refresh();

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        scrollPane.setBackground(Color.WHITE);
        inventoryList.add(scrollPane, BorderLayout.CENTER);

        return inventoryList;
    }

    private void showInventoryDetails(int itemID)
    {
        inventoryInfo = new InventoryInfo(itemID);
        JDialog treatmentDialog = new JDialog();
        treatmentDialog.add(inventoryInfo);
        treatmentDialog.pack();
        treatmentDialog.setLocationRelativeTo(null);
        treatmentDialog.setVisible(true);
    }

    private void ConfirmDeletion(int id){
        JFrame frame = new JFrame("Confirm");
        final JOptionPane optionPane = new JOptionPane(
                "Are you sure to delete?",
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION);

        final JDialog dialog = new JDialog(frame, "Confirm",
                                true);
        dialog.setContentPane(optionPane);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                //setLabel("user attempt to close window.");
            }
        });
        optionPane.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                String prop = e.getPropertyName();

                if (dialog.isVisible() && (e.getSource() == optionPane) && (prop.equals(JOptionPane.VALUE_PROPERTY))) {
                    dialog.setVisible(false);
                }
            }
        });
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        int value = ((Integer)optionPane.getValue()).intValue();
        if (value == JOptionPane.YES_OPTION) {
            controller.deleteInventoryItem(id);
        } else if (value == JOptionPane.NO_OPTION) {
           //no - close window
        }
    }

    // Custom renderer
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton viewButton;
        private JButton removeButton;

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER));
            setBackground(Color.WHITE);

            viewButton = new JButton("View Details");
            add(viewButton);

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
        protected JButton viewButton;
        protected JButton removeButton;
        private JTable table;
        private int currentRow;

        public ButtonEditor() {
            super(new JTextField());

            panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panel.setBackground(Color.WHITE);
            viewButton = new JButton("View Details");
            removeButton = new JButton("Remove");

            panel.add(viewButton);
            panel.add(removeButton);

            // Add action listener for the View button
            viewButton.addActionListener(e -> {
                int inventoryID = (int) table.getModel().getValueAt(currentRow, 0);
                showInventoryDetails(inventoryID);
            });

            removeButton.addActionListener(delegate -> {
                int inventoryID = (int) table.getModel().getValueAt(currentRow, 0);
                ConfirmDeletion(inventoryID);
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
