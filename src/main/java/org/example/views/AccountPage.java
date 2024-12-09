package org.example.views;

import org.example.classes.Booking;
import org.example.classes.User;
import org.example.controllers.BookingController;
import org.example.controllers.UserController;
import org.example.view.Login;
import org.example.view.MainFrame;

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
    private JLabel saveChangesLabel, changeUILabel;
    private JTextField fNameTf, lNameTf, emailTf, phoneTf, passTf;
    private JPanel mainPanel;

    AccountPage(User loggedUser) {
        this.loggedUser = loggedUser;
        JLabel titleLabel, fNameLabel, lNameLabel, emailLabel, phoneLabel, passLabel;

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
        //emailTf.setEditable(false);

        phoneLabel = new JLabel("Phone Number");
        phoneLabel.setFont(labelsFont);
        phoneLabel.setMaximumSize(new Dimension(300, 30));

        passLabel = new JLabel("Password");
        passLabel.setFont(labelsFont);
        passLabel.setMaximumSize(new Dimension(300, 30));


        saveChangesLabel = new JLabel("<html><u>Save Changes</u></html>");
        saveChangesLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        saveChangesLabel.setForeground(Color.GRAY);
        saveChangesLabel.setMaximumSize(new Dimension(300, 30));
        saveChangesLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
               if (!isInfoChanged()) return;
                updateInfoInBackground();
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



        fNameTf = new JTextField(loggedUser.getName().split(" ")[0], 23);
        fNameTf.setFont(new Font("SansSerif", Font.PLAIN, 15));
        fNameTf.setMaximumSize(new Dimension(200, 30));
        fNameTf.getDocument().addDocumentListener(new infoChangedListener());

        lNameTf = new JTextField(loggedUser.getName().split(" ")[1], 23);
        lNameTf.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lNameTf.setMaximumSize(new Dimension(200, 30));
        lNameTf.getDocument().addDocumentListener(new infoChangedListener());

        emailTf = new JTextField(loggedUser.getEmail(), 23);
        emailTf.setFont(new Font("SansSerif", Font.PLAIN, 15));
        emailTf.setMaximumSize(new Dimension(200, 30));
        //emailTf.getDocument().addDocumentListener(new infoChangedListener());
        emailTf.setEditable(false);

        passTf = new JTextField(loggedUser.getPassword(), 23);
        passTf.setFont(new Font("SansSerif", Font.PLAIN, 15));
        passTf.setMaximumSize(new Dimension(200, 30));
        passTf.getDocument().addDocumentListener(new infoChangedListener());

        phoneTf = new JTextField(loggedUser.getPhone(), 23);
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



        contentPanel.add(makeItFlowPanel(emailLabel));
        contentPanel.add(makeItFlowPanel(emailTf));
        contentPanel.add(Box.createVerticalGlue());

        contentPanel.add(makeItFlowPanel(passLabel));
        contentPanel.add(makeItFlowPanel(passTf));
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(Box.createVerticalGlue());

        contentPanel.add(makeItFlowPanel(phoneLabel));
        contentPanel.add(makeItFlowPanel(phoneTf));

        JPanel labelPanel = new JPanel();
        labelPanel .setLayout(new FlowLayout(FlowLayout.LEFT));



        changeUILabel = new JLabel("<html><u>Legacy UI</u></html>");
        changeUILabel .setFont(new Font("SansSerif", Font.PLAIN, 15));
        changeUILabel .setForeground(Color.BLACK);
        changeUILabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                java.awt.Window window = SwingUtilities.getWindowAncestor(changeUILabel);
                if (window != null) {
                    window.dispose();
                }
                new MainFrame(loggedUser);

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                changeUILabel .setFont(new Font("SansSerif", Font.BOLD, 15));

            }

            @Override
            public void mouseExited(MouseEvent e) {
                changeUILabel .setFont(new Font("SansSerif", Font.PLAIN, 15));
            }
        });


        labelPanel.add(saveChangesLabel);
        labelPanel.add(Box.createHorizontalGlue());
        labelPanel.add(Box.createRigidArea(new Dimension(100,10)));
        labelPanel.add(Box.createHorizontalGlue());
        labelPanel.add(changeUILabel);


        contentPanel.add(labelPanel);
        contentPanel.add(Box.createVerticalGlue());


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
        SwingWorker<Void, JLabel> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                BookingController bookingController = new BookingController();
                List<Booking> userBookings = bookingController.getAllBookingsByUserid(loggedUser.getId());
                LocalDateTime nowDate = LocalDateTime.now();

                for (Booking bookingInfo : userBookings) {
                    if (bookingInfo.getStatus().equals("active")) {
                        LocalDateTime endDate = bookingInfo.getEnd_date().toLocalDateTime();
                        if (bookingInfo.getBookedAt().toLocalDateTime().toLocalDate().equals(nowDate.toLocalDate())) {
                            JLabel notificationLabel = new JLabel("* Your booking number #" + bookingInfo.getId() + " has been accepted", JLabel.CENTER);
                            publish(notificationLabel); // Send to process() for GUI update
                        } else if (endDate.isAfter(nowDate) && endDate.isBefore(nowDate.plusDays(1))) {
                            JLabel notificationLabel = new JLabel("* Your booking number #" + bookingInfo.getId() + " ends soon", JLabel.CENTER);
                            publish(notificationLabel); // Send to process() for GUI update
                        }
                    }


                }
                return null;
            }

            @Override
            protected void process(List<JLabel> notifications) {
                // Safely update GUI here
                notificationsList.removeAll();
                for (JLabel notificationLabel : notifications) {
                    notificationsList.add(notificationLabel);
                }
                notificationsList.repaint();
                notificationsList.revalidate();
            }

            @Override
            protected void done() {
                // Optional: Perform any final tasks after completion, like logging
                System.out.println("Background task completed.");
            }
        };

// Execute the SwingWorker
        worker.execute();

    }

    private void updateInfoInBackground() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                UserController uc = new UserController();
                uc.updateCustomerInfo(
                        emailTf.getText(),
                        fNameTf.getText(),
                        lNameTf.getText(),
                        phoneTf.getText(),
                        passTf.getText()
                );

                loggedUser.setName(fNameTf.getText() + " " + lNameTf.getText());
                loggedUser.setEmail(emailTf.getText());
                loggedUser.setPhone(phoneTf.getText());
                loggedUser.setPassword(passTf.getText());

                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    saveChangesLabel.setForeground(Color.lightGray);
                    System.out.println("Saved");
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to update customer info: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        worker.execute();
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
            if (isInfoChanged()) saveChangesLabel.setForeground(Color.BLUE);
            else saveChangesLabel.setForeground(Color.lightGray);

        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            System.out.println("removeUpdate");
            if (isInfoChanged()) saveChangesLabel.setForeground(Color.BLUE);
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
                        || !passTf.getText().equals(loggedUser.getPassword())
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
