package model;

import org.json.simple.JSONObject;
import persistence.Savable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static model.GradCheckList.MAXIMUM_ALLOWED_CREDITS;

// Represents a student name, student id, courses taken so far, credits earned so far,

public class Student implements Savable {

    private String stuFirstName;                    // "Firstname"
    private String stuLastName;                     // "Lastname"
    private String stuId;                           // "12345678"
    private String stuProgram;                      // "BCS"
    private String stuFaculty;                      // "CPSC"
    private HashMap<String, Course> coursesTaken;                   // []
    private HashMap<String, Course> courseExempted;                 // []
    private HashMap<String, Course> courseToTake;                   // []
    private int currCredits;                        // 0
    private boolean canGraduate;

    // constructs a student with name and student id, faculty (optional)
    public Student(String firstnm, String lastnm, String idnum, String progm, String faculty) {
        stuFirstName = firstnm;
        stuLastName = lastnm;
        stuId = idnum;
        stuProgram = progm;
        stuFaculty = faculty;
        canGraduate = false;
        courseToTake = GradCheckList.getInstance().getRequiredCourses();
        coursesTaken = new HashMap<>();
        courseExempted = new HashMap<>();
    }

    // MODIFIES: this
    // EFFECTS: returns true when successfully:
    //          adds a course to students' course taken list; increases student current credit accordingly
    //          otherwise returns false
    public boolean addTakenCourse(Course course) {
        if (course.getCourseCredit() <= (MAXIMUM_ALLOWED_CREDITS - currCredits)) {
            if (! coursesTaken.containsKey(course.getCourseId())) {
                coursesTaken.put(course.getCourseId(), course);
                currCredits = currCredits + course.getCourseCredit();
                course.setCourseStatus(2);
                updateCourseToTake();
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: returns true when successfully:
    //          removes a course from students' course taken list; decreases student current credit accordingly
    //          otherwise returns false
    public boolean dropTakenCourse(Course course) {
        if (coursesTaken.containsKey(course.getCourseId())) {
            coursesTaken.remove(course.getCourseId());
            currCredits = currCredits - course.getCourseCredit();
            updateCourseToTake();
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: returns true when successfully adds a valid exempted course to course exempted
    //          otherwise returns false
    public boolean addExemptedCourse(Course course) {
        if (course.getIsExempted()) {
            course.setExemptionStatus(true);
            courseExempted.put(course.getCourseId(), course);
            updateCourseToTake();
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: returns true when successfully removes an exempted course from course exempted
    //          otherwise returns false
    public boolean removeExemptedCourse(Course course) {
        if (courseExempted.containsKey(course.getCourseId())) {
            course.setExemptionStatus(false);
            courseExempted.remove(course.getCourseId());
            updateCourseToTake();
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: updates course to take for student
    public void updateCourseToTake() {
        courseToTake = GradCheckList.getInstance().coursesToTake(coursesTaken);
    }

    // MODIFIES: this
    // EFFECTS: set graduate status to true if the student meet all graduate requirements
    public void graduateCheck() {
        canGraduate = GradCheckList.getInstance().graduationCheck(this);
    }

    // EFFECTS: returns the student's name
    public String getStudentName() {
        // "Firstname Lastname"
        String stuName = stuFirstName + " " + stuLastName;
        return stuName;
    }

    // EFFECTS: returns the student's 8-digit student id
    public String getStudentId() {
        return stuId;
    }

    // EFFECTS: returns the student's current credits
    public int getCurrCredits() {
        return currCredits;
    }

    // EFFECTS: returns true if the student can graduate
    public boolean getCanGraduate() {
        return canGraduate;
    }

    // EFFECTS: returns a list of courses taken by the student
    public HashMap<String, Course> getCoursesTaken() {
        return coursesTaken;
    }

    // EFFECTS: returns a list of exempted courses for the student
    public HashMap<String, Course> getCoursesExempted() {
        return courseExempted;
    }

    // EFFECTS: returns a list of courses to take
    public HashMap<String, Course> getCoursesToTake() {
        return courseToTake;
    }

    // MODIFIES: users
    // EFFECTS:  updates the student if it is an existing user, otherwise, add student to users
    @Override
    public void save(Users users) {
        if (users.getUsers().containsKey(stuId)) {
            users.getUsers().replace(stuId, this);
        } else {
            users.addUser(this);
        }
    }

    @Override
    public String toString() {
        return stuId + " " + stuFirstName + " " + stuLastName;
    }

    public JSONObject studentDetails() {
        JSONObject student = new JSONObject();

        student.put("First name", stuFirstName);
        student.put("Last name", stuLastName);
        student.put("StudentID", stuId);
        student.put("Program", stuProgram);
        student.put("Faculty", stuFaculty);

        student.put("Taken", extractCourseId(coursesTaken.keySet()));
        student.put("Exempted", extractCourseId(courseExempted.keySet()));

        return student;
    }

    private List<String> extractCourseId(Set<String> keys) {
        List<String> list = new ArrayList<>();
        for (String k: keys) {
            list.add(k);
        }
        return list;
    }

    
}


