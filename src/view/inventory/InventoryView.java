package view.inventory;

import javax.swing.JPanel;

import control.IInventoryView;
import control.InventoryController;

import java.awt.CardLayout;

public class InventoryView extends JPanel implements IInventoryView{
    private InventoryController controller;
    private CardLayout layout;

    public InventoryView() {
        controller = InventoryController.getInstance();
        controller.registerView(this);
        createUI();
    }

    public void refresh() {
        // TODO Auto-generated method stub
    }

    private void createUI() {
        layout = new CardLayout();
        setLayout(layout);

        InventoryListView listView = new InventoryListView();
        InventoryDetailView detailView = new InventoryDetailView();
        add(listView, "list");
        add(detailView, "detail");
        layout.show(this, "list");
    }
}