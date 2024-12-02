package org.example.classes;

import java.sql.*;

public class Agreement {
    private int id, bookingId;
    private String terms;
    private Timestamp issuedAt;

    public Agreement(int id, int bookingId, String terms, Timestamp issuedAt) {
        this.id = id;
        this.bookingId = bookingId;
        this.terms = terms;
        this.issuedAt = issuedAt;
    }

    public int getId() {
        return id;
    }


    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getTerms() {
        return terms;
    }

    public void setTerms(String terms) {
        this.terms = terms;
    }

    public Timestamp getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Timestamp issuedAt) {
        this.issuedAt = issuedAt;
    }
}
