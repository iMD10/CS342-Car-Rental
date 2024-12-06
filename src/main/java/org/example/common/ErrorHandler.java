package org.example.common;
import javax.swing.JOptionPane;


public class ErrorHandler {
    public static void showError(String msg){
        JOptionPane.showMessageDialog(null, msg,"Error",JOptionPane.ERROR_MESSAGE);
    }
    public static void handleException(Exception ex,String ermsg){
        showError(ermsg);
    }
    public static void handleWarning(String wmsg){
        JOptionPane.showMessageDialog(null, wmsg,"WARNING",JOptionPane.WARNING_MESSAGE);
    }
    public static void handleInfo(String wmsg){
        JOptionPane.showMessageDialog(null, wmsg,"INFO",JOptionPane.INFORMATION_MESSAGE);
    }
}
