import java.util.Scanner;

public class EscapeRoom
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        EscapeRoomDAL dal = new EscapeRoomDAL();

        System.out.print("Enter database name: ");
        String databaseName = sc.nextLine();

        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();
        
        
        while (true)
        {
            System.out.println("\nEscape Room System");
            System.out.println("1. Find Available Rooms");
            System.out.println("2. Check Birthday Availability");
            System.out.println("3. Generate Loyalty Report");
            System.out.println("4. Generate Monthly Performance Report");
            System.out.println("5. Exit");

            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice)
            {
                case 1:
                    System.out.print("Enter group size: ");
                    int groupSize = Integer.parseInt(sc.nextLine());

                    System.out.print("Enter date (YYYY-MM-DD): ");
                    String date = sc.nextLine();

                    dal.GetAvailableRooms(databaseName, username, password, groupSize, date);
                    break;

                case 2:
                    System.out.print("Enter birthday date (YYYY-MM-DD): ");
                    String birthdayDate = sc.nextLine();

                    dal.GetBirthdayAvailability(databaseName, username, password, birthdayDate);
                    break;

                case 3:
                    dal.GenerateLoyaltyReport(databaseName, username, password);
                    break;

                case 4:
                    System.out.print("Enter month (1-12): ");
                    int month = Integer.parseInt(sc.nextLine());

                    dal.MonthlyPerformanceReport(databaseName, username, password, month);
                    break;

                case 5:
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid option.");
                    break; 
                    
                
            }
            sc.close();
        }
    }
   
}
