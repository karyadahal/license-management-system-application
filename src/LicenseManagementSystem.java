import java.util.*;

public class LicenseManagementSystem {
    private static Map<String, User> users = new HashMap<>();
    private static List<LicenseApplication> Applications = new ArrayList<>();
    private static User currentUser = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== License Management System ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registerUser(scanner);
                    break;
                case 2:
                    loginUser(scanner);
                    break;
                case 3:
                    System.out.println("Existing the system. Thank you.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);


    }

    // User Registration
    private static void registerUser(Scanner scanner) {
        System.out.println("\n=== User Registration ===");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your citizenship number: ");
        String citizenshipNumber = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        System.out.println("Enter your birth date(YYYY-MM-DD): ");
        String birthDate=scanner.nextLine();
        // Age Validation
        int age=new User("","","","","","",birthDate).getAge();
        if(age<18)
        {
            System.out.println("You must cross 18 years to registered. ");
            return;
        }
        System.out.print("Enter your role (user/admin): ");
        String role = scanner.nextLine();
        String userId = UUID.randomUUID().toString();
        User newUser;
        if (role.equalsIgnoreCase("admin")) {
            newUser = new Admin(userId, name, email, citizenshipNumber, password, birthDate);
        } else {
            newUser = new NormalUser(userId, name, email, citizenshipNumber, password, birthDate);
        }
        users.put(userId, newUser);

        if (users.containsKey(citizenshipNumber)) {
            System.out.println("User already registered!");
        } else {
            users.put(citizenshipNumber, newUser);
            System.out.println("Registration successful! Your user ID is: " + userId);
        }

    }

    // User Login
    private static void loginUser(Scanner scanner) {
        System.out.println("\n=== User Login ===");
        System.out.print("Enter your user ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (users.containsKey(userId) && users.get(userId).password.equals(password)) {
            currentUser = users.get(userId);
            System.out.println("Login successful! Welcome, " + currentUser.name + ".");
            if (currentUser.role.equals("user")) {
                userDashboard((NormalUser) currentUser, scanner);
            } else if (currentUser.role.equals("admin")) {
                adminDashboard((Admin) currentUser, scanner);
            }
        } else {
            System.out.println("Invalid user ID or password.");
        }
    }

    // Normal User Dashboard
    private static void userDashboard(NormalUser user, Scanner scanner) {
        int choice;

        do {
            System.out.println("\n=== User Dashboard ===");
            System.out.println("1. Apply for a New License");
            System.out.println("2. Check Application Status");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter the license category (A, B, C): ");
                    String category = scanner.nextLine();
                    user.applyForLicense(category, Applications);
                    break;
                case 2:
                    user.checkStatus(Applications);
                    break;
                case 3:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }

    // Admin Dashboard
    private static void adminDashboard(Admin admin, Scanner scanner) {
        int choice;

        do {
            System.out.println("\n=== Admin Dashboard ===");
            System.out.println("1. View All Applications");
            System.out.println("2. Approve/Reject Application");
            System.out.println("3. Generate Report");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewAllApplications();
                    break;
                case 2:
                    processApplication(admin, scanner);
                    break;
                case 3:
                    admin.generateReport(Applications);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    // View All Applications
    private static void viewAllApplications() {
        System.out.println("\n=== All Applications ===");
        for (LicenseApplication app : Applications) {
            System.out.println("Application ID: " + app.applicationId + ", User ID: " + app.userId + ", Category: " + app.requestedCategory + ", Status: " + app.status);
        }
    }

    // Process Application (Approve/Reject)
    private static void processApplication(Admin admin, Scanner scanner) {
        System.out.print("Enter the application ID to process: ");
        String applicationId = scanner.nextLine();
        for (LicenseApplication app : Applications) {
            if (app.applicationId.equals(applicationId)) {
                System.out.print("Enter action (approve/reject): ");
                String action = scanner.nextLine();
                if (action.equalsIgnoreCase("approve")) {
                    admin.approveApplication(app);
                } else if (action.equalsIgnoreCase("reject")) {
                    admin.rejectApplication(app);
                } else {
                    System.out.println("Invalid action.");
                }
                return;
            }
        }
        System.out.println("Application not found.");
    }

}