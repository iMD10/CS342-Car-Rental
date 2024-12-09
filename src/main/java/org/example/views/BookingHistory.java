package org.example.views;

import org.example.classes.Booking;
import org.example.classes.Invoice;
import org.example.classes.User;
import org.example.classes.Vehicle;
import org.example.common.ErrorHandler;
import org.example.controllers.BookingController;
import org.example.controllers.InvoiceController;
import org.example.controllers.VehicleController;
import org.example.view.PDFInvoiceGenerator;
import org.example.view.RentalAgreement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BookingHistory extends JPanel {

    private final User loggedUser;
    private JPanel listPanel;
    private List<Booking> bookingsList;

    BookingHistory(User loggedUser) {
        this.setLayout(new BorderLayout()); // Set layout for the main panel
        this.loggedUser = loggedUser;
        JLabel titleLabel;


        titleLabel = new JLabel("My Bookings");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 25));

        listPanel = new JPanel();
        listPanel.setLayout(new GridLayout(0, 1, 30, 20));

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
        if (bookingsList == null)
        showResultMessage("Loading...");

        SwingWorker<List<JPanel>, JPanel> worker = new SwingWorker<>() {

            @Override
            protected List<JPanel> doInBackground() {
                BookingController bookingsController = new BookingController();
                VehicleController vehicleController = new VehicleController();
                InvoiceController invoiceController = new InvoiceController();

                bookingsList = bookingsController.getAllBookingsByUserid(loggedUser.getId());
                List<Vehicle> vehiclesList = vehicleController.getAllVehicles();
//                List<Invoice> invoicesList = invoiceController.getAllInvoicesByUserId(loggedUser.getId());


                bookingsList.sort(new bookingDatesComparator().reversed()); // Latest bookings first


                List<JPanel> bookingPanels = new ArrayList<>();
                for (Booking booking : bookingsList) {
                    Vehicle vehicleInfo =  vehiclesList.stream()
                            .filter(vehicle -> vehicle.getId() == booking.getVehicleId())
                            .findFirst()
                            .orElse(null);

                    Invoice invoiceInfo = invoiceController.getInvoiceByBookingId(booking.getId());


                    if (vehicleInfo == null) continue;
                    JPanel element = createElement(vehicleInfo, booking, invoiceInfo);
                    bookingPanels.add(element);
                }

                return bookingPanels;

            }

            @Override
            protected void done() {
                try {

                    List<JPanel> bookingPanels = get();
                    listPanel.removeAll();
                    listPanel.setLayout(new GridLayout(0, 1, 30, 20));
                    for (JPanel panel : bookingPanels) {
                        listPanel.add(panel);
                    }
                    listPanel.revalidate();
                    listPanel.repaint();
                    System.out.println("loading bookings done");
                } catch (Exception e) {
                    ErrorHandler.handleException(e,"Loading failed");
                }
            }
        };


        worker.execute();
    }





    private JPanel createElement(Vehicle vehicle, Booking booking, Invoice invoice) {
        JPanel elementPanel = new JPanel();
        elementPanel.setLayout(new BoxLayout(elementPanel, BoxLayout.X_AXIS));


        ImageIcon carImageSource = new ImageIcon("src\\main\\java\\org\\example\\res\\"+vehicle.getCarModel().getName()+".png");
        Image img = carImageSource.getImage(); // Transform the ImageIcon to Image
        Image scaledImg = img.getScaledInstance(120, 120 / 2, Image.SCALE_SMOOTH); // Resize the image
        carImageSource = new ImageIcon(scaledImg); // Create a new ImageIcon from the resized image
        JLabel carImage = new JLabel(carImageSource);

        Font carNameFont = new Font("SansSerif", Font.BOLD, 13);
        Font labelsFont = new Font("SansSerif", Font.PLAIN, 13);
        JLabel carName = new JLabel(vehicle.getCarModel().getName() +" "+ vehicle.getCarModel().getModelYear());
        carName.setFont(carNameFont);

        JLabel bookingState = new JLabel(booking.getStatus());
        bookingState.setFont(carNameFont);
        if (bookingState.getText().equals("active")) bookingState.setForeground(Color.GREEN);
        else bookingState.setForeground(Color.GRAY);

        JLabel startDate = new JLabel("From: " + booking.getStart_date().toLocalDateTime().toLocalDate().toString());
        startDate.setFont(labelsFont);
        JLabel endDate = new JLabel("To: " + booking.getEnd_date().toLocalDateTime().toLocalDate().toString());
        endDate.setFont(labelsFont);
        JLabel bookingNumber = new JLabel("Booking number: #" + booking.getId());
        bookingNumber.setFont(labelsFont);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.add(carName);
        detailsPanel.add(startDate);
        detailsPanel.add(endDate);
        detailsPanel.add(bookingNumber);

        Dimension bpreferredSize = new Dimension(110, 30);  // Same size for both
        Dimension preferredSize = new Dimension(125, 30);  // Same size for both
        JButton cancelBtn = new JButton("Cancel");

        cancelBtn.setPreferredSize(bpreferredSize);
        cancelBtn.setMaximumSize(bpreferredSize);
//        System.out.println(booking.getStatus());

        cancelBtn.setVisible( booking.getStatus().equals("active") );
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelBookingInBackground(booking);

            }
        });

        JLabel showAgreement = new JLabel("<html><u>Show Agreement</u></html>");
        showAgreement.setFont(new Font("SansSerif", Font.PLAIN, 15));
        showAgreement.setForeground(Color.BLUE);
        showAgreement.setPreferredSize(preferredSize);
        showAgreement.setMaximumSize(preferredSize);

        JLabel printInvoice = new JLabel("<html><u>Print Invoice</u></html>");
        printInvoice.setFont(new Font("SansSerif", Font.PLAIN, 15));
        printInvoice.setForeground(Color.BLUE);
        printInvoice.setPreferredSize(preferredSize);
        printInvoice.setMaximumSize(preferredSize);

        if (booking.getStatus().equals("RETURNED") && invoice != null) {
            printInvoice.setVisible(true);
            showAgreement.setVisible(false);
        }else {
            printInvoice.setVisible(false);
            showAgreement.setVisible(true);
        }


        showAgreement.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                new RentalAgreement(vehicle, loggedUser, booking.getStart_date().toLocalDateTime().toLocalDate(), booking.getEnd_date().toLocalDateTime().toLocalDate(), false, null, null);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                showAgreement.setFont(new Font("SansSerif", Font.BOLD, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                showAgreement.setFont(new Font("SansSerif", Font.PLAIN, 15));

            }
        });
        printInvoice.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                PDFInvoiceGenerator.generateInvoice(loggedUser.getName(), vehicle.getCarModel().getName(), booking.getStart_date().toLocalDateTime().toLocalDate(), booking.getEnd_date().toLocalDateTime().toLocalDate(), booking.getCost() + invoice.getLate_fees(), invoice.getLate_fees());
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                printInvoice.setFont(new Font("SansSerif", Font.BOLD, 15));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                printInvoice.setFont(new Font("SansSerif", Font.PLAIN, 15));

            }
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.add(Box.createRigidArea(new Dimension(10,10)));
        buttonsPanel.add(cancelBtn);
        buttonsPanel.add(showAgreement);
        buttonsPanel.add(printInvoice);
        buttonsPanel.add(Box.createRigidArea(new Dimension(10,10)));

        //            JLabel carName = new JLabel("Car Name");

        elementPanel.add(Box.createRigidArea(new Dimension(25, 1)));
        elementPanel.add(carImage);
        elementPanel.add(Box.createRigidArea(new Dimension(10, 1)));
        elementPanel.add(detailsPanel);
        elementPanel.add(Box.createHorizontalGlue());
        elementPanel.add(bookingState);
        elementPanel.add(Box.createHorizontalGlue());
        elementPanel.add(buttonsPanel);
        elementPanel.add(Box.createRigidArea(new Dimension(25, 1)));



        return elementPanel;
    }

    private static class bookingDatesComparator implements Comparator<Booking> {
        @Override
        public int compare(Booking o1, Booking o2) {
            LocalDateTime o1D = o1.getBookedAt().toLocalDateTime();
            LocalDateTime o2D = o2.getBookedAt().toLocalDateTime();
            System.out.println(o1D.toString());
            System.out.println(o1D);
            if (o1D.isBefore(o2D)) return -1;
            else if (o2D.isBefore(o1D)) return 1 ;
            else return 0;
        }
    }

    private void cancelBookingInBackground(Booking booking) {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {

                    BookingController bookingController = new BookingController();
                    bookingController.editBookingStatusToCanceled(booking.getId());
                } catch (Exception e) {
                    ErrorHandler.handleException(e,"Cancel Failed");
                }
                return null;
            }

            @Override
            protected void done() {
                try {
                    loadBookings(loggedUser);

                } catch (Exception e) {
                ErrorHandler.handleException(e,"Loading Failed");
                }
            }
        };

        // Execute the SwingWorker
        worker.execute();
    }

    private void showResultMessage(String msg) {
        listPanel.removeAll(); // Clear the gridList
        JLabel messageLabel = new JLabel(msg, JLabel.CENTER);
        messageLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        messageLabel.setForeground(Color.GRAY);
        listPanel.setLayout(new BorderLayout());
        listPanel.add(messageLabel, BorderLayout.CENTER);
        listPanel.revalidate(); // Revalidate to ensure changes are reflected
        listPanel.repaint();    // Repaint to refresh the UI
    }





}
