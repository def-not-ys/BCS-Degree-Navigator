package model;

import java.util.*;


// Represents a course having course name, course ID, course credit, faculty that offers this course
// Course can query and return the list of pre-reqs and dependents for this course

public class Course {

    private String courseName;            // "Computation, Programs, and Programming"
    private String courseNum;             // 110
    private String faculty;               // "CPSC"
    private String courseId;              // "CPSC 110" (faculty + courseId)
    private String courseDescription;     // (see description on course website)
    private int courseCredit;             // 4

    private Set<String> preReqs;         // []
    private Set<String> dependents;      // []
    private Set<String> equivalency;      // []
    private Set<String> keywords;  // "functional programming", "systematic programming design", "Racket"

    private int courseStatus;    // status of the course: 0:not taken 1: course in progress 3: completed
    private boolean isMandatory; // true -> this course is mandatory
    private boolean isExempted; // true -> this course is exempted

    // constructs a course with basic fields
    public Course(String name, String num, String faculty, String description, int credit) {
        this();

        courseName = name;
        courseNum = num;
        this.faculty = faculty;
        courseCredit = credit;
        courseDescription = description;
        courseId = faculty + " " + courseNum;
        keywords = new HashSet<>();
    }

    // constructs the pre-req list and dependent list for this course
    public Course()  {
        preReqs = new HashSet<>();
        equivalency = new HashSet<>();
        dependents = new HashSet<>();
        courseStatus = 0;
        isMandatory = false;
        isExempted = false;
    }

    // REQUIRES: course status must be 0, 1, or 2
    // EFFECTS: returns the status of the course
    public String printCourseStatus() {
        String str = "";
        switch (courseStatus) {
            case 0:
                str = "Not Completed";
                break;
            case 1:
                str = "Course in Progress";
                break;
            case 2:
                str = "Completed";
                break;
            default:
                str = "Status not found";
        }
        return str;
    }

    // REQUIRES: valid status parameter (0, 1, or 2)
    // MODIFIES: this
    // EFFECTS: sets the taken status of the course
    public void setCourseStatus(int status) {
        courseStatus = status;
    }

    // MODIFIES: this
    // EFFECTS: sets the mandatory status of the course
    public void setMandatoryStatus(boolean b) {
        isMandatory = b;
    }

    // MODIFIES: this
    // EFFECTS: sets the exemption status of the course
    public void setExemptionStatus(boolean b) {
        isExempted = b;
    }

    // MODIFIES: this
    // EFFECTS: sets the description of the course
    public void setCourseDescription(String description) {
        courseDescription = description;
    }

    // MODIFIES: this
    // EFFECTS: sets the prerequisites of the course
    public void setPreReqs(Set<String> list) {
        preReqs.addAll(list);
    }

    // MODIFIES: this
    // EFFECTS: sets the equivalency of the course
    public void setEquivalency(Set<String> list) {
        equivalency.addAll(list);
    }

    // MODIFIES: this
    // EFFECTS: sets the equivalency of the course
    public void setDependents(Set<String> list) {
        dependents.addAll(list); //stub
    }

    // MODIFIES: this
    // EFFECTS: add keywords for this course
    public void setKeywords(String keyword) {
        keywords.add(keyword);
    }

    // EFFECTS: returns the id for this course
    public String getCourseId() {
        return courseId;
    }

    // EFFECTS: returns the name for this course
    public String getCourseName() {
        return courseName;
    }

    // EFFECTS: returns the credit for this course
    public int getCourseCredit() {
        return courseCredit;
    }

    // EFFECTS: returns true this course is mandatory
    public boolean getIsMandatory() {
        return isMandatory;
    }

    // EFFECTS: returns true this course is exempted
    public boolean getIsExempted() {
        return isExempted;
    }

    // EFFECTS: returns course detail in formatted string
    public String courseToText() {
        String ids = "Course ID: " + courseId + " credit: " + courseCredit + "\nCourse name: " + courseName;
        String mandt;
        String havetk;
        if (isMandatory) {
            mandt = "\nMandatory for BCS program.";
        } else {
            mandt = "\nNot mandatory for BCS program.";
        }
        if (courseStatus != 0) {
            havetk = "\nYou have taken this course.";
        } else {
            havetk = "\nYou have not taken this course.";
        }
        String text = ids + mandt + havetk + "\nCourse description: " + courseDescription;
        return text;
    }

    // EFFECTS: returns the description for this course
    public String getCourseDescription() {
        return courseDescription;
    }

    // EFFECTS: returns the keywords for this course
    public Set<String> getCourseKeywords() {
        return keywords;
    }

    // MODIFIES: this
    // EFFECTS: returns a list of pre-reqs courses for this course
    public Set<String> getPreReqs() {
        return preReqs;
    }

    // MODIFIES: this
    // EFFECTS: returns a list of equivalency courses for this course
    public Set<String> getEquivalency() {
        return equivalency;
    }

    // MODIFIES: this
    // EFFECTS: returns a list of courses depends on this course
    public Set<String> getDependents() {
        return dependents;
    }

    @Override
    public String toString() {
        return courseId;
    }
}
