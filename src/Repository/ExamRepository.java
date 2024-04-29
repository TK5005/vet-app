package Repository;

import DAL.ConnectionManager;
import model.Exam;
import model.Pet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamRepository {
    private final Connection conn;

    public ExamRepository(){this.conn = ConnectionManager.getConnection();}

    public Exam[] getExamsByPetID(int petID){
        String sql =
            "select e.examID, e.petID, e.exam_datetime, e.description, e.weight, e.location, t.empID as techID, " +
                "v.empID as vetID, e.vitals from examination e " +
                    "join vet_exams v on e.examID = v.examID " +
                    "join tech_exams t on e.examID = v.examID where petID = ?";

        List<Exam> ret = new ArrayList<>();

        try(PreparedStatement get = conn.prepareStatement(sql)){
            get.setInt(1,petID);
            ResultSet rs = get.executeQuery();

            while(rs.next()){
                Exam add = new Exam();
                add.setExamID(rs.getInt("examID"));
                add.setPetID(rs.getInt("petID"));
                add.setDate(rs.getTimestamp("exam_datetime").toLocalDateTime());
                add.setVetID(rs.getInt("vetID"));
                add.setTechID(rs.getInt("techID"));
                add.setDescription(rs.getString("description"));
                add.setWeight(rs.getInt("weight"));
                add.setLocation(rs.getString("location"));
                add.setVitals(rs.getString("vitals"));

                ret.add(add);
            }
        }catch (SQLException ex) {
            System.err.println("Error running Exam Get statement");
            ex.printStackTrace();
        }
        return ret.toArray(new Exam[0]);
    }

    public Exam getSpecificExam(int examID){
        String sql =
                "select e.examID, e.petID, e.exam_datetime, e.description, e.weight, e.location, t.empID as techID, " +
                        "v.empID as vetID, e.vitals from examination e " +
                        "join vet_exams v on e.examID = v.examID " +
                        "join tech_exams t on e.examID = v.examID where e.examID = ?";
        Exam ret = null;
        try(PreparedStatement get = conn.prepareStatement(sql)){
            get.setInt(1,examID);
            ResultSet rs = get.executeQuery();

            while(rs.next()){
                ret = new Exam();
                ret.setExamID(rs.getInt("examID"));
                ret.setPetID(rs.getInt("petID"));
                ret.setDate(rs.getTimestamp("exam_datetime").toLocalDateTime());
                ret.setVetID(rs.getInt("vetID"));
                ret.setTechID(rs.getInt("techID"));
                ret.setDescription(rs.getString("description"));
                ret.setWeight(rs.getInt("weight"));
                ret.setLocation(rs.getString("location"));
                ret.setVitals(rs.getString("vitals"));
            }
        }catch (SQLException ex) {
            System.err.println("Error running Exam Get statement");
            ex.printStackTrace();
        }
        return ret;
    }

    public Exam addExam(Exam mod) {
        String sql
                = "INSERT INTO EXAMINATION (petID, exam_datetime, description, vitals, weight, location) " +
                "values(?,?,?,?,?,?,?)";

        try (PreparedStatement create = conn.prepareStatement(sql)) {
            create.setInt(1, mod.getPetID());
            create.setTimestamp(2, java.sql.Timestamp.valueOf(mod.getDate()));
            create.setString(3, mod.getDescription());
            create.setString(4, mod.getVitals());
            create.setInt(5, mod.getWeight());
            create.setString(6, mod.getLocation());

            create.executeUpdate();
            ResultSet rs = create.getGeneratedKeys();

            while(rs.next()){
                mod.setExamID(rs.getInt(1));
                addVetExam(mod.getExamID(),mod.getVetID());
                addTechExam(mod.getExamID(), mod.getTechID());
            }

            conn.commit();
        } catch (SQLException ex) {
            System.err.println("Error inserting exam entry");
            ex.printStackTrace();
            try {
                System.err.println("Rolling back changes");
                conn.rollback();
            } catch (SQLException e) {
                System.err.println("Error rolling back exam changes");
                e.printStackTrace();
            }
        }
        return mod;
    }

    public void updateExam(Exam mod){
        String sql
                = "UPDATE EXAMINATION SET exam_datetime = ?, description = ?, vitals = ?, weight = ?, location = ? " +
                "WHERE examID = ?";

        try(PreparedStatement update = conn.prepareStatement(sql)){
            update.setTimestamp(1, java.sql.Timestamp.valueOf(mod.getDate()));
            update.setString(2, mod.getDescription());
            update.setString(3, mod.getVitals());
            update.setInt(4, mod.getWeight());
            update.setString(5, mod.getLocation());

            update.executeUpdate();
            updateVetExam(mod.getExamID(), mod.getVetID());
            updateTechExam(mod.getExamID(), mod.getVetID());

        }catch (SQLException ex) {
            System.err.println("Error updating Exam entry");
            ex.printStackTrace();
            try {
                System.err.println("Rolling back changes");
                conn.rollback();
            } catch (SQLException e) {
                System.err.println("Error rolling back Exam changes");
                e.printStackTrace();
            }
        }
    }

    private void addVetExam(int examID, int vetID){
        String sql = "INSERT INTO VET_EXAMS VALUES (?, ?)";

        try (PreparedStatement create = conn.prepareStatement(sql)){
            create.setInt(1,vetID);
            create.setInt(2,examID);

            create.executeQuery();

        }catch (SQLException ex) {
            System.err.println("Error inserting vet_exams entry");
            ex.printStackTrace();
            try {
                System.err.println("Rolling back changes");
                conn.rollback();
            } catch (SQLException e) {
                System.err.println("Error rolling back vet_exams changes");
                e.printStackTrace();
            }
        }
    }

    private void updateVetExam(int examID, int vetID) {
        String sql = "UPDATE TECH_EXAMS SET vetID = ? WHERE examID = ?";

        try (PreparedStatement update = conn.prepareStatement(sql)) {
            update.setInt(1, vetID);
            update.setInt(2, examID);

            update.executeQuery();

        } catch (SQLException ex) {
            System.err.println("Error inserting vet_exams entry");
            ex.printStackTrace();
            try {
                System.err.println("Rolling back changes");
                conn.rollback();
            } catch (SQLException e) {
                System.err.println("Error rolling back vet_exams changes");
                e.printStackTrace();
            }
        }
    }

    private void addTechExam(int examID, int techID){
        String sql = "INSERT INTO TECH_EXAMS VALUES (?, ?)";

        try (PreparedStatement create = conn.prepareStatement(sql)){
            create.setInt(1,techID);
            create.setInt(2,examID);

            create.executeQuery();

        }catch (SQLException ex) {
            System.err.println("Error inserting tech_exams entry");
            ex.printStackTrace();
            try {
                System.err.println("Rolling back changes");
                conn.rollback();
            } catch (SQLException e) {
                System.err.println("Error rolling back tech_exams changes");
                e.printStackTrace();
            }
        }
    }
    private void updateTechExam(int examID, int techID){
        String sql = "UPDATE TECH_EXAMS SET techID = ? WHERE examID = ?";;

        try (PreparedStatement update = conn.prepareStatement(sql)){
            update.setInt(1,techID);
            update.setInt(2,examID);

            update.executeQuery();

        }catch (SQLException ex) {
            System.err.println("Error updating tech_exams entry");
            ex.printStackTrace();
            try {
                System.err.println("Rolling back changes");
                conn.rollback();
            } catch (SQLException e) {
                System.err.println("Error rolling back tech_exams changes");
                e.printStackTrace();
            }
        }
    }
}