package Repository;

import DAL.ConnectionManager;
import model.Pet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetRepository {
//    private final Connection conn;
//
//    public PetRepository(){this.conn = ConnectionManager.getConnection();}

    public Pet[] getPetsByClientID(int clientID){
        String sql = "SELECT * FROM PET WHERE ownerID = ?";
        List<Pet> ret = new ArrayList<>();
        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement get = conn.prepareStatement(sql)){
            get.setInt(1,clientID);
            ResultSet rs = get.executeQuery();

            while(rs.next()){
                ret.add(new Pet(rs.getInt("petID"), rs.getInt("ownerID"), rs.getString("name"), rs.getString("sex"),
                        rs.getString("color"),rs.getString("species"), rs.getString("breed"),
                        rs.getTimestamp("birthdate").toLocalDateTime().toLocalDate(), rs.getInt("weight"),
                        rs.getLong("microchipNumber"), rs.getLong("rabiesTag")));
            }
        }catch (SQLException ex) {
            System.err.println("Error running Pet Get statement");
            ex.printStackTrace();
        }
        return ret.toArray(new Pet[0]);
    }

    public Pet getSpecificPet(int petID) {
        String sql = "SELECT * FROM PET WHERE petID = ?";
        Pet ret = null;

        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement get = conn.prepareStatement(sql)) {
            get.setInt(1, petID);
            ResultSet rs = get.executeQuery();
            while (rs.next()) {
                ret = new Pet(rs.getInt("petID"), rs.getInt("ownerID"), rs.getString("name"), rs.getString("sex"),
                        rs.getString("color"), rs.getString("species"), rs.getString("breed"),
                        rs.getTimestamp("birthdate").toLocalDateTime().toLocalDate(), rs.getInt("weight"),
                        rs.getLong("microchipNumber"), rs.getLong("rabiesTag"));
            }
        } catch (SQLException ex) {
            System.err.println("Error running Pet Get statement");
            ex.printStackTrace();
        }
        return ret;
    }

    public void removePet(int petID)
    {
        //TODO: Implement removePet in PetRepository
        System.out.println("TODO: Implement removePet in PetRepository");
    }

    //Inserts a new pet into the pet table and returns the given pet model with the new petID
    public Pet addPet(Pet mod){
        String sql
         ="INSERT INTO PET (ownerID, name, sex, color, species, breed, birthdate, weight, microchipNumber, rabiesTag)"+
                " VALUES(?,?,?,?,?,?,?,?,?,?)";

        try(Connection conn = ConnectionManager.getConnection();
                PreparedStatement create = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            create.setInt(1,mod.getOwnerID());
            create.setString(2,mod.getName());
            create.setString(3,mod.getSex());
            create.setString(4,mod.getColor());
            create.setString(5,mod.getSpecies());
            create.setString(6,mod.getBreed());
            create.setDate(7,java.sql.Date.valueOf(mod.getBirthdate()));
            create.setInt(8, mod.getWeight());
            create.setLong(9, mod.getMicrochipNumber());
            create.setLong(10,mod.getRabiesTag());

            create.executeUpdate();

            ResultSet rs = create.getGeneratedKeys();
            while(rs.next()){
                mod.setPetID(rs.getInt(1));
            }
            conn.commit();
        }catch (SQLException ex) {
            System.err.println("Error inserting Pet entry");
            ex.printStackTrace();
        }
        return mod;
    }

    public void updatePet(Pet mod) {
        String updateSQL
                = "UPDATE PET SET name = ? , sex = ? , color = ? , species = ? , breed = ? , birthdate = ? , " +
                "weight = ? , microchipNumber = ? , rabiesTag = ? WHERE petID = ?";
        try (Connection conn = ConnectionManager.getConnection();
                PreparedStatement update = conn.prepareStatement(updateSQL)) {
            update.setString(1, mod.getName());
            update.setString(2, mod.getSex());
            update.setString(3, mod.getColor());
            update.setString(4, mod.getSpecies());
            update.setString(5, mod.getBreed());
            update.setDate(6, java.sql.Date.valueOf(mod.getBirthdate()));
            update.setInt(7, mod.getWeight());
            update.setLong(8, mod.getMicrochipNumber());
            update.setLong(9, mod.getRabiesTag());
            update.setInt(10,mod.getPetID());

            update.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            System.err.println("Error updating Pet entry");
            ex.printStackTrace();
        }
    }
}
