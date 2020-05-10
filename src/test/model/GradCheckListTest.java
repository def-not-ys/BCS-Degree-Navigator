package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class GradCheckListTest {

    GradCheckList testGradCheckList;
    String testProgram = "BCS";

    Student testStu;

    String cName = "Test Course";
    String cNum = "101";
    String cFaculty = "TEST";
    String cDescrip = "Test course description";
    int cCredit = 3;
    String cps110Name = "Computation, Programs, and Programming";
    String cps110Description = "Fundamental program and computation structures. Introductory programming skills. " +
            "Computation as a tool for information processing, simulation and modelling, " +
            "and interacting with the world.";

    Course testC1;
    Course cpsc110;

    @BeforeEach
    public void runBefore() {
        testGradCheckList = GradCheckList.getInstance();
        testStu = new Student("fn", "ln", "12345678", "BCS", "CPSC");
        testC1 = new Course(cName, cNum, cFaculty, cDescrip, cCredit);
        cpsc110 = new Course(cps110Name, "110", "CPSC", cps110Description, 4);
        testGradCheckList.reset();
    }

    @Test
    public void testConstructor() {
        String exptPg = testGradCheckList.getProgramName();
        assertEquals(exptPg, testProgram);

        assertEquals(13, testGradCheckList.getMandatoryCourses().size());
        assertEquals(8, testGradCheckList.getElectiveCourses().size());
        assertEquals(2, testGradCheckList.getExemptionList().size());
    }

    @Test
    public void testCourseToTakeAll() {

        Course testC1 = new Course(cName, cNum, cFaculty, cDescrip, cCredit);
        HashMap<String, Course> testCsTaken = new HashMap<>();
        testCsTaken.put(testC1.getCourseId(), testC1);
        HashMap<String, Course> testCtt = testGradCheckList.coursesToTake(testCsTaken);
        assertEquals(testCtt, testGradCheckList.getRequiredCourses());
    }

    @Test
    public void testGraduationCheckFail() {
        testStu.addTakenCourse(testC1);
        assertFalse(testGradCheckList.graduationCheck(testStu));
    }

    @Test
    public void testGraduationCheckFailEnoughCourses() {
        for (int i = 0; i<25; i++) {
            Course c = new Course(cNum, Integer.toString(i), cFaculty, cDescrip, cCredit);
            testStu.addTakenCourse(c);
        }
        assertFalse(testGradCheckList.graduationCheck(testStu));
    }

    @Test
    public void testGraduationCheckSuccess() {

       HashMap<String, Course> requires = testGradCheckList.getRequiredCourses();
       for (String key: requires.keySet()) {
           Course c = requires.get(key);
           testStu.addTakenCourse(c);
       }
       assertTrue(testGradCheckList.graduationCheck(testStu));
    }

    @Test
    public void testCourseToTakeSome() {
        HashMap<String, Course> testCsTaken = new HashMap<>();
        testCsTaken.put(testC1.getCourseId(), testC1);
        testCsTaken.put(cpsc110.getCourseId(), cpsc110);
        HashMap<String, Course> testCtt = testGradCheckList.coursesToTake(testCsTaken);
        HashMap<String, Course> exptCtt = testGradCheckList.getRequiredCourses();
        exptCtt.remove(cpsc110.getCourseId());
        assertEquals(testCtt, exptCtt);
    }
}
