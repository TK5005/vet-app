package view.inventory;

import javax.swing.JPanel;

import control.IVetAppView;
import control.InventoryController;

import java.awt.CardLayout;
import java.awt.Color;

public class InventoryView extends JPanel implements IVetAppView{
    private InventoryController controller;
    private CardLayout layout;

    public InventoryView() {
        controller = InventoryController.getInstance();
        controller.registerView(this);
        controller.setInventoryView(this);
        createUI();
    }

    public void refresh() {
        // TODO Auto-generated method stub
    }

    private void createUI() {
        layout = new CardLayout();
        setLayout(layout);
        setBackground(Color.WHITE);
        InventoryListView listView = new InventoryListView();
        add(listView, "list");
        layout.show(this, "list");
    }

    public void showListView() {
        layout.show(this, "list");
    }

    public void showDetailView() {
        layout.show(this, "detail");
    }
}