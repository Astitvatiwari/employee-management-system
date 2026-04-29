package employee.management.system;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileEmployeeRepository implements EmployeeRepository {
    private final Path filePath;

    public FileEmployeeRepository(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Employee> loadEmployees() {
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath.toFile()))) {
            Object loaded = inputStream.readObject();
            if (loaded instanceof List<?> rawList) {
                List<Employee> employees = new ArrayList<>();
                for (Object item : rawList) {
                    if (item instanceof Employee employee) {
                        employees.add(employee);
                    }
                }
                return employees;
            }
            throw new IllegalStateException("Saved employee data is not in the expected format.");
        } catch (IOException | ClassNotFoundException exception) {
            throw new IllegalStateException("Unable to load employee data from " + filePath, exception);
        }
    }

    @Override
    public void saveEmployees(List<Employee> employees) {
        try {
            Path parent = filePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath.toFile()))) {
                outputStream.writeObject(new ArrayList<>(employees));
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to save employee data to " + filePath, exception);
        }
    }
}
