package org.example.view;

import org.example.classes.*;
import org.example.controllers.AgreementController;
import org.example.controllers.BookingController;
import org.example.controllers.UserController;
import org.example.controllers.VehicleController;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import com.formdev.flatlaf.FlatLightLaf;

public class RentalAgreement extends JFrame {

    public RentalAgreement(Vehicle vehicle, User user, LocalDate start, LocalDate end, boolean is_confirm) {
        super("Rental Agreement");

        // Set layout and size of the frame
        setLayout(new BorderLayout());
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Booking Details Panel
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(5, 1, 10, 10)); // 5 rows, 1 column with spacing

        // Getting Vehicle details
        long diffInDays = ChronoUnit.DAYS.between(start, end);
        JLabel customerNameLabel = new JLabel("Customer Name: " + user.getName());
        JLabel rentalDatesLabel = new JLabel("Rental Dates: " + start + " to " + end);
        JLabel totalPriceLabel = new JLabel("Total Price: $" + (diffInDays * vehicle.getCarModel().getPrice()));
        JLabel vehicleDetailsLabel = new JLabel("Vehicle: " + vehicle.getCarModel().getName());

        detailsPanel.add(customerNameLabel);
        detailsPanel.add(rentalDatesLabel);
        detailsPanel.add(totalPriceLabel);
        detailsPanel.add(vehicleDetailsLabel);

        // Terms and Conditions Panel
        JTextArea termsArea = new JTextArea();
        termsArea.setText("""
                CAR RENTAL AGREEMENT TERMS

                1. Rental Period:
                The vehicle must be returned on or before the agreed return date and time. Late returns may incur additional charges.

                2. Payment:
                The renter is responsible for rental fees, taxes, and any additional charges for damages or late returns.

                3. Vehicle Usage:
                The renter must use the vehicle responsibly, follow all traffic laws, and not allow unauthorized drivers.

                4. Damage Liability:
                The renter is liable for any damages not covered by insurance.

                5. Insurance:
                Additional insurance options are available and recommended.

                6. Prohibited Uses:
                The vehicle must not be used for racing, towing, or illegal activities.

                By booking a vehicle, you agree to these terms.
                """);
        termsArea.setEditable(false);
        termsArea.setWrapStyleWord(true);
        termsArea.setLineWrap(true);
        JScrollPane termsScrollPane = new JScrollPane(termsArea);
        termsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        if (is_confirm) {
            JButton acceptButton = new JButton("Accept");
            JButton cancelButton = new JButton("Cancel");

            buttonsPanel.add(acceptButton);
            buttonsPanel.add(cancelButton);

            // Button Action Listeners
            acceptButton.addActionListener(e -> {
                BookingController bookingController = new BookingController();
                Timestamp fromstamp = Timestamp.valueOf(start.atStartOfDay());
                Timestamp tostamp = Timestamp.valueOf(end.atStartOfDay());
                Booking booking = bookingController.createBooking(user.getId(), vehicle.getId(), fromstamp, tostamp);
                if (booking == null) {
                    return;
                }
                AgreementController agreementController = new AgreementController();
                agreementController.createAgreement(booking.getId(), new Timestamp(System.currentTimeMillis()));
                JOptionPane.showMessageDialog(this, "Agreement accepted!");
                dispose(); // Close the frame
            });

            cancelButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(this, "Agreement canceled.");
                dispose(); // Close the frame
            });
        } else {
            JButton backButton = new JButton("Back");
            backButton.addActionListener(e -> dispose());
            buttonsPanel.add(backButton);
        }

        add(detailsPanel, BorderLayout.NORTH);
        add(termsScrollPane, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        // Center the frame on the screen
        setLocationRelativeTo(null);

        setVisible(true);
        FlatLightLaf.setup();
    }
}
