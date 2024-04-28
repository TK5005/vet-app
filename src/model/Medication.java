package model;

public class Medication extends Inventory {
    private String interactions;

    public Medication () {super();}

    public String getInteractions() {
        return interactions;
    }

    public void setInteractions(String interactions) {
        this.interactions = interactions;
    }



}
