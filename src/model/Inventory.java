package model;

public class Inventory
{
    public static enum InventoryType {MEDICATION, MEDICAL, OFFICE}

    private int itemID;
    private String name;
    private int reorderLevel;
    private int quantity;
    private int reorderQuantity;
    private float retailCost;
    private float wholesaleCost;
    private InventoryType type;
    private String manufacturer;

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getReorderQuantity() {
        return reorderQuantity;
    }

    public void setReorderQuantity(int reorderQuantity) {
        this.reorderQuantity = reorderQuantity;
    }

    public float getRetailCost() {
        return retailCost;
    }

    public void setRetailCost(float retailCost) {
        this.retailCost = retailCost;
    }

    public float getWholesaleCost() {
        return wholesaleCost;
    }

    public void setWholesaleCost(float wholesaleCost) {
        this.wholesaleCost = wholesaleCost;
    }

    public InventoryType getType() {
        return type;
    }

    public void setType(String type) {
        switch(type){
            case "MEDICATION":
                this.type = InventoryType.MEDICATION;
                break;
            case "MEDICAL":
                this.type = InventoryType.MEDICAL;
                break;
            case "OFFICE":
                this.type = InventoryType.OFFICE;
                break;
            default:
                this.type = InventoryType.MEDICAL;
        }
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public static String[] getInventoryTypes(){
        String[] options = new String[InventoryType.values().length];
        int i = 0;
        for(InventoryType t : InventoryType.values()){
            options[i] = t.toString();
            i++;
        }
        return options;
    }
}