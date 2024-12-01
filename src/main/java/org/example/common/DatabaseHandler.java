package org.example.common;
import java.sql.*;

public class DatabaseHandler {
    private  String DB_URL;
    private  String DB_USERNAME;
    private  String DB_PASSWORD;

    private Connection connection;

    public DatabaseHandler() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        }
    }
}
