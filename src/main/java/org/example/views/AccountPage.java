package org.example.views;

import com.formdev.flatlaf.FlatLightLaf;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateChangeListener;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;
import com.github.lgooddatepicker.zinternaltools.DateChangeEvent;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.time.LocalDate;

public class AccountPage extends JFrame {

    private JLabel titleLabel, fNameLabel, lNameLabel, emailLabel, phoneLabel;
    private JTextField titleTf, flNameTf, lNameTf, emailTf, phoneTf;
    private JPanel mainPanel;

    AccountPage() {

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int W = screenSize.width;
        int H = screenSize.height;
        this.setBounds((W) / 4, (H) / 4, W / 2, H / 2);

        mainPanel = new JPanel(new BorderLayout());
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        titleLabel = new JLabel("Account Page");
        fNameLabel = new JLabel("First Name");
        lNameLabel = new JLabel("Last Name");
        emailLabel = new JLabel("Email");


        mainPanel.add(new Components().createSideBarPanel(), BorderLayout.WEST);

        mainPanel.add(contentPanel, BorderLayout.CENTER);


        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    public static void main(String[] args) {
        new AccountPage();
    }
}
