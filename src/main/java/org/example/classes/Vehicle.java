package org.example.classes;

public class Vehicle {
    private int id, carModelId;
    private String serialNumber;
    private String color;
    private CarModel carModel;


    public Vehicle(int id, int carModelId, int modelYear, String serialNumber, String name, String color, String company, String type, double price) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.color = color;
        this.carModel =  new CarModel(carModelId,modelYear, name, company, type, price);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(int carModelId) {
        this.carModelId = carModelId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public CarModel getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", carModelId=" + carModelId +
                ", serialNumber='" + serialNumber + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
