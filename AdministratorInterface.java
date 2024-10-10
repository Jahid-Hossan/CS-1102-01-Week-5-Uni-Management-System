import java.util.*;

class Student {
    private String name;
    private int studentID;
    private List<Course> enrolledCourses;
    private Map<Course, Double> courseGrades;

    // Constructor
    public Student(String name, int studentID) {
        this.name = name;
        this.studentID = studentID;
        this.enrolledCourses = new ArrayList<>();
        this.courseGrades = new HashMap<>();
    }

    // Getter and Setter methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    // Update method for student information
    public void updateStudentInfo(String newName, int newID) {
        setName(newName);
        setStudentID(newID);
        System.out.println("Student information updated successfully.");
    }

    // Method to enroll in a course
    public void enrollInCourse(Course course) {
        if (enrolledCourses.contains(course)) {
            System.out.println("Student is already enrolled in this course.");
        } else if (course.getEnrolledStudentCount() < course.getMaxCapacity()) {
            enrolledCourses.add(course);
            course.incrementEnrolledStudentCount();
            System.out.println("Enrolled in course: " + course.getCourseName());
        } else {
            System.out.println("Cannot enroll. Course is full.");
        }
    }

    // Method to assign grade to a student for a course
    public void assignGrade(Course course, double grade) {
        if (enrolledCourses.contains(course)) {
            courseGrades.put(course, grade);
            System.out.println("Grade assigned for course: " + course.getCourseName());
        } else {
            System.out.println("Student is not enrolled in this course.");
        }
    }

    // Method to get grades
    public Map<Course, Double> getCourseGrades() {
        return courseGrades;
    }
}

class Course {
    private String courseCode;
    private String courseName;
    private int maxCapacity;
    private int enrolledStudentCount = 0;
    private static int totalEnrolledStudents = 0;

    // Constructor
    public Course(String courseCode, String courseName, int maxCapacity) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.maxCapacity = maxCapacity;
    }

    // Getter methods
    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getEnrolledStudentCount() {
        return enrolledStudentCount;
    }

    // Setter methods for course update functionality
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    // Method to update course details
    public void updateCourseDetails(String newCourseCode, String newCourseName, int newMaxCapacity) {
        setCourseCode(newCourseCode);
        setCourseName(newCourseName);
        setMaxCapacity(newMaxCapacity);
        System.out.println("Course details updated successfully.");
    }

    // Increment enrolled students count
    public void incrementEnrolledStudentCount() {
        if (enrolledStudentCount < maxCapacity) {
            enrolledStudentCount++;
            totalEnrolledStudents++;
        }
    }

    // Static method to get the total number of enrolled students across all courses
    public static int getTotalEnrolledStudents() {
        return totalEnrolledStudents;
    }
}

class CourseManagement {
    static List<Course> courses = new ArrayList<>();
    private static Map<Student, Map<Course, Double>> studentGrades = new HashMap<>();

    // Static method to add a new course
    public static void addCourse(String courseCode, String courseName, int maxCapacity) {
        Course course = new Course(courseCode, courseName, maxCapacity);
        courses.add(course);
        System.out.println("Course added: " + courseName);
    }

    // Method to update a course by its code
    public static void updateCourse(String courseCode, String newCode, String newName, int newMaxCapacity) {
        Course course = findCourseByCode(courseCode);
        if (course != null) {
            course.updateCourseDetails(newCode, newName, newMaxCapacity);
        } else {
            System.out.println("Course not found.");
        }
    }

    // Static method to remove a course by its code
    public static void removeCourse(String courseCode) {
        Course course = findCourseByCode(courseCode);
        if (course != null) {
            courses.remove(course);
            System.out.println("Course removed: " + course.getCourseName());
        } else {
            System.out.println("Course not found.");
        }
    }

    // Static method to enroll a student in a course
    public static void enrollStudent(Student student, Course course) {
        student.enrollInCourse(course);
    }

    // Static method to assign a grade to a student
    public static void assignGrade(Student student, Course course, double grade) {
        student.assignGrade(course, grade);
        studentGrades.putIfAbsent(student, student.getCourseGrades());
    }

    // Static method to calculate the overall course grade for a student
    public static double calculateOverallGrade(Student student) {
        Map<Course, Double> grades = student.getCourseGrades();
        double totalGrade = 0;
        int courseCount = grades.size();

        for (double grade : grades.values()) {
            totalGrade += grade;
        }

        return (courseCount > 0) ? totalGrade / courseCount : 0.0;
    }

    // Helper method to find a course by its code
    static Course findCourseByCode(String courseCode) {
        for (Course course : courses) {
            if (course.getCourseCode().equalsIgnoreCase(courseCode)) {
                return course;
            }
        }
        return null;
    }
}

public class AdministratorInterface {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Course Enrollment and Grade Management System ---");
            System.out.println("1. Add a new course");
            System.out.println("2. Update course details");
            System.out.println("3. Remove a course");
            System.out.println("4. Enroll student in a course");
            System.out.println("5. Assign grade to a student");
            System.out.println("6. Update student information");
            System.out.println("7. Calculate overall grade for a student");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter course code: ");
                    String code = scanner.nextLine();
                    System.out.print("Enter course name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter maximum capacity: ");
                    int capacity = scanner.nextInt();
                    CourseManagement.addCourse(code, name, capacity);
                    break;
                case 2:
                    System.out.print("Enter course code to update: ");
                    code = scanner.nextLine();
                    System.out.print("Enter new course code: ");
                    String newCode = scanner.nextLine();
                    System.out.print("Enter new course name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new maximum capacity: ");
                    int newCapacity = scanner.nextInt();
                    CourseManagement.updateCourse(code, newCode, newName, newCapacity);
                    break;
                case 3:
                    System.out.print("Enter course code to remove: ");
                    String removeCode = scanner.nextLine();
                    CourseManagement.removeCourse(removeCode);
                    break;
                case 4:
                    System.out.print("Enter student name: ");
                    String studentName = scanner.nextLine();
                    System.out.print("Enter student ID: ");
                    int studentID = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    Student student = new Student(studentName, studentID);

                    System.out.print("Enter course code for enrollment: ");
                    code = scanner.nextLine();
                    Course courseToEnroll = CourseManagement.findCourseByCode(code);
                    if (courseToEnroll != null) {
                        CourseManagement.enrollStudent(student, courseToEnroll);
                    } else {
                        System.out.println("Course not found.");
                    }
                    break;
                case 5:
                    System.out.print("Enter student name: ");
                    studentName = scanner.nextLine();
                    System.out.print("Enter student ID: ");
                    studentID = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    student = new Student(studentName, studentID);

                    System.out.print("Enter course code to assign grade: ");
                    code = scanner.nextLine();
                    System.out.print("Enter grade: ");
                    double grade = scanner.nextDouble();
                    courseToEnroll = CourseManagement.findCourseByCode(code);
                    if (courseToEnroll != null) {
                        CourseManagement.assignGrade(student, courseToEnroll, grade);
                    } else {
                        System.out.println("Course not found.");
                    }
                    break;
                case 6:
                    System.out.print("Enter student name: ");
                    studentName = scanner.nextLine();
                    System.out.print("Enter student ID: ");
                    studentID = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    student = new Student(studentName, studentID);

                    System.out.print("Enter new student name: ");
                    String newStudentName = scanner.nextLine();
                    System.out.print("Enter new student ID: ");
                    int newStudentID = scanner.nextInt();
                    student.updateStudentInfo(newStudentName, newStudentID);
                    break;
                case 7:
                    System.out.print("Enter student name: ");
                    studentName = scanner.nextLine();
                    System.out.print("Enter student ID: ");
                    studentID = scanner.nextInt();
                    student = new Student(studentName, studentID);
                    double overallGrade = CourseManagement.calculateOverallGrade(student);
                    System.out.println("Overall grade for " + student.getName() + ": " + overallGrade);
                    break;
                case 8:
                    exit = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
                    break;
            }
        }

        scanner.close();
    }
}
