package org.example.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Invoices extends JPanel {

    private final MainFrame mainFrame;

    public Invoices(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());

        // Center panel for table
        String[] columnNames = {"ID", "Car", "Start Date", "End Date", "Late Fees", "Total Price"};
        Object[][] data = {
                {"1", "Max Cruise", "01/01/2024", "03/01/2024", "$20", "$520"},
                {"2", "Max Cruise", "02/01/2024", "05/01/2024", "$15", "$415"},
                {"3", "Max Cruise", "01/02/2024", "07/02/2024", "$10", "$610"}
        };

        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 6) {
                    return Boolean.class;
                }
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6;
            }
        };

        JTable invoicesTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(invoicesTable);

        // Bottom panel for action buttons
        JPanel actionButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton printButton = new JButton("Print");
        JButton backButton = new JButton("Back to Dashboard");

        actionButtonPanel.add(printButton);

        // Add panels to the frame
        add(tableScrollPane, BorderLayout.CENTER);
        add(actionButtonPanel, BorderLayout.SOUTH);
    }
}
