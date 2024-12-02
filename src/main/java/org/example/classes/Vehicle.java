package org.example.classes;

public class Vehicle {
    private int id,milage,size,modelYear;
    private String serialNumber;
    private String  name,color,plate,company,type;
    private double price;


    public Vehicle(int id, int milage, int size, int modelYear, String serialNumber, String name, String color, String plate, double price, String company, String type) {
        this.id = id;
        this.milage = milage;
        this.size = size;
        this.modelYear = modelYear;
        this.serialNumber = serialNumber;
        this.name = name;
        this.color = color;
        this.plate = plate;
        this.price = price;
        this.company = company;
        this.type = type;

    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getModelYear() {
        return modelYear;
    }

    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getMilage() {
        return milage;
    }

    public void setMilage(int milage) {
        this.milage = milage;
    }
}
