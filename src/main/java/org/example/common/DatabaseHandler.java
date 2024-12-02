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

        } catch (SQLException e) {
            ErrorHandler.handleException(e, "Failed to connect to the database.");
            throw new RuntimeException("Failed to connect to the database.");
        }
    }
    public ResultSet executeQuery(String query, Object... params) throws SQLException {
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            setParameters(ps, params);
            return ps.executeQuery();
        } catch (SQLException e) {
            ErrorHandler.handleException(e, "Failed to execute the query, Please try again.");
            return null;
        }
    }

    public int executeUpdate(String query, Object... params) throws SQLException {
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            setParameters(ps, params);
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if(rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            ErrorHandler.handleException(e, "Failed to update the database, Please try again.");
        }
        return -1;
    }

    private void setParameters(PreparedStatement ps, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }
    }
    public void closeConnection() {
        try {
            if(connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            ErrorHandler.handleException(e, "Failed to close the database connection.");
            throw new RuntimeException("Failed to close the database connection.");
        }
    }
}
