package org.example.views;
import com.formdev.flatlaf.*;
import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

    private JLabel title, emailLabel, passwordLabel, GoLabel;
    private JButton loginButton, signUpButton;
    private JTextField emailTextField;
    private JPasswordField passwordField;

    public Login() {
        
        this.setTitle("Log In");
        this.setLocation(250, 250);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int W = screenSize.width;
        int H = screenSize.height;
        this.setBounds(W/4, H/4, W/2, H/2);

        JPanel mainPanel = new JPanel(new BorderLayout(2,1));

        //JPanel slideBarPanel = new JPanel(new GridLayout(3,1));

        // Initialize components
        title = new JLabel("Log in Page");
        emailLabel = new JLabel("Email:       ");
        passwordLabel = new JLabel("Password:");
        GoLabel = new JLabel("Go to");
        loginButton = new JButton("Log In");
        signUpButton = new JButton("Sign Up");
        emailTextField = new JTextField(20);
        passwordField = new JPasswordField(20);

        // Panel for login fields
        JPanel loginPanel = new JPanel(new GridLayout(5, 1, 2, 2));

        loginPanel.add(createPaddedPanelLabel(title));
        loginPanel.add(createPaddedPanel(emailLabel,emailTextField));
        loginPanel.add(createPaddedPanel(passwordLabel, passwordField));
        loginPanel.add(createPaddedPanelButton(loginButton));


        // Panel for footer
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.add(GoLabel);
        footerPanel.add(signUpButton);
        ;


        mainPanel.add(loginPanel,BorderLayout.CENTER);
        mainPanel.add(footerPanel,BorderLayout.SOUTH);

        // Add panels to the frame
        this.add(mainPanel);

        // Make frame visible
        this.setVisible(true);

        // Add action listener to the button
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open SignUp form and close current form
                new Signup().setVisible(true);
                dispose();
            }
    });
}

    private JPanel createPaddedPanel(JLabel label, JTextField field) {
        JPanel paddedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        paddedPanel.add(label);
        paddedPanel.add(field);
        return paddedPanel;
    }

    private JPanel createPaddedPanelLabel(JLabel label) {
        JPanel paddedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        paddedPanel.add(label);
        return paddedPanel;
    }

    private JPanel createPaddedPanelButton(JButton button) {
        JPanel paddedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        paddedPanel.add(button);
        return paddedPanel;
    }
    

    public static void main(String[] args) {
        FlatLightLaf.setup();
        new Login();
    }
}
