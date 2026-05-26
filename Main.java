import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GradeManager manager = new GradeManager();
        FileHandler fileHandler = new FileHandler();
        Scanner sc = new Scanner(System.in);

        manager.setStudents(fileHandler.load());
        System.out.println("=== Student Grade Management System ===");

        while (true) {
            System.out.println("\n1. Add Student");
            System.out.println("2. Update Grade");
            System.out.println("3. Delete Student");
            System.out.println("4. View All Students");
            System.out.println("5. View Statistics");
            System.out.println("6. Sort by Grade");
            System.out.println("7. Save & Exit");
            System.out.print("Choose: ");

            try {
                int choice = Integer.parseInt(sc.nextLine().trim());
                switch (choice) {
                    case 1 -> {
                        System.out.print("Name: ");
                        String name = sc.nextLine();
                        System.out.print("Grade: ");
                        double grade = Double.parseDouble(sc.nextLine().trim());
                        manager.addStudent(name, grade);
                    }
                    case 2 -> {
                        System.out.print("Student ID: ");
                        int id = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("New Grade: ");
                        double g = Double.parseDouble(sc.nextLine().trim());
                        manager.updateGrade(id, g);
                    }
                    case 3 -> {
                        System.out.print("Student ID to delete: ");
                        manager.deleteStudent(Integer.parseInt(sc.nextLine().trim()));
                    }
                    case 4 -> manager.displayAll();
                    case 5 -> manager.displayStats();
                    case 6 -> manager.sortByGrade();
                    case 7 -> {
                        fileHandler.save(manager.getStudents());
                        System.out.println("Goodbye!");
                        sc.close();
                        return;
                    }
                    default -> System.out.println("Invalid option.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input format. Please ensure numerical items are typed cleanly.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}