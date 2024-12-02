package org.example.views;

import javax.swing.*;
import java.awt.*;

public class AccountPage extends JPanel {

    private JLabel titleLabel, fNameLabel, lNameLabel, emailLabel, phoneLabel;
    private JLabel saveChangesLabel;
    private JTextField titleTf, fNameTf, lNameTf, emailTf, phoneTf;
    private JPanel mainPanel;

    AccountPage() {
        this.setLayout(new BorderLayout()); // Set layout for the main panel

        mainPanel = new JPanel(new BorderLayout());
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        titleLabel = new JLabel("Account Page");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 25));

        Font labelsFont = new Font("SansSerif", Font.PLAIN, 15);
        fNameLabel = new JLabel("First Name");
        fNameLabel.setFont(labelsFont);
        fNameLabel.setPreferredSize(new Dimension(300,30));

        lNameLabel = new JLabel("Last Name");
        lNameLabel.setFont(labelsFont);
        lNameLabel.setMaximumSize(new Dimension(300, 30));

        emailLabel = new JLabel("Email");
        emailLabel.setFont(labelsFont);
        emailLabel.setMaximumSize(new Dimension(300, 30));

        phoneLabel = new JLabel("Phone Number");
        phoneLabel.setFont(labelsFont);
        phoneLabel.setMaximumSize(new Dimension(300, 30));

        saveChangesLabel = new JLabel("<html><u>Save Changes</u></html>");
        saveChangesLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        saveChangesLabel.setForeground(Color.GRAY);
        saveChangesLabel.setMaximumSize(new Dimension(300, 30));




        fNameTf = new JTextField("Your fName", 20);
        fNameTf.setFont(new Font("SansSerif", Font.PLAIN, 15));
        fNameTf.setMaximumSize(new Dimension(200, 30));

        lNameTf = new JTextField("Your lName", 20);
        lNameTf.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lNameTf.setMaximumSize(new Dimension(200, 30));

        emailTf = new JTextField("Your Email", 20);
        emailTf.setFont(new Font("SansSerif", Font.PLAIN, 15));
        emailTf.setMaximumSize(new Dimension(200, 30));

        phoneTf = new JTextField("Your Phone", 20);
        phoneTf.setFont(new Font("SansSerif", Font.PLAIN, 15));
        phoneTf.setMaximumSize(new Dimension(200, 30));


        contentPanel.add(makeItFlowPanel(titleLabel));
        contentPanel.add(Box.createRigidArea(new Dimension(300, 25)));
        contentPanel.add(Box.createVerticalGlue());

        contentPanel.add(makeItFlowPanel(fNameLabel));
        contentPanel.add((makeItFlowPanel(fNameTf)));
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(makeItFlowPanel(lNameLabel));
        contentPanel.add(makeItFlowPanel(lNameTf));
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(Box.createRigidArea(new Dimension(1, 20)));


        contentPanel.add(makeItFlowPanel(emailLabel));
        contentPanel.add(makeItFlowPanel(emailTf));
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(Box.createVerticalGlue());

        contentPanel.add(makeItFlowPanel(phoneLabel));
        contentPanel.add(makeItFlowPanel(phoneTf));
        contentPanel.add(makeItFlowPanel(saveChangesLabel));
        contentPanel.add(Box.createVerticalGlue());


//        mainPanel.add(Components.createSideBarPanel(), BorderLayout.WEST);

        JPanel paddingPanel = new JPanel();
        paddingPanel.setLayout(new BoxLayout(paddingPanel, BoxLayout.X_AXIS));
        paddingPanel.add(Box.createRigidArea(new Dimension(10,1)));
        paddingPanel.add(contentPanel);
        paddingPanel.add(Box.createRigidArea(new Dimension(10,1)));

        mainPanel.add(paddingPanel, BorderLayout.CENTER);


        this.add(mainPanel);

    }

    private JPanel makeItFlowPanel(Component label) {
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        labelPanel.add(label);


        return labelPanel;
    }

    public static void main(String[] args) {
        new AccountPage();
    }
}
