package org.example.views;

import org.example.classes.Vehicle;

import javax.swing.*;
import java.awt.*;

public class BookingDone extends JPanel {


    public BookingDone(Vehicle selectedCar) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // "Booking Done!" label setup
        JLabel doneLabel = new JLabel("Booking Done!");
        doneLabel.setFont(new Font("SansSerif", Font.BOLD, 35));

        // Car image setup
        ImageIcon carImageSource = new ImageIcon("res\\sampleCar.png");
        Image scaledImage = carImageSource.getImage().getScaledInstance(350, 175, Image.SCALE_SMOOTH);
        carImageSource = new ImageIcon(scaledImage);
        JLabel carImage = new JLabel(carImageSource);

        // Print agreement label setup
        JLabel printAgreementLabel = new JLabel("<html><u>Print Agreement?</u></html>");
        printAgreementLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        printAgreementLabel.setForeground(Color.BLUE);

        // Layout for the different panels
        JPanel donePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        donePanel.add(doneLabel);

        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imagePanel.add(carImage);

        JPanel agreementPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        agreementPanel.add(printAgreementLabel);

        // Add components to the main panel
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(donePanel);
        mainPanel.add(imagePanel);
        mainPanel.add(agreementPanel);
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

