package view.inventory;

import javax.swing.JPanel;

import control.IInventoryView;
import control.InventoryController;

import java.awt.CardLayout;
import java.awt.Color;

public class InventoryView extends JPanel implements IInventoryView{
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
        InventoryDetailView detailView = new InventoryDetailView();
        add(listView, "list");
        add(detailView, "detail");
        layout.show(this, "list");
    }

    public void showListView() {
        layout.show(this, "list");
    }

    public void showDetailView() {
        layout.show(this, "detail");
    }
}