package org.example.view;

import org.example.classes.User;
import org.example.controllers.UserController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CustomersInfo extends JPanel {

    private final AdminDashboard dashboard;
    private final UserController userController = new UserController();
    private JTable table;
    private Object[][] data;
    private final String[] columnNames = {"ID", "Name", "Email", "Phone Number"};

    public CustomersInfo(AdminDashboard dashboard) {
        this.dashboard = dashboard;
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Top panel for search by ID
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel searchLabel = new JLabel("Search by ID:");
        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");
        topPanel.add(searchLabel);
        topPanel.add(searchField);
        topPanel.add(searchButton);

        

        // Create initial table data
        updateTableData();
        table = new JTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Bottom panel for buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton refreshButton = new JButton("Refresh");


        bottomPanel.add(refreshButton);
        

        // Add panels to the frame
        add(topPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);

        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTableData();
                searchField.setText("");
            }
        });

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean found = false; 
                int searchId;
                try {
                    searchId = Integer.parseInt(searchField.getText());
                }
                catch (NumberFormatException er) {
                 // Display an error message if parsing fails
                 JOptionPane.showMessageDialog(new JFrame(), "Error: Please enter a valid ID!", 
                 "Invalid Input", JOptionPane.ERROR_MESSAGE);
                 searchField.setText("");
                 return;
                }
                List<User> allUsers = userController.getAllUsers();
                data = new Object[1][columnNames.length];
                for (int i = 0; i < allUsers.size(); i++) {
                    User user = allUsers.get(i);
                    if (user.getId() == searchId){
                        data[0][0] = user.getId();
                        data[0][1] = user.getName();
                        data[0][2] = user.getEmail();
                        data[0][3] = user.getPhone();
                        found = true;
                    }
                }
                if (found) {
                    table.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
                }
                else{
                    JOptionPane.showMessageDialog(new JFrame(), "ID not found!", 
                 "Not Found", JOptionPane.WARNING_MESSAGE);
                 searchField.setText("");
                }


            }
        });


    }

    private void updateTableData() {
        List<User> allUsers = userController.getAllUsers();
        data = new Object[allUsers.size()][columnNames.length];
        for (int i = 0; i < allUsers.size(); i++) {
            User user = allUsers.get(i);
            data[i][0] = user.getId();
            data[i][1] = user.getName();
            data[i][2] = user.getEmail();
            data[i][3] = user.getPhone();
        }
        if (table != null) {
            table.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        }
        
    }

        

}