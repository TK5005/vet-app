package control;

import Repository.InventoryRepository;
import model.DataModel;
import model.Inventory;
import model.Medication;
import view.inventory.InventoryView;

public class InventoryController extends ViewController{
    private static InventoryController instance;
    private DataModel dataModel;
    private InventoryRepository inventoryRepository;
    private InventoryView inventoryView;
    private int currentInventoryID = -1;

    private InventoryController() {
        inventoryRepository = new InventoryRepository();
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

    public void addNewItem() {
        Inventory item = new Inventory();
        item.setName("New Item");
        item.setManufacturer("Manufacturer");
        item.setType(Inventory.InventoryType.OFFICE);
        item.setQuantity(0);
        item.setReorderLevel(0);
        item.setReorderQuantity(0);
        item.setWholesaleCost(0);
        item.setRetailCost(0);
        inventoryRepository.addInventory(item);

        refreshViews();
    }

    public Inventory[] getInventory() {
        return inventoryRepository.getAll();
    }

    public void setCurrentInventoryID(int itemID) {
        this.currentInventoryID = itemID;
    }

    public int getCurrentInventoryID() {
        return this.currentInventoryID;
    }

    public Inventory getItem(int itemID)
    {
        return inventoryRepository.getSpecificItem(itemID);
    }

    public Medication getMedItem(int itemID){return inventoryRepository.getSpecificMedication(itemID);}

    public void updateInventoryItem(int itemID, String name, String manufacturer, String type,
                                int quantity, int reorderLevel, int reorderQuantity, float wholesaleCost,
                                float retailCost) {
        Inventory item = new Inventory();
        item.setItemID(itemID);
        item.setName(name);
        item.setManufacturer(manufacturer);
        item.setType(type);
        item.setQuantity(quantity);
        item.setReorderLevel(reorderLevel);
        item.setReorderQuantity(reorderQuantity);
        item.setWholesaleCost(wholesaleCost);
        item.setRetailCost(retailCost);
        inventoryRepository.updateInventory(item);
        refreshViews();
    }

    public void updateMedicationItem(int itemID, String name, String manufacturer, String type,
                                     int quantity, int reorderLevel, int reorderQuantity, float wholesaleCost,
                                     float retailCost, String dosage, String interactions){

        Medication item = new Medication();
        item.setItemID(itemID);
        item.setName(name);
        item.setManufacturer(manufacturer);
        item.setType(type);
        item.setQuantity(quantity);
        item.setReorderLevel(reorderLevel);
        item.setReorderQuantity(reorderQuantity);
        item.setWholesaleCost(wholesaleCost);
        item.setRetailCost(retailCost);
        item.setDosage(dosage);
        item.setInteractions(interactions);
        inventoryRepository.addOrUpdateMedication(item);
        refreshViews();
    }

    public void deleteInventoryItem(int inventoryID) {
        deleteMedicationEntry(inventoryID);
        inventoryRepository.deleteInventoryItem(inventoryID);
        refreshViews();
    }
    public void deleteMedicationEntry(int itemID){
        inventoryRepository.deleteMedicationEntry(itemID);
        refreshViews();
    }
}
