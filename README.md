# Student Management System (Java)

A robust, console-based Java application for managing student records using persistent binary storage. This project focuses on high-performance file handling, data integrity, and professional input validation.

## 🚀 Key Features

- **Persistent Storage**: Utilizes Java Serialization (`.dat` files) to ensure data remains available across sessions.
- **Atomic File Updates**: Implements a "Temporary File Swap" approach for Update and Delete operations to prevent data corruption.
- **Advanced Analytics**: 
  - **Top Performer Logic**: An efficient $O(n)$ single-pass algorithm to find all students tied for the highest marks.
  - **Smart Sorting**: Uses Java Collections and Lambda expressions for $O(n \log n)$ sorting by performance.
- **Robust Input Validation**: Features a retry mechanism with buffer flushing to handle `InputMismatchException` and logical range validation (0-100 for marks).
- **Data Export**: Generates human-readable, buffered `.txt` reports in a standardized CSV-style format.
- **Aligned UI**: Uses `printf` formatting to ensure perfectly aligned tabular displays regardless of data length.

## 🛠️ Tech Stack

- **Language**: Java 8+
- **I/O**: `java.io` (Object Streams, Buffered Writers)
- **Data Structures**: `ArrayList`, `Collections` Framework
- **Tools**: `Scanner` with custom retry logic

## 📂 File Structure

- `Student.java`: The serializable model class.
- `FileHandler.java`: The core engine containing all CRUD and analytical logic.
- `Main.java`: The interactive CLI menu with input validation.
- `students.dat`: The binary data store (Auto-generated).
- `studentstext.txt`: The exported report (Generated on request).


## 📥 Installation & Usage

1. **Clone the repository**:
   ```bash
   git clone [https://github.com/your-username/StdMSys.git](https://github.com/your-username/StdMSys.git)

2. **Compile the project**:
   ```bash
   javac -d bin src/StdMSys/*.java

3. **Run the application**:
   ```bash
   java -cp bin StdMSys.Main
   
## 🧠 Technical Highlights
- `Efficiency`: The topper() method avoids the "Two-Pass" trap (reading the file twice). It updates a champion list in a single iteration, making it scalable for large datasets.

- `Resource Management`: Implements try-with-resources throughout the application to guarantee file closure and prevent memory leaks.
