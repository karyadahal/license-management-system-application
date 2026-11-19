
    class LicenseApplication {
        String applicationId;
        String userId;
        String requestedCategory;
        String status; // "Pending", "Approved", "Rejected"

        public LicenseApplication(String applicationId, String userId, String requestedCategory) {
            this.applicationId = applicationId;
            this.userId = userId;
            this.requestedCategory = requestedCategory;
            this.status = "Pending";
        }
    }

