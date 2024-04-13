package model;

import java.time.LocalDateTime;

public class Invoice
{
    public static enum Status
    {
        PAID,
        UNPAID,
        OVERDUE
    }

    private long invoiceNo;
    private long examID;
    private long clientID;
    private double amtDue = 0.0;
    private Status status;
    private LocalDateTime invoiceDate;

    public long getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(long invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public long getExamID() {
        return examID;
    }

    public void setExamID(int examID) {
        this.examID = examID;
    }

    public long getClientID() {
        return clientID;
    }

    public void setClientID(long clientID) {
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
        if(this.status != null)
        {
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

    public static String[] getStatusOptions()
    {
        String[] options = new String[Status.values().length];
        int i = 0;
        for(Status status : Status.values())
        {
            options[i] = status.toString();
            i++;
        }
        return options;
    }

    public LocalDateTime getInvoiceDate() {
        if(invoiceDate == null)
        {
            return LocalDateTime.now();
        }
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDateTime invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
}