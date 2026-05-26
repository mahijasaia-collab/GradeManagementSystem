import java.util.*;
import java.util.stream.Collectors;

public class GradeManager {
    private List<Student> students = new ArrayList<>();
    private int nextId = 1;

    public void addStudent(String name, double grade) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Name cannot be empty.");
        if (grade < 0 || grade > 100)
            throw new IllegalArgumentException("Grade must be between 0 and 100.");
        students.add(new Student(nextId++, name.trim(), grade));
        System.out.println("Student added successfully.");
    }

    public void updateGrade(int id, double newGrade) {
        Student s = findById(id);
        if (newGrade < 0 || newGrade > 100)
            throw new IllegalArgumentException("Grade must be between 0 and 100.");
        s.setGrade(newGrade);
        System.out.println("Grade updated for: " + s.getName());
    }

    public void deleteStudent(int id) {
        Student s = findById(id);
        students.remove(s);
        System.out.println("Removed: " + s.getName());
    }

    public void displayAll() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        System.out.println("\n--- All Students ---");
        students.forEach(System.out::println);
    }

    public void displayStats() {
        if (students.isEmpty()) {
            System.out.println("No data available.");
            return;
        }
        double avg = students.stream().mapToDouble(Student::getGrade).average().orElse(0);
        double max = students.stream().mapToDouble(Student::getGrade).max().orElse(0);
        double min = students.stream().mapToDouble(Student::getGrade).min().orElse(0);
        Student topper = students.stream().max(Comparator.comparingDouble(Student::getGrade)).get();

        System.out.println("\n--- Class Statistics ---");
        System.out.printf("Average Grade : %.2f%n", avg);
        System.out.printf("Highest Grade : %.1f (%s)%n", max, topper.getName());
        System.out.printf("Lowest Grade  : %.1f%n", min);
        System.out.printf("Total Students: %d%n", students.size());

        System.out.println("\n--- Grade Distribution ---");
        Map<String, Long> dist = students.stream()
            .collect(Collectors.groupingBy(Student::getLetterGrade, Collectors.counting()));
        new TreeMap<>(dist).forEach((k, v) -> System.out.println("  " + k + " : " + v + " students"));
    }

    public void sortByGrade() {
        if (students.isEmpty()) {
            System.out.println("No students to sort.");
            return;
        }
        // Clone and sort to print without permanently changing database entry configurations
        List<Student> sortedList = new ArrayList<>(students);
        sortedList.sort(Comparator.comparingDouble(Student::getGrade).reversed());
        System.out.println("Sorted by grade (highest first):");
        sortedList.forEach(System.out::println);
    }

    public List<Student> getStudents() { return students; }
    
    public void setStudents(List<Student> loadedStudents) {
        // Create a new mutable array wrapper to prevent fixed-size list exceptions
        this.students = new ArrayList<>(loadedStudents);
        this.nextId = this.students.stream().mapToInt(Student::getId).max().orElse(0) + 1;
    }

    private Student findById(int id) {
        return students.stream().filter(s -> s.getId() == id)
                .findFirst().orElseThrow(() ->
                        new NoSuchElementException("Student with ID " + id + " not found."));
    }
}