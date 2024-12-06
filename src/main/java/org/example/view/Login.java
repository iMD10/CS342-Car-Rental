package org.example.view;
import com.formdev.flatlaf.*;
import javax.swing.*;
import java.awt.*;

import org.example.classes.User;
import org.example.controllers.UserController;
import org.example.common.Validation;
import org.example.views.UserUIWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login extends JFrame {

    private JLabel welcome, title, emailLabel, passwordLabel, GoLabel;
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

        // Initialize components
        welcome = new JLabel("Welcom to Blu");
        welcome.setFont(new Font("Arial", Font.BOLD, 20));
        title = new JLabel("Log in Page");
        emailLabel = new JLabel("Email:       ");
        passwordLabel = new JLabel("Password: ");
        GoLabel = new JLabel("Go to");
        loginButton = new JButton("Log In");
        signUpButton = new JButton("Sign Up");
        emailTextField = new JTextField(20);
        passwordField = new JPasswordField(20);

        // Panel for login fields
        JPanel loginPanel = new JPanel(new GridLayout(6, 1, 1, 1));
        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("res\\R.png");
        Image scaledImage = logoIcon.getImage().getScaledInstance(80, 60, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(scaledImage);
        logoLabel.setIcon(logoIcon);
        JPanel logoPanel = new JPanel();
        logoPanel.setPreferredSize(new Dimension(100, 85));
        logoPanel.add(logoLabel, BorderLayout.CENTER);
        loginPanel.add(logoPanel);
        loginPanel.add(createPaddedPanelLabel(welcome));
        loginPanel.add(createPaddedPanelLabel(title));
        JLabel emailmsg = new JLabel("");
        loginPanel.add(createPaddedPanel(emailLabel,emailTextField, emailmsg));
        JLabel passwordmsg = new JLabel("");
        loginPanel.add(createPaddedPanel(passwordLabel, passwordField, passwordmsg));
        loginPanel.add(createPaddedPanelButton(loginButton));

        // Panel for footer
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.add(GoLabel);
        footerPanel.add(signUpButton);

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

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UserController uc = new UserController();
                User loggedUser = uc.loginUser(emailTextField.getText(), passwordField.getText());
                if(loggedUser == null) return;
                dispose();

                if (loggedUser.isAdmin()){
                    AdminDashboard ad = new AdminDashboard(loggedUser);
                } else {
                    UserUIWindow userUi = new UserUIWindow(loggedUser);
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

    private JPanel createPaddedPanel(JLabel label, JTextField field, JLabel msg) {
        JPanel paddedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        paddedPanel.add(label);
        paddedPanel.add(field);
        paddedPanel.add(msg);
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
