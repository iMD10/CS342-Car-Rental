package org.example.view;

import org.example.classes.Booking;
import org.example.classes.Vehicle;
import org.example.classes.User;
import org.example.controllers.AgreementController;
import org.example.controllers.BookingController;
import org.example.views.UserUIWindow;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.security.cert.Extension;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RentalAgreement extends JFrame {

    public RentalAgreement(Vehicle vehicle, User user, LocalDate start, LocalDate end, boolean isConfirm,  CardLayout cardLayout, JPanel cardPanel) {
        super("Rental Agreement");

        setLayout(new BorderLayout());
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel titleLabel = new JLabel("Rental Agreement");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(titleLabel);

        JPanel centerPanel = new JPanel(new BorderLayout());

        JPanel detailsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        long diffInDays = ChronoUnit.DAYS.between(start, end);
        JLabel customerNameLabel = new JLabel("Customer Name: " + user.getName());
        JLabel rentalDatesLabel = new JLabel("Rental Dates: " + start + " to " + end);
        JLabel totalPriceLabel = new JLabel("Total Price: $" + (diffInDays * vehicle.getCarModel().getPrice()));
        JLabel vehicleDetailsLabel = new JLabel("Vehicle: " + vehicle.getCarModel().getName());

        detailsPanel.add(customerNameLabel);
        detailsPanel.add(rentalDatesLabel);
        detailsPanel.add(totalPriceLabel);
        detailsPanel.add(vehicleDetailsLabel);

        centerPanel.add(detailsPanel, BorderLayout.NORTH);

        JPanel termsPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        termsPanel.setBorder(BorderFactory.createTitledBorder(null, "Car Rental Agreement Terms", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.BOLD, 16)));

        termsPanel.add(createTermLabel("1. Rental Period: The vehicle must be returned on or before the agreed return date and time. Late returns may incur additional charges."));
        termsPanel.add(createTermLabel("2. Payment: The renter is responsible for rental fees, taxes, and any additional charges for damages or late returns."));
        termsPanel.add(createTermLabel("3. Vehicle Usage: The renter must use the vehicle responsibly, follow all traffic laws, and not allow unauthorized drivers."));
        termsPanel.add(createTermLabel("4. Damage Liability: The renter is liable for any damages not covered by insurance."));
        termsPanel.add(createTermLabel("5. Insurance: Additional insurance options are available and recommended."));
        termsPanel.add(createTermLabel("6. Prohibited Uses: The vehicle must not be used for racing, towing, or illegal activities."));
        termsPanel.add(createTermLabel("By confirming you agree to the terms."));

        centerPanel.add(termsPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        if (isConfirm) {
            JButton acceptButton = new JButton("Accept");
            JButton cancelButton = new JButton("Cancel");

            acceptButton.addActionListener(e -> {

                createAgreementInBackground( start, end, user, vehicle);
                JOptionPane.showMessageDialog(this, "Agreement accepted!");

                if (cardPanel != null && cardLayout != null )
                    cardLayout.show(cardPanel, UserUIWindow.BOOKING_DONE_PANEL);

                dispose();
            });

            cancelButton.addActionListener(e -> {
                JOptionPane.showMessageDialog(this, "Agreement canceled.");
                dispose();
            });

            buttonsPanel.add(acceptButton);
            buttonsPanel.add(cancelButton);
        } else {
            JButton backButton = new JButton("Back");
            backButton.addActionListener(e -> dispose());
            buttonsPanel.add(backButton);
        }

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JLabel createTermLabel(String text) {
        JLabel termLabel = new JLabel("<html>" + text + "</html>");
        termLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        return termLabel;
    }

    private void createAgreementInBackground(LocalDate start, LocalDate end, User user, Vehicle vehicle) {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {

                BookingController bookingController = new BookingController();
                Timestamp fromStamp = Timestamp.valueOf(start.atStartOfDay());
                Timestamp toStamp = Timestamp.valueOf(end.atStartOfDay());
                Booking booking = bookingController.createBooking(user.getId(), vehicle.getId(), fromStamp, toStamp);

                if (booking == null) return null ;

                AgreementController agreementController = new AgreementController();
                agreementController.createAgreement(booking.getId(), new Timestamp(System.currentTimeMillis()));
                return null;
            }



        };

        worker.execute();
    }

    void test(){
        System.out.println("This is not my first commit :(");

    }

}
