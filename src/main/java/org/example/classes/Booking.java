package org.example.classes;

public class Booking {
    private int id, userId, invoiceId, vehicleId, branchId;
    private String bookingDate, returnDate;
    boolean carReturned = false;

    public Booking(int id, int userId, int invoiceId, int vehicleId, int branchId, String bookingDate, String returnDate) {
        this.id = id;
        this.userId = userId;
        this.invoiceId = invoiceId;
        this.vehicleId = vehicleId;
        this.branchId = branchId;
        this.bookingDate = bookingDate;
        this.returnDate = returnDate;
    }
    public boolean returnCar(){

        return false;
    }

}
