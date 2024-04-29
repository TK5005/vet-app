package ui;

import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.table.DefaultTableModel;


public class AdminViewDetails extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JPopupMenu popupMenu;
    
    public AdminViewDetails(String[] colName,String[][] rowData){
        super("User Details");
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
