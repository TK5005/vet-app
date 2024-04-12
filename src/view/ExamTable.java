package view;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.BorderLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.table.TableCellRenderer;

import control.ClientController;
import control.IClientView;
import model.Exam;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.Color;

public class ExamTable extends JPanel implements IClientView
{
    
    private JButton newExamButton;
    private JTable examTable;
    private DefaultTableModel tableModel;

    private ClientController clientController;

    public ExamTable()
    {
        clientController = ClientController.getInstance();
        clientController.registerView(this);
        createUI();
    }

    private void createUI()
    {
        this.setLayout(new BorderLayout());

        newExamButton = new JButton("+ New Exam");

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
        examTable.getTableHeader().setOpaque(false);
        examTable.getTableHeader().setBackground(new Color(173, 216, 230));

        // Custom renderer for the pet column
        examTable.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
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

        this.add(newExamButton, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);

        addEventListeners();
    }

    private void addEventListeners()
    {
        newExamButton.addActionListener(e -> {
            clientController.addExam();
        });
    }
    
    public void refresh()
    {
        // Clear the table
        tableModel.setRowCount(0);

        // Get the pets data from ClientController
        Exam[] exams = clientController.getExams(clientController.getCurrentPetID());

        // Populate the table with pet data
        for (Exam exam : exams) {
            Object[] rowData = {exam, exam.getDateTime(), ""};
            tableModel.addRow(rowData);
        }
    }

    // Custom renderer
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton viewButton;
    
        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER));
            viewButton = new JButton("View Exam Record");
            add(viewButton);
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
        private JTable table;
        private int currentRow;
    
        public ButtonEditor() {
            super(new JTextField());
    
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    
            viewButton = new JButton("View Exam Record");
    
            panel.add(viewButton);

            // Add action listener for the View button
            viewButton.addActionListener(e -> {
                Exam exam = (Exam) tableModel.getValueAt(currentRow, 0);
                clientController.showExamInfo(exam.getExamID());
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