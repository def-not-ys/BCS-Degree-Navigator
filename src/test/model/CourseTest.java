package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    Course testCourse;

    String cName = "Test Course";
    String cNum = "101";
    String cFaculty = "TEST";
    String cDescrip = "Test course description";
    String cKw = "Test";
    int cCredit = 3;

    String statnc = "Not Completed";
    String statcip = "Course in Progress";
    String statc = "Completed";
    String satnf = "Status not found";

    @BeforeEach
    public void runBefore() {
        testCourse = new Course(cName, cNum, cFaculty, cDescrip, cCredit);
    }

    @Test
    public void testConstructor() {
        String expCid = cFaculty + " " + cNum;
        String testCid = testCourse.getCourseId();
        assertEquals(testCid, expCid);
        assertEquals(testCourse.getCourseName(), cName);
        assertEquals(cCredit, testCourse.getCourseCredit());
        assertFalse(testCourse.getIsExempted());
        assertFalse(testCourse.getIsMandatory());

        testCourse.setPreReqs(new HashSet<>());
        testCourse.setEquivalency(new HashSet<>());
        testCourse.setDependents(new HashSet<>());

        assertTrue(testCourse.getPreReqs().isEmpty());
        assertTrue(testCourse.getDependents().isEmpty());
        assertTrue(testCourse.getEquivalency().isEmpty());
    }

    @Test
    public void testSetCourseDescription() {
        String str = "new description";
        testCourse.setCourseDescription(str);
        assertEquals(testCourse.getCourseDescription(), str);
    }


    @Test
    public void testAddKeywords() {
        testCourse.setKeywords(cKw);
        Set<String> testKws = testCourse.getCourseKeywords();
        assertTrue(testKws.contains(cKw));
        assertEquals(1, testKws.size());
    }

    @Test
    public void testPrintCourseStatus() {
        String expStat = testCourse.printCourseStatus();
        assertTrue(expStat.equals(statnc));

        testCourse.setCourseStatus(1);
        expStat = testCourse.printCourseStatus();
        assertTrue(expStat.equals(statcip));

        testCourse.setCourseStatus(2);
        expStat = testCourse.printCourseStatus();
        assertTrue(expStat.equals(statc));

        testCourse.setCourseStatus(3);
        expStat = testCourse.printCourseStatus();
        assertTrue(expStat.equals(satnf));
    }

    @Test
    public void testSetExemptionStatus() {
        assertFalse(testCourse.getIsExempted());
        testCourse.setExemptionStatus(true);
        assertTrue(testCourse.getIsExempted());
    }

    @Test
    public void testSetMandatoryStatus() {
        testCourse.setMandatoryStatus(true);
        assertTrue(testCourse.getIsMandatory());
        testCourse.setMandatoryStatus(false);
        assertFalse(testCourse.getIsMandatory());
    }

    @Test
    public void testToString() {
        assertEquals(testCourse.toString(), testCourse.getCourseId());
    }

    @Test
    public void testCourseToText() {
        String ids = "Course ID: " + testCourse.getCourseId() + " credit: " + testCourse.getCourseCredit()
                + "\nCourse name: " + testCourse.getCourseName();
        String notTakenNotMandatory = ids + "\nNot mandatory for BCS program." + "\nYou have not taken this course."
                + "\nCourse description: " + testCourse.getCourseDescription();
        String notTakenMandatory = ids + "\nMandatory for BCS program." + "\nYou have not taken this course."
                + "\nCourse description: " + testCourse.getCourseDescription();
        String takenMandatory = ids + "\nMandatory for BCS program." + "\nYou have taken this course."
                + "\nCourse description: " + testCourse.getCourseDescription();
        String takenNotMandatory = ids + "\nNot mandatory for BCS program." + "\nYou have taken this course."
                + "\nCourse description: " + testCourse.getCourseDescription();

        assertEquals(notTakenNotMandatory, testCourse.courseToText());
        testCourse.setMandatoryStatus(true);
        assertEquals(notTakenMandatory, testCourse.courseToText());
        testCourse.setCourseStatus(2);
        assertEquals(takenMandatory, testCourse.courseToText());
        testCourse.setMandatoryStatus(false);
        assertEquals(takenNotMandatory, testCourse.courseToText());
    }
}