package model;

import java.time.LocalDate;

public class Pet {
    private int petID;
    private int ownerID;
    private String name;
    private String sex;
    private String color;
    private String species;
    private String breed;
    private LocalDate birthdate;
    private int weight;
    private long microchipNumber;
    private long rabiesTag;

    public int getPetID() {
        return petID;
    }

    public void setPetID(int petID) {
        this.petID = petID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public long getMicrochipNumber() {
        return microchipNumber;
    }

    public void setMicrochipNumber(long microchipNumber) {
        this.microchipNumber = microchipNumber;
    }

    public long getRabiesTag() {
        return rabiesTag;
    }

    public void setRabiesTag(long rabiesTag) {
        this.rabiesTag = rabiesTag;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    @Override
    public String toString() {
        return name;
    }
}