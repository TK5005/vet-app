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
            "SELECT e.examID, e.petID, e.exam_datetime, e.description, e.weight, e.location, t.empID as techID, " +
                "v.empID as vetID, e.vitals from EXAMINATION e " +
                    "join VET_EXAMS v on e.examID = v.examID " +
                    "join TECH_EXAMS t on e.examID = v.examID " +
                    "WHERE petID = ?";

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
                "SELECT e.examID, e.petID, e.exam_datetime, e.description, e.weight, e.location, t.empID as techID, " +
                        "v.empID as vetID, e.vitals from EXAMINATION e " +
                        "join VET_EXAMS v on e.examID = v.examID " +
                        "join TECH_EXAMS t on e.examID = v.examID WHERE e.examID = ?";
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
                "VALUES(?,?,?,?,?,?)";

        try (PreparedStatement create = conn.prepareStatement(sql)) {
            create.setInt(1, mod.getPetID());
            create.setTimestamp(2, java.sql.Timestamp.valueOf(mod.getDate()));
            create.setString(3, mod.getDescription());
            create.setString(4, mod.getVitals());
            create.setInt(5, mod.getWeight());
            create.setString(6, mod.getLocation());

            create.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
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

    // Added examID to the query - KS
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
            update.setInt(6, mod.getExamID());
            update.executeUpdate();
            updateVetExam(mod.getExamID(), mod.getVetID());
            updateTechExam(mod.getExamID(), mod.getTechID());

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

            create.executeUpdate();

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

    // Changed to executeUpdate - KS
    // Changed TECH_EXAMS to VET_EXAMS - KS
    private void updateVetExam(int examID, int vetID) {
        String sql = "UPDATE VET_EXAMS SET empID = ? WHERE examID = ?";

        try (PreparedStatement update = conn.prepareStatement(sql)) {
            update.setInt(1, vetID);
            update.setInt(2, examID);

            update.executeUpdate();

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

    // Changed to executeUpdate - KS
    private void addTechExam(int examID, int techID){
        String sql = "INSERT INTO TECH_EXAMS VALUES (?, ?)";

        try (PreparedStatement create = conn.prepareStatement(sql)){
            create.setInt(1,techID);
            create.setInt(2,examID);

            create.executeUpdate();

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

    // Changed to executeUpdate - KS
    private void updateTechExam(int examID, int techID){
        String sql = "UPDATE TECH_EXAMS SET empID = ? WHERE examID = ?";;

        try (PreparedStatement update = conn.prepareStatement(sql)){
            update.setInt(1,techID);
            update.setInt(2,examID);

            update.executeUpdate();

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

    public void deleteExam(int examID)
    {
        //TODO: Implement deleteExam
        System.out.println("deleteExam not implemented");
    }

    public Exam[] getAllExams()
    {
        //TODO: Implement getAllExams
        System.out.println("getAllExams not implemented");
        return null;
    }
}
