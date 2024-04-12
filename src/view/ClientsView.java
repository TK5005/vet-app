package view;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import control.ClientController;
import control.IClientView;
import model.Client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientsView extends JPanel implements IClientView
{
    private JButton addButton;
    private JTable clientsTable;
    private DefaultTableModel tableModel;
    private ClientController clientController;

    public ClientsView()
    {
        clientController = ClientController.getInstance();
        clientController.registerView(this);

        setLayout(new BorderLayout());

        // Create the "Add Client" button
        addButton = new JButton("Add Client");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the button click event
                clientController.addClient();
            }
        });

        // Create the table with two columns
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make the action cell editable
                return column == 1;
            }
        };

        tableModel.addColumn("Client");
        tableModel.addColumn("Actions");

        clientsTable = new JTable(tableModel);
        clientsTable.setRowHeight(50);
        clientsTable.getTableHeader().setOpaque(false);
        clientsTable.getTableHeader().setBackground(new Color(173, 216, 230));

        // Custom renderer for the client column
        clientsTable.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(Client.class.cast(value).getName());
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
        });

        // Custom renderer and editor for the action column
        clientsTable.getColumnModel().getColumn(1).setCellRenderer(new ButtonRenderer());
        clientsTable.getColumnModel().getColumn(1).setCellEditor(new ButtonEditor());

        refresh();

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(clientsTable);

        // Add the components to the panel
        add(addButton, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void refresh()
    {
        refreshTable();
    }

    private void refreshTable()
    {
        // Clear the table
        tableModel.setRowCount(0);

        // Get the clients data from ClientController
        Client[] clients = clientController.getClients();

        // Populate the table with client data
        for (Client client : clients) {
            Object[] rowData = {client, ""};
            tableModel.addRow(rowData);
        }
    }

    // Custom renderer
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton viewButton;
        private JButton removeButton;
    
        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER));
            viewButton = new JButton("View");
            removeButton = new JButton("Remove");
            add(viewButton);
            add(removeButton);
        }
    
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            // You can customize the buttons further here, such as setting a different text based on the cell value
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
    
            viewButton = new JButton("View");
            removeButton = new JButton("Remove");
    
            panel.add(viewButton);
            panel.add(removeButton);
    
            // Add action listener for the Remove button
            removeButton.addActionListener(e -> {
                Client client = (Client)table.getModel().getValueAt(currentRow, 0);
                clientController.deleteClient(client.getClientID());
            });

            // Add action listener for the View button
            viewButton.addActionListener(e -> {
                Client client = (Client)table.getModel().getValueAt(currentRow, 0);
                clientController.showClientInfo(client.getClientID());
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