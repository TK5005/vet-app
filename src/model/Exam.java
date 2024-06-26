package model;

import java.time.LocalDateTime;

public class Exam {
    private int examID;
    private int petID;
    private LocalDateTime date;
    private Integer vetID;
    private Integer techID;
    private String description;
    private int weight;
    private String location;
    private String vitals;

    public String getVitals() {
        return vitals;
    }

    public void setVitals(String vitals) {
        this.vitals = vitals;
    }

    public int getExamID() {
        return examID;
    }

    public void setExamID(int examID) {
        this.examID = examID;
    }

    public int getPetID() {
        return petID;
    }

    public void setPetID(int petID) {this.petID = petID;}

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date=date;
    }

    public Integer getVetID() {
        return vetID;
    }

    public void setVetID(Integer vetID) {
        this.vetID = vetID;
    }

    public Integer getTechID() {
        return techID;
    }

    public void setTechID(Integer techID) {
        this.techID = techID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return Long.toString(examID);
    }
}