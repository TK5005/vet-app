package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Appointment {
    private int appointmentID;
    private int clientID;
    private int petID;
    private Integer staffID;
    private LocalDateTime appointmentDate;
    private String description;
    private LocalDateTime checkInTime;

    public Appointment(){}
    public Appointment (Integer appointmentID, int clientID, int petID, int staffID,
                        LocalDateTime appointmentDate,
                        String description){
        this.appointmentID = appointmentID;
        this.clientID = clientID;
        this.petID = petID;
        this.staffID = staffID;
        this.appointmentDate = appointmentDate;
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

    public Integer getStaffID() {
        return staffID;
    }

    public void setStaffID(Integer staffID) {
        this.staffID = staffID;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }
}