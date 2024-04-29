package ui;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import control.AppController;
import view.appointmentView.*;

/**
 * AppointmentPanel
 */
public class AppointmentPanel extends JPanel {
    private AppController controller;
    private final JPanel topPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel bottomJPanel = new JPanel(cardLayout);
    viewDetails details;
    NewAppointment app;
    public AppointmentPanel(AppController controller) {
        this.controller = controller;
        createUI();
    }

    private void createUI() {
        setLayout(new BorderLayout());
        
        Object[] columns = { "ID","Owner Name", "Pet Name", "Phone #", "Appointment","Action"};
        Object[][] returnedData = controller.loadAppointments();
        JButton newButton = new JButton("+ New Appointment");
        newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                app = new NewAppointment(controller);                
                
            }
        });
        topPanel.add(newButton);
        setTable(returnedData, columns);
        add(topPanel, BorderLayout.NORTH);
    }
    private void setTable(Object[][] data, Object[] col) {

        JTable table = new JTable(data, col);
        setCellsAlignment(table, SwingConstants.CENTER);
        Dimension d = table.getPreferredSize();
        table.setPreferredScrollableViewportSize(d);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new Color(173, 216, 230));
        // Custom renderer and editor for the action column
        table.getColumnModel().getColumn(table.getModel().getColumnCount()-1).setPreferredWidth(150);;
        table.getColumnModel().getColumn(table.getModel().getColumnCount()-1).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(table.getModel().getColumnCount()-1).setCellEditor(new ButtonEditor());
        adjustHeight(table);
        table.setVisible(true);

        JScrollPane panel = new JScrollPane(table);
        panel.getViewport().setBackground(Color.WHITE);
        bottomJPanel.add(panel);
        add(bottomJPanel, BorderLayout.CENTER);
        repaint();
        revalidate();
    }

    // center text class
    private void setCellsAlignment(JTable table, int alignment) {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(alignment);

        TableModel tableModel = table.getModel();

        for (int columnIndex = 0; columnIndex < tableModel.getColumnCount(); columnIndex++) {
            table.getColumnModel().getColumn(columnIndex).setCellRenderer(rightRenderer);
        }
    }
    //update row height
    private void adjustHeight(JTable table){
        for (int row = 0; row < table.getRowCount(); row++)
        {
            int rowHeight = table.getRowHeight();
            for (int column = 0; column < table.getColumnCount(); column++)
            {
                Component comp = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
                rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
            }
                table.setRowHeight(row, rowHeight);
        }
    }
    private void navButtonPressed(ActionEvent e) {
        JButton pressedButton = (JButton) e.getSource();
        cardLayout.show(bottomJPanel, pressedButton.getText());
    }
    public void ConfirmDeletion(int id){
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
          // controller.deleteAppoitment();
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

            viewButton = new JButton("View");
            JButton removeButton = new JButton("Remove");

            panel.add(viewButton);
            panel.add(removeButton);

            // Add action listener for the View button
            viewButton.addActionListener(e -> {
                int appID = Integer.parseInt(table.getValueAt(currentRow, 0).toString());
                //controller.getAppoitment(appID);
                String[][] data = { {"1", "Smith Henry", "Brandy", "4/28/2024 8:00AM", "test" },
                { "2","Mary", "Sassy", "443-890-1234", "5/23/2024 2:30PM", "" } };
                String[] colName = {"ID", "Name","Pet Name","Appointment Time","Description"};
                details = new viewDetails(colName, data);
                details.setVisible(true);
            });

            // Add action listener for the Remove button
            removeButton.addActionListener(e -> {
                int appID = Integer.parseInt(table.getValueAt(currentRow, 0).toString());
                ConfirmDeletion(appID);
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
