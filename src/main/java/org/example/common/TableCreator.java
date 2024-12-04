package org.example.common;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TableCreator {

    public static JScrollPane createTablePanel(String[] columnNames, Object[][] data, boolean[] editableColumns) {
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex < editableColumns.length && editableColumns[columnIndex]) {
                    return Boolean.class; // For columns marked as editable
                }
                return String.class; // Default to String for other columns
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column < editableColumns.length && editableColumns[column];
            }
        };

        JTable table = new JTable(tableModel);
        return new JScrollPane(table);
    }


    public static JPanel createBottomPanel(Component[] buttons) {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        for (Component button : buttons) {
            bottomPanel.add(button);
        }
        return bottomPanel;
    }

    public static JPanel createTopPanel(String labelText, int textFieldLength, JButton actionButton) {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JLabel label = new JLabel(labelText);
        JTextField textField = new JTextField(textFieldLength);
        topPanel.add(label);
        topPanel.add(textField);
        topPanel.add(actionButton);
        return topPanel;
    }
}