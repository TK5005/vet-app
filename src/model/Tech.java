package model;

public class Tech extends Staff {
    private String certNumber;
    public Tech() {
        super();
    }

    public String getCertNumber() {
        return certNumber;
    }

    public void setCertNumber(String certNumber) {
        this.certNumber = certNumber;
    }
}