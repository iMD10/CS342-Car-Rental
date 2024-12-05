package org.example.view;
import com.formdev.flatlaf.*;
import javax.swing.*;
import java.awt.*;

import org.example.classes.User;
import org.example.controllers.UserController;
import org.example.common.Validation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Signup extends JFrame {

    private JLabel welcome, title, fnameLabel, lnameLabel, emailLabel, phoneLabel, passwordLabel, goLabel;
    private JButton signupButton, goToLoginButton;
    private JTextField fnameTextField, lnameTextField, emailTextField, phoneTextField;
    private JPasswordField passwordField;

    public Signup() {

        this.setTitle("Sign Up");
        this.setLocation(250, 250);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int W = screenSize.width;
        int H = screenSize.height;
        this.setBounds(W/4, H/4, W/2, H/2);

        JPanel mainPanel = new JPanel(new BorderLayout(2, 1));

        // Initialize components
        welcome = new JLabel("Welcome to Blu");
        welcome.setFont(new Font("Arial", Font.BOLD, 20));
        title = new JLabel("Sign Up Page");
        fnameLabel = new JLabel("First Name: ");
        lnameLabel = new JLabel("Last Name: ");
        emailLabel = new JLabel("Email:      ");
        phoneLabel = new JLabel("Phone:     ");
        passwordLabel = new JLabel("Password: ");
        signupButton = new JButton("Sign Up");
        goToLoginButton = new JButton("Sign In");
        fnameTextField = new JTextField(20);
        lnameTextField = new JTextField(20);
        emailTextField = new JTextField(20);
        phoneTextField = new JTextField(20);
        passwordField = new JPasswordField(20);

        // Add logo
        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("res\\R.png");
        Image scaledImage = logoIcon.getImage().getScaledInstance(80, 60, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(scaledImage);
        logoLabel.setIcon(logoIcon);
        JPanel logoPanel = new JPanel();
        logoPanel.setPreferredSize(new Dimension(100, 85));
        logoPanel.add(logoLabel, BorderLayout.CENTER);

        // Panel for signup fields
        JPanel signupPanel = new JPanel(new GridLayout(9, 1, 1, 1));
        signupPanel.add(logoPanel);
        signupPanel.add(createPaddedPanelLabel(welcome));
        signupPanel.add(createPaddedPanelLabel(title));
        signupPanel.add(createPaddedPanel(fnameLabel, fnameTextField));
        signupPanel.add(createPaddedPanel(lnameLabel, lnameTextField));
        signupPanel.add(createPaddedPanel(emailLabel, emailTextField));
        signupPanel.add(createPaddedPanel(phoneLabel, phoneTextField));
        signupPanel.add(createPaddedPanel(passwordLabel, passwordField));
        signupPanel.add(createPaddedPanelButton(signupButton));

        mainPanel.add(signupPanel, BorderLayout.CENTER);

        // Panel for footer with "Go to Sign In" button
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        goLabel = new JLabel("Go to");
        footerPanel.add(goLabel);
        footerPanel.add(goToLoginButton);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        // Add panels to the frame
        this.add(mainPanel);

        // Make frame visible
        this.setVisible(true);

        // Add action listener to the sign-up button
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performSignup();
            }
        });

        // Add action listener to the "Go to Sign In" button
        goToLoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Login().setVisible(true);
                dispose();
            }
        });

        // Add Enter key functionality
        KeyAdapter enterKeyListener = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performSignup();
                }
            }
        };

        fnameTextField.addKeyListener(enterKeyListener);
        lnameTextField.addKeyListener(enterKeyListener);
        emailTextField.addKeyListener(enterKeyListener);
        phoneTextField.addKeyListener(enterKeyListener);
        passwordField.addKeyListener(enterKeyListener);
    }

    private void performSignup() {
        Validation v = new Validation();

        // Check if all fields are filled
        if (fnameTextField.getText().isEmpty() || lnameTextField.getText().isEmpty() || emailTextField.getText().isEmpty() ||
                phoneTextField.getText().isEmpty() || passwordField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate email format
        if (!v.checkEmail(emailTextField.getText())) {
            JOptionPane.showMessageDialog(this, "Email is incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate password format
        if (!v.checkPassword(new String(passwordField.getPassword()))) {
            JOptionPane.showMessageDialog(this, "Password is incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate phone number is exactly 10 digits
        if (!v.checkPhone(phoneTextField.getText())) {
            JOptionPane.showMessageDialog(this, "Phone number must be exactly 10 digits!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate first name and last name contain only letters
        if (!v.checkName(fnameTextField.getText()) || !v.checkName(lnameTextField.getText())) {
            JOptionPane.showMessageDialog(this, "First and Last names must contain only letters!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        UserController uc = new UserController();
        User newUser = uc.registerCustomer(emailTextField.getText(), fnameTextField.getText(), lnameTextField.getText(), phoneTextField.getText(), new String(passwordField.getPassword()));

        if (newUser == null) {
            JOptionPane.showMessageDialog(this, "Phone number is already used!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        dispose();

        if (newUser.isAdmin()) {
            AdminDashboard ad = new AdminDashboard(newUser);
        } else {
            MainFrame mf = new MainFrame(newUser);
        }
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
        new Signup();
    }
}
