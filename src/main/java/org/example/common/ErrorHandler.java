package org.example.common;
import javax.swing.JOptionPane;


public class ErrorHandler {
    public static void showError(String msg){
        JOptionPane.showMessageDialog(null, msg,"Error",JOptionPane.ERROR_MESSAGE);
    }
    public static void handleException(Exception ex,String ermsg){
        showError(ermsg);
    }
}
