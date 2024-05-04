package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import control.AppController;
import model.Tech;

public class NewTech {
    
    private AppController controller;
    private final Color selectedColor = new Color(173, 216, 230);
    Tech newTech;
    JTextField id;
    JTextArea cert;

    public NewTech(AppController controller)  {
        this.controller = controller;
        createUI();
    }  
    private void createUI(){
        int selection = JOptionPane.showConfirmDialog(null, getPanel(), "New Tech : "
                                , JOptionPane.OK_CANCEL_OPTION
                                , JOptionPane.PLAIN_MESSAGE);
        if (selection == JOptionPane.OK_OPTION) 
        {
            newTech = new Tech();
            newTech.setCertification(cert.getText());
            controller.addTech(newTech);
        }
    }
    private JPanel getPanel()
    {
        JTextField id;
        JTextArea cert;
        JPanel basePanel = new JPanel();
        basePanel.setOpaque(true);
        basePanel.setBackground(selectedColor);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 2, 5, 2));
        centerPanel.setBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5));
        centerPanel.setOpaque(true);
        centerPanel.setBackground(Color.WHITE);

        JLabel idLabel = new JLabel("ID : ");
        id = new JTextField();
        id.setEnabled(false);

        JLabel sLabel = new JLabel("Certification : ");
        cert = new JTextArea();
        JScrollPane panel = new JScrollPane(cert);
        panel.getViewport().setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(30, 30));
        
        centerPanel.add(idLabel);
        centerPanel.add(id);

        centerPanel.add(sLabel);
        centerPanel.add(panel);

        basePanel.add(centerPanel);
        return basePanel;
    }
}
