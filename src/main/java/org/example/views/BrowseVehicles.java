package org.example.views;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import org.example.classes.CarModel;
import org.example.classes.User;
import org.example.classes.Vehicle;
import org.example.controllers.VehicleController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BrowseVehicles extends JPanel {

    private  boolean isLoading = false;
    private  Vehicle selectedCar = null;
    private  User loggedUser;

    private VehicleController vehicleController;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JPanel gridList;
    private List<Vehicle> vehiclesList;

    private LocalDate startingDate = LocalDate.now();
    private LocalDate endingDate = LocalDate.now().plusDays(30);

    private JTextField searchTf;
    private JTextPane pageTitle;

    private DatePicker startDatePicker, endDatePicker;

    final private String FILTER_ALL = "all";

    public BrowseVehicles(User loggedUser) {
        this.setLayout(new BorderLayout()); // Set layout for the main panel
        this.loggedUser = loggedUser;
        vehicleController = new VehicleController();
        System.out.println("loggedUser:");
        System.out.println(loggedUser.getEmail());


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

        add(cardPanel, BorderLayout.CENTER);
//        cardLayout.show(cardPanel, "BrowseVehicles");

    }
    private JPanel createHead() {
        JPanel contentHead = new JPanel(new BorderLayout());
        contentHead.setBorder(new EmptyBorder(5, 7, 5, 7));  // 10px padding on top and bottom, 20px on left and right

        JPanel titleAndSearchPanel = new JPanel();
        titleAndSearchPanel.setLayout(new BorderLayout());
        JLabel pageTitle = new JLabel("Browse Vehicles");
        pageTitle.setFont(new Font("SansSerif", Font.BOLD, 20));

        JLabel searchLabel = new JLabel("Search: ");
        searchLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));

        searchTf = new JTextField(15);
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.add(searchLabel);
        searchPanel.add(searchTf);

        titleAndSearchPanel.add(pageTitle, BorderLayout.CENTER);
        titleAndSearchPanel.add(searchPanel, BorderLayout.EAST);

        contentHead.add(titleAndSearchPanel, BorderLayout.NORTH);

        // Booking date panel
        JPanel filtersPanel = new JPanel();
        filtersPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0,10));

        Font filtersFont = new Font("SansSerif", Font.PLAIN, 13);
        JLabel byTypeLabel = new JLabel("Filter by type: ");
        byTypeLabel.setFont(filtersFont);
        JLabel startBookLabel = new JLabel("Booking start: ");
        startBookLabel.setFont(filtersFont);
        JLabel endBookLabel = new JLabel("Booking end: ");
        endBookLabel.setFont(filtersFont);

        JComboBox<String> typeComboBox = new JComboBox<>(getVehicleTypes());
        typeComboBox.setFont(filtersFont);
        typeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                filterByType(typeComboBox.getSelectedItem().toString());


            }
        });
        Font datesFont = new Font("SansSerif", Font.BOLD, 13);

        DatePickerSettings startDateFormat = new DatePickerSettings();
        startDateFormat.setFormatForDatesCommonEra("dd/MM/yyyy");
        startDateFormat.setAllowEmptyDates(false);
        startDateFormat.setAllowKeyboardEditing(false);
        startDateFormat.setFontCalendarDateLabels(filtersFont);
        startDateFormat.setFontValidDate(datesFont);
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



        filtersPanel.add(byTypeLabel);
        filtersPanel.add(typeComboBox);

        filtersPanel.add(Box.createRigidArea(new Dimension(30,1)));

        filtersPanel.add(startBookLabel);
        filtersPanel.add(startDatePicker);

        filtersPanel.add(Box.createRigidArea(new Dimension(30,1)));
        filtersPanel.add(endBookLabel);
        filtersPanel.add(endDatePicker);


        contentHead.add(filtersPanel, BorderLayout.SOUTH);
        return contentHead;

    }

    private void filterByType(String type) {

        gridList.removeAll();
        for (Vehicle vehicle : vehiclesList) {
//            System.out.println(vehicle.getCarModel().getType() + " != " + type);

            if ( !(vehicle.getCarModel().getType()) .equalsIgnoreCase(type) && !(type.equalsIgnoreCase(FILTER_ALL)) ) continue;
            JPanel elementPanel = createElementPanel(vehicle);
            gridList.add(elementPanel);
        }

        // Revalidate and repaint to update the UI
        gridList.revalidate();
        gridList.repaint();
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
//                System.out.println("_________________");
//                System.out.println(vehicleController.getAvailableVehicles(
//                        Timestamp.valueOf(startingDate.atStartOfDay()),
//                        Timestamp.valueOf(endingDate.atStartOfDay())
//                ).size());
//                System.out.println("_________________");

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
                    System.out.println(vehiclesList.size());


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

        ImageIcon carImageSource = new ImageIcon("res\\"+vehicle.getCarModel().getName()+".png");
        JLabel carImage = new JLabel(carImageSource);

        JLabel carName = new JLabel(vehicle.getCarModel().getName()+ " " +vehicle.getCarModel().getModelYear()  , JLabel.CENTER);
        carName.setFont(new Font("SansSerif", Font.BOLD, 16));
        JLabel carPrice = new JLabel(vehicle.getCarModel().getPrice() + " SAR"  , JLabel.CENTER);
        carPrice.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JPanel nameAndPrice = new JPanel(new BorderLayout());
        nameAndPrice.add(carName, BorderLayout.NORTH);
        nameAndPrice.add(carPrice, BorderLayout.SOUTH);

        elementPanel.add(carImage, BorderLayout.CENTER);
        elementPanel.add(nameAndPrice, BorderLayout.SOUTH);
        elementPanel.addMouseListener(new carDetailsClick(vehicle, elementPanel,nameAndPrice)); // Handle click
        return elementPanel;
    }
    private String[] getVehicleTypes() {
        // Fetch vehicle types dynamically from the controller
        List<CarModel> carModels= vehicleController.getCarModels();
        List<String> types = new ArrayList<>(carModels.size());

        types.add(FILTER_ALL);
        for(CarModel cars :carModels) {
            types.add(cars.getType());
        }
        return types.toArray(new String[0]);
    }


    private class carDetailsClick implements MouseListener {
        private final JPanel elementPanel;
        private final JPanel nameAndPrice;
        private Vehicle selectedCar_;
        private Color orignalColor;

        carDetailsClick(Vehicle selectedCar_, JPanel elementPanel, JPanel nameAndPrice) {
//          this.destination = destination;
//            System.out.println(selectedCar);
            this.selectedCar_ = selectedCar_;
            this.elementPanel = elementPanel;
            this.nameAndPrice = nameAndPrice;
            this.orignalColor = elementPanel.getBackground();
        }

        @Override
        public void mouseClicked(MouseEvent e) {

            cardPanel.add(new CarDetails( loggedUser, cardPanel, cardLayout, selectedCar_, startingDate, endingDate), UserUIWindow.CAR_DETAILS_PANEL);
            cardLayout.show(cardPanel, UserUIWindow.CAR_DETAILS_PANEL);


            if (cardPanel.getComponents().length == 3) cardPanel.remove(1); // remove last carDetails panel
//            System.out.println("- - - -");
//            System.out.println(cardPanel.getComponents().length);
//            System.out.println("- - - -");
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {
            int red = Math.min(255, (int) (orignalColor.getRed() * 0.9));
            int green = Math.min(255, (int) (orignalColor.getGreen() * 0.9));
            int blue = Math.min(255, (int) (orignalColor.getBlue() * 0.9));

            elementPanel.setBackground(new Color(red, green, blue));
            nameAndPrice.setBackground(new Color(red, green, blue));
        }

        @Override
        public void mouseExited(MouseEvent e) {

            elementPanel.setBackground(orignalColor);
            nameAndPrice.setBackground(orignalColor);
        }
    }

    // Custom veto policy to disallow past dates
    private class StartingDatesVetoPolicy implements DateVetoPolicy {
        @Override
        public boolean isDateAllowed(LocalDate date) {
            LocalDate today = LocalDate.now();
            LocalDate maxDate = today.plusDays(30); // Calculate the maximum allowed date (30 days from today)
            // Allow only dates from today to 30 days in the future
            boolean isNotAfterEnd = ! date.isAfter(endingDate.minusDays(1));
            return !date.isBefore(today) && !date.isAfter(maxDate) && isNotAfterEnd;
        }
    }

    // Custom veto policy to disallow 30+ days
    private class EndingDatesVetoPolicy implements DateVetoPolicy {
        @Override
        public boolean isDateAllowed(LocalDate date) {
            LocalDate today = LocalDate.now().plusDays(1);
            LocalDate maxDate = today.plusDays(29); // Calculate the maximum allowed date (30 days from today)

            boolean isNotBeforeStart = ! date.isBefore(startingDate.plusDays(1));
            return !date.isBefore(today) && !date.isAfter(maxDate) && isNotBeforeStart ;
        }
    }



}
