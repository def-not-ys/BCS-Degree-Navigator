package model;

import ui.coursedata.CourseDataReader;
import ui.coursedata.DataPath;
import ui.coursedata.ListReader;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

// represents available faculties with a list of courses offered by each faculty

public class Faculty {

    // applying Singleton pattern
    private static Faculty faculty;

    private HashMap<String, Course> allCourses;
    private HashMap<String, HashMap<String, Course>>  coursesByFaculty;
    private String[] facultyList;

    // constructs a faculty object
    private Faculty() {
        String facultyName = "ELEC,MATH,CPEN,CPSC,STAT,ENGL";
        facultyList = facultyName.split(",");
        try {
            constructCourseList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // MODIFIES: this
    // EFFECTS: gets instance of Faculty, creates it if it doesn't already exist
    // (Singleton Design Pattern)
    public static Faculty getInstance() {
        if (faculty == null) {
            faculty = new Faculty();
        }
        return faculty;
    }

    // MODIFIES: this
    // EFFECTS: construct the faculty course category
    private void constructCourseList() throws IOException {
        allCourses = new HashMap<>();
        coursesByFaculty = new HashMap<>();
        for (String fn : facultyList) {
            DataPath dataPath = new DataPath();
            String path = dataPath.findPath(fn);
            HashMap<String, Course> tempCourses;
            if (fn.equals("ELEC")) {
                tempCourses = setElectiveCourses(path);
            } else {
                tempCourses = constructFacultyCourses(path);
            }
            allCourses.putAll(tempCourses);
            coursesByFaculty.put(fn, tempCourses);
        }
    }

    // REQUIRES: course must be in available faculties
    // EFFECTS: returns the course of the given course id
    public Course findCourse(String courseid) {
        Course course = getAllCourses().get(courseid);
        return course;
    }

    // EFFECTS: returns the course of the given course id
    public HashMap<String, Course> getAllCourses() {
        return allCourses;
    }

    // EFFECTS: returns the course list of the given faculty
    public HashMap<String, Course> getCoursesByFaculty(String fn) {
        return coursesByFaculty.get(fn);
    }

    // EFFECTS: returns the faculty list
    public String[] getFacultyList() {
        return facultyList;
    }

    // REQUIRES: Faculty is one of CPSC, MATH, STAT, CPEN, ENGL
    // MODIFIES: this
    // EFFECTS: returns the list of courses offered by this faculty
    private HashMap<String, Course> constructFacultyCourses(String path) throws IOException {
        CourseDataReader courseDataReader = new CourseDataReader(path);
        return courseDataReader.getCourses();
    }

    // EFFECTS: returns elective courses extracted from given text file
    private HashMap<String, Course> setElectiveCourses(String path) throws IOException {
        ListReader listReader = new ListReader();
        LinkedList<String> list = listReader.getList(path);
        HashMap<String, Course>  electives = new HashMap<>();
        for (String line: list) {
            String[] words = line.split(" ");
            Course elec = new Course(words[0], words[1], words[2], words[3], Integer.parseInt(words[4]));
            String key = words[2] + " " + words[1];
            electives.put(key, elec);
        }
        return electives;
    }
}