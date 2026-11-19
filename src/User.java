import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class User {
    String userId;
    String name;
    String role; // "user" or "admin"
    String email;
    String citizenshipNumber;
    String password;
    String birthDate;

    public User(String userId, String name, String role, String email, String citizenshipNumber, String password, String birthDate) {
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.email = email;
        this.citizenshipNumber = citizenshipNumber;
        this.password = password;
        this.birthDate = birthDate;

    }

    public int getAge() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDate = LocalDate.parse(this.birthDate, formatter);
        LocalDate today= LocalDate.now();
        return Period.between(birthDate, today).getYears();
    }
}
//making subclass admin inherating property of class User
class Admin extends User {
    public Admin(String userId, String name, String email, String citizenshipNumber, String password,String birthDate) {
        super(userId, name, "admin", email, citizenshipNumber, password,birthDate);
    }

public void approveApplication(LicenseApplication application) {
    application.status = "Approved";
    System.out.println("Application " + application.applicationId + " approved.");
    System.out.println("License issued.");
}

public void rejectApplication(LicenseApplication application) {
    application.status = "Rejected";
    System.out.println("Application " + application.applicationId + " rejected.");
}

public void generateReport(List<LicenseApplication> Applications) {
    int approved = 0, pending = 0, rejected = 0;
    for (LicenseApplication app : Applications) {
        if (app.status.equals("Approved")) approved++;
        else if (app.status.equals("Pending")) pending++;
        else if (app.status.equals("Rejected")) rejected++;
    }
    System.out.println("\n=== Report ===");
    System.out.println("Approved Applications: " + approved);
    System.out.println("Pending Applications: " + pending);
    System.out.println("Rejected Applications: " + rejected);
}
}

//making subclass Normaluser inherating property of class User
class NormalUser extends User {
    public NormalUser(String userId, String name, String email, String citizenshipNumber, String password,String birthDate) {
        super(userId, name, "user", email, citizenshipNumber, password,birthDate);//super usage base method in project files
    }

    public void applyForLicense(String category, List<LicenseApplication> applications) {
        LicenseApplication application = new LicenseApplication(UUID.randomUUID().toString(), this.userId, category);//UUID- used for importing behaviour of LicenseApplication class
        applications.add(application);
        System.out.println("Application submitted for category " + category + ".");
        System.out.println("Status:"+application.status);
    }

    public void checkStatus(List<LicenseApplication> applications) {
        System.out.println("\n=== Application Status ===");
        for (LicenseApplication app : applications) {
            if (app.userId.equals(this.userId)) {
                System.out.println("Application ID: " + app.applicationId + ", Category: " + app.requestedCategory + ", Status: " + app.status);
            }
        }
    }
}



