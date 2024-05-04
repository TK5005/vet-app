package model;

public class Vet extends Staff {
    private String licenseNumber;
    private String specialty;

    public Vet() {
        super();
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String spec) {
        this.specialty = spec;
    }
}