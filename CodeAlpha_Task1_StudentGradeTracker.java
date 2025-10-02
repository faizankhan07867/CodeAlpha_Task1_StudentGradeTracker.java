import java.util.*;

public class StudentGradeTracker {
    static class Student {
        String id;
        String name;
        List<Integer> scores = new ArrayList<>();

        Student(String id, String name) {
            this.id = id;
            this.name = name;
        }
        void addScore(int s) { scores.add(s); }
        double getAverage() {
            if (scores.isEmpty()) return 0.0;
            int sum = 0;
            for (int v : scores) sum += v;
            return sum / (double) scores.size();
        }
        int getMax() {
            if (scores.isEmpty()) return 0;
            int m = Integer.MIN_VALUE;
            for (int v : scores) if (v > m) m = v;
            return m;
        }
        int getMin() {
            if (scores.isEmpty()) return 0;
            int m = Integer.MAX_VALUE;
            for (int v : scores) if (v < m) m = v;
            return m;
        }
        @Override
        public String toString() {
            return String.format("ID:%s Name:%s Scores:%s Avg:%.2f Max:%d Min:%d",
                    id, name, scores, getAverage(), getMax(), getMin());
        }
    }

    static Scanner sc = new Scanner(System.in);
    static List<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Student Grade Tracker ---");
            System.out.println("1. Add new student");
            System.out.println("2. Add scores to student");
            System.out.println("3. Show student report");
            System.out.println("4. Show summary of all students");
            System.out.println("5. Remove student");
            System.out.println("6. Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": addNewStudent(); break;
                case "2": addScores(); break;
                case "3": showStudentReport(); break;
                case "4": showSummary(); break;
                case "5": removeStudent(); break;
                case "6": System.out.println("Exiting..."); return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    static void addNewStudent() {
        System.out.print("Enter student ID: ");
        String id = sc.nextLine().trim();
        System.out.print("Enter student name: ");
        String name = sc.nextLine().trim();
        Student s = new Student(id, name);
        students.add(s);
        System.out.println("Student added.");
    }

    static Student findById(String id) {
        for (Student s : students) if (s.id.equals(id)) return s;
        return null;
    }

    static void addScores() {
        System.out.print("Enter student ID: ");
        String id = sc.nextLine().trim();
        Student s = findById(id);
        if (s == null) { System.out.println("Student not found."); return; }
        System.out.print("How many scores to add? ");
        int n = Integer.parseInt(sc.nextLine().trim());
        for (int i = 0; i < n; i++) {
            System.out.printf("Score %d: ", i+1);
            int val = Integer.parseInt(sc.nextLine().trim());
            if (val < 0) { System.out.println("Score must be non-negative. Skipping."); continue; }
            s.addScore(val);
        }
        System.out.println("Scores added.");
    }

    static void showStudentReport() {
        System.out.print("Enter student ID: ");
        String id = sc.nextLine().trim();
        Student s = findById(id);
        if (s == null) { System.out.println("Student not found."); return; }
        System.out.println(s);
    }

    static void showSummary() {
        if (students.isEmpty()) { System.out.println("No students yet."); return; }
        System.out.println("\nAll students summary:");
        for (Student s : students) {
            System.out.println(s);
        }
        // Overall highest & lowest across all students
        double highestAvg = Double.MIN_VALUE; Student top = null;
        double lowestAvg = Double.MAX_VALUE; Student low = null;
        for (Student s : students) {
            double avg = s.getAverage();
            if (avg > highestAvg) { highestAvg = avg; top = s; }
            if (avg < lowestAvg) { lowestAvg = avg; low = s; }
        }
        if (top != null) System.out.printf("Top student: %s (Avg=%.2f)%n", top.name, highestAvg);
        if (low != null) System.out.printf("Lowest student: %s (Avg=%.2f)%n", low.name, lowestAvg);
    }

    static void removeStudent() {
        System.out.print("Enter student ID to remove: ");
        String id = sc.nextLine().trim();
        Student s = findById(id);
        if (s == null) { System.out.println("Student not found."); return; }
        students.remove(s);
        System.out.println("Student removed.");
    }
}
