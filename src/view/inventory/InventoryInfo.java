package view.inventory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

import control.InventoryController;
import model.Inventory;
import model.Medication;


public class InventoryInfo extends JPanel {
    InventoryController controller;
    int itemID;
    Inventory item;
    Medication medItem;

    JButton saveButton;

    private JFormattedTextField idField;
    private JTextField nameField;
    private JTextField manufacturerField;
    private JComboBox<Inventory.InventoryType> typeBox;
    private JFormattedTextField quantityField;
    private JFormattedTextField reoderLevelField;
    private JFormattedTextField reorderQuantityField;
    private JFormattedTextField wholesaleCostField;
    private JFormattedTextField retailCostField;
    private JTextField dosageField;
    private JTextArea interactionsField;
    private JLabel dosageLabel, interactionsLabel;
    private JPanel interactionsPanel, dosagePanel;

    NumberFormat integerFormat = NumberFormat.getIntegerInstance();
    NumberFormatter numberFormatter = new NumberFormatter(integerFormat) {
        @Override
        public Object stringToValue(String string) throws ParseException {
            Number number = (Number) super.stringToValue(string);
            return number.intValue(); // Always return an Integer
        }
    };

    public InventoryInfo(int itemID) {
        controller = InventoryController.getInstance();
        this.itemID = itemID;
        item = controller.getItem(itemID);
        if(item.getType() == Inventory.InventoryType.MEDICATION){
            medItem = controller.getMedItem(itemID);
            if(medItem == null)
                medItem = new Medication();
        }
        createUI();
        createEventListeners();
    }

    private void loadData()
    {
        idField.setValue(item.getItemID());
        nameField.setText(item.getName());
        manufacturerField.setText(item.getManufacturer());
        typeBox.setSelectedItem(item.getType());
        quantityField.setValue(item.getQuantity());
        reoderLevelField.setValue(item.getReorderLevel());
        reorderQuantityField.setValue(item.getReorderQuantity());
        retailCostField.setValue(item.getRetailCost());
        wholesaleCostField.setValue(item.getWholesaleCost());
        if(medItem != null) {
            dosageField.setText(medItem.getDosage());
            interactionsField.setText(medItem.getInteractions());
        }
    }

    public void saveItem() {
        int id = (Integer) idField.getValue();
        String name = nameField.getText();
        String manufacturer = manufacturerField.getText();
        String type = typeBox.getSelectedItem().toString();
        int quantity = (Integer) quantityField.getValue();
        int reorderLevel = (Integer) reoderLevelField.getValue();
        int reorderQuantity = (Integer) reorderQuantityField.getValue();
        float retailCost = 0.0f;
        float wholesaleCost = 0.0f;
        try{
            retailCost = NumberFormat.getCurrencyInstance().parse(retailCostField.getText()).floatValue();
            wholesaleCost = NumberFormat.getCurrencyInstance().parse(wholesaleCostField.getText()).floatValue();
        } catch (Exception e) {
            System.out.println("Error parsing float");
        }
        if(!type.equals("MEDICATION")) {
            //Check if this was Medication to begin with, Delete if it was
            if(medItem != null)
                controller.deleteMedicationEntry(id);

            controller.updateInventoryItem(id, name, manufacturer, type, quantity, reorderLevel,
                    reorderQuantity, wholesaleCost, retailCost);
        }else {
            String dosage = dosageField.getText();
            String interactions = interactionsField.getText();
            controller.updateMedicationItem(id, name, manufacturer, type, quantity, reorderLevel,
                    reorderQuantity, wholesaleCost, retailCost, dosage, interactions);
        }
    }

    private void createEventListeners() {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveItem();
            }
        });

        typeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(typeBox.getSelectedItem() ==Inventory.InventoryType.MEDICATION){
                    interactionsLabel.setVisible(true);
                    interactionsField.setVisible(true);
                    dosageField.setVisible(true);
                    dosageLabel.setVisible(true);
                }
                else{
                    interactionsLabel.setVisible(false);
                    interactionsField.setVisible(false);
                    dosageField.setVisible(false);
                    dosageLabel.setVisible(false);
                }
            }
        });
    }

    private void createUI() {
        this.setLayout(new BorderLayout());
        saveButton = new JButton("Save");
        add(createInventoryDetails(), BorderLayout.CENTER);
        add(saveButton, BorderLayout.SOUTH);
        loadData();
    }

    private JPanel createInventoryDetails()
    {
        JPanel inventoryDetails = new JPanel();
        inventoryDetails.setLayout(new BoxLayout(inventoryDetails, BoxLayout.Y_AXIS));
        inventoryDetails.setBackground(Color.WHITE);

        JPanel inventoryIDPanel = new JPanel();
        inventoryIDPanel.setLayout(new BoxLayout(inventoryIDPanel, BoxLayout.Y_AXIS));
        inventoryIDPanel.setBackground(Color.WHITE);
        JLabel idLabel = new JLabel("Inventory ID");
        idField = new JFormattedTextField(numberFormatter);
        idField.setEditable(false);
        inventoryIDPanel.add(idLabel);
        inventoryIDPanel.add(idField);

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
        namePanel.setBackground(Color.WHITE);
        JLabel nameLabel = new JLabel("Name");
        nameField = new JTextField(10);
        namePanel.add(nameLabel);
        namePanel.add(nameField);

        JPanel manufacturerPanel = new JPanel();
        manufacturerPanel.setLayout(new BoxLayout(manufacturerPanel, BoxLayout.Y_AXIS));
        manufacturerPanel.setBackground(Color.WHITE);
        JLabel manufacturerLabel = new JLabel("Manufacturer");
        manufacturerField = new JTextField(10);
        manufacturerPanel.add(manufacturerLabel);
        manufacturerPanel.add(manufacturerField);

        JPanel typePanel = new JPanel();
        typePanel.setLayout(new BoxLayout(typePanel, BoxLayout.Y_AXIS));
        typePanel.setBackground(Color.WHITE);
        JLabel typeLabel = new JLabel("Type");
        typeBox = new JComboBox<Inventory.InventoryType>(Inventory.InventoryType.values());
        typePanel.add(typeLabel);
        typePanel.add(typeBox);

        JPanel quantityPanel = new JPanel();
        quantityPanel.setLayout(new BoxLayout(quantityPanel, BoxLayout.Y_AXIS));
        quantityPanel.setBackground(Color.WHITE);
        JLabel quantityLabel = new JLabel("Quantity");
        quantityField = new JFormattedTextField(numberFormatter);
        quantityPanel.add(quantityLabel);
        quantityPanel.add(quantityField);

        JPanel reorderLevelPanel = new JPanel();
        reorderLevelPanel.setLayout(new BoxLayout(reorderLevelPanel, BoxLayout.Y_AXIS));
        reorderLevelPanel.setBackground(Color.WHITE);
        JLabel reorderLevelLabel = new JLabel("Reorder Level");
        reoderLevelField = new JFormattedTextField(numberFormatter);
        reorderLevelPanel.add(reorderLevelLabel);
        reorderLevelPanel.add(reoderLevelField);

        JPanel reorderQuantityPanel = new JPanel();
        reorderQuantityPanel.setLayout(new BoxLayout(reorderQuantityPanel, BoxLayout.Y_AXIS));
        reorderQuantityPanel.setBackground(Color.WHITE);
        JLabel reorderQuantityLabel = new JLabel("Reorder Quantity");
        reorderQuantityField = new JFormattedTextField(numberFormatter);
        reorderQuantityPanel.add(reorderQuantityLabel);
        reorderQuantityPanel.add(reorderQuantityField);

        JPanel retailCostPanel = new JPanel();
        retailCostPanel.setLayout(new BoxLayout(retailCostPanel, BoxLayout.Y_AXIS));
        retailCostPanel.setBackground(Color.WHITE);
        JLabel retailCostLabel = new JLabel("Retail Cost");
        retailCostField = new JFormattedTextField(new NumberFormatter(NumberFormat.getCurrencyInstance()));
        retailCostPanel.add(retailCostLabel);
        retailCostPanel.add(retailCostField);

        JPanel wholesaleCostPanel = new JPanel();
        wholesaleCostPanel.setLayout(new BoxLayout(wholesaleCostPanel, BoxLayout.Y_AXIS));
        wholesaleCostPanel.setBackground(Color.WHITE);
        JLabel wholesaleCostLabel = new JLabel("Wholesale Cost");
        wholesaleCostField = new JFormattedTextField(new NumberFormatter(NumberFormat.getCurrencyInstance()));
        wholesaleCostPanel.add(wholesaleCostLabel);
        wholesaleCostPanel.add(wholesaleCostField);

        dosagePanel = new JPanel();
        dosagePanel.setLayout(new BoxLayout(dosagePanel, BoxLayout.Y_AXIS));
        dosagePanel.setBackground(Color.WHITE);
        dosageLabel = new JLabel("Dosage");
        dosageField = new JTextField(20);
        dosagePanel.add(dosageLabel);
        dosagePanel.add(dosageField);

        interactionsPanel = new JPanel();
        interactionsPanel.setLayout(new BoxLayout(interactionsPanel, BoxLayout.Y_AXIS));
        interactionsPanel.setBackground(Color.WHITE);
        interactionsLabel = new JLabel("Interactions");
        interactionsField = new JTextArea(5,20);
        JScrollPane scrollPane = new JScrollPane(interactionsField);
        interactionsPanel.add(interactionsLabel);
        interactionsPanel.add(scrollPane);

        if(item.getType() != Inventory.InventoryType.MEDICATION)
        {
            interactionsLabel.setVisible(false);
            interactionsField.setVisible(false);
            dosageField.setVisible(false);
            dosageLabel.setVisible(false);
        }


        inventoryDetails.add(inventoryIDPanel);
        inventoryDetails.add(namePanel);
        inventoryDetails.add(manufacturerPanel);
        inventoryDetails.add(typePanel);
        inventoryDetails.add(quantityPanel);
        inventoryDetails.add(reorderLevelPanel);
        inventoryDetails.add(reorderQuantityPanel);
        inventoryDetails.add(retailCostPanel);
        inventoryDetails.add(wholesaleCostPanel);
        inventoryDetails.add(dosagePanel);
        inventoryDetails.add(interactionsPanel);


        return inventoryDetails;
    }
}