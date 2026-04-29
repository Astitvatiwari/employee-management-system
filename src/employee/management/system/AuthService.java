package employee.management.system;

public class AuthService {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "password123";

    public boolean authenticate(String username, String password) {
        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }
}
