package model;

import java.time.LocalDateTime;

public class Pet
{
    private int petID;
    private int ownerID;
    private String name;
    private String sex;
    private String color;
    private String species;
    private String breed;
    private LocalDateTime birthdate;
    private int age;
    private int weight;
    private int microchipNumber;
    private int rabiesTag;
    
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

    public LocalDateTime getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDateTime birthdate) {
        this.birthdate = birthdate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getMicrochipNumber() {
        return microchipNumber;
    }

    public void setMicrochipNumber(int microchipNumber) {
        this.microchipNumber = microchipNumber;
    }

    public int getRabiesTag() {
        return rabiesTag;
    }

    public void setRabiesTag(int rabiesTag) {
        this.rabiesTag = rabiesTag;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }
}