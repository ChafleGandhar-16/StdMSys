import java.io.*;
import java.util.ArrayList;

public class DataSeeder {
    public static void main(String[] args) {
        String[] names = {
                "Aarav", "Aditi", "Advait", "Ananya", "Arjun", "Bhavya", "Chaitanya", "Devansh", "Diya", "Eesha",
                "Gautam", "Hriti", "Ishaan", "Jhanvi", "Kabir", "Kavya", "Kiara", "Laksh", "Meher", "Myra",
                "Nakul", "Navya", "Neev", "Niharika", "Ojas", "Pari", "Pranav", "Riya", "Rohan", "Saanvi",
                "Samarth", "Sara", "Shaurya", "Sia", "Siddharth", "Suhana", "Tanish", "Tara", "Uday", "Vanya",
                "Vedant", "Vihaan", "Vivaan", "Yuvraj", "Zoya", "Aaryan", "Ira", "Kiaan", "Prisha", "Reyansh"
        };

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("students.dat"))) {
            for (int i = 0; i < 50; i++) {
                int id = 150 + i; // IDs will be 100, 101, 102...
                String name = names[i];
                // Generates marks between 40.0 and 99.9
                double marks = (double) Math.round(50 + Math.random() * 35);

                oos.writeObject(new Student(id, name, marks));
            }
            System.out.println("Success! 50 dummy students added to students.dat.");
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}