package model;

import java.time.LocalDate;

public class Appointment {
    private int appointmentID;
    private int clientID;
    private int petID;
    private int staffID;
    private String appointmentTime;
    private LocalDate appointmentDate;
    private String description;

    public Appointment(){}
    public Appointment (Integer appointmentID, int clientID, int petID, int staffID,
                        LocalDate appointmentDate, String appointmentTime,
                        String description){
        this.appointmentID = appointmentID;
        this.clientID = clientID;
        this.petID = petID;
        this.staffID = staffID;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.description = description;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getPetID() {
        return petID;
    }

    public void setPetID(int petID) {
        this.petID = petID;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}