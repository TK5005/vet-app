package model;

import java.time.LocalDate;

public class Treatment {
    public static enum TreatType {VACCINE, LIFESTYLE, MEDICATION, TEST}
    private int examID;
    private int treatmentID;
    private TreatType type;
    //private String medication;
    private Integer medicationID = null;
    private LocalDate startDate;
    private LocalDate endDate;
    private String directions;

    public int getExamID() {
        return examID;
    }

    public void setExamID(int examID) {
        this.examID = examID;
    }

    public int getTreatmentID() {
        return treatmentID;
    }

    public void setTreatmentID(int treatmentID) {
        this.treatmentID = treatmentID;
    }

    public TreatType getType(){return type;}

    public void setType(String type){
        switch(type){
            case "VACCINE":
                this.type = TreatType.VACCINE;
                break;
            case "LIFESTYLE":
                this.type = TreatType.LIFESTYLE;
                break;
            case "MEDICATION":
                this.type = TreatType.MEDICATION;
                break;
            case "TEST":
                this.type = TreatType.TEST;
                break;
            default:
                this.type = null;
        }
    }

    public void setType(TreatType type){this.type = type;}

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public Integer getMedicationID(){return medicationID;}

    public void setMedicationID(Integer medicationID){this.medicationID = medicationID;}

    public String getTreatmentTypeString() {
        if (this.type != null) {
            switch (this.type) {
                case VACCINE:
                    return "VACCINE";
                case MEDICATION:
                    return "MEDICATION";
                case LIFESTYLE:
                    return "LIFESTYLE";
                case TEST:
                    return "TEST";
                default:
                    return "";
            }
        } else {
            return "";
        }
    }

    public static String[] getTreatTypes(){
        String [] options = new String[TreatType.values().length];
        int i=0;
        for(TreatType t : TreatType.values()){
            options[i] = t.toString();
            i++;
        }
        return options;
    }

//    public String getMedication() {
//        return medication;
//    }

//    public void setMedication(String medication) {
//        this.medication = medication;
//    }
}