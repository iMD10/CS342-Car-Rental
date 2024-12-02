package org.example.view;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Invoices extends JFrame {

    public Invoices() {
        setTitle("Invoices");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Center panel for table
        String[] columnNames = {"ID", "Name", "Start Date", "End Date", "Late Fees", "Total Price", "Selected"};
        Object[][] data = {
                {"1", "John Doe", "01/01/2024", "03/01/2024", "$20", "$520", false},
                {"2", "Jane Smith", "02/01/2024", "05/01/2024", "$15", "$415", false},
                {"3", "Robert Brown", "01/02/2024", "07/02/2024", "$10", "$610", false}
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
        actionButtonPanel.add(backButton);

        // Add panels to the frame
        add(tableScrollPane, BorderLayout.CENTER);
        add(actionButtonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Invoices();
    }
}
