package org.example.views;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import org.example.views.Components;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;

public class BrowseVehicles extends JPanel {

    private CardLayout cardLayout;
    private JPanel cardPanel;


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
        this.setLayout(new BorderLayout()); // Set layout for the main panel


        JPanel vehiclesPanel = new JPanel(new BorderLayout());

        JPanel contentHead = createHead();

        vehiclesPanel.add(contentHead, BorderLayout.NORTH);

        // Create the main panel with a GridLayout (dynamic rows, 3 columns)
        JPanel gridList = new JPanel();
        gridList.setLayout(new GridLayout(0, 3, 10, 10)); // Dynamic rows, 3 columns, 10px gaps

        // Add labels to the grid
        loadCars(gridList);

        // Add the grid panel to a scroll pane
        JScrollPane scrollPane = new JScrollPane(gridList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        vehiclesPanel.add(scrollPane, BorderLayout.CENTER);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(vehiclesPanel, "BrowseVehicles1");
        cardPanel.add(new CarDetails(cardPanel, cardLayout), "CarDetails");

        add(cardPanel, BorderLayout.CENTER);
//        cardLayout.show(cardPanel, "BrowseVehicles");

    }

    private void loadCars(JPanel gridList) {
        int totalElements = 25; // Maximum number of cars to display

        for (int id = 1; id <= totalElements; id++) {
            JPanel elementPanel = new JPanel(new BorderLayout());

            ImageIcon carImageSource = new ImageIcon("res\\sampleCar.png");
            JLabel carImage = new JLabel(carImageSource);

            JLabel carName = new JLabel("Car Name " + id, JLabel.CENTER);

            elementPanel.add(carImage, BorderLayout.CENTER);
            elementPanel.add(carName, BorderLayout.PAGE_END);
            elementPanel.addMouseListener(new MouseAction("CarDetails",id)); // Handle click
            gridList.add(elementPanel);
        }
    }

    private JPanel createHead() {
        JPanel contentHead = new JPanel(new BorderLayout());

        JPanel titleAndSearchPanel = new JPanel();
        titleAndSearchPanel.setLayout(new BoxLayout(titleAndSearchPanel, BoxLayout.X_AXIS));
        pageTitle = new JTextPane();

        // Styled text
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setBold(attributes, true);
        StyleConstants.setFontSize(attributes, 25);

        pageTitle.setCharacterAttributes(attributes, true);
        pageTitle.setText("Browse Vehicles");
        pageTitle.setEditable(false);

        searchLabel = new JLabel("Search: ");
        searchTf = new JTextField(20);

        titleAndSearchPanel.add(pageTitle);
        titleAndSearchPanel.add(Box.createHorizontalGlue());
        titleAndSearchPanel.add(searchLabel);
        titleAndSearchPanel.add(searchTf);

        contentHead.add(titleAndSearchPanel, BorderLayout.NORTH);

        // Booking date panel
        JPanel bookDatePanel = new JPanel();
        bookDatePanel.setLayout(new BoxLayout(bookDatePanel, BoxLayout.X_AXIS));

        startBookLabel = new JLabel("Booking start:");
        endBookLabel = new JLabel("Booking end:");

        DatePickerSettings startDateFormat = new DatePickerSettings();
        startDateFormat.setFormatForDatesCommonEra("dd/MM/yyyy");
        startDateFormat.setAllowEmptyDates(false);

        DatePickerSettings endDateFormat = startDateFormat.copySettings();

        startDatePicker = new DatePicker(startDateFormat);
        startDatePicker.setDate(startingDate);
        startDatePicker.addDateChangeListener(event -> startingDate = event.getNewDate());

        endDatePicker = new DatePicker(endDateFormat);
        endDatePicker.setDate(endingDate);
        endDatePicker.addDateChangeListener(event -> endingDate = event.getNewDate());

        bookDatePanel.add(Box.createHorizontalGlue());
        bookDatePanel.add(startBookLabel);
        bookDatePanel.add(startDatePicker);
        bookDatePanel.add(endBookLabel);
        bookDatePanel.add(endDatePicker);

        contentHead.add(bookDatePanel, BorderLayout.SOUTH);
        return contentHead;
    }

    private class MouseAction implements MouseListener {
        private String destination;

        MouseAction(String destination, int id) {
            this.destination = destination;
            System.out.println(id+"");
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            cardLayout.show(cardPanel, destination);
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }

    public static void main(String[] args) {
        // Test the panel in a standalone JFrame
        JFrame frame = new JFrame("Browse Vehicles Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new BrowseVehicles());
        frame.setVisible(true);
    }
}
