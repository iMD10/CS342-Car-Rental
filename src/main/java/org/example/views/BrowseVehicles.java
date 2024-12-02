package org.example.views;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.time.LocalDate;


public class BrowseVehicles extends JFrame {

    private LocalDate startingDate = LocalDate.now() ;

    private JPanel accountSideButton,  browseSideButton, historySideButton;
    private JLabel buttonText, searchLabel, startBookLabel, endBookLabel ;
    private JTextField searchTf ;
    private JTextPane pageTitle;
    private ImageIcon buttonIcon;
    private JLabel accIcon;
    private DatePicker startDatePicker, endDatePicker;

    public BrowseVehicles(){
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int W = screenSize.width;
        int H = screenSize.height;
        this.setBounds(W/4, H/4, W/2, H/2);


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
        int totalElements = 25; // Number of elements to add
        for (int i = 1; i <= totalElements; i++) {
            JLabel label = new JLabel("element " + i, JLabel.CENTER);
            label.setPreferredSize(new Dimension(100,100));
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add a border for visibility
            gridList.add(label);
        }

        // Add the grid panel to a scroll pane
        JScrollPane scrollPane = new JScrollPane(gridList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        contentPanel.add(scrollPane, BorderLayout.CENTER);


        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    /**
     * Create sideBar with buttons
     * @return JPanel object of the side bar
     */
    private JPanel createSideBarPanel() {
        // Create the sidebar panel with BoxLayout
        JPanel sideBarPanel = new JPanel();
        sideBarPanel.setLayout(new BoxLayout(sideBarPanel, BoxLayout.Y_AXIS)); // Vertical stacking

        // Add glue at the top for automatic spacing
        sideBarPanel.add(Box.createVerticalGlue());

        // Create the sidebar buttons using the helper method
         accountSideButton = createSideBarButton("C:\\Users\\Amer_\\Downloads\\Class_DB Digram - Frame 2.jpg", "Account");
         browseSideButton = createSideBarButton("C:\\Users\\Amer_\\Downloads\\Class_DB Digram - Frame 2.jpg", "Browse");
         historySideButton = createSideBarButton("C:\\Users\\Amer_\\Downloads\\Class_DB Digram - Frame 2.jpg", "History");

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
        buttonIcon = new ImageIcon(imageIconPath);
        // Resize the image
        Image scaledImage = buttonIcon.getImage().getScaledInstance(50, 50, Image.SCALE_AREA_AVERAGING);
        buttonIcon = new ImageIcon(scaledImage);
        accIcon = new JLabel(buttonIcon);

        buttonText = new JLabel(buttonName);
        buttonText.setSize(100,100);

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
        titleAndSearchPanel.setLayout(new BoxLayout(titleAndSearchPanel,BoxLayout.X_AXIS));
        JTextPane pageTitle = new JTextPane();
        // Create styled text
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setBold(attributes, true);
        StyleConstants.setFontSize(attributes, 25);

        pageTitle.setCharacterAttributes(attributes,true);
        pageTitle.setText("Browse Vehicles");

        JLabel searchLabel = new JLabel("Search: ");
        searchLabel.setSize(100,30);

        JTextField searchTf = new JTextField(20);
        searchTf.setMinimumSize(new Dimension(100,30));
        searchTf.setMaximumSize(new Dimension(100,30));
        titleAndSearchPanel.add(pageTitle);
        titleAndSearchPanel.add(Box.createHorizontalGlue());
        titleAndSearchPanel.add(searchLabel);
        titleAndSearchPanel.add(searchTf);

        contentHead.add(titleAndSearchPanel, BorderLayout.PAGE_START);




        // - - - - - book date
        JPanel bookDatePanel = new JPanel();
        bookDatePanel.setLayout(new BoxLayout(bookDatePanel, BoxLayout.X_AXIS));


        startBookLabel = new JLabel("Booking start:");
        startBookLabel.setMinimumSize(new Dimension(100,30));
        startBookLabel.setMaximumSize(new Dimension(100,30));
        endBookLabel = new JLabel("Booking end:");
        endBookLabel.setMinimumSize(new Dimension(100,30));
        endBookLabel.setMaximumSize(new Dimension(100,30));


        DatePickerSettings startDateFormat = new DatePickerSettings();
        startDateFormat.setFormatForDatesCommonEra("dd/MM/yyyy");
        startDateFormat.setAllowEmptyDates(false);
        startDateFormat.setAllowKeyboardEditing(false);
        startDateFormat.setEnableYearMenu(false);

        DatePickerSettings endDateFormat = startDateFormat.copySettings();

        startDatePicker = new DatePicker(startDateFormat);
        startDatePicker.setMinimumSize(new Dimension(100,30));
        startDatePicker.setMaximumSize(new Dimension(100,30));
        startDatePicker.setDateToToday();
        startDateFormat.setVetoPolicy(new EndingDatesVetoPolicy()); // user can only choose from today to 30 days ahead


        endDatePicker = new DatePicker(endDateFormat);
        getLayeredPane().setMinimumSize(new Dimension(100,30));
        endDatePicker.setMaximumSize(new Dimension(100,30));
        endDateFormat.setVetoPolicy(new EndingDatesVetoPolicy());  // user can only choose from today to 30 days ahead

        bookDatePanel.add(Box.createHorizontalGlue());
        bookDatePanel.add(startBookLabel);
        bookDatePanel.add(startDatePicker);
        bookDatePanel.add(endBookLabel);
        bookDatePanel.add(endDatePicker);

        contentHead.add(bookDatePanel,BorderLayout.PAGE_END);
        return contentHead;
    }

    // Custom veto policy to disallow past dates
    private static class StartingDatesVetoPolicy implements DateVetoPolicy {
        @Override
        public boolean isDateAllowed(LocalDate date) {
            LocalDate today = LocalDate.now();
            LocalDate maxDate = today.plusDays(30); // Calculate the maximum allowed date (30 days from today)
            // Allow only dates from today to 30 days in the future
            return !date.isBefore(today) && !date.isAfter(maxDate) ;
        }
    }
    // Custom veto policy to disallow 30+ days
    private static class EndingDatesVetoPolicy implements DateVetoPolicy {
        @Override
        public boolean isDateAllowed(LocalDate date) {
            LocalDate today = LocalDate.now();
            LocalDate maxDate = today.plusDays(30); // Calculate the maximum allowed date (30 days from today)
            // Allow only dates from today to 30 days in the future
            return !date.isBefore(today) && !date.isAfter(maxDate);
        }
    }

    public static void main(String[] args) {

        BrowseVehicles bv = new BrowseVehicles();

    }

}
