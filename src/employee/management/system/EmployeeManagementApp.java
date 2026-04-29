package employee.management.system;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.nio.file.Path;

public class EmployeeManagementApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }

            Path dataFile = Path.of("data", "employees.ser");
            EmployeeRepository repository = new FileEmployeeRepository(dataFile);
            EmployeeService employeeService = new EmployeeService(repository);
            AuthService authService = new AuthService();

            LoginFrame loginFrame = new LoginFrame(authService, () -> {
                MainFrame mainFrame = new MainFrame(employeeService, dataFile);
                mainFrame.setVisible(true);
            });
            loginFrame.setVisible(true);
        });
    }
}
