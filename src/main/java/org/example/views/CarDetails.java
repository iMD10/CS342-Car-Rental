package org.example.views;

import com.formdev.flatlaf.FlatLightLaf;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import org.example.classes.User;
import org.example.classes.Vehicle;
import org.example.view.RentalAgreement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class CarDetails extends JPanel {

    private LocalDate startingDate = LocalDate.now();
    private LocalDate endingDate = LocalDate.now().plusDays(30);
    private Vehicle selectedCar;
    private int totalPrice = 0 ;
//    private DatePicker startDatePicker, endDatePicker;

    private JLabel carImage, totalPriceLabel;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    CarDetails(User loggedUser, JPanel parentCardPanel, CardLayout parentcardLayout, Vehicle selectedCar, LocalDate startingDate, LocalDate endingDate) {
        this.selectedCar = selectedCar;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.totalPrice = calculateTotalPrice();
        JPanel contentPanel = new JPanel(new BorderLayout());


        ImageIcon carImageSource = new ImageIcon("res\\sampleCar.png");
        Image scaledImage = carImageSource.getImage().getScaledInstance(350, 175, Image.SCALE_SMOOTH);

        carImageSource = new ImageIcon(scaledImage);
        carImage = new JLabel(carImageSource);

        JPanel underImagePanel = new JPanel();
        underImagePanel.setLayout(new BoxLayout(underImagePanel, BoxLayout.Y_AXIS));

        JPanel nameAndBookPanel = new JPanel();
        nameAndBookPanel.setLayout(new BoxLayout(nameAndBookPanel, BoxLayout.X_AXIS));


        JLabel carName = new JLabel(selectedCar.getCarModel().getName()+ " " +selectedCar.getCarModel().getModelYear());
        carName.setFont(new Font("SansSerif", Font.BOLD, 28));
        carName.setMaximumSize(new Dimension(200, 40));


        nameAndBookPanel.add(carName);
        nameAndBookPanel.add(Box.createHorizontalGlue());
//        nameAndBookPanel.add(btnPanel);




        JPanel dateSelectorRow = dateSelector();
        totalPriceLabel = new JLabel();
        totalPriceLabel.setForeground(Color.BLUE);

        JButton bookBtn = new JButton("BOOK");
        bookBtn.setBackground(Color.BLUE);
        bookBtn.setForeground(Color.WHITE);
        bookBtn.setFont(new Font("SansSerif", Font.BOLD, 20));
        bookBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                cardLayout.show(cardPanel, UserUIWindow.BOOKING_DONE_PANEL);
                //new RentalAgreement(selectedCar, loggedUser, startingDate,endingDate);
            }
        });

        JPanel btnPanel = new JPanel(new GridLayout(1,3));
        btnPanel.add(bookBtn);
//        btnPanel.setMaximumSize(new Dimension(200, 40));
//        btnPanel.setPreferredSize(new Dimension(200, 40));

        underImagePanel.add(nameAndBookPanel);
        underImagePanel.add(Box.createVerticalGlue());

        underImagePanel.add(Components.createLabelRow("Price per day: " + selectedCar.getCarModel().getPrice()+ " SAR per day",true));
        underImagePanel.add(Components.createLabelRow("Company: " + selectedCar.getCarModel().getCompany(),false));
        underImagePanel.add(Components.createLabelRow("Type: " + selectedCar.getCarModel().getType(),false));
        underImagePanel.add(Components.createLabelRow("Color: " + selectedCar.getColor(),false));
        underImagePanel.add(Box.createVerticalGlue());

        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL); // Create a horizontal separator
        separator.setPreferredSize(new Dimension(300, 15)); // Set the size of the separator
        underImagePanel.add(separator);

        underImagePanel.add(dateSelectorRow);
        underImagePanel.add(Components.createLabelRow(totalPriceLabel, "Total price: " + totalPrice + " SAR", true));
        underImagePanel.add(Box.createVerticalGlue());
        underImagePanel.add(Box.createVerticalGlue());
        underImagePanel.add(Box.createVerticalGlue());

        underImagePanel.add(btnPanel);

        contentPanel.add(carImage, BorderLayout.NORTH);

        JLabel paddingPanel = new JLabel();
        paddingPanel.setLayout(new BoxLayout(paddingPanel,BoxLayout.X_AXIS));
        paddingPanel.add(Box.createRigidArea(new Dimension(50, 1)));
        paddingPanel.add(underImagePanel);
        paddingPanel.add(Box.createRigidArea(new Dimension(50, 1)));

        contentPanel.add(paddingPanel, BorderLayout.CENTER);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(contentPanel, UserUIWindow.CAR_DETAILS_PANEL);
        cardPanel.add(new ConfirmBooking(), UserUIWindow.BOOKING_DONE_PANEL);


        this.setLayout(new BorderLayout());
        this.add(cardPanel);
        this.setVisible(true);

    }

    private JPanel dateSelector(){

        // Booking date setup
        JPanel bookDatePanel = new JPanel();
//        bookDatePanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));

        bookDatePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        Font bookFont = new Font("SansSerif", Font.BOLD, 18);
        JLabel startBookLabel = new JLabel("Booking start: ");
        startBookLabel.setFont(bookFont);
        JLabel endBookLabel = new JLabel("Booking end: ");
        endBookLabel.setFont(bookFont);

        DatePickerSettings startDateFormat = new DatePickerSettings();
        startDateFormat.setFormatForDatesCommonEra("dd/MM/yyyy");
        startDateFormat.setAllowKeyboardEditing(false);
        startDateFormat.setEnableYearMenu(false);
        startDateFormat.setFontValidDate(bookFont);

        DatePickerSettings endDateFormat = startDateFormat.copySettings();

        DatePicker startDatePicker = new DatePicker(startDateFormat);
        startDatePicker.setDate(startingDate);
        startDatePicker.addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent dateChangeEvent) {
                startingDate = dateChangeEvent.getNewDate();
                int totalPrice = (int) selectedCar.getCarModel().getPrice() * calculateTotalDays();
                totalPriceLabel.setText("Total price: " + totalPrice + " SAR");
            }
        });
        startDateFormat.setVetoPolicy(new StartingDatesVetoPolicy()); // user can only choose from today to 30 days ahead


        DatePicker endDatePicker = new DatePicker(endDateFormat);
        endDatePicker.setDate(endingDate);
        endDatePicker.addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent dateChangeEvent) {
                endingDate = dateChangeEvent.getNewDate();
                int totalPrice = calculateTotalPrice();
                totalPriceLabel.setText("Total price: " + totalPrice + " SAR");
            }
        });
        endDateFormat.setVetoPolicy(new EndingDatesVetoPolicy()); // user can only choose from today to 30 days ahead

        // Add date pickers to book date panel
        bookDatePanel.add(startBookLabel);
        bookDatePanel.add(startDatePicker);
        bookDatePanel.add(Box.createRigidArea(new Dimension(10, 1)));
        bookDatePanel.add(endBookLabel);
        bookDatePanel.add(endDatePicker);
//        bookDatePanel.add(Box.createHorizontalGlue());

        return bookDatePanel;
    }

    private int calculateTotalPrice() {
        return (int) selectedCar.getCarModel().getPrice() * calculateTotalDays();
    }

    private int calculateTotalDays() {
        return (int) java.time.temporal.ChronoUnit.DAYS.between(startingDate, endingDate) + 1 ;
    }
    // Custom veto policy to disallow past dates
    private class StartingDatesVetoPolicy implements DateVetoPolicy {
        @Override
        public boolean isDateAllowed(LocalDate date) {
            LocalDate today = LocalDate.now();
            LocalDate maxDate = today.plusDays(30); // Calculate the maximum allowed date (30 days from today)
            // Allow only dates from today to 30 days in the future
            boolean isNotAfterEnd = ! date.isAfter(endingDate);
            return !date.isBefore(today) && !date.isAfter(maxDate) && isNotAfterEnd;
        }
    }

    // Custom veto policy to disallow 30+ days
    private class EndingDatesVetoPolicy implements DateVetoPolicy {
        @Override
        public boolean isDateAllowed(LocalDate date) {
            LocalDate today = LocalDate.now();
            LocalDate maxDate = today.plusDays(30); // Calculate the maximum allowed date (30 days from today)

            boolean isNotBeforeStart = ! date.isBefore(startingDate);
            return !date.isBefore(today) && !date.isAfter(maxDate) && isNotBeforeStart ;
        }
    }


    public static void main(String[] args) {
        FlatLightLaf.setup();


    }

}
