package org.example.views;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.formdev.flatlaf.*;
import org.example.controllers.UserController;
import org.example.common.Validation;

public class Signup extends JFrame {

    public Signup() {
        
        this.setTitle("Sign up");
        this.setLocation(250, 250);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int W = screenSize.width;
        int H = screenSize.height;
        this.setBounds(W/4, H/4, W/2, H/2);

        JPanel mainPanel = new JPanel(new BorderLayout(2,1));

        // Create login and signup panels
        JPanel signupPanel = createSignupPanel();

        JButton loginButton = new JButton("Log In");
        JLabel GoLabel = new JLabel("Go to");
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.add(GoLabel);
        footerPanel.add(loginButton);
        ;

        // Add panels to the main panel with card layout
        mainPanel.add(signupPanel, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        // Add main panel to the frame
        add(mainPanel);


        setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open SignUp form and close current form
                new Login().setVisible(true);
                dispose();
            }
        });

    }



    private JPanel createSignupPanel() {

        // Your existing signup panel code
        JLabel title = new JLabel("Sign Up Page");
        JLabel fnLabel = new JLabel("First Name:               ");
        JTextField fnTextField = new JTextField(20);
        JLabel lnLabel = new JLabel("Last Name:                ");
        JTextField lnTextField = new JTextField(20);
        JLabel phoneLabel = new JLabel("Phone Number:         ");
        JTextField phoneTextField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:                        ");
        JTextField emailTextField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:                  ");
        JPasswordField passwordField = new JPasswordField(20);
        JButton signupButton = new JButton("Sign Up");



        JPanel signupFieldsPanel = new JPanel(new GridLayout(9, 1, 5, 5));

        signupFieldsPanel.add(createPaddedPanelLabel(title));
        JLabel fnamemsg = new JLabel("");
        signupFieldsPanel.add(createPaddedPanel(fnLabel,fnTextField, fnamemsg));
        JLabel lnamemsg = new JLabel("");
        signupFieldsPanel.add(createPaddedPanel(lnLabel, lnTextField, lnamemsg));
        JLabel phonemsg = new JLabel("");
        signupFieldsPanel.add(createPaddedPanel(phoneLabel, phoneTextField,phonemsg));
        JLabel emailmsg = new JLabel("");
        signupFieldsPanel.add(createPaddedPanel(emailLabel, emailTextField, emailmsg));
        JLabel passwordmsg = new JLabel("");
        signupFieldsPanel.add(createPaddedPanel(passwordLabel, passwordField, passwordmsg));
        signupFieldsPanel.add(createPaddedPanelButton(signupButton));

        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Validation v = new Validation();

                fnamemsg.setText("");
                lnamemsg.setText("");
                phonemsg.setText("");
                emailmsg.setText("");
                passwordmsg.setText("");

                if (!v.checkName(fnTextField.getText())){ // if it is False
                    fnamemsg.setText("Must only contain alphabetic characters or numbers!");
                    fnTextField.setText("");
                    return;
                }

                if (!v.checkName(lnTextField.getText())){ // if it is False
                    lnamemsg.setText("Must only contain alphabetic characters or numbers!");
                    lnTextField.setText("");
                    return;
                }

                if (!v.checkPhone(phoneTextField.getText())){ // if it is False
                    phonemsg.setText("Must be 10 numbers!");
                    phoneTextField.setText("");
                    return;
                }

                if (!v.checkEmail(emailTextField.getText())){ // if it is False
                    emailmsg.setText("Must contain '@'!");
                    emailTextField.setText("");
                    return;
                }

                if (!v.checkPassword(passwordField.getText())){ // if it is False
                    passwordmsg.setText("Must be more than 6 Characters!");
                    passwordField.setText("");
                    return;
                }

                UserController uc = new UserController();
                uc.registerCustomer(emailTextField.getText(),fnTextField.getText(), lnTextField.getText(), phoneTextField.getText(),  passwordField.getText());

            }
        });

        return signupFieldsPanel;

    }

    private JPanel createPaddedPanel(JLabel label, JTextField field, JLabel msg) {
        JPanel paddedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
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
        new Signup();
    }
}