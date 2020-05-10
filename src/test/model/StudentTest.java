package model;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static model.GradCheckList.MAXIMUM_ALLOWED_CREDITS;
import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    Student testStudent;

    String cName = "Test Course";
    String cNum = "101";
    String cFaculty = "TEST";
    String cDescrip = "Test course description";
    int cCredit = 3;
    String cps110Name = "Computation, Programs, and Programming";
    String cps110Description = "Fundamental program and computation structures. Introductory programming skills. " +
            "Computation as a tool for information processing, simulation and modelling, " +
            "and interacting with the world.";
    String engl112Name = "Strategies for University Writing";
    String engl112Description = "Through the study and application of the principles of university-level discourse " +
            "and with emphasis on the specific genre of academic writing, this course will introduce students to " +
            "critical reading and university-level writing. ";

    Course testC1;
    Course testC2;
    Course testC3;
    Course cpsc110;
    Course engl112;


    @BeforeEach
    public void runBefore() {
        testStudent = new Student("fn", "ln", "12345678", "BCS", "CPSC");
        testC1 = new Course(cName, cNum, cFaculty, cDescrip, cCredit);
        testC2 = new Course("t2", "cNum2", cFaculty, cDescrip, MAXIMUM_ALLOWED_CREDITS - 3);
        testC3 = new Course("t3", "cNum3", cFaculty, cDescrip, 1);
        cpsc110 = new Course(cps110Name, "110", "CPSC", cps110Description, 4);
        engl112 = new Course(engl112Name, "112", "ENGL", engl112Description, 3);
    }

    @Test
    public void testConstructor() {
        String testName = "fn ln";
        assertEquals(testName, testStudent.getStudentName());
        assertFalse(testStudent.getCanGraduate());
        assertEquals("12345678", testStudent.getStudentId());
    }

    @Test
    public void testAddTakenCourseOneSuccess() {
        assertEquals(0, testStudent.getCoursesTaken().size());
        assertTrue(testStudent.addTakenCourse(testC1));
        assertEquals(1, testStudent.getCoursesTaken().size());
        assertEquals(cCredit, testStudent.getCurrCredits());
    }

    @Test
    public void testAddTakenCourseOneMultipleSuccess() {
        assertEquals(0, testStudent.getCoursesTaken().size());
        assertTrue(testStudent.addTakenCourse(testC1));
        assertFalse(testStudent.addTakenCourse(testC1));
        assertFalse(testStudent.addTakenCourse(testC1));
        assertEquals(1, testStudent.getCoursesTaken().size());
        assertEquals(cCredit,testStudent.getCurrCredits());
    }

    @Test
    public void testAddTakenCourseMultipleSuccessBoundary() {
        assertEquals(0, testStudent.getCoursesTaken().size());
        assertTrue(testStudent.addTakenCourse(testC1));
        assertTrue(testStudent.addTakenCourse(testC2));
        assertEquals(2, testStudent.getCoursesTaken().size());
        assertEquals(testC1.getCourseCredit()+testC2.getCourseCredit(), testStudent.getCurrCredits());
    }

    @Test
    public void testAddTakenCourseMultipleFailExceedMaxCredit() {
        assertEquals(0, testStudent.getCoursesTaken().size());
        assertTrue(testStudent.addTakenCourse(cpsc110));
        assertFalse(testStudent.addTakenCourse(testC2));
        assertEquals(1, testStudent.getCoursesTaken().size());
        assertEquals(cpsc110.getCourseCredit(), testStudent.getCurrCredits());
    }

    @Test
    public void testAddTakenCourseLots() {
        assertEquals(0, testStudent.getCoursesTaken().size());
        testStudent.addTakenCourse(testC1);
        testStudent.addTakenCourse(cpsc110);
        assertEquals(2, testStudent.getCoursesTaken().size());
        assertEquals(cCredit + 4, testStudent.getCurrCredits());
    }

    @Test
    public void testDropTakenCourseOne() {
        testStudent.addTakenCourse(testC1);
        assertTrue(testStudent.dropTakenCourse(testC1));
        assertEquals(0, testStudent.getCoursesTaken().size());
        assertEquals(0, testStudent.getCurrCredits());
    }

    @Test
    public void testDropTakenCourseOneMultiple() {
        assertFalse(testStudent.dropTakenCourse(testC1));
        assertFalse(testStudent.dropTakenCourse(testC1));
        assertEquals(0, testStudent.getCoursesTaken().size());
        assertEquals(0, testStudent.getCurrCredits());
    }

    @Test
    public void testDropTakenCourseLots() {
        testStudent.addTakenCourse(testC1);
        testStudent.addTakenCourse(cpsc110);
        testStudent.addTakenCourse(engl112);
        testStudent.dropTakenCourse(testC1);
        testStudent.dropTakenCourse(engl112);
        assertEquals(1, testStudent.getCoursesTaken().size());
        assertEquals(4, testStudent.getCurrCredits());
    }

    @Test
    public void testAddExemptedCoursesOneSuccess() {
        assertEquals(0, testStudent.getCurrCredits());
        assertEquals(0, testStudent.getCoursesExempted().size());
        cpsc110.setExemptionStatus(true);
        assertTrue(testStudent.addExemptedCourse(cpsc110));
        assertEquals(0, testStudent.getCurrCredits());
        assertEquals(1, testStudent.getCoursesExempted().size());
    }

    @Test
    public void testAddExemptedCoursesOneFail() {
        assertEquals(0, testStudent.getCurrCredits());
        assertEquals(0, testStudent.getCoursesExempted().size());
        cpsc110.setExemptionStatus(false);
        assertFalse(testStudent.addExemptedCourse(cpsc110));
        assertEquals(0, testStudent.getCurrCredits());
        assertEquals(0, testStudent.getCoursesExempted().size());
    }

    @Test
    public void testAddExemptedCoursesLots() {
        assertEquals(0, testStudent.getCurrCredits());
        assertEquals(0, testStudent.getCoursesExempted().size());
        cpsc110.setExemptionStatus(true);
        engl112.setExemptionStatus(true);
        testStudent.addExemptedCourse(cpsc110);
        testStudent.addExemptedCourse(cpsc110);
        testStudent.addExemptedCourse(engl112);
        assertEquals(0, testStudent.getCurrCredits());
        assertEquals(2, testStudent.getCoursesExempted().size());
    }

    @Test
    public void testRemoveExemptedCoursesOneSuccess() {
        cpsc110.setExemptionStatus(true);
        testStudent.addExemptedCourse(cpsc110);
        testStudent.removeExemptedCourse(cpsc110);
        assertEquals(0, testStudent.getCurrCredits());
        assertEquals(0, testStudent.getCoursesExempted().size());
    }

    @Test
    public void testRemoveExemptedCoursesOneFail() {
        assertFalse(testStudent.removeExemptedCourse(cpsc110));
        assertEquals(0, testStudent.getCurrCredits());
        assertEquals(0, testStudent.getCoursesExempted().size());
    }

    @Test
    public void testUpdateCourseToTake() {
        HashMap reqcs = testStudent.getCoursesToTake();
        testStudent.addTakenCourse(cpsc110);
        testStudent.updateCourseToTake();
        assertEquals(reqcs.size()-1, testStudent.getCoursesToTake().size());
    }

    @Test
    public void testGraduateCheckFail() {
        testStudent.addTakenCourse(testC1);
        testStudent.addTakenCourse(cpsc110);
        testStudent.graduateCheck();
        assertFalse(testStudent.getCanGraduate());
    }

    @Test
    public void testGraduateCheckSuccess() {
        HashMap<String, Course> reqcs = testStudent.getCoursesToTake();
        reqcs.forEach((k,v) -> {
            testStudent.addTakenCourse(v);
        }
        );
        testStudent.graduateCheck();
        assertTrue(testStudent.getCanGraduate());
    }

    @Test
    public void testGraduateCheckFailExceedMaxCredit() {
        for(int i=0; i<22; i++) {
            testStudent.addTakenCourse(testC3);
        }
        testStudent.addTakenCourse(testC2);
        testStudent.graduateCheck();
        assertFalse(testStudent.getCanGraduate());
    }

    @Test
    public void testSave() {
        Users users = new Users(new HashMap<>());
        testStudent.save(users);
        assertEquals(1, users.getUsers().size());
        assertTrue(users.getUsers().containsKey(testStudent.getStudentId()));

        testStudent.save(users);
        assertEquals(1, users.getUsers().size());
        assertTrue(users.getUsers().containsKey(testStudent.getStudentId()));
    }

    @Test
    public void testStudentDetails() {
        testStudent.addTakenCourse(testC1);
        JSONObject json = testStudent.studentDetails();
        assertTrue(json.containsValue(testStudent.getStudentId()));
        List<String> list = (List<String>) json.get("Taken");
        assertEquals(testC1.getCourseId(), list.get(0));
    }

    @Test
    public void testToString() {
        String text = testStudent.getStudentId() + " " + testStudent.getStudentName();
        assertEquals(text, testStudent.toString());
    }
}
