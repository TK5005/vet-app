package ui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.util.List;

import control.AdminController;
import control.AppController;
import model.Staff;

/**
 * AdminPanel
 */
public class AdminPanel extends JPanel {
    private AppController controller;

    private AdminController adminController;
    private final JPanel topPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel bottomJPanel = new JPanel(cardLayout);
    private DefaultTableModel tableModel;
    AdminViewDetails admin;
    AdminNewUser user;
    NewVet vet;
    NewTech tech;

    public AdminPanel(AppController controller) {
        this.controller = controller;
        createUI();
    }

    private void createUI() {

        setLayout(new BorderLayout());
        Object[] columns = { "ID","User ID", "User Name", "User Role", "Action"};
        //Staff[] returnedData = adminController.getStaff();
        //setTable(returnedData, columns);
        Object[][] returnedData = controller.loadStaff();
        setTable2(returnedData, columns);
        cardLayout.show(bottomJPanel, "View All");
        JButton newButton = new JButton("+ New Staff");
        newButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              user = new AdminNewUser(adminController);
           
            }
        });
        JButton newVet = new JButton("+ New Vet");
        newVet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              vet = new NewVet(adminController);
            }
        });
        JButton newTech = new JButton("+ New Tech");
        newTech.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              tech = new NewTech(adminController);
            }
        });
        topPanel.add(newButton);
        topPanel.add(newVet);
        topPanel.add(newTech);

        add(topPanel, BorderLayout.NORTH);
    }
    private void setTable2(Object[][] data, Object[] col) {
        tableModel = new DefaultTableModel(data, col);
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
        adjustHeight(table);
        table.setVisible(true);

        JScrollPane panel = new JScrollPane(table);
        panel.getViewport().setBackground(Color.WHITE);
        bottomJPanel.add(panel,"View All");
        add(bottomJPanel, BorderLayout.CENTER);
        repaint();
        revalidate();
    }
    private void setTable(Staff[] data, Object[] col) {
        tableModel = new DefaultTableModel();
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
        adjustHeight(table);
        table.setVisible(true);

        JScrollPane panel = new JScrollPane(table);
        panel.getViewport().setBackground(Color.WHITE);
        bottomJPanel.add(panel,"View All");
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
            //controller.removeStaff(id);
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
                int empID = Integer.parseInt(table.getValueAt(currentRow, 0).toString());
                admin = new AdminViewDetails(empID);
                admin.setVisible(true);
            });

            // Add action listener for the Remove button
            removeButton.addActionListener(e -> {
                int empID = Integer.parseInt(table.getValueAt(currentRow, 0).toString());
                ConfirmDeletion(empID);
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