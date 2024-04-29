package Repository;

import DAL.ConnectionManager;
import model.Inventory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryRepository {
    private final Connection conn;

    public InventoryRepository(){this.conn = ConnectionManager.getConnection();}

    public Inventory[] getAll(){
        String sql = "select * from inventory";
        List<Inventory> ret = new ArrayList<>();

        try(PreparedStatement get = conn.prepareStatement(sql)){
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
        String sql = "select * from inventory where itemID = ?";
        Inventory ret = new Inventory();

        try(PreparedStatement get = conn.prepareStatement(sql)){

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
                "reorderQuantity,wholesaleCost,retailcost " +
                "VALUES(?,?,?,?,?,?,?,?)";

        try(PreparedStatement create = conn.prepareStatement(sql)){
            create.setString(1, mod.getName());
            create.setString(2, mod.getManufacturer());
            create.setString(3, mod.getType());
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
            try {
                System.err.println("Rolling back changes");
                conn.rollback();
            } catch (SQLException e) {
                System.err.println("Error rolling back Inventory changes");
                e.printStackTrace();
            }
        }
        return mod;

    }

    public void updateInventory(Inventory mod){
        String sql
                = "UPDATE INVENTORY SET name = ?, manufacturer = ?, type = ?, quantity = ?, reorderLevel = ?, " +
                "reorderQuantity = ?, wholesaleCost = ?, retailCost = ? where itemID = ?";

        try(PreparedStatement update = conn.prepareStatement(sql)){
            update.setString(1, mod.getName());
            update.setString(2, mod.getManufacturer());
            update.setString(3, mod.getType());
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
            try {
                System.err.println("Rolling back changes");
                conn.rollback();
            } catch (SQLException e) {
                System.err.println("Error rolling back Inventory changes");
                e.printStackTrace();
            }
        }
    }
}
