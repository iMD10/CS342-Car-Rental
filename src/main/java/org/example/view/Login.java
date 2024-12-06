package org.example.view;

import com.formdev.flatlaf.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import org.example.classes.User;
import org.example.controllers.UserController;

public class Login extends JFrame {

    private JLabel welcome, title, emailLabel, passwordLabel, goLabel;
    private JButton loginButton, signUpButton;
    private JTextField emailTextField;
    private JPasswordField passwordField;
    private JLabel logoLabel; // Make logoLabel a class member
    private ImageIcon logoIcon;

    public Login() {

        this.setTitle("Log In");
        this.setLocation(250, 250);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);

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
        title = new JLabel("            Log in Page");
        emailLabel = new JLabel("Email:       ");
        passwordLabel = new JLabel("Password: ");
        goLabel = new JLabel("Go to");
        loginButton = new JButton("Log In");
        signUpButton = new JButton("Sign Up");
        emailTextField = new JTextField(20);
        passwordField = new JPasswordField(20);

        // Add logo
        logoLabel = new JLabel();
        logoIcon = new ImageIcon("res\\R.png");
        logoLabel.setIcon(scaleImage(logoIcon, 80, 60));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(logoLabel, gbc);

        // Welcome and Title
        gbc.gridy = 1;
        gbc.gridx = 1;
        mainPanel.add(welcome, gbc);
        gbc.gridy = 2;

        mainPanel.add(title, gbc);

        // Form fields
        addLabelAndField(mainPanel, gbc, emailLabel, emailTextField, 3);
        addLabelAndField(mainPanel, gbc, passwordLabel, passwordField, 4);

        // Login button
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        mainPanel.add(loginButton, gbc);

        // Footer panel for "Go to Sign Up" button
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.add(goLabel);
        footerPanel.add(signUpButton);

        gbc.gridy = 6;
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

        // Add action listener to the button
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open SignUp form and close current form
                new Signup().setVisible(true);
                dispose();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UserController uc = new UserController();
                User loggedUser = uc.loginUser(emailTextField.getText().toLowerCase(), passwordField.getText());
                if (loggedUser == null) return;
                dispose();

                if (loggedUser.isAdmin()) {
                    new AdminDashboard(loggedUser);
                } else {
                    new MainFrame(loggedUser);
                }
            }
        });

        // Add Enter key functionality
        emailTextField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginButton.doClick();
                }
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginButton.doClick();
                }
            }
        });
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
        new Login();
    }
}
