package org.example.views;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;
import org.example.classes.CarModel;
import org.example.classes.User;
import org.example.classes.Vehicle;
import org.example.common.ErrorHandler;
import org.example.controllers.VehicleController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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


        gridList = new JPanel();
        gridList.setLayout(new GridLayout(0, 3, 10, 20)); // Dynamic rows, 3 columns, 10px gaps




        JScrollPane scrollPane = new JScrollPane(gridList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        vehiclesPanel.add(scrollPane, BorderLayout.CENTER);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(vehiclesPanel, UserUIWindow.BROWSE_PANEL);

        add(cardPanel, BorderLayout.CENTER);

        loadVehicles();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                System.out.println("Component shown detected!");
                loadVehicles();
            }
        });



        this.setVisible(true);

    }
    private JPanel createHead() {
        JPanel contentHead = new JPanel(new BorderLayout());
        contentHead.setBorder(new EmptyBorder(5, 7, 5, 7));  // 10px padding on top and bottom, 20px on left and right

        JPanel titleAndSearchPanel = new JPanel();
        titleAndSearchPanel.setLayout(new BorderLayout());
        JLabel pageTitle = new JLabel("Browse Vehicles");
        pageTitle.setFont(new Font("SansSerif", Font.BOLD, 20));

        JLabel searchLabel = new JLabel("Search by name: ");
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

                loadElements(
                filterByType(typeComboBox.getSelectedItem().toString(), filterByName(searchTf.getText(), vehiclesList))
                );

            }
        });

        searchTf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                loadElements(
                        filterByName(searchTf.getText(), filterByType(typeComboBox.getSelectedItem().toString(), vehiclesList))
                );

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                loadElements(
                        filterByName(searchTf.getText(), filterByType(typeComboBox.getSelectedItem().toString(), vehiclesList))
                );

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

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
                loadVehicles();

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
                loadVehicles();

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

    private void loadElements(List<Vehicle> vehiclesList) {
        gridList.removeAll();

        if (vehiclesList == null || vehiclesList.isEmpty()) {
            showResultMessage("No results found");
        } else {
            // Update the gridList with new vehicles
            gridList.setLayout(new GridLayout(0, 3, 10, 20)); // Restore the GridLayout
            for (Vehicle vehicle : vehiclesList) {
                JPanel elementPanel = createElementPanel(vehicle);
                gridList.add(elementPanel);
            }
        }

        gridList.revalidate();
        gridList.repaint();
    }

    private void showResultMessage(String msg) {
        gridList.removeAll(); // Clear the gridList
        JLabel messageLabel = new JLabel(msg, JLabel.CENTER);
        messageLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        messageLabel.setForeground(Color.GRAY);
        gridList.setLayout(new BorderLayout());
        gridList.add(messageLabel, BorderLayout.CENTER);
        gridList.revalidate(); // Revalidate to ensure changes are reflected
        gridList.repaint();    // Repaint to refresh the UI
    }


    private List<Vehicle> filterByName(String name, List<Vehicle> vehiclesList_) {

        if (name == null || name.isEmpty()) {
            return vehiclesList_;
        }


        String lowerCaseName = name.toLowerCase();
        return vehiclesList_.stream()
                .filter(vehicle -> {
                    String vehicleName = vehicle.getCarModel().getName().toLowerCase();
                    return vehicleName.startsWith(lowerCaseName); // Match by prefix
                })
                .collect(Collectors.toList());
    }


    private List<Vehicle> filterByType(String type, List<Vehicle> vehiclesList_) {

        if (type.equals(FILTER_ALL)) return  vehiclesList_;

        List<Vehicle> filterdList = new ArrayList<>(vehiclesList.size());
        for (Vehicle vehicle : vehiclesList_) {

            if ( vehicle.getCarModel().getType().equalsIgnoreCase(type))
                filterdList.add(vehicle);

        }

        return filterdList;
    }

    /**
     * Reload the gridList with new cars based on availability of selected dates
     */
    private void loadVehicles() {
//        if (vehiclesList == null)
        showResultMessage("Loading...");

        System.out.println("loadCars start");
        SwingWorker<List<Vehicle>, Void> worker = new SwingWorker<List<Vehicle>, Void>() {
            @Override
            protected List<Vehicle> doInBackground() throws Exception {



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

                    loadElements(vehiclesList);
                    System.out.println("loadCars done");
                    System.out.println(vehiclesList.size());


                } catch (Exception e) {

                    ErrorHandler.handleException(e, "Loading Failed");
                }
            }
        };

        // Execute the worker
        worker.execute();

    }



    private JPanel createElementPanel(Vehicle vehicle) {
        JPanel elementPanel = new JPanel(new BorderLayout());

        ImageIcon carImageSource = new ImageIcon("src\\main\\java\\org\\example\\res\\"+ vehicle.getCarModel().getName() +".png");
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


    private class StartingDatesVetoPolicy implements DateVetoPolicy {
        @Override
        public boolean isDateAllowed(LocalDate date) {
            LocalDate today = LocalDate.now();
            LocalDate maxDate = today.plusDays(60);

            boolean isNotAfterEnd = ! date.isAfter(endingDate.minusDays(1));
            return !date.isBefore(today) && !date.isAfter(maxDate) && isNotAfterEnd;
        }
    }

    // Custom veto policy to disallow 30+ days
    private class EndingDatesVetoPolicy implements DateVetoPolicy {
        @Override
        public boolean isDateAllowed(LocalDate date) {
            LocalDate today = LocalDate.now().plusDays(1);
            LocalDate maxDate = today.plusDays(60);

            boolean isNotBeforeStart = ! date.isBefore(startingDate.plusDays(1));
            return !date.isBefore(today) && !date.isAfter(maxDate) && isNotBeforeStart ;
        }
    }



}
