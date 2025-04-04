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
            // Dynamically construct the query with placeholders for parameters
            StringBuilder queryBuilder = new StringBuilder("{Call ");
            queryBuilder.append(storedProcedureName);
            if (params.length > 0) {
                queryBuilder.append("(");
                for (int i = 0; i < params.length; i++) {
                    queryBuilder.append(i == 0 ? "?" : ",?");
                }
                queryBuilder.append(")");
            }
            queryBuilder.append("}");
    
            String query = queryBuilder.toString();
            CallableStatement myStoredProcedureCall = myConnection.prepareCall(query);
    
            // Set input parameters dynamically
            for (int i = 0; i < params.length; i++) {
                myStoredProcedureCall.setObject(i + 1, params[i]);
            }
    
            // Register output parameters if the procedure is related to available_rooms
            if (storedProcedureName.equals("GetAvailableRoomsForFiveFriends") || storedProcedureName.equals("GetAvailableRoomsForBirthday")) {
                // Register available_rooms as an output parameter (position is params.length + 1)
                myStoredProcedureCall.registerOutParameter(params.length + 1, Types.VARCHAR); // Text is VARCHAR in JDBC
            }
    
            // Execute the stored procedure
            boolean hasResultSet = myStoredProcedureCall.execute();
    
            // Handle the results if there is a ResultSet
            if (hasResultSet) {
                ResultSet myResults = myStoredProcedureCall.getResultSet();
                while (myResults.next()) {
                    // Process the result (for this example, we're just printing it)
                    System.out.println("Result: " + myResults.getString(1)); // Adjust column index or name as needed
                }
            } else {
                // Process the output parameter (for available_rooms)
                if (storedProcedureName.equals("GetAvailableRoomsForFiveFriends") || storedProcedureName.equals("GetAvailableRoomsForBirthday")) {
                    String availableRooms = myStoredProcedureCall.getString(params.length + 1); // Retrieve the output
                    System.out.println("Available Rooms: " + availableRooms);
                }
                System.out.println("Stored procedure executed successfully, no result set returned.");
            }
        } catch (SQLException exception) {
            System.out.println("Failed to execute stored procedure: " + exception.getMessage());
            return false;
        }
        return true;
    }

    // Wrapper method for calling 'GetAvailableRoomsForFiveFriends'
    public boolean GetAvailableRoomsForFiveFriends(String databaseName, String user, String password, String friend_ids) {
        return TryExecutingAStoredProcedure(databaseName, user, password, "GetAvailableRoomsForFiveFriends", friend_ids);
    }

    // Wrapper method for calling 'GetAvailableRoomsForBirthday'
    public boolean GetAvailableRoomsForBirthday(String databaseName, String user, String password, Date check_date) {
        return TryExecutingAStoredProcedure(databaseName, user, password, "GetAvailableRoomsForBirthday", check_date);
    }

    // Wrapper method for calling 'GenerateLoyaltyReport'
    public boolean GenerateLoyaltyReport(String databaseName, String user, String password, int customer_id) {
        return TryExecutingAStoredProcedure(databaseName, user, password, "GenerateLoyaltyReport", customer_id);
    }

    // Wrapper method for calling 'GenerateMonthlyReport'
    public boolean GenerateMonthlyReport(String databaseName, String user, String password, int report_month, int report_year) {
        return TryExecutingAStoredProcedure(databaseName, user, password, "GenerateMonthlyReport", report_month, report_year);
    }
}
