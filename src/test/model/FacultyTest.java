package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.coursedata.DataPath;

import static org.junit.jupiter.api.Assertions.*;

public class FacultyTest {

    Faculty testFaculty;

    @BeforeEach
    public void runBefore() {
        testFaculty = Faculty.getInstance();
    }

    @Test
    public void testConstructor() {
        int n = testFaculty.getAllCourses().size();
        assertEquals(521,n);
    }

    @Test
    public void testFindCourse() {
        Course c1 = testFaculty.findCourse("CPSC 110");
        assertEquals("CPSC 110", c1.getCourseId());
        Course c2 = testFaculty.findCourse("STAT 200");
        assertEquals("STAT 200", c2.getCourseId());
        Course c3 = testFaculty.findCourse("CPEN 211");
        assertEquals("CPEN 211", c3.getCourseId());
        Course c4 = testFaculty.findCourse("MATH 200");
        assertEquals("MATH 200", c4.getCourseId());
        Course c5 = testFaculty.findCourse("ENGL 112");
        assertEquals("ENGL 112", c5.getCourseId());
        Course c6 = testFaculty.findCourse("ELEC 301");
        assertEquals("ELEC 301", c6.getCourseId());
    }

    @Test
    public void testGetCoursesByFaculty() {
        int n1 = testFaculty.getCoursesByFaculty("CPSC").size();
        assertEquals(109, n1);
        int n2 = testFaculty.getCoursesByFaculty("MATH").size();
        assertEquals(176, n2);
        int n3 = testFaculty.getCoursesByFaculty("CPEN").size();
        assertEquals(37, n3);
        int n4 = testFaculty.getCoursesByFaculty("STAT").size();
        assertEquals(51, n4);
        int n5 = testFaculty.getCoursesByFaculty("ENGL").size();
        assertEquals(140, n5);
        int n6 = testFaculty.getCoursesByFaculty("ELEC").size();
        assertEquals(8, n6);
    }

    @Test
    public void testFacultyNotFound() {
        DataPath path = new DataPath();
        assertEquals("error", path.findPath("FAKEPATH")) ;
    }

    @Test
    public void testGetFacultyList() {
        assertEquals(6, testFaculty.getFacultyList().length);
    }
}
