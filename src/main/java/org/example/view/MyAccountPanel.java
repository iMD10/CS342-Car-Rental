package org.example.view;

import org.example.controllers.UserController;
import org.example.common.Validation;
import org.example.classes.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MyAccountPanel extends JPanel {

    private JLabel welcome, title, fnameLabel, lnameLabel, emailLabel, phoneLabel, passwordLabel;
    private JButton saveChangesButton;
    private JTextField fnameTextField, lnameTextField, emailTextField, phoneTextField;
    private JPasswordField passwordField;

    private User loggedUser;

    public MyAccountPanel(User loggedUser) {
        this.loggedUser = loggedUser;

        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(8, 1, 1, 1));

        welcome = new JLabel("Update Your Info");
        welcome.setFont(new Font("Arial", Font.BOLD, 20));
        title = new JLabel("My Account");
        fnameLabel = new JLabel("First Name: ");
        lnameLabel = new JLabel("Last Name: ");
        emailLabel = new JLabel("Email:      ");
        phoneLabel = new JLabel("Phone:     ");
        passwordLabel = new JLabel("Password: ");
        saveChangesButton = new JButton("Save Changes");
        String[] fullName =loggedUser.getName().split(" ");
        fnameTextField = new JTextField(fullName[0], 20);
        lnameTextField = new JTextField(fullName[1], 20);
        emailTextField = new JTextField(loggedUser.getEmail(), 20);
        emailTextField.setEditable(false);
        phoneTextField = new JTextField(loggedUser.getPhone(), 20);
        passwordField = new JPasswordField(20);

        mainPanel.add(createPaddedPanelLabel(welcome));
        mainPanel.add(createPaddedPanelLabel(title));
        mainPanel.add(createPaddedPanel(fnameLabel, fnameTextField));
        mainPanel.add(createPaddedPanel(lnameLabel, lnameTextField));
        mainPanel.add(createPaddedPanel(emailLabel, emailTextField));
        mainPanel.add(createPaddedPanel(phoneLabel, phoneTextField));
        mainPanel.add(createPaddedPanel(passwordLabel, passwordField));
        mainPanel.add(createPaddedPanelButton(saveChangesButton));

        add(mainPanel, BorderLayout.CENTER);

        saveChangesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateUserInfo();
            }
        });

        KeyAdapter enterKeyListener = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    updateUserInfo();
                }
            }
        };

        fnameTextField.addKeyListener(enterKeyListener);
        lnameTextField.addKeyListener(enterKeyListener);
        emailTextField.addKeyListener(enterKeyListener);
        phoneTextField.addKeyListener(enterKeyListener);
        passwordField.addKeyListener(enterKeyListener);
    }

    private void updateUserInfo() {
        Validation v = new Validation();

        if (fnameTextField.getText().isEmpty() || lnameTextField.getText().isEmpty() || emailTextField.getText().isEmpty() ||
                phoneTextField.getText().isEmpty() || passwordField.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!v.checkEmail(emailTextField.getText())) {
            JOptionPane.showMessageDialog(this, "Email is incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!v.checkPassword(new String(passwordField.getPassword()))) {
            JOptionPane.showMessageDialog(this, "Password is incorrect!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!v.checkPhone(phoneTextField.getText())) {
            JOptionPane.showMessageDialog(this, "Phone number must be exactly 10 digits!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!v.checkName(fnameTextField.getText()) || !v.checkName(lnameTextField.getText())) {
            JOptionPane.showMessageDialog(this, "First and Last names must contain only letters!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        UserController uc = new UserController();
        boolean updated = uc.updateCustomerInfo(emailTextField.getText(), fnameTextField.getText(), lnameTextField.getText(), phoneTextField.getText(), new String(passwordField.getPassword()));

        if (!updated) {
            JOptionPane.showMessageDialog(this, "Error updating user information!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Information updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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
}
