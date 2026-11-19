import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class License {
        String licenseId;
        String category;
        String issueDate;
        String expiryDate;

        public License(String licenseId, String category, String issueDate, String expiryDate) {
            this.licenseId = licenseId;
            this.category = category;
            this.issueDate = issueDate;
            this.expiryDate = expiryDate;
        }


    }

