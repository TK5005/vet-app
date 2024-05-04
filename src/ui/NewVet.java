package ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import control.AppController;
import model.Vet;

public class NewVet {
    
    private AppController controller;
    private final Color selectedColor = new Color(173, 216, 230);
   
    Vet newVet;
    JTextField id;
    JTextField licenseNo;
    JTextArea specialty;

    public NewVet(AppController controller)  {
        this.controller = controller;
        createUI();
    }  
    private void createUI(){
        int selection = JOptionPane.showConfirmDialog(null, getPanel(), "New Vet : "
                                , JOptionPane.OK_CANCEL_OPTION
                                , JOptionPane.PLAIN_MESSAGE);
        if (selection == JOptionPane.OK_OPTION) 
        {
            newVet = new Vet();
            newVet.setLicenseNumber(licenseNo.getText());
            newVet.setSpecialty(specialty.getText());
            controller.addVet(newVet);
        }
    }
    private JPanel getPanel()
    {
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

        JLabel licLabel = new JLabel("License Number : ");
        licenseNo = new JTextField();

        JLabel sLabel = new JLabel("Specialty : ");
        specialty = new JTextArea();
        JScrollPane panel = new JScrollPane(specialty);
        panel.getViewport().setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(30, 30));

        centerPanel.add(idLabel);
        centerPanel.add(id);

        centerPanel.add(licLabel);
        centerPanel.add(licenseNo);

        centerPanel.add(sLabel);
        centerPanel.add(panel);

        basePanel.add(centerPanel);
        return basePanel;
    }
}
