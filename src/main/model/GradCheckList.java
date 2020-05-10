package model;

import ui.coursedata.ListReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

// represents a check list of the graduation requirements for BCS program
// all requirements are listed on BCS graduation checklist: https://www.cs.ubc.ca/sites/default/files/shared/advising_checklist_bcs.jun_2017.pdf

public class GradCheckList {

    // apply singleton design pattern
    private static GradCheckList gradCheckList;

    public static final int TOTAL_NUM_OF_COURSES = 21;
    public static final int MAXIMUM_ALLOWED_CREDITS = 90;
    public static final String WARNING_MSG = "Maximum allowed credit exceeds! Please see adviser for details.";

    private String program;
    private HashMap requiredCourses;
    private HashMap mandatoryCourses;
    private HashMap electiveCourses;
    private HashMap exemptionList;


    // constructs a graduation check list
    private GradCheckList() {
        program = "BCS";
        try {
            mandatoryCourses = constructMandatoryCourses();
        } catch (IOException e) {
            e.printStackTrace();
        }
        electiveCourses = constructElectiveCourses();
        exemptionList = constructExemptions();
        requiredCourses = constructRequiredCourses();
    }

    // MODIFIES: this
    // EFFECTS: gets instance of GradCheckList, creates it if it doesn't already exist
    // (Singleton Design Pattern)
    public static GradCheckList getInstance() {
        if (gradCheckList == null) {
            gradCheckList = new GradCheckList();
        }

        return gradCheckList;
    }

    // MODIFIES: this
    // EFFECTS: resets the required courses to default
    public void reset() {
        gradCheckList = new GradCheckList();
    }

    // EFFECTS: returns true if student meets all graduation requirement, otherwise, returns false
    public boolean graduationCheck(Student student) {
        boolean b1 = student.getCoursesToTake().size() == 0;
        boolean b2 = student.getCoursesTaken().size() >= TOTAL_NUM_OF_COURSES;
        return b1 && b2;
    }

    // EFFECTS: returns a list of courses still yet to take
    public HashMap<String, Course> coursesToTake(HashMap<String, Course> courses) {
        HashMap<String, Course> coursesToTake = new HashMap();
        HashMap<String, Course> mandatories = (HashMap) mandatoryCourses.clone();
        HashMap<String, Course> electives = (HashMap) electiveCourses.clone();

        courses.forEach((k,v) -> {
            if (mandatories.containsKey(k)) {
                mandatories.remove(k);
            } else if (electives.containsKey(k)) {
                electives.remove(k);
            }
        }
        );
        coursesToTake.putAll(mandatories);
        coursesToTake.putAll(electives);

        return coursesToTake;
    }

    // EFFECTS: returns the name of the current program
    public String getProgramName() {
        return program;
    }

    // EFFECTS: returns a list of required courses for the program
    public HashMap<String, Course> getRequiredCourses() {
        return requiredCourses;
    }

    // EFFECTS: returns a list of mandatory courses for the program
    public HashMap<String, Course> getMandatoryCourses() {
        return mandatoryCourses;
    }

    // EFFECTS: returns a list of elective courses for the program
    public HashMap<String, Course> getElectiveCourses() {
        return electiveCourses;
    }

    // EFFECTS: returns the exemption list for the program
    public HashMap<String, Course> getExemptionList() {
        return exemptionList;
    }

    // EFFECTS: returns a list of mandatory courses for the checklist
    private HashMap<String, Course> constructMandatoryCourses() throws IOException {
        HashMap<String, Course> allCourses = Faculty.getInstance().getAllCourses();
        HashMap<String, Course> courses = new HashMap();
        ListReader listReader = new ListReader();
        String dir = "./data/";
        LinkedList<String> mcnames = listReader.getList(dir + "MandatoryCourseList.txt");
        for (String s: mcnames) {
            courses.put(s, allCourses.get(s));
        }
        return courses;
    }

    // EFFECTS: returns a list of elective courses for the checklist
    private HashMap<String, Course> constructElectiveCourses() {
        HashMap<String, Course> eleccourses = Faculty.getInstance().getCoursesByFaculty("ELEC");
        return eleccourses;
    }

    // EFFECTS:return a list of potential exemption courses
    private HashMap<String, Course> constructExemptions() {
        HashMap<String, Course> exptcourses = new HashMap();
        Course c = Faculty.getInstance().findCourse("ENGL 112");
        exptcourses.put("ENGL 112", c);
        Course test = new Course("EXMP 101", "101", "EXMP", "exempted", 3);
        exptcourses.put("EXMP 101", test);
        return exptcourses;
    }

    // EFFECTS:return a list of required courses
    private HashMap<String, Course> constructRequiredCourses() {
        HashMap<String, Course> exptcourses = new HashMap();
        exptcourses.putAll(mandatoryCourses);
        exptcourses.putAll(electiveCourses);
        return exptcourses;
    }
}
