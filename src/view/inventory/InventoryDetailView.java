package view.inventory;

import javax.swing.JPanel;

import control.IInventoryView;
import control.InventoryController;

public class InventoryDetailView extends JPanel implements IInventoryView{
    public InventoryController controller;

    public InventoryDetailView() {
        controller = InventoryController.getInstance();
        controller.registerView(this);
        createUI();
    }

    public void refresh() {
        // TODO Auto-generated method stub
        
    }

    private void createUI() {
        // TODO Auto-generated method stub
        
    }
}
