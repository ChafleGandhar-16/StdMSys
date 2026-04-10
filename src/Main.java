import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Student implements Serializable {
    private int ID;
    private String name;
    private double marks;
    public Student(int ID, String name, double marks) {
        this.ID = ID;
        this.name = name;
        this.marks = marks;
    }
    void setID(int ID) {
        this.ID = ID;
    }
    void setName(String name) {
        this.name = name;
    }
    void setMarks(double marks) {
        this.marks = marks;
    }
    int getID() {
        return ID;
    }
    String getName() {
        return name;
    }
    double getMarks() {
        return marks;
    }
}

class FileHandler {

    FileHandler() throws Exception {}

    boolean addStudent(Student student) throws Exception { //✅✅✅✅✅✅
        if (searchStudent(student.getID()) != null) {
            return false;
        }
        ArrayList<Student> students = new ArrayList<>();
        File f = new File("students.dat");
        if (f.exists()) {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("students.dat"));
            Student s;

            while (true) {
                try {
                    students.add((Student) ois.readObject());
                } catch (EOFException e) {
                    break;
                }
            }
            ois.close();
        }
        students.add(student);
        ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream("students.dat"));
        for (int i = 0; i<students.size(); i++) {
            oos.writeObject(students.get(i));
        }
        oos.close();
        return true;
    }

    void displayStudents() throws Exception { //✅✅✅✅✅✅
        File f = new File("students.dat");
        if (!f.exists()) {
            System.out.println("No students found");
            return;
        }
        System.out.printf("%-5s %-20s %-10s%n", "ID", "Name", "Marks");
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("students.dat"))) {
            while (true) {
                Student s = (Student) ois.readObject();
                System.out.printf("%-5d %-20s %-10.2f%n", s.getID(), s.getName(), s.getMarks());
            }
        } catch(EOFException e) {}
    }

    Student searchStudent(int ID) throws Exception { //✅✅✅✅✅✅
        File f = new File("students.dat");
        if (!f.exists()) {
            return null;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("students.dat"))) {
            while (true) {
                Student s = (Student) ois.readObject();
                if (s.getID() == ID) {
                    return s;
                }
            }
        } catch (EOFException e) {
            return null;
        }
    }

    boolean updateStudent (Student s) throws Exception { //✅✅✅✅✅✅
        File f = new File("students.dat");
        if (!f.exists()) {
            return false;
        }
        boolean flag = false;

        try (ObjectOutputStream tempoos = new ObjectOutputStream(new FileOutputStream("temp.dat"));
             ObjectInputStream ois = new ObjectInputStream(new FileInputStream("students.dat"))) {
            while (true) {
                Student student = (Student) ois.readObject();
                if (student.getID() == s.getID()) {
                    tempoos.writeObject(s);
                    flag = true;
                } else {
                    tempoos.writeObject(student);
                }
            }
        } catch (EOFException e) {
            File tempf = new File("temp.dat");
            if (flag) {
                f.delete();
                tempf.renameTo(f);
            } else {
                tempf.delete();
            }
        }

        return flag;
    }

    boolean deleteStudent (int ID) throws Exception { //✅✅✅✅✅✅
        File f = new File("students.dat");
        if (!f.exists()) {
            return false;
        }
        boolean flag = false;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("students.dat"));
             ObjectOutputStream tempoos = new ObjectOutputStream(new FileOutputStream("temp.dat"))) {
            while (true) {
                Student s = (Student) ois.readObject();
                if (s.getID() != ID) {
                    tempoos.writeObject(s);
                } else {
                    flag = true;
                }
            }
        } catch (EOFException e) {
            File tempf = new File("temp.dat");
            if (flag) {
                f.delete();
                tempf.renameTo(f);
            } else {
                tempf.delete();
            }
        }
        return flag;
    }

    ArrayList<Student> sortByMarks() throws Exception { //✅✅✅✅✅✅
        File f = new File("students.dat");
        if (!f.exists()) return null;
        ArrayList<Student> students = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("students.dat"))) {
            while (true) {
                Student s = (Student) ois.readObject();
                students.add(s);
            }
        } catch (EOFException e) {
            if (students.isEmpty()) return students;
        }

        Collections.sort(students, (s1, s2) -> Double.compare(s2.getMarks(), s1.getMarks()));
        return students;
    }

    ArrayList<Student> topper() throws Exception { //✅✅✅✅✅✅
        File f = new File("students.dat");
        if (!f.exists()) return null;
        double maxMarks = -1;
        ArrayList<Student> toppers = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("students.dat"))) {
            while (true) {
                Student s = (Student) ois.readObject();
                if (s.getMarks() > maxMarks) {
                    maxMarks =  s.getMarks();
                    toppers.clear();
                    toppers.add(s);
                } else if (s.getMarks() == maxMarks) {
                    toppers.add(s);
                }
            }
        }catch (EOFException e) {}
        return toppers;
    }

    int countOfStudents() throws Exception { //✅✅✅✅✅✅
        File f = new File("students.dat");
        if (!f.exists()) return 0;
        int count = 0;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("students.dat"))) {
            while (true) {
                ois.readObject();
                count++;
            }
        } catch (EOFException e) {}
        return count;
    }

    boolean exportTotxt() throws Exception { //✅✅✅✅✅✅
        File f = new File("students.dat");
        if (!f.exists()) return false;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("students.dat")); BufferedWriter bw = new BufferedWriter(new FileWriter("studentstext.txt"))) {
            while (true) {
                Student s = (Student) ois.readObject();
                bw.write(s.getID() + "," + s.getName() + "," + s.getMarks());
                bw.newLine();
            }
        } catch (EOFException e) {}
        return true;
    }

    double readMarks(Scanner in) {
        double marks;
        while (true) {
            try {
                marks = in.nextDouble();
                if (marks <= 100 && marks >= 0) {
                    return marks;
                }
                System.out.println("Invalid Marks! Please try again.");
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid Marks! Please try again.");
                in.nextLine();
            }
        }
    }

    String readName(Scanner in) {
        String name;
        boolean flag = false;
        in.nextLine();
        while (true) {
            try {
                name = in.nextLine();
                flag = false;
                for (int i = 0; name.length() > i; i++) {
                    if (Character.isDigit(name.charAt(i))) {
                        System.out.println("Invalid Name! Please try again.");
                        flag = true;
                        break;
                    }
                }
                if (!flag && !name.trim().isEmpty()) return name;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid Name! Please try again.");
            }
        }
    }

    int readID(Scanner in) {
        int ID;
        while (true) {
            try {
                ID = in.nextInt();
                if (ID < 0)  {
                    System.out.println("Invalid ID! Please try again.");
                } else {
                    return ID;
                }
            } catch (java.util.InputMismatchException e) {
                in.nextLine();
                System.out.println("Invalid ID! Please try again.");
            }
        }
    }

}

public class Main {
    static void choices() {
        System.out.println("\n1. Add student");
        System.out.println("2. Display students");
        System.out.println("3. Search students");
        System.out.println("4. Update student");
        System.out.println("5. Delete student");
        System.out.println("6. Advanced Features");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
    }
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner ( System.in);
        int ch;
        int ID;
        double marks;
        String name;
        FileHandler f = new FileHandler();
        System.out.println("=====Welcome to Student Management System=====");
        while ( true ) {
            choices();
            switch (ch = in.nextInt()) {
                case 1:// Add Student
                    System.out.print("Enter Student ID: ");
                    ID =  f.readID(in);
                    System.out.print("Enter Student Name: ");
                    name = f.readName(in);
                    System.out.print("Enter Student Marks: ");
                    marks = f.readMarks(in);
                    Student s = new Student(ID, name, marks);
                    if (f.addStudent(s)) {
                        System.out.println("Student with ID " + s.getID() + " has been added");
                    } else {
                        System.out.println("Student with ID " + s.getID() + " already exists");
                    }
                    break;

                case 2:// View Student
                    f.displayStudents();
                    break;

                case 3:// Search Student by ID
                    System.out.print("Enter Student ID: ");
                    ID = f.readID(in);
                    Student s1 = f.searchStudent(ID);
                    if (s1 != null) {
                        System.out.printf("%-5s %-20s %-10s%n", "ID", "Name", "Marks");
                        System.out.printf("%-5d %-20s %-10.2f%n", s1.getID(), s1.getName(), s1.getMarks());
                    } else {
                        System.out.println("Student with ID " + ID + " does not exist");
                    }
                    break;

                case 4://Update Student
                    System.out.print("Enter Student ID: ");
                    ID = f.readID(in);
                    System.out.print("Enter updated student Name: ");
                    name = f.readName(in);
                    System.out.print("Enter updated student marks: ");
                    marks = f.readMarks(in);
                    Student s2 = new Student(ID, name, marks);
                    if (f.updateStudent(s2)) {
                        System.out.println("The  student with ID " + s2.getID() + " has been updated");
                    } else {
                        System.out.println("The student with ID " + s2.getID() + " has been failed to update");
                    }
                    break;

                case 5:// Delete Student
                    System.out.print("Enter Student ID: ");
                    ID = f.readID(in);
                    if (f.deleteStudent(ID)) {
                        System.out.println("Student with ID " + ID + " has been deleted");
                    } else {
                        System.out.println("The student with ID " + ID + " was not found");
                    }
                    break;

                case 6:// Advanced Search
                    int adv;
                    System.out.println("\n1. Sort the Students by Marks");
                    System.out.println("2. Display the topper");
                    System.out.println("3. Count total students");
                    System.out.println("4. Export data to txt file");
                    System.out.println("5. <-Back");
                    System.out.print("Enter your choice: ");
                    switch (adv = in.nextInt()) {
                        case 1:// Sort by marks
                            ArrayList<Student> students = new ArrayList<>();
                            students = f.sortByMarks();
                            System.out.printf("%-5s %-5s %-20s %-10s%n","Rank", "ID", "Name", "Marks");
                            for (int i = 0; i<students.size(); i++) {
                                System.out.printf("%-5s %-5s %-20s %-10s%n", (i+1), students.get(i).getID(), students.get(i).getName(), students.get(i).getMarks());
                            }
                            break;

                        case 2:// Topper display
                            ArrayList<Student> winners = f.topper();
                            if (winners == null || winners.isEmpty()) {
                                System.out.println("No records found.");
                            } else {
                                System.out.printf("%-5s %-20s %-10s%n", "ID", "Name", "Marks");
                                for (int i = 0; i<winners.size(); i++) {
                                    System.out.printf("%-5s %-20s %-10s%n", winners.get(i).getID(), winners.get(i).getName(), winners.get(i).getMarks());
                                }
                            }
                            break;

                        case 3:// Count total students
                            int count = f.countOfStudents();
                            if (count == 0) {
                                System.out.println("No records found.");
                                break;
                            }
                            System.out.println("Total number of students: " + count);
                            break;

                        case 4:// Export data to text file
                            if (f.exportTotxt()) {
                                System.out.println("The data has been exported successfully");
                            } else {
                                System.out.println("The data has been failed to export");
                            }
                            break;

                        case 5:
                            break;

                        default:
                            System.out.println("Invalid choice\nPlease try again");
                    }

                    break;

                case 7:// Exit
                    System.out.println("Exiting the Student Management System");
                    return;

                default:
                    System.out.println("Invalid choice");

            }
        }
    }
}