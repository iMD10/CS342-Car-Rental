package org.example.views;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {

    private JLabel emailLabel, passwordLabel, problemLabel, forgetLabel;
    private JButton loginButton, signUpButton;
    private JTextField emailTextField;
    private JPasswordField passwordField;

    public Login() {
        setTitle("LogIn");
        setLocation(250, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(new BorderLayout());

        // Initialize components
        emailLabel = new JLabel("Email:");
        passwordLabel = new JLabel("Password:");
        problemLabel = new JLabel("Having a problem? Contact Us");
        forgetLabel = new JLabel("Forgot your password? Click here");
        loginButton = new JButton("Log In");
        signUpButton = new JButton("Sign Up");
        emailTextField = new JTextField(20);
        passwordField = new JPasswordField(20);

        // Panel for login fields
        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        loginPanel.add(emailLabel);
        loginPanel.add(emailTextField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginPanel.add(forgetLabel);

        // Panel for footer
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.add(signUpButton);
        footerPanel.add(problemLabel);

        // Add panels to the frame
        add(loginPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);

        // Make frame visible
        setVisible(true);
    }

    public static void main(String[] args) {
        new Login();
    }
}
