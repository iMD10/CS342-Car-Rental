package org.example.views;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {

    private JLabel emailLabel, passwordLabel, problemLabel, forgetLabel;
    private JButton loginButton1,loginButton2, signUpButton;
    private JTextField emailTextField;
    private JPasswordField passwordField;

    public Login() {
        setTitle("LogIn");
        setLocation(250, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(440, 200);
        setLayout(new BorderLayout());

        // Initialize components
        emailLabel = new JLabel("Email:");
        passwordLabel = new JLabel("Password:");
        problemLabel = new JLabel("Having a problem? Contact Us");
        problemLabel.setBounds(0,105,0,0);
        forgetLabel = new JLabel("Forgot your password? Click here");
        loginButton1 = new JButton("Log In");
        loginButton2 = new JButton("Log In");
        loginButton2.setBounds(0,55,150,50);
        signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(0,0,150,50);
        emailTextField = new JTextField(20);
        passwordField = new JPasswordField(20);




        JPanel MainPanel = new JPanel((new BorderLayout()));
        // Panel for login fields
        JPanel loginPanel = new JPanel(new GridLayout(6, 1, 5, 5));
        loginPanel.add(emailLabel);
        loginPanel.add(emailTextField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton1);
        loginPanel.add(forgetLabel);

        // Panel on the left Side
        JPanel SidePanel = new JPanel((new GridLayout(4,1)));
        SidePanel.add(signUpButton);
        SidePanel.add(loginButton2);
        SidePanel.add(problemLabel);

        // Add panels to the frame
        add(loginPanel, BorderLayout.EAST);
        add(SidePanel, BorderLayout.WEST);

        // Make frame visible
        setVisible(true);
        this.pack();
    }

    public static void main(String[] args) {
        new Login();
    }
}
