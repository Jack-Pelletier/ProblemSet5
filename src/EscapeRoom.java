import java.util.Scanner;

public class EscapeRoom {
    public static void main(String[] args) {
        Scanner userInformation = new Scanner(System.in);

        // Prompt for user login credentials
        System.out.println("Enter username and password:");
        String userName = userInformation.nextLine();
        String password = userInformation.nextLine();

        // Get an instance of the DAL class
        EscapeRoomDAL dal = new EscapeRoomDAL();

        // Display menu options
        while (true) {
            System.out.println("\n--- Escape Room System ---");
            System.out.println("1. Find available rooms for a group of 5 friends with loyalty conditions");
            System.out.println("2. Check room availability for a birthday reservation");
            System.out.println("3. Generate loyalty reports and gift cards");
            System.out.println("4. Generate monthly business performance report");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");
            int choice = userInformation.nextInt();
            userInformation.nextLine();  // Consume the newline left over from nextInt()

            // Handle the selected option
            switch (choice) {
                case 1:
                    System.out.println("Finding available rooms for a group of 5 friends with loyalty conditions...");
                    if (dal.TryExecutingAStoredProcedure("EscapeRooms", userName, password, "GetAvailableRoomsForFiveFriends", 5)) {
                        System.out.println("Successfully found available rooms.");
                    } else {
                        System.out.println("Failed to find available rooms.");
                    }
                    break;

                case 2:
                    System.out.println("Checking room availability for a birthday reservation...");
                    if (dal.TryExecutingAStoredProcedure("EscapeRooms", userName, password, "GetAvailableRoomsForBirthday", 5)) {
                        System.out.println("Successfully checked room availability for birthday.");
                    } else {
                        System.out.println("Failed to check room availability for birthday.");
                    }
                    break;

                case 3:
                    System.out.println("Generating loyalty reports and gift cards...");
                    if (dal.TryExecutingAStoredProcedure("EscapeRooms", userName, password, "GenerateLoyaltyReport", userName)) {
                        System.out.println("Successfully generated loyalty reports and gift cards.");
                    } else {
                        System.out.println("Failed to generate loyalty reports and gift cards.");
                    }
                    break;

                case 4:
                    System.out.println("Generating monthly business performance report...");
                    if (dal.TryExecutingAStoredProcedure("EscapeRooms", userName, password, "GenerateMonthlyReport")) {
                        System.out.println("Successfully generated monthly business performance report.");
                    } else {
                        System.out.println("Failed to generate monthly business performance report.");
                    }
                    break;

                case 5:
                    System.out.println("Exiting the system...");
                    userInformation.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}
