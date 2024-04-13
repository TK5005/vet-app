package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Exam
{
    private long examID;
    private long petID;
    private LocalDateTime dateTime;
    private long vetID;
    private long techID;
    private String description;
    private long weight;
    private String location;
    private String vitals;

    public Exam()
    {

    }

    public String getVitals() {
        return vitals;
    }

    public void setVitals(String vitals) {
        this.vitals = vitals;
    }

    public long getExamID() {
        return examID;
    }

    public void setExamID(long examID) {
        this.examID = examID;
    }

    public long getPetID() {
        return petID;
    }

    public void setPetID(long petID) {
        this.petID = petID;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime date) {
        this.dateTime = date;
    }

    public String getTime() {
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedDate = dateTime.format(myFormatObj);
        return formattedDate;
    }

    public long getVetID() {
        return vetID;
    }

    public void setVetID(long vetID) {
        this.vetID = vetID;
    }

    public long getTechID() {
        return techID;
    }

    public void setTechID(long techID) {
        this.techID = techID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getWeight() {
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