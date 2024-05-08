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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import control.AppController;
import control.AppointmentController;
import control.IVetAppView;
import model.Appointment;
import model.Client;
import model.Pet;
import view.appointmentView.*;

/**
 * AppointmentPanel
 */
public class AppointmentPanel extends JPanel implements IVetAppView {
    private AppointmentController controller;
    private final JPanel topPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel bottomJPanel = new JPanel(cardLayout);
    private DefaultTableModel tableModel;
    viewDetails details;
    NewAppointment app;

    public AppointmentPanel(AppointmentController controller) {
        this.controller = controller;
        createUI();
    }

    public AppointmentPanel(){
        this.controller = AppointmentController.getInstance();
        controller.registerView(this);
        createUI();
    }

    public void refresh(){
        tableModel.setRowCount(0);

        Appointment[] appointments = controller.getAppointments();

        for(Appointment app : appointments){
            Client client = controller.getClient(app.getClientID());
            Pet pet = controller.getPet(app.getPetID());
            Object[] rowData = {app.getAppointmentID(),client.getName(), pet.getName(),
                    app.getAppointmentDate()};

            tableModel.addRow(rowData);
        }

    }

    private void createUI() {
        setLayout(new BorderLayout());
        
        Object[] columns = {"Appointment ID", "Owner Name", "Pet Name", "Appointment Time","Action"};
        Appointment[] returnedData = controller.getAppointments();
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
    private void setTable(Appointment[] data, Object[] col) {
         tableModel = new DefaultTableModel(){
            public boolean isCellEditable(int row, int column){
                return column==4;
            }
        };

        tableModel.setColumnIdentifiers(col);
        JTable table = new JTable(tableModel);

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
        //Remove the ID column from customer view
        table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0));
        table.setRowHeight(50);
        table.setVisible(true);

        refresh();

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
            viewButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int appID = Integer.parseInt(table.getModel().getValueAt(currentRow, 0).toString());
                    app = new NewAppointment(controller,appID);
                }
            });

            // Add action listener for the Remove button
            removeButton.addActionListener(e -> {
                int appID = Integer.parseInt(table.getModel().getValueAt(currentRow, 0).toString());
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
