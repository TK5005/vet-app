package view.treatment;

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

import control.ClientController;
import control.IVetAppView;
import model.Treatment;

public class TreatmentTable extends JPanel implements IVetAppView {
    private JTable treatmentTable;
    private DefaultTableModel tableModel;
    private JButton addTreatmentButton;
    private ClientController clientController;
    private TreatmentInfo treatmentInfo;
    
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
            clientController.addTreatment(clientController.getCurrentExamID());
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

    private void showTreatmentInfo(int treatmentID)
    {
        treatmentInfo = new TreatmentInfo(treatmentID);
        JDialog treatmentDialog = new JDialog();
        treatmentDialog.add(treatmentInfo);
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
            clientController.removeTreatment(id);
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
                int treatmentID = Integer.parseInt(table.getValueAt(currentRow, 0).toString());
                ConfirmDeletion(treatmentID);
            });

            // Add action listener for the View button
            viewButton.addActionListener(e -> {
                int treatmentID = Integer.parseInt(table.getValueAt(currentRow, 0).toString());
                showTreatmentInfo(treatmentID);
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
