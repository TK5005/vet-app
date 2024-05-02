package Repository;

import DAL.ConnectionManager;
import model.Invoice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceRepository {
    private final Connection conn;

    public InvoiceRepository(){conn = ConnectionManager.getConnection();}

    public Invoice[] getAll(){
        String sql =
                "SELECT * FROM INVOICE";
        List<Invoice> ret = new ArrayList<>();

        try(PreparedStatement get = conn.prepareStatement(sql)){
            ResultSet rs = get.executeQuery();

            while(rs.next()){
                Invoice add = new Invoice();
                add.setInvoiceID(rs.getInt("invoiceNo"));
                add.setExamID(rs.getInt("examID"));
                add.setClientID(rs.getInt("custID"));
                add.setAmtDue(rs.getFloat("amtDue"));
                add.setStatus(Invoice.Status.valueOf(rs.getString("status")));
                add.setInvoiceDate(rs.getDate("invoiceDate").toLocalDate());

                ret.add(add);
            }

        }catch (SQLException ex) {
            System.err.println("Error running Invoice Get statement");
            ex.printStackTrace();
        }
        return ret.toArray(new Invoice[0]);
    }

    public Invoice getSpecificInvoice(int invoiceNo){
        String sql =
                "SELECT * FROM INVOICE WHERE invoiceNo = ?";
        Invoice ret = new Invoice();

        try(PreparedStatement get = conn.prepareStatement(sql)){
            get.setInt(1,invoiceNo);
            ResultSet rs = get.executeQuery();

            while(rs.next()){

                ret.setInvoiceID(rs.getInt("invoiceNo"));
                ret.setExamID(rs.getInt("examID"));
                ret.setClientID(rs.getInt("custID"));
                ret.setAmtDue(rs.getFloat("amtDue"));
                ret.setStatus(Invoice.Status.valueOf(rs.getString("status")));
                ret.setInvoiceDate(rs.getDate("invoiceDate").toLocalDate());

            }

        }catch (SQLException ex) {
            System.err.println("Error running Invoice Get statement");
            ex.printStackTrace();
        }
        return ret;
    }

    public Invoice[] getInvoicesByClientID(int clientID){
        String sql =
                "SELECT * FROM INVOICE WHERE custID = ?";
        List<Invoice> ret = new ArrayList<>();

        try(PreparedStatement get = conn.prepareStatement(sql)){
            get.setInt(1,clientID);
            ResultSet rs = get.executeQuery();

            while(rs.next()){
                Invoice add = new Invoice();
                add.setInvoiceID(rs.getInt("invoiceNo"));
                add.setExamID(rs.getInt("examID"));
                add.setClientID(rs.getInt("custID"));
                add.setAmtDue(rs.getFloat("amtDue"));
                add.setStatus(Invoice.Status.valueOf(rs.getString("status")));
                add.setInvoiceDate(rs.getDate("invoiceDate").toLocalDate());

                ret.add(add);
            }

        }catch (SQLException ex) {
            System.err.println("Error running Invoice Get statement");
            ex.printStackTrace();
        }
        return ret.toArray(new Invoice[0]);
    }

    public Invoice[] getInvoicesByExamID(int examID){
        String sql =
                "SELECT * FROM INVOICE WHERE examID = ? ";
        List<Invoice> ret = new ArrayList<>();

        try(PreparedStatement get = conn.prepareStatement(sql)){
            get.setInt(1,examID);
            ResultSet rs = get.executeQuery();

            while(rs.next()){
                Invoice add = new Invoice();
                add.setInvoiceID(rs.getInt("invoiceNo"));
                add.setExamID(rs.getInt("examID"));
                add.setClientID(rs.getInt("custID"));
                add.setAmtDue(rs.getFloat("amtDue"));
                add.setStatus(Invoice.Status.valueOf(rs.getString("status")));
                add.setInvoiceDate(rs.getDate("invoiceDate").toLocalDate());

                ret.add(add);
            }

        }catch (SQLException ex) {
            System.err.println("Error running Invoice Get statement");
            ex.printStackTrace();
        }
        return ret.toArray(new Invoice[0]);
    }

    public Invoice[] getInvoicesByPetID(int petID){
        String sql =
                "SELECT * FROM INVOICE WHERE examID IN" +
                        " (SELECT examID FROM EXAMINATION WHERE petID = ?)";
        List<Invoice> ret = new ArrayList<>();

        try(PreparedStatement get = conn.prepareStatement(sql)){
            get.setInt(1,petID);
            ResultSet rs = get.executeQuery();

            while(rs.next()){
                Invoice add = new Invoice();
                add.setInvoiceID(rs.getInt("invoiceNo"));
                add.setExamID(rs.getInt("examID"));
                add.setClientID(rs.getInt("custID"));
                add.setAmtDue(rs.getFloat("amtDue"));
                add.setStatus(Invoice.Status.valueOf(rs.getString("status")));
                add.setInvoiceDate(rs.getDate("invoiceDate").toLocalDate());

                ret.add(add);
            }

        }catch (SQLException ex) {
            System.err.println("Error running Invoice Get statement");
            ex.printStackTrace();
        }
        return ret.toArray(new Invoice[0]);
    }

    public Invoice addInvoice(Invoice mod){
        String sql
                = "INSERT INTO INVOICE (examID,custID,amtDue,status,invoiceDate)" +
                " VALUES(?,?,?,?,?)";

        try(PreparedStatement create = conn.prepareStatement(sql)){
            create.setInt(1, mod.getExamID());
            create.setInt(2, mod.getClientID());
            create.setFloat(3, mod.getAmtDue());
            create.setString(4, mod.getStatusString());
            create.setDate(5, java.sql.Date.valueOf(mod.getInvoiceDate()));

            create.executeUpdate();

            ResultSet rs = create.getGeneratedKeys();
            while(rs.next()){
                mod.setInvoiceID(rs.getInt(1));
            }
            conn.commit();
        }catch (SQLException ex) {
            System.err.println("Error inserting Invoice entry");
            ex.printStackTrace();
            try {
                System.err.println("Rolling back changes");
                conn.rollback();
            } catch (SQLException e) {
                System.err.println("Error rolling back Invoice changes");
                e.printStackTrace();
            }
        }
        return mod;

    }

    public void updateInvoice(Invoice mod){
        String sql
                ="UPDATE INVOICE SET examID=?,custID=?,amtDue=?,status=?,invoiceDate=? " +
                "WHERE invoiceNo = ?";

        try(PreparedStatement update = conn.prepareStatement(sql)){
            update.setInt(1, mod.getExamID());
            update.setInt(2, mod.getClientID());
            update.setFloat(3, mod.getAmtDue());
            update.setString(4, mod.getStatusString());
            update.setDate(5, java.sql.Date.valueOf(mod.getInvoiceDate()));
            update.setInt(6,mod.getInvoiceID());

            update.executeUpdate();
            conn.commit();
        }catch (SQLException ex) {
            System.err.println("Error updating Invoice entry");
            ex.printStackTrace();
            try {
                System.err.println("Rolling back changes");
                conn.rollback();
            } catch (SQLException e) {
                System.err.println("Error rolling back Invoice changes");
                e.printStackTrace();
            }
        }
    }

    public void removeInvoice(int invoiceID)
    {
        //TODO: Implement removeInvoice
        System.out.println("removeInvoice not implemented");
    }
}
