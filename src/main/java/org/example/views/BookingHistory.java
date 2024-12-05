package org.example.views;

import org.example.classes.Booking;
import org.example.classes.User;
import org.example.classes.Vehicle;
import org.example.controllers.BookingController;
import org.example.controllers.VehicleController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Comparator;
import java.util.List;

public class BookingHistory extends JPanel {

        private JPanel listPanel;
        private List<Booking> bookingsList;
    BookingHistory(User loggedUser) {
        this.setLayout(new BorderLayout()); // Set layout for the main panel

        JLabel titleLabel;


        titleLabel = new JLabel("Account Page");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 25));

        listPanel = new JPanel(new GridLayout(0, 1, 30, 20));


//        JPanel elementPanel = createElemenet();

//        listPanel.add(createElement());
//        loadBookings(loggedUser);


        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


        add(titleLabel, BorderLayout.PAGE_START);
        add(scrollPane, BorderLayout.CENTER);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                loadBookings(loggedUser);
                System.out.println("componentShown");

            }
        });


        this.setVisible(true);


    }

    private void loadBookings(User loggedUser) {
        // Create a SwingWorker to fetch and process bookings in the background
        SwingWorker<List<Vehicle>, Void> worker = new SwingWorker<List<Vehicle>, Void>() {

            @Override
            protected List<Vehicle> doInBackground() {
                BookingController bookingsController = new BookingController();
                VehicleController vehicleController = new VehicleController();

                // Fetch and sort bookings
                List<Booking> fetchedBookings = bookingsController.getAllBookingsByUserid(loggedUser.getId());
                fetchedBookings.sort(Comparator.comparing(Booking::getBookedAt)); // Latest bookings first

                // Create elements for each booking
//                List<JPanel> bookingPanels = new ArrayList<>();
                listPanel.removeAll();
                for (Booking booking : fetchedBookings) {
                    Vehicle vehicle = vehicleController.getVehicleByVehicleId(booking.getVehicleId());
                    JPanel element = createElement(vehicle, booking);
                    listPanel.add(element);
                }
                listPanel.revalidate();
                listPanel.repaint();
                return null ;
            }

            @Override
            protected void done() {
//                try {
//                    // Update the UI with the fetched booking panels
//                    List<JPanel> bookingPanels = get();
//                    listPanel.removeAll(); // Clear the current list panel
//                    for (JPanel panel : bookingPanels) {
//                        listPanel.add(panel);
//                    }
//                    System.out.println("loadBookings");
//                    listPanel.revalidate(); // Ensure layout is updated
//                    listPanel.repaint();   // Repaint to reflect changes
//                } catch (Exception e) {
//                    e.printStackTrace(); // Handle exceptions
//                }
            }
        };

        // Execute the SwingWorker
        worker.execute();
    }




    private JPanel createElement(Vehicle vehicle, Booking booking) {
        JPanel elementPanel = new JPanel();
        elementPanel.setLayout(new BoxLayout(elementPanel, BoxLayout.X_AXIS));

        ImageIcon carImageSource = new ImageIcon("res\\sampleCar.png");
        Image img = carImageSource.getImage(); // Transform the ImageIcon to Image
        Image scaledImg = img.getScaledInstance(120, 120 / 2, Image.SCALE_SMOOTH); // Resize the image
        carImageSource = new ImageIcon(scaledImg); // Create a new ImageIcon from the resized image
        JLabel carImage = new JLabel(carImageSource);

        Font carNameFont = new Font("SansSerif", Font.BOLD, 13);
        Font labelsFont = new Font("SansSerif", Font.PLAIN, 13);
        JLabel carName = new JLabel(vehicle.getCarModel().getName() +" "+ vehicle.getCarModel().getModelYear());
        carName.setFont(carNameFont);
        JLabel startDate = new JLabel("From: " + booking.getStart_date().toLocalDateTime().toLocalDate().toString());
        startDate.setFont(labelsFont);
        JLabel endDate = new JLabel("To: " + booking.getEnd_date().toLocalDateTime().toLocalDate().toString());
        endDate.setFont(labelsFont);
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.add(carName);
        detailsPanel.add(startDate);
        detailsPanel.add(endDate);

        Dimension bpreferredSize = new Dimension(110, 30);  // Same size for both
        Dimension preferredSize = new Dimension(125, 30);  // Same size for both
        JButton cancelBtn = new JButton("Cancel");

        cancelBtn.setPreferredSize(bpreferredSize);
        cancelBtn.setMaximumSize(bpreferredSize);
//        System.out.println(booking.getStatus());
        cancelBtn.setVisible( booking.getStatus().equals("active") );
        JLabel printAgreement = new JLabel("<html><u>Print Agreement</u></html>");
        printAgreement.setFont(new Font("SansSerif", Font.PLAIN, 15));
        printAgreement.setForeground(Color.BLUE);
        printAgreement.setPreferredSize(preferredSize);
        printAgreement.setMaximumSize(preferredSize);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.add(Box.createRigidArea(new Dimension(10,10)));
        buttonsPanel.add(cancelBtn);
        buttonsPanel.add(printAgreement);
        buttonsPanel.add(Box.createRigidArea(new Dimension(10,10)));

        //            JLabel carName = new JLabel("Car Name");

        elementPanel.add(Box.createRigidArea(new Dimension(25, 1)));
        elementPanel.add(carImage);
        elementPanel.add(Box.createRigidArea(new Dimension(10, 1)));
        elementPanel.add(detailsPanel);
        elementPanel.add(Box.createHorizontalGlue());
        elementPanel.add(buttonsPanel);
        elementPanel.add(Box.createRigidArea(new Dimension(25, 1)));

        return elementPanel;
    }


//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("Booking History Test");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//            // Add the BookingHistory panel to the frame
//            BookingHistory bookingHistoryPanel = new BookingHistory();
//            frame.add(bookingHistoryPanel);
//
//            // Set the frame's size and make it visible
//            frame.setSize(800, 600); // Adjust size as needed
//            frame.setLocationRelativeTo(null); // Center the frame on the screen
//            frame.setVisible(true);
//        });
//    }


}
