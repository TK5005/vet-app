package model;

public class Tech extends Staff {
    String certNumber;
    public Tech() {
        super();
    }
    public String getCertification() {
        return certNumber;
    }

    public void setCertification(String certNumber) {
        this.certNumber = certNumber;
    }
}