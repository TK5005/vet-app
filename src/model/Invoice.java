package model;

import java.time.LocalDateTime;

public class Invoice
{
    private int invoiceNo;
    private int examID;
    private int clientID;
    private double amtDue;
    private String status;
    private LocalDateTime invoiceDate;

    public int getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(int invoiceNo) {
        this.invoiceNo = invoiceNo;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDateTime invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
}