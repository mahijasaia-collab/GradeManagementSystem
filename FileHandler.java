import java.io.*;
import java.util.*;

public class FileHandler {
    private static final String FILE = "students.txt";

    public void save(List<Student> students) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE))) {
            for (Student s : students) {
                bw.write(s.getId() + "," + s.getName() + "," + s.getGrade());
                bw.newLine();
            }
            System.out.println("Data saved to " + FILE);
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }

    public List<Student> load() {
        List<Student> students = new ArrayList<>();
        File f = new File(FILE);
        if (!f.exists()) return students;
        
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // Skip accidental trailing line breaks
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    students.add(new Student(Integer.parseInt(parts[0].trim()),
                            parts[1].trim(), Double.parseDouble(parts[2].trim())));
                }
            }
            System.out.println("Data loaded from " + FILE);
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading: " + e.getMessage());
        }
        return students;
    }
}