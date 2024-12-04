package org.example.views;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import org.example.classes.Vehicle;
import org.example.controllers.VehicleController;
import org.example.views.Components;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public class BrowseVehicles extends JPanel {

    private VehicleController vehicleController;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel gridList;
    private List<Vehicle> vehiclesList;
    private  boolean isLoading = false;

    private LocalDate startingDate = LocalDate.now();
    private LocalDate endingDate = LocalDate.now().plusDays(30);

//    private JPanel accountSideButton, browseSideButton, historySideButton;
    private JLabel buttonText, searchLabel, startBookLabel, endBookLabel;
    private JTextField searchTf;
    private JTextPane pageTitle;

    private DatePicker startDatePicker, endDatePicker;

    public BrowseVehicles() {
        this.setLayout(new BorderLayout()); // Set layout for the main panel

        vehicleController = new VehicleController();



        JPanel vehiclesPanel = new JPanel(new BorderLayout());

        JPanel contentHead = createHead();

        vehiclesPanel.add(contentHead, BorderLayout.NORTH);

        // Create the main panel with a GridLayout (dynamic rows, 3 columns)
        gridList = new JPanel();
        gridList.setLayout(new GridLayout(0, 3, 10, 20)); // Dynamic rows, 3 columns, 10px gaps

        // Add labels to the grid
        loadCars();

        // Add the grid panel to a scroll pane
        JScrollPane scrollPane = new JScrollPane(gridList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        vehiclesPanel.add(scrollPane, BorderLayout.CENTER);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(vehiclesPanel, UserUIWindow.BROWSE_PANEL);
        cardPanel.add(new CarDetails(cardPanel, cardLayout), UserUIWindow.CAR_DETAILS_PANEL);

        add(cardPanel, BorderLayout.CENTER);
//        cardLayout.show(cardPanel, "BrowseVehicles");

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
        startDatePicker.addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent dateChangeEvent) {
                startingDate = dateChangeEvent.getNewDate();
                loadCars();

                System.out.println(vehiclesList);
            }
        });

        startDateFormat.setVetoPolicy(new StartingDatesVetoPolicy()); // user can only choose from today to 30 days ahead


        endDatePicker = new DatePicker(endDateFormat);
        endDatePicker.setDate(endingDate);
        endDatePicker.addDateChangeListener(new DateChangeListener() {
            @Override
            public void dateChanged(DateChangeEvent dateChangeEvent) {
                endingDate = dateChangeEvent.getNewDate();
                loadCars();

            }
        });
        endDateFormat.setVetoPolicy(new EndingDatesVetoPolicy()); // user can only choose from today to 30 days ahead


        bookDatePanel.add(Box.createHorizontalGlue());
        bookDatePanel.add(startBookLabel);
        bookDatePanel.add(startDatePicker);
        bookDatePanel.add(endBookLabel);
        bookDatePanel.add(endDatePicker);


        contentHead.add(bookDatePanel, BorderLayout.SOUTH);
        return contentHead;

    }

    /**
     * Reload the gridList with new cars based on availability of selected dates
     */
    private void loadCars() {
        // Create a SwingWorker to fetch the data in the background
        System.out.println("loadCars start");
        SwingWorker<List<Vehicle>, Void> worker = new SwingWorker<List<Vehicle>, Void>() {
            @Override
            protected List<Vehicle> doInBackground() throws Exception {
                // Fetch the vehicles from the database in the background
                return vehicleController.getAvailableVehicles(
                        Timestamp.valueOf(startingDate.atStartOfDay()),
                        Timestamp.valueOf(endingDate.atStartOfDay())
                );
            }

            @Override
            protected void done() {
                try {
                    // Get the result of the background task
                    vehiclesList = get();

                    // Clear and update the gridList with the new vehicles
                    gridList.removeAll();
                    for (Vehicle vehicle : vehiclesList) {
                        JPanel elementPanel = createElementPanel(vehicle);
                        gridList.add(elementPanel);
                    }

                    // Revalidate and repaint to update the UI
                    gridList.revalidate();
                    gridList.repaint();
                    System.out.println("loadCars done");


                } catch (Exception e) {
                    e.printStackTrace(); // Handle exception
                }
            }
        };

        // Execute the worker
        worker.execute();

    }


    private JPanel createElementPanel(Vehicle vehicle) {
        JPanel elementPanel = new JPanel(new BorderLayout());

        ImageIcon carImageSource = new ImageIcon("res\\sampleCar.png");
        JLabel carImage = new JLabel(carImageSource);

        JLabel carName = new JLabel(vehicle.getCarModel().getName()  , JLabel.CENTER);
        carName.setFont(new Font("SansSerif", Font.BOLD, 16));
        JLabel carPrice = new JLabel(vehicle.getCarModel().getPrice() + " SAR"  , JLabel.CENTER);
        carPrice.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JPanel nameAndPrice = new JPanel(new BorderLayout());
        nameAndPrice.add(carName, BorderLayout.NORTH);
        nameAndPrice.add(carPrice, BorderLayout.SOUTH);

        elementPanel.add(carImage, BorderLayout.CENTER);
        elementPanel.add(nameAndPrice, BorderLayout.SOUTH);
        elementPanel.addMouseListener(new MouseAction(UserUIWindow.CAR_DETAILS_PANEL,vehicle.getId())); // Handle click
        return elementPanel;
    }


    private class MouseAction implements MouseListener {
        private String destination;

        MouseAction(String destination, int id) {
            this.destination = destination;
            System.out.println(id+"");
        }

        @Override
        public void mouseClicked(MouseEvent e) {

            Vehicle vehicle = vehicleController.getVehicleByVehicleId(vehiclesList.getFirst().getId());
            JPanel elementPanel = createElementPanel(vehicle);
            gridList.add(elementPanel);
//            cardLayout.show(cardPanel, destination);

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
        // Test the panel in a standalone JFrame
        JFrame frame = new JFrame("Browse Vehicles Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new BrowseVehicles());
        frame.setVisible(true);
    }
}
