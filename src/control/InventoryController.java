package control;

import java.util.ArrayList;

import Repository.InventoryRepository;
import model.DataModel;
import model.Inventory;
import view.inventory.InventoryView;

public class InventoryController {
    private static InventoryController instance;
    private DataModel dataModel;
    private InventoryRepository inventoryRepository;
    private ArrayList<IInventoryView> views;
    private InventoryView inventoryView;
    private int currentInventoryID = -1;

    private InventoryController() {
        dataModel = DataModel.getInstance();
        inventoryRepository = new InventoryRepository();
        views = new ArrayList<>();
    }

    public static InventoryController getInstance() {
        if (instance == null) {
            synchronized (InventoryController.class) {
                if (instance == null) {
                    instance = new InventoryController();
                }
            }
        }
        return instance;
    }

    public void setInventoryView(InventoryView inventoryView) {
        this.inventoryView = inventoryView;
    }

    public void registerView(IInventoryView view) {
        views.add(view);
    }

    public void refreshViews() {
        for (IInventoryView view : views) {
            view.refresh();
        }
    }

    public void addNewItem() {
        //TODO: Conditionally Check for Type = MEDICATION and insert into Medication table accordingly
        Inventory item = new Inventory();
        item.setItemID(0);
        item.setName("New Item");
        item.setQuantity(0);
        item.setReorderLevel(0);
        item.setReorderQuantity(0);
        item.setRetailCost(0);
        item.setWholesaleCost(0);
        item.setType("Item Type");
        item.setManufacturer("Manufacturer");
        dataModel.addInventoryItem(item);
        refreshViews();
    }

    public Inventory[] getInventory() {
        //return dataModel.getInventory();
        return inventoryRepository.getAll();
    }

    public void showInventoryDetails(int itemID) {
        this.setCurrentInventoryID(itemID);
        this.refreshViews();
        inventoryView.showDetailView();
    }

    public void showInventoryList() {
        this.setCurrentInventoryID(-1);
        this.refreshViews();
        inventoryView.showListView();
    }

    public void setCurrentInventoryID(int itemID) {
        this.currentInventoryID = itemID;
    }

    public int getCurrentInventoryID() {
        return this.currentInventoryID;
    }

    public Inventory getCurrentInventoryItem() {
        //return dataModel.getInventoryItem(currentInventoryID);
        return inventoryRepository.getSpecificItem(currentInventoryID);
    }

    public void updateInventoryItem(Inventory item) {
        //dataModel.updateInventoryItem(currentInventoryID, item);
        item.setItemID(currentInventoryID);
        inventoryRepository.updateInventory(item);
        refreshViews();
        showInventoryList();
    }
}
