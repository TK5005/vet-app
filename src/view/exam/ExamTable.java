package view.exam;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import control.ClientController;
import control.IVetAppView;
import model.Exam;

public class ExamTable extends JPanel implements IVetAppView {

    private JButton newExamButton;
    private JTable examTable;
    private DefaultTableModel tableModel;

    private ClientController clientController;

    public ExamTable() {
        clientController = ClientController.getInstance();
        clientController.registerView(this);
        createUI();
    }

    private void createUI() {
        this.setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        newExamButton = new JButton("Add New Exam");
        JPanel examTableHeader = new JPanel();
        examTableHeader.setLayout(new FlowLayout(FlowLayout.LEFT));
        examTableHeader.setBackground(Color.WHITE);
        examTableHeader.add(newExamButton);

        // Create the table with two columns
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make the action cell editable
                return column == 2;
            }
        };

        tableModel.addColumn("Reason for Visit");
        tableModel.addColumn("Date");
        tableModel.addColumn("Actions");

        examTable = new JTable(tableModel);
        examTable.setRowHeight(50);
        examTable.setBackground(Color.WHITE);
        examTable.getTableHeader().setOpaque(false);
        examTable.getTableHeader().setBackground(new Color(173, 216, 230));

        // Custom renderer for the pet column
        examTable.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(Exam.class.cast(value).getDescription());
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
        });

        // Custom renderer and editor for the action column
        examTable.getColumnModel().getColumn(2).setCellRenderer(new ButtonRenderer());
        examTable.getColumnModel().getColumn(2).setCellEditor(new ButtonEditor());

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(examTable);

        this.add(examTableHeader, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);

        addEventListeners();
    }

    private void addEventListeners() {
        newExamButton.addActionListener(e -> {
            clientController.addExam();
        });
    }

    public void refresh() {
        // Clear the table
        tableModel.setRowCount(0);

        // Get the pets data from ClientController
        Exam[] exams = clientController.getExams(clientController.getCurrentPetID());

        // Populate the table with pet data
        for (Exam exam : exams) {
            Object[] rowData = { exam, exam.getDate(), "" };
            tableModel.addRow(rowData);
        }
    }

    // Custom renderer
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton viewButton;
        private JButton invoiceButton;

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER));
            setBackground(Color.WHITE);
            viewButton = new JButton("View Exam Record");
            invoiceButton = new JButton("Generate Invoice");
            add(viewButton);
            add(invoiceButton);
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
        protected JButton invoiceButton;
        private int currentRow;

        public ButtonEditor() {
            super(new JTextField());

            panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panel.setBackground(Color.WHITE);
            viewButton = new JButton("View Exam Record");
            invoiceButton = new JButton("Generate Invoice");

            panel.add(viewButton);
            panel.add(invoiceButton);

            // Add action listener for the View button
            viewButton.addActionListener(e -> {
                Exam exam = (Exam) tableModel.getValueAt(currentRow, 0);
                clientController.showExamInfo(exam.getExamID());
            });

            //Add action listener for Invoice Button
            invoiceButton.addActionListener(e -> {
                Exam exam = (Exam) tableModel.getValueAt(currentRow,0);
                clientController.generateInvoice(exam);
                JOptionPane.showMessageDialog(null, "Invoice has been created");
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            this.currentRow = row; // Capture the current row
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }
}