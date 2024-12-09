package org.example.views;

import org.example.classes.User;
import org.example.classes.Vehicle;
import org.example.controllers.InvoiceController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BookingDone extends JPanel {


    public BookingDone(Vehicle selectedCar, User loggedUser, CardLayout cardLayout, JPanel cardPanel) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // "Booking Done!" label setup
        JLabel doneLabel = new JLabel("Booking Done!");
        doneLabel.setFont(new Font("SansSerif", Font.BOLD, 35));

        // Car image setup
        ImageIcon carImageSource = new ImageIcon("src\\main\\java\\org\\example\\res\\"+selectedCar.getCarModel().getName()+".png");
        Image scaledImage = carImageSource.getImage().getScaledInstance(350, 175, Image.SCALE_SMOOTH);
        carImageSource = new ImageIcon(scaledImage);
        JLabel carImage = new JLabel(carImageSource);


        JLabel goBackLabel = new JLabel("<html><u>Browse Vehicles</u></html>");
        goBackLabel.setFont(new Font("SansSerif", Font.PLAIN, 19));
        goBackLabel.setForeground(Color.GRAY);
        goBackLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(cardPanel, UserUIWindow.BROWSE_PANEL);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                goBackLabel.setFont(new Font("SansSerif", Font.BOLD, 19));

            }

            @Override
            public void mouseExited(MouseEvent e) {
                goBackLabel.setFont(new Font("SansSerif", Font.PLAIN, 19));

            }
        });

        // Layout for the different panels
        JPanel donePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        donePanel.add(doneLabel);

        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imagePanel.add(carImage);

        JPanel agreementPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        agreementPanel.add(printInvoiceLabel);
        JPanel goBackPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        goBackPanel.add(goBackLabel);

        // Add components to the main panel
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(donePanel);
        mainPanel.add(imagePanel);
        mainPanel.add(agreementPanel);
        mainPanel.add(goBackPanel);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(Box.createVerticalGlue());

        // Add the main panel to this JPanel
        this.setLayout(new BorderLayout());
        this.add(mainPanel, BorderLayout.CENTER);
    }

    // If this JPanel is to be used in a JFrame, you can add this panel like so:
    // JFrame frame = new JFrame("Booking Done");
    // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // frame.setSize(800, 600); // Set an appropriate size
    // frame.add(new BookingDone());
    // frame.setVisible(true);
}

