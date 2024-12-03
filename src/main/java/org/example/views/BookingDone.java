package org.example.views;

import javax.swing.*;
import java.awt.*;

public class BookingDone extends JFrame {

    private JLabel carImage, doneLabel, printAgreementLabel;

    BookingDone(){
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int W = screenSize.width;
        int H = screenSize.height;
        this.setBounds(W / 4, H / 4, W / 2, H / 2);


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
        // A blue line border, 3 pixels thick
        // mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));

        doneLabel = new JLabel("Booking Done!");
        doneLabel.setFont(new Font("SansSerif", Font.BOLD, 35));
        ImageIcon carImageSource = new ImageIcon("res\\sampleCar.png");
        Image scaledImage = carImageSource.getImage().getScaledInstance(350, 175, Image.SCALE_SMOOTH);
        carImageSource = new ImageIcon(scaledImage);
        carImage = new JLabel(carImageSource);

        printAgreementLabel = new JLabel("<html><u>Print Agreement?</u></html>");
        printAgreementLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        printAgreementLabel.setForeground(Color.BLUE);

        JPanel donePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        donePanel.add(doneLabel);
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        imagePanel.add(carImage);
        JPanel agreementPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        agreementPanel.add(printAgreementLabel);

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(donePanel);
        mainPanel.add(imagePanel);
        mainPanel.add(agreementPanel);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(Box.createVerticalGlue());

        this.add(mainPanel, BorderLayout.CENTER);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new BookingDone();
    }

}
