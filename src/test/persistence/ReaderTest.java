package persistence;

import model.Course;
import model.Student;
import model.Users;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import static model.GradCheckList.MAXIMUM_ALLOWED_CREDITS;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class ReaderTest {

    Student student;
    Writer writer;
    Reader reader;
    Users users;
    String fn = "./data/readtest.json";

    @BeforeEach
    public void runBefore() throws IOException {
        users = new Users(new HashMap<>());
        student = new Student("first", "last", "87654321", "BCS", "CPSC");
    }

    @Test
    public void testReaderOne() {
        try {
            users.addUser(student);
            writer = new Writer(fn);
            writer.write(users);
        } catch (IOException e) {
            fail("write fail to write users");
        }

        try {
            reader = new Reader(fn);
            users = reader.read();
            assertEquals(1, users.getUsers().size());
            assertTrue(users.getUsers().containsKey("87654321"));
        } catch (IOException e) {
            fail("reader IO exception error");
        } catch (ParseException e) {
            fail("reader parse exception error");
        }
    }

    @Test
    public void testReaderLots() throws IOException {
        Student s1 = new Student("s1", "s", "11111111", "A", "B");
        Student s2 = new Student("s2", "s", "22222222", "C", "D");

        Course testC1 = new Course("t1", "110", "CPSC", "cDescrip", 3);
        Course testC2 = new Course("t2", "121", "CPSC", "cDescrip", 2);
        Course testC3 = new Course("t3", "200", "MATH", "cDescrip", 1);

        s1.addTakenCourse(testC1);
        s1.addTakenCourse(testC2);
        s1.addTakenCourse(testC3);

        users.addUser(s1);
        users.addUser(s2);

        try {
            users.addUser(student);
            writer = new Writer(fn);
            writer.write(users);
        } catch (IOException e) {
            fail("write fail to write users");
        }

        try {
            reader = new Reader(fn);
            users = reader.read();
            assertEquals(3, users.getUsers().size());
            assertTrue(users.getUsers().containsKey("87654321"));
            assertTrue(users.getUsers().containsKey("11111111"));
            assertTrue(users.getUsers().containsKey("22222222"));
            assertFalse(users.getUsers().containsKey("33333333"));

            Student s = users.getUsers().get("11111111");
            assertEquals(3, s.getCoursesTaken().size());


        } catch (IOException e) {
            fail("reader IOE exception error");
        } catch (ParseException e) {
            fail("reader parse exception error");
        }
    }

    @Test
    public void testIOException() {
        try {
            reader = new Reader("file does not exist");
        } catch (FileNotFoundException e) {
           // expected
        }
    }
}
