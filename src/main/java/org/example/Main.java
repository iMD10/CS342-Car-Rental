package org.example;

import com.formdev.flatlaf.FlatLightLaf;
import org.example.view.Login;

public class Main {
    public static void main(String[] args) {

        FlatLightLaf.setup();
        Login login = new Login();
    }
}