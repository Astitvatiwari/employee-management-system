public class AuthService {
    /**
     * Handles authentication logic so it is separated from the UI (LoginGUI).
     * This makes it easy to explain in a Viva or replace with a Database/File check later.
     */
    public static boolean authenticate(String username, String password) {
        // Simple hardcoded check as per previous logic
        return "admin".equals(username) && "password123".equals(password);
    }
}
