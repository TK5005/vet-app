package model;

public class Medication extends Inventory {
    private String interactions;
    private String dosage;


    public Medication () {super();}

    public String getInteractions() {
        return interactions;
    }

    public void setInteractions(String interactions) {
        this.interactions = interactions;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

}
