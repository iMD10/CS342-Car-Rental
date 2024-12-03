package org.example.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Agreements extends JPanel {

    private final MainFrame mainFrame;

    public Agreements(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // Center panel for table
        String[] columnNames = {"ID", "Booking ID", "Car", "Start Date", "End Date", "Selected"};
        Object[][] data = {
                {"1", "101", "ACCORD", "01/01/2024", "03/01/2024", false},
                {"2", "102", "MaxCruize", "02/01/2024", "05/01/2024", false},
                {"3", "103", "Fortuner", "01/02/2024", "07/02/2024", false}
        };

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 5) {
                    return Boolean.class;
                }
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };

        JTable agreementTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(agreementTable);

        // Bottom panel for action buttons
        JPanel actionButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton printButton = new JButton("Print");

        actionButtonPanel.add(printButton);

        // Add panels to the frame
        add(tableScrollPane, BorderLayout.CENTER);
        add(actionButtonPanel, BorderLayout.SOUTH);
    }
}
