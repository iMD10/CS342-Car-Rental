package org.example.classes;

public class Invoice {
    private int id, vehicleId, userId, bookingId, totalPrice;

    public Invoice(int id, int vehicleId, int userId, int bookingId, int totalPrice) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.userId = userId;
        this.bookingId = bookingId;
        this.totalPrice = totalPrice;
    }

    /**
     * Prints invoice info for the user.
     */
    public void printInvoice(){

    }

}
