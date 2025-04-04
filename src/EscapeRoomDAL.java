import java.sql.*;

public class EscapeRoomDAL {

    // Helper method to get MySQL connection
    private Connection getMySQLConnection(String databaseName, String user, String password) {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName, user, password);
        } catch (SQLException exception) {
            System.out.println("Failed to connect to the database: " + exception.getMessage());
            return null;
        }
    }

    // Method to execute a stored procedure
    public boolean TryExecutingAStoredProcedure(String databaseName, String user, String password, String storedProcedureName, Object... params) {
        Connection myConnection = getMySQLConnection(databaseName, user, password);
        if (myConnection == null) {
            System.out.println("Failed to get a connection, cannot execute stored procedure");
            return false;
        }
        try {
            // Create the callable statement based on the stored procedure name
            String query = "{Call " + storedProcedureName + "}";
            CallableStatement myStoredProcedureCall = myConnection.prepareCall(query);

            // Set parameters based on the stored procedure signature
            for (int i = 0; i < params.length; i++) {
                myStoredProcedureCall.setObject(i + 1, params[i]);
            }

            // Execute the stored procedure
            ResultSet myResults = myStoredProcedureCall.executeQuery();

            // Iterate over the ResultSet, row by row
            while (myResults.next()) {
                // Print out the result set (Modify based on actual result set structure)
                System.out.println("Result: " + myResults.getString(1));
            }
        } catch (SQLException exception) {
            System.out.println("Failed to execute stored procedure: " + exception.getMessage());
            return false;
        }
        return true;
    }
}
