import java.sql.*;

public class EscapeRoomDAL
{
    private Connection getMySQLConnection(String databaseName, String user, String password)
    {
        try
        {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName, user, password);
        } 
        catch (SQLException exception)
        {
            System.out.println("Connection failed: " + exception.getMessage());
            return null;
        }
    }

    public boolean GetAvailableRooms(String databaseName, String user, String password, int groupSize, String date)
    {
        Connection conn = getMySQLConnection(databaseName, user, password);
        if (conn == null) return false;

        try
        {
            CallableStatement stmt = conn.prepareCall("{CALL GetAvailableRooms(?, ?)}");
            stmt.setInt(1, groupSize);
            stmt.setString(2, date);
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {
                int roomID = rs.getInt("RoomID");
                String themeName = rs.getString("Name");
                int minPlayers = rs.getInt("MinPlayers");
                int maxPlayers = rs.getInt("MaxPlayers");
                System.out.println("Room ID: " + roomID + ", Theme: " + themeName + ", Min: " + minPlayers + ", Max: " + maxPlayers);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Failed to execute GetAvailableRooms: " + ex.getMessage());
            return false;
        }
        return true;
    }

    public boolean GetBirthdayAvailability(String databaseName, String user, String password, String desiredDate)
    {
        Connection conn = getMySQLConnection(databaseName, user, password);
        if (conn == null) return false;

        try
        {
            CallableStatement stmt = conn.prepareCall("{CALL GetBirthdayAvailability(?)}");
            stmt.setString(1, desiredDate);
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {
                int roomID = rs.getInt("RoomID");
                String time = rs.getString("Time");
                System.out.println("Available Room ID: " + roomID + " at " + time);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Failed to execute GetBirthdayAvailability: " + ex.getMessage());
            return false;
        }
        return true;
    }

    public boolean GenerateLoyaltyReport(String databaseName, String user, String password)
    {
        Connection conn = getMySQLConnection(databaseName, user, password);
        if (conn == null) return false;

        try
        {
            CallableStatement stmt = conn.prepareCall("{CALL GenerateLoyaltyReport()}");
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {
                int customerID = rs.getInt("CustomerID");
                int completedEscapes = rs.getInt("CompletedEscapes");
                int freeVisits = rs.getInt("FreeVisits");
                System.out.println("Customer: " + customerID + ", Escapes: " + completedEscapes + ", Free Visits: " + freeVisits);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Failed to execute GenerateLoyaltyReport: " + ex.getMessage());
            return false;
        }
        return true;
    }

    public boolean MonthlyPerformanceReport(String databaseName, String user, String password, int month)
    {
        Connection conn = getMySQLConnection(databaseName, user, password);
        if (conn == null) return false;

        try
        {
            CallableStatement stmt = conn.prepareCall("{CALL MonthlyPerformanceReport(?)}");
            stmt.setInt(1, month);
            ResultSet rs = stmt.executeQuery();

            while (rs.next())
            {
                int totalBookings = rs.getInt("TotalBookings");
                int totalWins = rs.getInt("TotalWins");
                int totalLosses = rs.getInt("TotalLosses");
                System.out.println("Month: " + month + " | Bookings: " + totalBookings + " | Wins: " + totalWins + " | Losses: " + totalLosses);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Failed to execute MonthlyPerformanceReport: " + ex.getMessage());
            return false;
        }
        return true;
    }
}
