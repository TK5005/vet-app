package model;

public class Vet extends Staff {
    private long licenseNumber;

    public Vet() {
        super();
    }

    public long getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(long licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
}