package org.example.views;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Signup extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public Signup() {
        setTitle("Car Rental Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create login and signup panels
        JPanel loginPanel = createLoginPanel();
        JPanel signupPanel = createSignupPanel();

        // Add panels to the main panel with card layout
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(signupPanel, "Signup");

        // Add main panel to the frame
        add(mainPanel);

        // Show login panel by default
        cardLayout.show(mainPanel, "Login");

        setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Your existing login panel code
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailTextField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton1 = new JButton("Log In");
        JButton loginButton2 = new JButton("Log In");
        JButton signUpButton = new JButton("Sign Up");
        JLabel problemLabel = new JLabel("Having a problem? Contact Us");
        JLabel forgetLabel = new JLabel("Forgot your password? Click here");

        JPanel loginFieldsPanel = new JPanel(new GridLayout(6, 1, 50, 50));
        loginFieldsPanel.add(emailLabel);
        loginFieldsPanel.add(emailTextField);
        loginFieldsPanel.add(passwordLabel);
        loginFieldsPanel.add(passwordField);
        loginFieldsPanel.add(loginButton1);
        loginFieldsPanel.add(forgetLabel);

        JPanel sidePanel = new JPanel(new GridLayout(4, 1));
        sidePanel.add(signUpButton);
        sidePanel.add(loginButton2);
        sidePanel.add(problemLabel);

        panel.add(loginFieldsPanel, BorderLayout.EAST);
        panel.add(sidePanel, BorderLayout.WEST);

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Signup");
            }
        });

        return panel;
    }

    private JPanel createSignupPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Your existing signup panel code
        JLabel nidLabel = new JLabel("National ID:");
        JTextField nidTextField = new JTextField(20);
        JLabel fnLabel = new JLabel("First Name:");
        JTextField fnTextField = new JTextField(20);
        JLabel lnLabel = new JLabel("Last Name:");
        JTextField lnTextField = new JTextField(20);
        JLabel phoneLabel = new JLabel("Phone Number 966+:");
        JTextField phoneTextField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailTextField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JButton signupButton = new JButton("Sign Up");
        JButton loginButton = new JButton("Log In");
        JLabel problemLabel = new JLabel("Having a problem? Contact Us");
        JLabel forgetLabel = new JLabel("Forgot your password? Click here");

        JPanel signupFieldsPanel = new JPanel(new GridLayout(8, 1, 5, 5));
        signupFieldsPanel.add(nidLabel);
        signupFieldsPanel.add(nidTextField);
        signupFieldsPanel.add(fnLabel);
        signupFieldsPanel.add(fnTextField);
        signupFieldsPanel.add(lnLabel);
        signupFieldsPanel.add(lnTextField);
        signupFieldsPanel.add(phoneLabel);
        signupFieldsPanel.add(phoneTextField);
        signupFieldsPanel.add(emailLabel);
        signupFieldsPanel.add(emailTextField);
        signupFieldsPanel.add(passwordLabel);
        signupFieldsPanel.add(passwordField);
        signupFieldsPanel.add(signupButton);
        signupFieldsPanel.add(forgetLabel);

        JPanel sidePanel = new JPanel(new GridLayout(4, 1));
        sidePanel.add(signupButton);
        sidePanel.add(loginButton);
        sidePanel.add(problemLabel);

        panel.add(signupFieldsPanel, BorderLayout.EAST);
        panel.add(sidePanel, BorderLayout.WEST);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Login");
            }

        });
        this.pack();
        return panel;

    }

    public static void main(String[] args) {
        new Signup();
    }
}