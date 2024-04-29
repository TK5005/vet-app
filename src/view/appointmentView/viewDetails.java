package view.appointmentView;

import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.table.DefaultTableModel;

import view.appointmentView.viewDetails;

public class viewDetails extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JPopupMenu popupMenu;
    
     
    public viewDetails(String[] colName,String[][] rowData){
        super("Appointment Details");
        tableModel = new DefaultTableModel(rowData, colName);
        table = new JTable(tableModel);
        createUI();
    }
    private void createUI(){
       // sets the popup menu for the table
       table.setComponentPopupMenu(popupMenu);
              
       // adds the table to the frame
       add(new JScrollPane(table));
       setSize(640, 150);
       setLocationRelativeTo(null);
       //setVisible(true);
    }
}
