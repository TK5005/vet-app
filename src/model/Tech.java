package model;

import java.util.ArrayList;

public class Tech extends Staff
{
    private ArrayList<Certification> certifications;

    public Tech()
    {
        certifications = new ArrayList<>();
    }

    public ArrayList<Certification> getCertifications() {
        return certifications;
    }

    public void addCertification(Certification certification) {
        certifications.add(certification);
    }

    public void removeCertification(Certification certification) {
        certifications.remove(certification);
    }
}