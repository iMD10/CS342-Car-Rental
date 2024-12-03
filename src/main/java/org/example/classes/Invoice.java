package org.example.classes;
import java.sql.*;
public class Invoice {
    private int id,booking_id;
    private double late_fees,total_price;
    private Timestamp issued_at;

    public Invoice(int id,int booking_id, double late_fees, double total_price, Timestamp issued_at) {
        this.id = id;
        this.booking_id = booking_id;
        this.late_fees = late_fees;
        this.total_price = total_price;
        this.issued_at = issued_at;
    }

    public int getId() {
        return id;
    }

    public double getLate_fees() {
        return late_fees;
    }

    public void setLate_fees(double late_fees) {
        this.late_fees = late_fees;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public Timestamp getIssued_at() {
        return issued_at;
    }

    public void setIssued_at(Timestamp issued_at) {
        this.issued_at = issued_at;
    }
    public int getBooking_id() {
        return booking_id;
    }
    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }
}


