package view.inventory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

import control.IVetAppView;
import control.InventoryController;
import model.Inventory;

import java.text.ParseException;



public class InventoryDetailView extends JPanel implements IVetAppView{
    public InventoryController controller;
    private JButton saveButton;
    private JButton closeButton;
    private JTextField nameField;
    private JFormattedTextField idField;
    private JFormattedTextField reoderLevelField;
    private JFormattedTextField quantityField;
    private JFormattedTextField reorderQuantityField;
    private JFormattedTextField retailCostField;
    private JFormattedTextField wholesaleCostField;
    //private JTextField typeField;
    private JComboBox typeBox;
    private JTextField manufacturerField;

    NumberFormat integerFormat = NumberFormat.getIntegerInstance();
    NumberFormatter numberFormatter = new NumberFormatter(integerFormat) {
        @Override
        public Object stringToValue(String string) throws ParseException {
            Number number = (Number) super.stringToValue(string);
            return number.intValue(); // Always return an Integer
        }
    };

    public InventoryDetailView() {
        controller = InventoryController.getInstance();
        controller.registerView(this);
        createUI();
        createEventListeners();
    }

    private void createEventListeners() {
        saveButton.addActionListener(e -> {
            Inventory item = controller.getCurrentInventoryItem();
            if(item != null)
            {
                item.setName(nameField.getText());
                //item.setType(typeField.getText());
                item.setType((String) typeBox.getSelectedItem());
                item.setManufacturer(manufacturerField.getText());

                //  Update item numbers
                item.setReorderLevel((Integer)reoderLevelField.getValue());
                item.setQuantity((Integer)quantityField.getValue());
                item.setReorderQuantity((Integer)reorderQuantityField.getValue());
                item.setRetailCost((Float)retailCostField.getValue());
                item.setWholesaleCost((Float)wholesaleCostField.getValue());

                controller.updateInventoryItem(item);
            }
        });
        closeButton.addActionListener(e -> {
            controller.showInventoryList();
        });
    }

    public void refresh() {
        Inventory item = controller.getCurrentInventoryItem();
        if(item != null)
        {
            idField.setValue(item.getItemID());
            nameField.setText(item.getName());
            reoderLevelField.setValue(item.getReorderLevel());
            quantityField.setValue(item.getQuantity());
            reorderQuantityField.setValue(item.getReorderQuantity());
            retailCostField.setValue(item.getRetailCost());
            wholesaleCostField.setValue(item.getWholesaleCost());
            //typeField.setText(item.getType());
            typeBox.setSelectedItem(item.getType());
            manufacturerField.setText(item.getManufacturer());
        }
    }

    private void createUI() {
        this.setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        JPanel header = createHeaderPanel();
        this.add(header, BorderLayout.NORTH);
        header.setBackground(Color.WHITE);
        JPanel detailPanel = createDetailPanel();
        this.add(detailPanel, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel();
        header.setLayout(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(Color.WHITE);
        nameField = new JTextField(10);
        saveButton = new JButton("Save");
        closeButton = new JButton("Close");
        header.add(nameField);
        header.add(saveButton);
        header.add(closeButton);
        return header;
    }

    private JPanel createDetailPanel() {

        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        detailPanel.setBackground(Color.WHITE);
        JPanel idPanel = new JPanel();
        idPanel.setLayout(new BoxLayout(idPanel, BoxLayout.Y_AXIS));
        idPanel.setBackground(Color.WHITE);
        JLabel idLabel = new JLabel("Item ID");
        idField = new JFormattedTextField(numberFormatter);
        idField.setEditable(false);
        idPanel.add(idLabel);
        idPanel.add(idField);

        JPanel reorderLevelPanel = new JPanel();
        reorderLevelPanel.setLayout(new BoxLayout(reorderLevelPanel, BoxLayout.Y_AXIS));
        reorderLevelPanel.setBackground(Color.WHITE);
        JLabel reorderLevelLabel = new JLabel("Reorder Level");
        reoderLevelField = new JFormattedTextField(numberFormatter);
        reorderLevelPanel.add(reorderLevelLabel);
        reorderLevelPanel.add(reoderLevelField);

        JPanel quantityPanel = new JPanel();
        quantityPanel.setLayout(new BoxLayout(quantityPanel, BoxLayout.Y_AXIS));
        quantityPanel.setBackground(Color.WHITE);
        JLabel quantityLabel = new JLabel("Quantity");
        quantityField = new JFormattedTextField(numberFormatter);
        quantityPanel.add(quantityLabel);
        quantityPanel.add(quantityField);

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
        retailCostField = new JFormattedTextField(new DecimalFormat("#0.00"));
        retailCostPanel.add(retailCostLabel);
        retailCostPanel.add(retailCostField);

        JPanel wholesaleCostPanel = new JPanel();
        wholesaleCostPanel.setLayout(new BoxLayout(wholesaleCostPanel, BoxLayout.Y_AXIS));
        wholesaleCostPanel.setBackground(Color.WHITE);
        JLabel wholesaleCostLabel = new JLabel("Wholesale Cost");
        wholesaleCostField = new JFormattedTextField(new DecimalFormat("#0.00"));
        wholesaleCostPanel.add(wholesaleCostLabel);
        wholesaleCostPanel.add(wholesaleCostField);

        JPanel typePanel = new JPanel();
        typePanel.setLayout(new BoxLayout(typePanel, BoxLayout.Y_AXIS));
        typePanel.setBackground(Color.WHITE);
        JLabel typeLabel = new JLabel("Type");
        //typeField = new JTextField(10);
        typeBox = new JComboBox(Inventory.getInventoryTypes());
        typePanel.add(typeLabel);
        //typePanel.add(typeField);
        typePanel.add(typeBox);

        JPanel manufacturerPanel = new JPanel();
        manufacturerPanel.setLayout(new BoxLayout(manufacturerPanel, BoxLayout.Y_AXIS));
        manufacturerPanel.setBackground(Color.WHITE);
        JLabel manufacturerLabel = new JLabel("Manufacturer");
        manufacturerField = new JTextField(10);
        manufacturerPanel.add(manufacturerLabel);
        manufacturerPanel.add(manufacturerField);

        detailPanel.add(idPanel);
        detailPanel.add(reorderLevelPanel);
        detailPanel.add(quantityPanel);
        detailPanel.add(reorderQuantityPanel);
        detailPanel.add(retailCostPanel);
        detailPanel.add(wholesaleCostPanel);
        detailPanel.add(typePanel);
        detailPanel.add(manufacturerPanel);

        return detailPanel;
    }
}
