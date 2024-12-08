package org.example.common;

import java.sql.*;

public class DatabaseHandler {
        private static final String DB_URL = System.getenv("DB_URL");
        private static final String DB_USERNAME = System.getenv("DB_USERNAME");
        private static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

        private Connection connection;
        private PreparedStatement ps;

        public DatabaseHandler() {
            try {
                connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            } catch (SQLException e) {
                ErrorHandler.handleException(e, "Failed to connect to the database.");
                throw new RuntimeException("Failed to connect to the database.");
            }
        }

    public ResultSet executeQuery(String query, Object... params) throws SQLException {

            PreparedStatement ps = connection.prepareStatement(query);
            setParameters(ps, params);
            return ps.executeQuery(); // Caller must close the ResultSet

    }

    public int executeUpdate(String query, Object... params) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            setParameters(ps, params);

            int rowsAffected = ps.executeUpdate(); // Execute the query
            if (rowsAffected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt("id"); // Return the generated key
                    }
                }
            }
            return rowsAffected; // Return rowsAffected if no keys are generated
        } catch (SQLException e) {
            ErrorHandler.handleException(e, "Failed to update the database, Please try again.");
            throw e; // Rethrow the exception for the caller to handle
        }
    }


    private void setParameters(PreparedStatement ps, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        } catch (SQLException e) {
            ErrorHandler.handleException(e, "Failed to close the database connection.");
            throw new RuntimeException("Failed to close the database connection.");
        }
    }
}
