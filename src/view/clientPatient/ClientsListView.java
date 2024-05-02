package view.clientPatient;

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
import control.IVetAppView;
import model.Client;

public class ClientsListView extends JPanel implements IVetAppView {
    private JButton addButton;
    private JTable clientsTable;
    private DefaultTableModel tableModel;
    private ClientController clientController;

    public ClientsListView() {
        clientController = ClientController.getInstance();
        clientController.registerView(this);

        setLayout(new BorderLayout());

        createUI();

        refresh();
    }

    private void createUI()
    {
        this.setBackground(Color.WHITE);
        JPanel header = new JPanel();
        header.setLayout(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(Color.WHITE);
        addButton = new JButton("Add Client");
        header.add(addButton);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(createClientsTable());
        scrollPane.getViewport().setBackground(Color.WHITE);
        // Add the components to the panel
        add(header, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        createActionListeners();
    }

    private JTable createClientsTable()
    {    
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
        clientsTable.setBackground(Color.WHITE);
        clientsTable.setRowHeight(50);
        clientsTable.getTableHeader().setOpaque(false);
        clientsTable.getTableHeader().setBackground(new Color(173, 216, 230));

        // Custom renderer for the client column
        clientsTable.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(Client.class.cast(value).getName());
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
        });

        // Custom renderer and editor for the action column
        clientsTable.getColumnModel().getColumn(1).setCellRenderer(new ButtonRenderer());
        clientsTable.getColumnModel().getColumn(1).setCellEditor(new ButtonEditor());

        return clientsTable;
    }

    private void createActionListeners() {
        addButton.addActionListener(e -> {
            clientController.addClient();
        });
    }

    public void refresh() {
        refreshTable();
    }

    private void refreshTable() {
        // Clear the table
        tableModel.setRowCount(0);

        // Get the clients data from ClientController
        Client[] clients = clientController.getClients();

        // Populate the table with client data
        for (Client client : clients) {
            Object[] rowData = { client, "" };
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
                Client client = (Client) table.getModel().getValueAt(currentRow, 0);
                clientController.deleteClient(client.getClientID());
            });

            // Add action listener for the View button
            viewButton.addActionListener(e -> {
                Client client = (Client) table.getModel().getValueAt(currentRow, 0);
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