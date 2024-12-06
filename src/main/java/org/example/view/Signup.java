package org.example.view;

import com.formdev.flatlaf.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import org.example.classes.User;
import org.example.controllers.UserController;
import org.example.common.Validation;

public class Signup extends JFrame {

    private JLabel welcome, title, fnameLabel, lnameLabel, emailLabel, phoneLabel, passwordLabel, goLabel;
    private JButton signupButton, goToLoginButton;
    private JTextField fnameTextField, lnameTextField, emailTextField, phoneTextField;
    private JPasswordField passwordField;
    private JLabel logoLabel; // Make logoLabel a class member
    private ImageIcon logoIcon;
    Image icon = Toolkit.getDefaultToolkit().getImage("res/R.png");
    public Signup() {

        this.setTitle("Sign Up");
        this.setLocation(250, 250);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(icon);
        this.setMinimumSize(new Dimension(600, 600));

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int W = screenSize.width;
        int H = screenSize.height;
        this.setBounds(W / 4, H / 4, W / 2, H / 2);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Initialize components
        welcome = new JLabel("Welcome to Blu");
        welcome.setFont(new Font("Arial", Font.BOLD, 20));
        title = new JLabel("            Sign Up Page");
        fnameLabel = new JLabel("First Name: ");
        lnameLabel = new JLabel("Last Name: ");
        emailLabel = new JLabel("Email:      ");
        phoneLabel = new JLabel("Phone:     ");
        passwordLabel = new JLabel("Password: ");
        signupButton = new JButton("Sign Up");
        goToLoginButton = new JButton("Log In");
        fnameTextField = new JTextField(20);
        lnameTextField = new JTextField(20);
        emailTextField = new JTextField(20);
        phoneTextField = new JTextField(20);
        passwordField = new JPasswordField(20);

        // Add logo
        logoLabel = new JLabel();
        logoIcon = new ImageIcon("res\\R.png");
        logoLabel.setIcon(scaleImage(logoIcon, 50, 30));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(logoLabel, gbc);

        // Welcome and Title
        gbc.gridy = 1;
        mainPanel.add(welcome, gbc);
        gbc.gridy = 2;
        gbc.gridx = 1;
        mainPanel.add(title, gbc);

        // Form fields
        addLabelAndField(mainPanel, gbc, fnameLabel, fnameTextField, 3);
        addLabelAndField(mainPanel, gbc, lnameLabel, lnameTextField, 4);
        addLabelAndField(mainPanel, gbc, emailLabel, emailTextField, 5);
        addLabelAndField(mainPanel, gbc, phoneLabel, phoneTextField, 6);
        addLabelAndField(mainPanel, gbc, passwordLabel, passwordField, 7);

        // Signup button
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        mainPanel.add(signupButton, gbc);

        // Footer panel for "Go to Sign In" button
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        goLabel = new JLabel("Go to");
        footerPanel.add(goLabel);
        footerPanel.add(goToLoginButton);

        gbc.gridy = 9;
        mainPanel.add(footerPanel, gbc);

        // Add panels to the frame
        this.add(mainPanel);

        // Resize listener to adjust logo size dynamically
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustLogoSize();
            }
        });

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
        User newUser = uc.registerCustomer(emailTextField.getText().toLowerCase(), fnameTextField.getText(), lnameTextField.getText(), phoneTextField.getText(), new String(passwordField.getPassword()));

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

    private void addLabelAndField(JPanel panel, GridBagConstraints gbc, JLabel label, JTextField field, int y) {
        gbc.gridy = y;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void adjustLogoSize() {
        int frameWidth = getWidth();
        int newWidth = frameWidth / 10; // Adjust the factor as needed
        int newHeight = (int) (newWidth * 0.70); // Maintain aspect ratio
        logoLabel.setIcon(scaleImage(logoIcon, newWidth, newHeight));
    }

    private ImageIcon scaleImage(ImageIcon icon, int width, int height) {
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public static void main(String[] args) {
        FlatLightLaf.setup();
        new Signup();
    }
}
