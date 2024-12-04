package org.example.classes;

public class CarModel {
    private int id, modelYear;
    private String name, company, type;
    private double price;

    public CarModel(int id, int modelYear, String name, String company, String type, double price) {
        this.id = id;
        this.modelYear = modelYear;
        this.name = name;
        this.company = company;
        this.type = type;
        this.price = price;
    }

   public String getDetails() {
        return name + "|" + company + "|" + type + "|" + price + "|" + modelYear;
   }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModelYear() {
        return modelYear;
    }

    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
