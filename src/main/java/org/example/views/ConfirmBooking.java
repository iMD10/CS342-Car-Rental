package org.example.views;

import com.formdev.flatlaf.FlatLightLaf;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.time.LocalDate;

public class ConfirmBooking extends JPanel {

    private LocalDate startingDate = LocalDate.now();
    private LocalDate endingDate = LocalDate.now().plusDays(30);
    private DatePicker startDatePicker, endDatePicker;
    private int pricePerDay, totalPrice;

    private JLabel totalPriceLabel;
    private JTextPane carName;
    private JLabel carImage;
    private JButton confirmBtn;
    private JPanel mainPanel;

    public ConfirmBooking() {
        pricePerDay = 1000;

        // Setting the layout for the panel
        mainPanel = new JPanel(new BorderLayout());
        JPanel contentPanel = new JPanel(new BorderLayout());

        // Set up car image
        ImageIcon carImageSource = new ImageIcon("res\\sampleCar.png");
        Image scaledImage = carImageSource.getImage().getScaledInstance(350, 175, Image.SCALE_SMOOTH);
        carImageSource = new ImageIcon(scaledImage);
        carImage = new JLabel(carImageSource);

        JPanel underImagePanel = new JPanel();
        underImagePanel.setLayout(new BoxLayout(underImagePanel, BoxLayout.Y_AXIS));

        // Car name setup
        carName = new JTextPane();
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setBold(attributes, true);
        StyleConstants.setFontSize(attributes, 28);
        carName.setCharacterAttributes(attributes, true);
        carName.setText("Car Name");
        carName.setEditable(false);

        underImagePanel.add(carName);
        underImagePanel.add(Box.createRigidArea(new Dimension(7, 10)));

        // Booking date setup
        JPanel bookDatePanel = new JPanel();
        bookDatePanel.setLayout(new BoxLayout(bookDatePanel, BoxLayout.X_AXIS));

        Font bookFont = new Font("SansSerif", Font.BOLD, 18);
        JLabel startBookLabel = new JLabel("Booking start: ");
        startBookLabel.setFont(bookFont);
        JLabel endBookLabel = new JLabel("Booking end: ");
        endBookLabel.setFont(bookFont);

        DatePickerSettings startDateFormat = new DatePickerSettings();
        startDateFormat.setFormatForDatesCommonEra("dd/MM/yyyy");
        startDateFormat.setAllowKeyboardEditing(false);
        startDateFormat.setEnableYearMenu(false);
        startDateFormat.setFontValidDate(new Font("SansSerif", Font.BOLD, 15));

        DatePickerSettings endDateFormat = startDateFormat.copySettings();

        startDatePicker = new DatePicker(startDateFormat);
        startDatePicker.setDate(startingDate);
        startDatePicker.setMaximumSize(new Dimension(120, 33));
        startDatePicker.setPreferredSize(new Dimension(120, 33));
        startDatePicker.addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent dateChangeEvent) {
                startingDate = dateChangeEvent.getNewDate();
                totalPrice = pricePerDay * calculateTotalDays();
                totalPriceLabel.setText("Total price: " + totalPrice + " SAR");
            }
        });

        endDatePicker = new DatePicker(endDateFormat);
        endDatePicker.setMaximumSize(new Dimension(120, 33));
        endDatePicker.setPreferredSize(new Dimension(120, 33));
        endDatePicker.setDate(null);
        endDatePicker.addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent dateChangeEvent) {
                endingDate = dateChangeEvent.getNewDate();
                totalPrice = pricePerDay * calculateTotalDays();
                totalPriceLabel.setText("Total price: " + totalPrice + " SAR");
            }
        });

        // Add date pickers to book date panel
        bookDatePanel.add(startBookLabel);
        bookDatePanel.add(startDatePicker);
        bookDatePanel.add(Box.createRigidArea(new Dimension(10, 1)));
        bookDatePanel.add(endBookLabel);
        bookDatePanel.add(endDatePicker);
        underImagePanel.add(bookDatePanel);

        // Add car details
        underImagePanel.add(Box.createRigidArea(new Dimension(1, 10)));
        underImagePanel.add(Components.createLabelRow("Price per day: " + "1,000" + " SAR", true));
        underImagePanel.add(Components.createLabelRow("Car model: " + "carModel", false));
        underImagePanel.add(Components.createLabelRow("Car size: " + "carSize", false));
        underImagePanel.add(Components.createLabelRow("Serial number: " + "serialNumber", false));

        // Total price label
        totalPriceLabel = new JLabel();
        totalPriceLabel.setForeground(Color.BLUE);
        underImagePanel.add(Components.createLabelRow(totalPriceLabel, "Total price: " + totalPrice + " SAR", true));

        // Confirm button setup
        confirmBtn = new JButton("Confirm");
        confirmBtn.setBackground(Color.BLUE);
        confirmBtn.setForeground(Color.WHITE);
        confirmBtn.setFont(new Font("SansSerif", Font.BOLD, 20));

        JPanel btnPanel = new JPanel(new GridLayout(1, 1));
        btnPanel.add(confirmBtn);
        btnPanel.setMaximumSize(new Dimension(200, 40));
        btnPanel.setPreferredSize(new Dimension(200, 40));

        underImagePanel.add(btnPanel);

        // Final panel setup
        contentPanel.add(carImage, BorderLayout.NORTH);

        JLabel paddingPanel = new JLabel();
        paddingPanel.setLayout(new BoxLayout(paddingPanel, BoxLayout.X_AXIS));
        paddingPanel.add(Box.createRigidArea(new Dimension(35, 1)));
        paddingPanel.add(underImagePanel);
        paddingPanel.add(Box.createRigidArea(new Dimension(35, 1)));

        contentPanel.add(paddingPanel, BorderLayout.CENTER);

        // Main panel setup
        mainPanel.add(new Components().createSideBarPanel(), BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        this.setLayout(new BorderLayout());
        this.add(mainPanel, BorderLayout.CENTER);
    }

    private int calculateTotalDays() {
        return (int) java.time.temporal.ChronoUnit.DAYS.between(startingDate, endingDate);
    }

    // Custom veto policy classes
    private class StartingDatesVetoPolicy implements DateVetoPolicy {
        @Override
        public boolean isDateAllowed(LocalDate date) {
            LocalDate today = LocalDate.now();
            return !date.isBefore(today) && !date.isAfter(today.plusDays(30));
        }
    }

    private class EndingDatesVetoPolicy implements DateVetoPolicy {
        @Override
        public boolean isDateAllowed(LocalDate date) {
            LocalDate today = LocalDate.now();
            return !date.isBefore(today) && !date.isAfter(today.plusDays(30));
        }
    }
}

