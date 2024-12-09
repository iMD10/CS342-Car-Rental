package org.example.view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import org.example.classes.User;
import org.example.common.Validation;
import org.example.controllers.UserController;
import org.example.views.UserUIWindow;

public class Login extends JFrame {

    private JLabel welcome, title, emailLabel, passwordLabel, goLabel;
    private JButton loginButton, signUpButton;
    private JTextField emailTextField;
    private JPasswordField passwordField;
    private JLabel logoLabel; // Make logoLabel a class member
    private ImageIcon logoIcon;
    public Login() {

        this.setTitle("Log In");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Image icon = Toolkit.getDefaultToolkit().getImage("src/main/java/org/example/res/R.png");
        this.setIconImage(icon);


        this.setBounds(350, 450 , 1000, 750);
        this.setResizable(false);
        setLocationRelativeTo(null);

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
        logoIcon = new ImageIcon(icon);
        logoLabel.setIcon(scaleImage(logoIcon, 100,100 ));

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

        this.setVisible(true);

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

                new SwingWorker<User, Void>() {
                    @Override
                    protected User doInBackground() throws Exception {
                        Validation v = new Validation();
                        String email = emailTextField.getText().toLowerCase();
                        String password = passwordField.getText();
                        if (!v.checkEmail(email) || !v.checkPassword(password)) {
                            JOptionPane.showMessageDialog(null, "Invalid email or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);

                        }  else {
                         return uc.loginUser(email, password);
                        }
                        return null;
                    }

                    @Override
                    protected void done() {
                        try {
                            User loggedUser = get();
                            if (loggedUser == null) {
                                return;
                            }

                            SwingUtilities.invokeLater(() -> {
                                dispose();
                                if (loggedUser.isAdmin()) {
                                    new AdminDashboard(loggedUser);
                                } else {
                                    // First GUI
//                                    new MainFrame(loggedUser);
                                    // Second GUI
                                   new UserUIWindow(loggedUser);

                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(null, "An error occurred during login: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }.execute();

            }
        });


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

    private ImageIcon scaleImage(ImageIcon icon, int width, int height) {
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }


}
