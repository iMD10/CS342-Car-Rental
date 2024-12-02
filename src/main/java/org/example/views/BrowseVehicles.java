package org.example.views;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;


public class BrowseVehicles extends JFrame {

    private LocalDate startingDate = LocalDate.now();
    private LocalDate endingDate = LocalDate.now().plusDays(30);


    private JPanel accountSideButton, browseSideButton, historySideButton;
    private JLabel buttonText, searchLabel, startBookLabel, endBookLabel;
    private JTextField searchTf;
    private JTextPane pageTitle;
    private ImageIcon carImage;
    private JLabel accIcon;
    private DatePicker startDatePicker, endDatePicker;


    public BrowseVehicles() {

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int W = screenSize.width;
        int H = screenSize.height;
        this.setBounds(W / 4, H / 4, W / 2, H / 2);


        JPanel mainPanel = new JPanel(new BorderLayout());      // GLOBAL panel
        JPanel contentPanel = new JPanel(new BorderLayout());   // main content (the Right)

        JPanel sideBarPanel = createSideBarPanel();             // the left navigation bar
        JPanel contentHead = createHead();

        mainPanel.add(sideBarPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        contentPanel.add(contentHead, BorderLayout.NORTH);

        // Create the main panel with a GridLayout (dynamic rows, 3 columns)
        JPanel gridList = new JPanel();
        gridList.setLayout(new GridLayout(0, 3, 10, 10)); // Dynamic rows, 3 columns, 10px gaps

        // Add labels to the grid
        loadCars(gridList);

        // Add the grid panel to a scroll pane
        JScrollPane scrollPane = new JScrollPane(gridList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        contentPanel.add(scrollPane, BorderLayout.CENTER);


        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    private void loadCars(JPanel gridList) {
        // Here, load car details from DB, loop over each car and display as element

        int totalElements = 25; // We can control maximum number of cars to display

        for (int id = 1; id <= totalElements; id++) {
            JPanel elementPanel = new JPanel(new BorderLayout());


            ImageIcon carImageSource = new ImageIcon("res\\sampleCar.png");
//            Image scaledImage = carImageSource.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
//            carImageSource = new ImageIcon(scaledImage);
            JLabel carImage = new JLabel(carImageSource);

            JLabel carName = new JLabel("Car Name " + id, JLabel.CENTER);


            elementPanel.add(carImage, BorderLayout.CENTER);
            elementPanel.add(carName, BorderLayout.PAGE_END);
            elementPanel.addMouseListener(new MouseAction("element id =" + id)); // Pass car ID to the actionListener, ID is important to show more details about car and book it in the booking screen
            gridList.add(elementPanel);

        }
    }

    /**
     * Create sideBar with buttons
     *
     * @return JPanel object of the side bar
     */
    private JPanel createSideBarPanel() {
        // Create the sidebar panel with BoxLayout
        JPanel sideBarPanel = new JPanel();
        sideBarPanel.setLayout(new BoxLayout(sideBarPanel, BoxLayout.Y_AXIS)); // Vertical stacking

        // Add glue at the top for automatic spacing
        sideBarPanel.add(Box.createVerticalGlue());

        // Create the sidebar buttons using the helper method
        accountSideButton = createSideBarButton("res\\personIcon.png", "Account");
        browseSideButton = createSideBarButton("res\\searchIcon.png", "Browse");
        historySideButton = createSideBarButton("res\\bookingIcon.png", "History");

        // Add buttons with automatic spacing
        sideBarPanel.add(accountSideButton);
        sideBarPanel.add(Box.createVerticalGlue()); // Add glue to create flexible space
        sideBarPanel.add(browseSideButton);
        sideBarPanel.add(Box.createVerticalGlue()); // Add glue to create flexible space
        sideBarPanel.add(historySideButton);

        // Add glue at the bottom for automatic spacing
        sideBarPanel.add(Box.createVerticalGlue());
        return sideBarPanel;
    }

    /**
     * Create sideBar button
     *
     * @return JPanel object of the button
     */
    private JPanel createSideBarButton(String imageIconPath, String buttonName) {
        carImage = new ImageIcon(imageIconPath);
        // Resize the image
//        Image scaledImage = carImage.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
//        carImage = new ImageIcon(scaledImage);
        accIcon = new JLabel(carImage);

        buttonText = new JLabel(buttonName);
        buttonText.setSize(100, 100);

        JPanel accBtnPan = new JPanel();
        accBtnPan.setLayout(new BoxLayout(accBtnPan, BoxLayout.Y_AXIS)); // Stack components vertically

        // Add icon and text to the panel
        accBtnPan.add(accIcon);
        accBtnPan.add(buttonText);

        return accBtnPan;
    }

    private JPanel createHead() {
        JPanel contentHead = new JPanel(new BorderLayout());


        JPanel titleAndSearchPanel = new JPanel();
        titleAndSearchPanel.setLayout(new BoxLayout(titleAndSearchPanel, BoxLayout.X_AXIS));
        pageTitle = new JTextPane();
        // Create styled text
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setBold(attributes, true);
        StyleConstants.setFontSize(attributes, 25);

        pageTitle.setCharacterAttributes(attributes, true);
        pageTitle.setText("Browse Vehicles");
        pageTitle.setEditable(false);


        searchLabel = new JLabel("Search: ");
        searchLabel.setSize(100, 30);

        searchTf = new JTextField(20);
        searchTf.setMinimumSize(new Dimension(100, 30));
        searchTf.setMaximumSize(new Dimension(100, 30));
        titleAndSearchPanel.add(pageTitle);
        titleAndSearchPanel.add(Box.createHorizontalGlue());
        titleAndSearchPanel.add(searchLabel);
        titleAndSearchPanel.add(searchTf);

        contentHead.add(titleAndSearchPanel, BorderLayout.PAGE_START);


        // - - - - - book date
        JPanel bookDatePanel = new JPanel();
        bookDatePanel.setLayout(new BoxLayout(bookDatePanel, BoxLayout.X_AXIS));


        startBookLabel = new JLabel("Booking start:");
        startBookLabel.setMinimumSize(new Dimension(100, 30));
        startBookLabel.setMaximumSize(new Dimension(100, 30));
        endBookLabel = new JLabel("Booking end:");
        endBookLabel.setMinimumSize(new Dimension(100, 30));
        endBookLabel.setMaximumSize(new Dimension(100, 30));


        DatePickerSettings startDateFormat = new DatePickerSettings();
        startDateFormat.setFormatForDatesCommonEra("dd/MM/yyyy");
        startDateFormat.setAllowEmptyDates(false);
        startDateFormat.setAllowKeyboardEditing(false);
        startDateFormat.setEnableYearMenu(false);

        DatePickerSettings endDateFormat = startDateFormat.copySettings();

        startDatePicker = new DatePicker(startDateFormat);
        startDatePicker.setMinimumSize(new Dimension(100, 30));
        startDatePicker.setMaximumSize(new Dimension(100, 30));
        startDatePicker.setDate(startingDate);
        startDatePicker.addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent dateChangeEvent) {
                startingDate = dateChangeEvent.getNewDate();

            }
        });
        startDateFormat.setVetoPolicy(new StartingDatesVetoPolicy()); // user can only choose from today to 30 days ahead


        endDatePicker = new DatePicker(endDateFormat);
        endDatePicker.setMinimumSize(new Dimension(100, 30));
        endDatePicker.setMaximumSize(new Dimension(100, 30));
        endDatePicker.setDate(endingDate);
        endDatePicker.addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent dateChangeEvent) {
                endingDate = dateChangeEvent.getNewDate();
            }
        });
        endDateFormat.setVetoPolicy(new EndingDatesVetoPolicy());  // user can only choose from today to 30 days ahead


        bookDatePanel.add(Box.createHorizontalGlue());
        bookDatePanel.add(startBookLabel);
        bookDatePanel.add(startDatePicker);
        bookDatePanel.add(endBookLabel);
        bookDatePanel.add(endDatePicker);

        contentHead.add(bookDatePanel, BorderLayout.PAGE_END);
        return contentHead;
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

        BrowseVehicles bv = new BrowseVehicles();

    }

    private class MouseAction implements MouseListener {
        private String elementId;

        MouseAction(String id) {
            elementId = id;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println(elementId);
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
