package model;

import java.time.LocalDate;

public class Invoice {
    public static enum Status {
        PAID,
        UNPAID,
        OVERDUE
    }

    private int invoiceID;
    private int examID;
    private int clientID;
    private double amtDue = 0.0;
    private Status status;
    private LocalDate invoiceDate;

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public int getExamID() {
        return examID;
    }

    public void setExamID(int examID) {
        this.examID = examID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public double getAmtDue() {
        return amtDue;
    }

    public void setAmtDue(double amtDue) {
        this.amtDue = amtDue;
    }

    public Status getStatus() {
        return this.status;
    }

    public String getStatusString() {
        if (this.status != null) {
            switch (this.status) {
                case PAID:
                    return "PAID";
                case UNPAID:
                    return "UNPAID";
                case OVERDUE:
                    return "OVERDUE";
                default:
                    return "";
            }
        } else {
            return "";
        }
    }

    public String getFormattedAmtDue() {
        return String.format("$%.2f", amtDue);
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static String[] getStatusOptions() {
        String[] options = new String[Status.values().length];
        int i = 0;
        for (Status status : Status.values()) {
            options[i] = status.toString();
            i++;
        }
        return options;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
}