package org.example.view;

import org.example.classes.User;
import org.example.controllers.UserController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import org.example.common.Validation;

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
        JLabel searchLabel = new JLabel("Search by Name:");
        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");
        topPanel.add(searchLabel);
        topPanel.add(searchField);
        topPanel.add(searchButton);

        

        // Create initial table data
        updateTableData();

        table = new JTable(new DefaultTableModel(data, columnNames){
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        });
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
                String key_word = searchField.getText().toLowerCase();
                Validation v = new Validation();
                
                if (key_word.isEmpty()|!v.checkName(key_word)){
                    JOptionPane.showMessageDialog(new JFrame(), "Error: Please enter a valid Name!", 
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    searchField.setText("");
                    return;
                }

                List<User> allUsers = userController.getCustomers();
                data = new Object[allUsers.size()][columnNames.length];
                int found_data_size = 0;
                for (int i = 0; i < allUsers.size(); i++) {
                    User user = allUsers.get(i);
                    if (user.isAdmin()){ // Skip Admins
                        continue;
                    }
                    if (user.getName().toLowerCase().contains(key_word)){
                        data[found_data_size][0] = user.getId();
                        data[found_data_size][1] = user.getName();
                        data[found_data_size][2] = user.getEmail();
                        data[found_data_size][3] = user.getPhone();
                        found_data_size++;
                        found = true;
                    }
                }

                if (found) {
                    Object[][] found_data = new Object[found_data_size][columnNames.length]; // Create Another shorter data, so only what we found is shown

                    for (int i = 0; i < found_data_size; i++){
                        found_data[i][0] = data[i][0];
                        found_data[i][1] = data[i][1];
                        found_data[i][2] = data[i][2];
                        found_data[i][3] = data[i][3];
                    }

                    table.setModel(new DefaultTableModel(found_data, columnNames){
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    });
                }
                else{
                    JOptionPane.showMessageDialog(new JFrame(), "Name not found!", 
                 "Not Found", JOptionPane.WARNING_MESSAGE);
                 searchField.setText("");
                }


            }
        });


    }

    private void updateTableData() {
        List<User> allUsers = userController.getCustomers();
        data = new Object[allUsers.size()][columnNames.length];
        for (int i = 0; i < allUsers.size(); i++) {
            User user = allUsers.get(i);
            data[i][0] = user.getId();
            data[i][1] = user.getName();
            data[i][2] = user.getEmail();
            data[i][3] = user.getPhone();
        }
        if (table != null) {
            table.setModel(new javax.swing.table.DefaultTableModel(data, columnNames){
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });
        }
        
    }

        

}
