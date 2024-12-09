package org.example.view;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;

public class PDFInvoiceGenerator {

    public static void generateInvoice(String customerName, String vehicleName, LocalDate fromDate, LocalDate toDate, double totalPrice, double lateFees) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Invoice");
        fileChooser.setSelectedFile(new File(customerName + "Invoice.pdf"));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String pdfPath = fileToSave.getAbsolutePath();

            if (!pdfPath.endsWith(".pdf")) {
                pdfPath += ".pdf"; // Ensure the file has a .pdf extension
            }

            try {
                PdfWriter writer = new PdfWriter(new FileOutputStream(pdfPath));
                com.itextpdf.kernel.pdf.PdfDocument pdfDocument = new com.itextpdf.kernel.pdf.PdfDocument(writer);
                Document document = new Document(pdfDocument);

                document.add(new Paragraph("Invoice").setFontSize(18).setBold());
                document.add(new Paragraph("Customer Name: " + customerName));
                document.add(new Paragraph("Vehicle Name: " + vehicleName));
                document.add(new Paragraph("Booking start at: " + fromDate));
                document.add(new Paragraph("Booking end at: " + toDate));
                document.add(new Paragraph("Total Price: $" + totalPrice));
                if (lateFees > 0) {
                    document.add(new Paragraph("Late Fees: $" + lateFees));
                }

                document.close();
                JOptionPane.showMessageDialog(null, "Invoice generated successfully! Saved at: " + pdfPath);

            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to generate the invoice.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Save operation canceled.");
        }
    }
}
