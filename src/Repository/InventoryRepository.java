package Repository;

import DAL.ConnectionManager;
import model.Inventory;
import model.Medication;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryRepository {
//    private final Connection conn;
//
//    public InventoryRepository(){this.conn = ConnectionManager.getConnection();}

    public Inventory[] getAll(){
        String sql = "SELECT * FROM INVENTORY";
        List<Inventory> ret = new ArrayList<>();

        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement get = conn.prepareStatement(sql)){
            ResultSet rs = get.executeQuery();

            while(rs.next()){
                Inventory add = new Inventory();
                add.setItemID(rs.getInt("itemID"));
                add.setName(rs.getString("name"));
                add.setManufacturer(rs.getString("manufacturer"));
                add.setType(rs.getString("type"));
                add.setQuantity(rs.getInt("quantity"));
                add.setReorderLevel(rs.getInt("reorderLevel"));
                add.setReorderQuantity(rs.getInt("reorderQuantity"));
                add.setWholesaleCost(rs.getFloat("wholesaleCost"));
                add.setRetailCost(rs.getFloat("retailCost"));

                ret.add(add);
            }
        }catch (SQLException ex) {
            System.err.println("Error running Inventory Get statement");
            ex.printStackTrace();
        }
        return ret.toArray(new Inventory[0]);
    }

    public Inventory getSpecificItem(int itemID){
        String sql = "SELECT * FROM INVENTORY WHERE itemID = ?";
        Inventory ret = new Inventory();

        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement get = conn.prepareStatement(sql)){

            get.setInt(1,itemID);
            ResultSet rs = get.executeQuery();

            while(rs.next()){

                ret.setItemID(rs.getInt("itemID"));
                ret.setName(rs.getString("name"));
                ret.setManufacturer(rs.getString("manufacturer"));
                ret.setType(rs.getString("type"));
                ret.setQuantity(rs.getInt("quantity"));
                ret.setReorderLevel(rs.getInt("reorderLevel"));
                ret.setReorderQuantity(rs.getInt("reorderQuantity"));
                ret.setWholesaleCost(rs.getFloat("wholesaleCost"));
                ret.setRetailCost(rs.getFloat("retailCost"));

            }
        }catch (SQLException ex) {
            System.err.println("Error running Inventory Get statement");
            ex.printStackTrace();
        }
        return ret;
    }

    public Inventory addInventory(Inventory mod){
        String sql
                = "INSERT INTO INVENTORY(name,manufacturer,type,quantity,reorderLevel, " +
                "reorderQuantity,wholesaleCost,retailcost) " +
                "VALUES(?,?,?,?,?,?,?,?)";

        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement create = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            create.setString(1, mod.getName());
            create.setString(2, mod.getManufacturer());
            create.setString(3, mod.getType().toString());
            create.setInt(4, mod.getQuantity());
            create.setInt(5, mod.getReorderLevel());
            create.setInt(6, mod.getReorderQuantity());
            create.setFloat(7, mod.getWholesaleCost());
            create.setFloat(8, mod.getRetailCost());

            create.executeUpdate();
            ResultSet rs = create.getGeneratedKeys();
            while(rs.next()){
                mod.setItemID(rs.getInt(1));
            }
            conn.commit();
        }catch (SQLException ex) {
            System.err.println("Error inserting Inventory entry");
            ex.printStackTrace();
        }
        return mod;

    }

    public void updateInventory(Inventory mod){
        String sql
                = "UPDATE INVENTORY SET name = ?, manufacturer = ?, type = ?, quantity = ?, reorderLevel = ?, " +
                "reorderQuantity = ?, wholesaleCost = ?, retailCost = ? WHERE itemID = ?";

        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement update = conn.prepareStatement(sql)){
            update.setString(1, mod.getName());
            update.setString(2, mod.getManufacturer());
            update.setString(3, mod.getType().toString());
            update.setInt(4, mod.getQuantity());
            update.setInt(5, mod.getReorderLevel());
            update.setInt(6, mod.getReorderQuantity());
            update.setFloat(7, mod.getWholesaleCost());
            update.setFloat(8, mod.getRetailCost());
            update.setInt(9, mod.getItemID());

            update.executeUpdate();
            conn.commit();
        }catch (SQLException ex) {
            System.err.println("Error updating Inventory entry");
            ex.printStackTrace();
        }
    }

    public void deleteInventoryItem(int inventoryID)
    {
        String sql = "DELETE FROM INVENTORY WHERE itemID = ?";
        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement delete = conn.prepareStatement(sql)){
            delete.setInt(1, inventoryID);
            delete.executeUpdate();
            conn.commit();
        }catch (SQLException ex) {
            System.err.println("Error deleting Inventory entry");
            ex.printStackTrace();
        }
    }

    // Medication Methods

    public Medication[] getMedications(){
        String sql = "SELECT i.*, m.interactions, m.dosage FROM INVENTORY i JOIN MEDICATION m ON m.itemID = i.itemID";
        List<Medication> ret = new ArrayList<>();

        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement get = conn.prepareStatement(sql)){
            ResultSet rs = get.executeQuery();

            while(rs.next()){
                Medication add = new Medication();
                add.setItemID(rs.getInt("itemID"));
                add.setName(rs.getString("name"));
                add.setManufacturer(rs.getString("manufacturer"));
                add.setType(rs.getString("type"));
                add.setQuantity(rs.getInt("quantity"));
                add.setReorderLevel(rs.getInt("reorderLevel"));
                add.setReorderQuantity(rs.getInt("reorderQuantity"));
                add.setWholesaleCost(rs.getFloat("wholesaleCost"));
                add.setRetailCost(rs.getFloat("retailCost"));
                add.setInteractions(rs.getString("interactions"));
                add.setDosage(rs.getString("dosage"));

                ret.add(add);
            }
        }catch (SQLException ex) {
            System.err.println("Error running Medication Get statement");
            ex.printStackTrace();
        }
        return ret.toArray(new Medication[0]);
    }

    // Added setMedicationID to the Medication object (fixed error during saving) - KS
    public Medication[] getInStockMedications(){
        String sql = "SELECT i.*, m.interactions, m.dosage FROM INVENTORY i JOIN MEDICATION m ON m.itemID = i.itemID " +
                "WHERE i.quantity > 0";
        List<Medication> ret = new ArrayList<>();

        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement get = conn.prepareStatement(sql)){
            ResultSet rs = get.executeQuery();

            while(rs.next()){
                Medication add = new Medication();
                add.setItemID(rs.getInt("itemID"));
                add.setName(rs.getString("name"));
                add.setManufacturer(rs.getString("manufacturer"));
                add.setType(rs.getString("type"));
                add.setQuantity(rs.getInt("quantity"));
                add.setReorderLevel(rs.getInt("reorderLevel"));
                add.setReorderQuantity(rs.getInt("reorderQuantity"));
                add.setWholesaleCost(rs.getFloat("wholesaleCost"));
                add.setRetailCost(rs.getFloat("retailCost"));
                add.setInteractions(rs.getString("interactions"));
                add.setDosage(rs.getString("dosage"));

                ret.add(add);
            }
        }catch (SQLException ex) {
            System.err.println("Error running Medication Get statement");
            ex.printStackTrace();
        }
        return ret.toArray(new Medication[0]);
    }

    public Medication getSpecificMedication(int medID){
        String sql = "SELECT i.*, m.interactions, m.dosage FROM INVENTORY i JOIN MEDICATION m ON m.itemID = i.itemID " +
                " WHERE m.itemID = ?";
        Medication ret = null;

        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement get = conn.prepareStatement(sql)){
            get.setInt(1, medID);
            ResultSet rs = get.executeQuery();

            while(rs.next()){
                ret = new Medication();
                ret.setItemID(rs.getInt("itemID"));
                ret.setName(rs.getString("name"));
                ret.setManufacturer(rs.getString("manufacturer"));
                ret.setType(rs.getString("type"));
                ret.setQuantity(rs.getInt("quantity"));
                ret.setReorderLevel(rs.getInt("reorderLevel"));
                ret.setReorderQuantity(rs.getInt("reorderQuantity"));
                ret.setWholesaleCost(rs.getFloat("wholesaleCost"));
                ret.setRetailCost(rs.getFloat("retailCost"));
                ret.setInteractions(rs.getString("interactions"));
                ret.setDosage(rs.getString("dosage"));

            }
        }catch (SQLException ex) {
            System.err.println("Error running Medication Get statement");
            ex.printStackTrace();
        }
        return ret;
    }


    private Medication addMedication(Medication mod){
        updateInventory(mod);
        String sql =
                "INSERT INTO MEDICATION VALUES(?,?,?)";

        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement create = conn.prepareStatement(sql)){
            create.setInt(1, mod.getItemID());
            create.setString(2, mod.getInteractions());
            create.setString(3, mod.getDosage());

            create.executeUpdate();
            conn.commit();
        }catch (SQLException ex) {
            System.err.println("Error inserting Medication entry");
            ex.printStackTrace();
        }
        return mod;
    }

    private void updateMedication(Medication mod){
        updateInventory(mod);
        String sql =
                "UPDATE MEDICATION SET interactions = ?, dosage = ? WHERE itemID = ?";

        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement update = conn.prepareStatement(sql)){
            update.setString(1, mod.getInteractions());
            update.setString(2, mod.getDosage());
            update.setInt(3, mod.getItemID());

            update.executeUpdate();
            conn.commit();
        }catch (SQLException ex) {
            System.err.println("Error inserting Medication entry");
            ex.printStackTrace();
        }
    }

    public void addOrUpdateMedication(Medication mod){
        String sql
                = "SELECT * FROM MEDICATION WHERE itemID = ?";

        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement get = conn.prepareStatement(sql)){

            get.setInt(1,mod.getItemID());

            ResultSet rs = get.executeQuery();

            boolean exists = false;
            while(rs.next())
                exists = true;

            if(exists)
                updateMedication(mod);
            else
                addMedication(mod);

        }catch (SQLException ex) {
            System.err.println("Error running Medication Get statement");
            ex.printStackTrace();
        }
    }

    public void deleteMedicationEntry(int itemID){
        blankMedications(itemID);
        String sql
                = "DELETE FROM MEDICATION WHERE itemID = ?";
        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement del = conn.prepareStatement(sql)){
            del.setInt(1, itemID);
            del.executeUpdate();
            conn.commit();
        }catch (SQLException ex) {
            System.err.println("Error deleting Medication entry");
            ex.printStackTrace();
        }
    }

    private void blankMedications(int itemID){
        String sql
                = "UPDATE TREATMENT SET medID = ? WHERE medID = ?";

        try(Connection conn = ConnectionManager.getConnection();
        PreparedStatement del = conn.prepareStatement(sql)){

            del.setNull(1, Types.INTEGER);
            del.setInt(2,itemID);

            del.executeUpdate();
            conn.commit();

        }catch (SQLException ex) {
            System.err.println("Error Blanking Medication from treatment entry");
            ex.printStackTrace();
        }
    }
}
