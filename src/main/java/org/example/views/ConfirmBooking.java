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

public class ConfirmBooking extends JFrame {

    private LocalDate startingDate = LocalDate.now();
    private LocalDate endingDate = LocalDate.now().plusDays(30);
    private int pricePerDay, totalPrice;

    private JLabel totalPriceLabel;
    private JTextPane carName;
    private JLabel carImage;
    private JButton confirmBtn;
    private JPanel mainPanel;
    private DatePicker startDatePicker, endDatePicker;


    ConfirmBooking(){

        {
            pricePerDay = 1000;
            Toolkit kit = Toolkit.getDefaultToolkit();
            Dimension screenSize = kit.getScreenSize();
            int W = screenSize.width+ 100;
            int H = screenSize.height+ 100;
            this.setBounds((W-100) / 4, (H-100) / 4, W / 2, H / 2);

            mainPanel = new JPanel(new BorderLayout());
            JPanel contentPanel = new JPanel(new BorderLayout());


            ImageIcon carImageSource = new ImageIcon("res\\sampleCar.png");
            Image scaledImage = carImageSource.getImage().getScaledInstance(350, 175, Image.SCALE_SMOOTH);

            carImageSource = new ImageIcon(scaledImage);
            carImage = new JLabel(carImageSource);

            JPanel underImagePanel = new JPanel();
            underImagePanel.setLayout(new BoxLayout(underImagePanel, BoxLayout.Y_AXIS));

//        nameAndBookPanel.setMaximumSize(new Dimension(300,25));

            carName = new JTextPane();
            SimpleAttributeSet attributes = new SimpleAttributeSet();
            StyleConstants.setBold(attributes, true);
            StyleConstants.setFontSize(attributes, 28);

            carName.setCharacterAttributes(attributes, true);
            carName.setText("Car Name");
            carName.setEditable(false);


//            nameAndBookPanel.add(carName);
            underImagePanel.add(carName);
            underImagePanel.add(Box.createRigidArea(new Dimension(7,10)));
            // - - - - - book date
            JPanel bookDatePanel = new JPanel();
            bookDatePanel.setLayout(new BoxLayout(bookDatePanel, BoxLayout.X_AXIS));


            Font bookFont = new Font("SansSerif", Font.BOLD, 18);
            JLabel startBookLabel = new JLabel("Booking start: ");
            startBookLabel.setFont(bookFont);
//            startBookLabel.setMinimumSize(new Dimension(100, 30));
//            startBookLabel.setMaximumSize(new Dimension(100, 30));
            JLabel endBookLabel = new JLabel("Booking end: ");
            endBookLabel.setFont(bookFont);
//            endBookLabel.setMinimumSize(new Dimension(100, 30));
//            endBookLabel.setMaximumSize(new Dimension(100, 30));


            DatePickerSettings startDateFormat = new DatePickerSettings();
            startDateFormat.setFormatForDatesCommonEra("dd/MM/yyyy");
//            startDateFormat.setAllowEmptyDates(false);
            startDateFormat.setAllowKeyboardEditing(false);
            startDateFormat.setEnableYearMenu(false);
            startDateFormat.setFontValidDate(new Font("SansSerif", Font.BOLD, 15));

            DatePickerSettings endDateFormat = startDateFormat.copySettings();

            startDatePicker = new DatePicker(startDateFormat);
//            startDatePicker.setMinimumSize(new Dimension(100, 30));
            startDatePicker.setDate(startingDate);
            startDatePicker.setMaximumSize(new Dimension(120, 33));
            startDatePicker.setPreferredSize(new Dimension(120, 33));
            startDatePicker.addDateChangeListener(new DateChangeListener() {
                @Override
                public void dateChanged(DateChangeEvent dateChangeEvent) {
                    startingDate = dateChangeEvent.getNewDate();
                    totalPrice = pricePerDay * calculateTotalDays();
                    totalPriceLabel.setText("Total price: "+ totalPrice+" SAR");
                    System.out.println(totalPrice);
                }
            });
            startDateFormat.setVetoPolicy(new StartingDatesVetoPolicy()); // user can only choose from today to 30 days ahead


            endDatePicker = new DatePicker(endDateFormat);
            endDatePicker.setMaximumSize(new Dimension(120, 33));
            endDatePicker.setPreferredSize(new Dimension(120, 33));
            endDatePicker.setDate(null);
            endDatePicker.addDateChangeListener(new DateChangeListener() {
                @Override
                public void dateChanged(DateChangeEvent dateChangeEvent) {
                    endingDate = dateChangeEvent.getNewDate();
                    // calculate total price
                    totalPrice = pricePerDay * calculateTotalDays();
                    totalPriceLabel.setText("Total price: "+ totalPrice+" SAR");
                    System.out.println(totalPrice);
                }
            });
            endDateFormat.setVetoPolicy(new EndingDatesVetoPolicy());  // user can only choose from today to 30 days ahead


//            bookDatePanel.add(Box.createHorizontalGlue());
            bookDatePanel.add(startBookLabel);
            bookDatePanel.add(startDatePicker);
            bookDatePanel.add(Box.createRigidArea(new Dimension(10,1)));
            bookDatePanel.add(endBookLabel);
            bookDatePanel.add(endDatePicker);
            underImagePanel.add(bookDatePanel);
//            bookDatePanel.add(Box.createHorizontalGlue());

            underImagePanel.add(Box.createRigidArea(new Dimension(1,10)));
            underImagePanel.add(Components.createLabelRow("Price per day: "+"1,000"+ " SAR",true));
            underImagePanel.add(Components.createLabelRow("Car model: "+"carModel",false));
            underImagePanel.add(Components.createLabelRow("Car size: "+"carSize",false));
            underImagePanel.add(Components.createLabelRow("Serial number: "+"serialNumber",false));
            totalPriceLabel = new JLabel();
            totalPriceLabel.setForeground(Color.BLUE);
            underImagePanel.add(Components.createLabelRow(totalPriceLabel,"Total price: "+ totalPrice+" SAR",true));
            carName.setMaximumSize(new Dimension(200, 40));

            confirmBtn = new JButton("Confirm");
            confirmBtn.setBackground(Color.BLUE);
            confirmBtn.setForeground(Color.WHITE);
            confirmBtn.setFont(new Font("SansSerif", Font.BOLD, 20));


            JPanel btnPanel = new JPanel(new GridLayout(1,1));
            btnPanel.add(confirmBtn);
            btnPanel.setMaximumSize(new Dimension(200, 40));
            btnPanel.setPreferredSize(new Dimension(200, 40));

            underImagePanel.add(btnPanel);



            contentPanel.add(carImage, BorderLayout.NORTH);

            JLabel paddingPanel = new JLabel();
            paddingPanel.setLayout(new BoxLayout(paddingPanel,BoxLayout.X_AXIS));
            paddingPanel.add(Box.createRigidArea(new Dimension(35, 1)));
            paddingPanel.add(underImagePanel);
            paddingPanel.add(Box.createRigidArea(new Dimension(35, 1)));

            contentPanel.add(paddingPanel, BorderLayout.CENTER);


            mainPanel.add(new Components().createSideBarPanel(), BorderLayout.WEST);

            mainPanel.add(contentPanel, BorderLayout.CENTER);


            this.add(mainPanel);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setVisible(true);

        }

    }
    private int calculateTotalDays(){
        int totalDays;
        if (startingDate.getYear() == endingDate.getYear()) {
            totalDays = endingDate.getDayOfYear() - startingDate.getDayOfYear() + 1;
        }
        else {
            totalDays = 365 - startingDate.getDayOfYear() + endingDate.getDayOfYear() + 2;
        }
        System.out.println(totalDays);
        return totalDays;
    }
    // Custom veto policy to disallow past dates
    private class StartingDatesVetoPolicy implements DateVetoPolicy {
        @Override
        public boolean isDateAllowed(LocalDate date) {
            LocalDate today = LocalDate.now();
            LocalDate maxDate = today.plusDays(60); // Calculate the maximum allowed date (30 days from today)
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
            LocalDate maxDate = today.plusDays(60); // Calculate the maximum allowed date (30 days from today)

            boolean isNotBeforeStart = ! date.isBefore(startingDate);
            return !date.isBefore(today) && !date.isAfter(maxDate) && isNotBeforeStart ;
        }
    }


    public static void main(String[] args) {
        FlatLightLaf.setup();

        new ConfirmBooking();
    }
}
