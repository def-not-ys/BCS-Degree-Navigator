package persistence;

import model.Student;
import model.Users;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class WriterTest {

    Writer writer;
    Student student;
    Users users;
    String fn = "./data/writertest.json";


    @BeforeEach
    public void runBefore() throws IOException {
        student = new Student("first", "last", "12345678", "BCS", "CPSC");
        writer = new Writer(fn);
        users = new Users(new HashMap<>());
        users.addUser(student);
    }

    @Test
    public void testWriteUserOne() {
        try {
            writer.write(users);
            writer.close();
        } catch (IOException e) {
            fail("not expected IOException");
        }

        try {
            Reader reader = new Reader(fn);
            Users readUser = reader.read();
            assertEquals(1, readUser.getUsers().size());
            assertTrue(readUser.getUsers().containsKey("12345678"));
        } catch (IOException e) {
            fail("reader fail to read file written");
        } catch (ParseException e) {
            fail("reader fail to parse content");
        }
    }

    @Test
    public void testWriteUsersLots() throws IOException {
        Student s1 = new Student("s1", "s", "11111111", "A", "B");
        Student s2 = new Student("s2", "s", "22222222", "C", "D");

        users.addUser(s1);
        users.addUser(s2);

        try {
            writer.write(users);
            writer.close();
        } catch (IOException e) {
            fail("not expected IOException");
        }

        try {
            Reader reader = new Reader(fn);
            Users readUser = reader.read();
            assertEquals(3, readUser.getUsers().size());
            assertTrue(readUser.getUsers().containsKey("12345678"));
            assertTrue(readUser.getUsers().containsKey("11111111"));
            assertTrue(readUser.getUsers().containsKey("22222222"));
            assertFalse(readUser.getUsers().containsKey("33333333"));
        } catch (IOException e) {
            fail("reader fail to read file written");
        } catch (ParseException e) {
            fail("reader fail to parse content");
        }
    }



}
