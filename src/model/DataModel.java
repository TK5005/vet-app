package model;

public class DataModel {
    private static DataModel instance;

    private DataModel() {

    }

    public static DataModel getInstance() {
        if (instance == null) {
            synchronized (DataModel.class) {
                if (instance == null) {
                    instance = new DataModel();
                }
            }
        }
        return instance;
    }

    public String[][] loadActivePatient() {
        String[][] data = { { "Tom", "4/5/2024", "Exam1", "test/test", "Vaccination" },
                { "Mary", "5/5/2024", "Test", "test/test", "Trimming" } };
        return data;
    }

    public String[][] loadTempAppointments() {
        String[][] data = { {"1", "Smith Henry", "Brandy", "443-123-4567", "4/28/2024 8:00AM", "" },
                { "2","Mary", "Sassy", "443-890-1234", "5/23/2024 2:30PM", "" } };
        return data;
    }

    public String[][] loadReview() {
        String[][] data = { { "Smith Henry", "Brandy", "443-123-4567", "4/28/2024 8:00AM", "test" },
                { "Mary", "Sassy", "443-890-1234", "5/23/2024 2:30PM", "test" } };
        return data;
    }

    public String[][] loadMedication() {
        String[][] data = { { "Smith Henry", "Brandy", "443-123-4567", "12", "Yes" },
                { "Mary", "Sassy", "443-890-1234", "5", "No" } };
        return data;
    }

    public String[][] loadStaff() {
        String[][] data = { {"1", "sb12","Smith Brandy","vet","",""},
                { "2", "tes34","test","tech","",""  } };
        return data;
    }
}