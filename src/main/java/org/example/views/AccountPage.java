package org.example.views;

import org.example.classes.Booking;
import org.example.classes.User;
import org.example.controllers.BookingController;
import org.example.controllers.UserController;
import org.example.view.Login;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDateTime;
import java.util.List;

public class AccountPage extends JPanel {

    private User loggedUser;
    private  JPanel notificationsList;
    private JLabel saveChangesLabel;
    private JTextField fNameTf, lNameTf, emailTf, phoneTf;
    private JPanel mainPanel;

    AccountPage(User loggedUser) {
        this.loggedUser = loggedUser;
        JLabel titleLabel, fNameLabel, lNameLabel, emailLabel, phoneLabel;

        this.setLayout(new BorderLayout()); // Set layout for the main panel

        mainPanel = new JPanel(new BorderLayout());
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        titleLabel = new JLabel("Account Page");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 25));

        Font labelsFont = new Font("SansSerif", Font.PLAIN, 15);
        fNameLabel = new JLabel("First Name");
        fNameLabel.setFont(labelsFont);
        fNameLabel.setPreferredSize(new Dimension(300,30));

        lNameLabel = new JLabel("Last Name");
        lNameLabel.setFont(labelsFont);
        lNameLabel.setMaximumSize(new Dimension(300, 30));

        emailLabel = new JLabel("Email");
        emailLabel.setFont(labelsFont);
        emailLabel.setMaximumSize(new Dimension(300, 30));

        phoneLabel = new JLabel("Phone Number");
        phoneLabel.setFont(labelsFont);
        phoneLabel.setMaximumSize(new Dimension(300, 30));

        saveChangesLabel = new JLabel("<html><u>Save Changes</u></html>");
        saveChangesLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        saveChangesLabel.setForeground(Color.GRAY);
        saveChangesLabel.setMaximumSize(new Dimension(300, 30));
        saveChangesLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
               if (!isInfoChanged()) return;
                saveChangesLabel.setForeground(Color.lightGray);
                System.out.println("Saved");
                UserController uc =  new UserController();
                uc.updateCustomerInfo(emailTf.getText(), fNameTf.getText(), lNameTf.getText(), phoneTf.getText(), loggedUser.getPassword());

                loggedUser.setName(fNameTf.getText() + " " + lNameTf.getText());
                loggedUser.setEmail(emailTf.getText());
                loggedUser.setPhone(phoneTf.getText());
            }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });



        fNameTf = new JTextField(loggedUser.getName().split(" ")[0], 20);
        fNameTf.setFont(new Font("SansSerif", Font.PLAIN, 15));
        fNameTf.setMaximumSize(new Dimension(200, 30));
        fNameTf.getDocument().addDocumentListener(new infoChangedListener());

        lNameTf = new JTextField(loggedUser.getName().split(" ")[1], 20);
        lNameTf.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lNameTf.setMaximumSize(new Dimension(200, 30));
        lNameTf.getDocument().addDocumentListener(new infoChangedListener());

        emailTf = new JTextField(loggedUser.getEmail(), 20);
        emailTf.setFont(new Font("SansSerif", Font.PLAIN, 15));
        emailTf.setMaximumSize(new Dimension(200, 30));
        emailTf.getDocument().addDocumentListener(new infoChangedListener());

        phoneTf = new JTextField(loggedUser.getPhone(), 20);
        phoneTf.setFont(new Font("SansSerif", Font.PLAIN, 15));
        phoneTf.setMaximumSize(new Dimension(200, 30));
        phoneTf.getDocument().addDocumentListener(new infoChangedListener());



        contentPanel.add(makeItFlowPanel(titleLabel));
        contentPanel.add(Box.createRigidArea(new Dimension(300, 25)));
        contentPanel.add(Box.createVerticalGlue());

        contentPanel.add(makeItFlowPanel(fNameLabel));
        contentPanel.add((makeItFlowPanel(fNameTf)));
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(makeItFlowPanel(lNameLabel));
        contentPanel.add(makeItFlowPanel(lNameTf));
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(Box.createRigidArea(new Dimension(1, 20)));


        contentPanel.add(makeItFlowPanel(emailLabel));
        contentPanel.add(makeItFlowPanel(emailTf));
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(Box.createVerticalGlue());

        contentPanel.add(makeItFlowPanel(phoneLabel));
        contentPanel.add(makeItFlowPanel(phoneTf));
        contentPanel.add(makeItFlowPanel(saveChangesLabel));
        contentPanel.add(Box.createVerticalGlue());


//        mainPanel.add(Components.createSideBarPanel(), BorderLayout.WEST);

        JLabel notificationsTitle = new JLabel("Notifications", JLabel.CENTER);
        notificationsTitle.setFont(new Font("SansSerif", Font.PLAIN, 15));
        notificationsTitle.setForeground(Color.BLACK);

        notificationsList = new JPanel(new GridLayout(0,1, 0, 10));
        loadNotifications();

        JLabel logOutLabel = new JLabel("<html><u>Log out </u></html>", JLabel.CENTER);
        logOutLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        logOutLabel.setForeground(Color.RED);
        logOutLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Login();
                java.awt.Window window = SwingUtilities.getWindowAncestor(logOutLabel);
                if (window != null) {
                    window.dispose();
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });



        JPanel fullSpace = new JPanel(new BorderLayout());
        JPanel header = new JPanel(new BorderLayout());

        JPanel sidePanel = new JPanel(new BorderLayout());

        header.add(notificationsTitle, BorderLayout.NORTH);
        header.add(notificationsList, BorderLayout.CENTER);

        sidePanel.add(header, BorderLayout.NORTH);
        sidePanel.add(logOutLabel, BorderLayout.SOUTH);
//        sidePanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2)); // Line border with thickness of 2
        fullSpace.add(sidePanel, BorderLayout.CENTER);
        fullSpace.add(Box.createRigidArea(new Dimension(1,15)), BorderLayout.SOUTH);
        JPanel paddingPanel = new JPanel();
        paddingPanel.setLayout(new BoxLayout(paddingPanel, BoxLayout.X_AXIS));
        paddingPanel.add(Box.createRigidArea(new Dimension(10,1)));
        paddingPanel.add(contentPanel);
        paddingPanel.add(Box.createHorizontalGlue());

        JSeparator js = new JSeparator(JSeparator.VERTICAL);
        js.setForeground(Color.LIGHT_GRAY);
        js.setPreferredSize(new Dimension(2, 300)); // Adjust the width and height for vertical separator
        fullSpace.add(js, BorderLayout.WEST);

//        paddingPanel.add(js);

        paddingPanel.add(Box.createRigidArea(new Dimension(20,1)));
        paddingPanel.add(fullSpace);
        paddingPanel.add(Box.createRigidArea(new Dimension(10,1)));



//        mainPanel.add(sidePanel,BorderLayout.EAST);
        mainPanel.add(paddingPanel, BorderLayout.CENTER);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                loadNotifications();
                System.out.println("componentShown");

            }
        });

        this.add(mainPanel);

    }

    private void loadNotifications() {
        BookingController bookingController= new BookingController();
        List<Booking> userBookings = bookingController.getAllBookingsByUserid(loggedUser.getId());
        LocalDateTime nowDate = LocalDateTime.now();


        notificationsList.removeAll();
        for (Booking bookingInfo : userBookings ){
            if (! bookingInfo.getStatus().equals("active")) continue;

            LocalDateTime endDate = bookingInfo.getEnd_date().toLocalDateTime();
            if (bookingInfo.getBookedAt().toLocalDateTime().toLocalDate().equals(nowDate.toLocalDate())){
                JLabel notificationLabel = new JLabel("* Your booking number #"+bookingInfo.getId()+" has been accepted", JLabel.CENTER);
                notificationsList.add(notificationLabel);
            } else if (endDate.isAfter(nowDate) && endDate.isBefore(nowDate.plusDays(1)) ){
                JLabel notificationLabel = new JLabel("* Your booking number #"+bookingInfo.getId()+" ends soon", JLabel.CENTER);
                notificationsList.add(notificationLabel);
            }

        }
        notificationsList.repaint();
        notificationsList.revalidate();
    }

    private JPanel makeItFlowPanel(Component label) {
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        labelPanel.add(label);


        return labelPanel;
    }

    private class infoChangedListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent e) {
            if (isInfoChanged()) saveChangesLabel.setForeground(Color.cyan);
            else saveChangesLabel.setForeground(Color.lightGray);
            isInfoChanged();

        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            System.out.println("removeUpdate");
            if (isInfoChanged()) saveChangesLabel.setForeground(Color.cyan);
            else saveChangesLabel.setForeground(Color.lightGray);

        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    }

    boolean isInfoChanged(){
        if (
                !fNameTf.getText().equals(loggedUser.getName().split(" ")[0])
                        || !lNameTf.getText().equals(loggedUser.getName().split(" ")[1])
                        || !emailTf.getText().equals(loggedUser.getEmail())
                        || !phoneTf.getText().equals(loggedUser.getPhone())
        ) {
                return true;
        }
        else {
                return false;
        }

    }

//    public static void main(String[] args) {
//        new AccountPage(loggedUser);
//    }
}
