package model;

import java.util.ArrayList;

public class Vet extends Staff
{
    private int licenseNumber;
    private ArrayList<Specialty> specialties;

    public Vet()
    {
        specialties = new ArrayList<>();
    }

    public int getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(int licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public ArrayList<Specialty> getSpecialties() {
        return specialties;
    }

    public void addSpecialty(Specialty specialty) {
        specialties.add(specialty);
    }

    public void removeSpecialty(Specialty specialty) {
        specialties.remove(specialty);
    }
}