package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class UsersTest {

    Users users;

    @BeforeEach
    public void runBefore() {
        HashMap<String, Student> existing = new HashMap<>();
        users = new Users(existing);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, users.getUsers().size());
    }

    @Test
    public void testAddUser() {
        Student s1 = new Student("s1", "s", "11111111", "A", "A");
        Student s2 = new Student("s2", "s", "22222222", "A", "A");
        users.addUser(s1);
        assertEquals(1, users.getUsers().size());
        users.addUser(s1);
        assertEquals(1, users.getUsers().size());
        users.addUser(s2);
        assertEquals(2, users.getUsers().size());
    }

    @Test
    public void testDeleteUser() {
        Student s1 = new Student("s1", "s", "11111111", "A", "A");
        Student s2 = new Student("s2", "s", "22222222", "A", "A");
        users.addUser(s1);
        users.addUser(s2);

        users.deleteUser(s2);
        assertEquals(1, users.getUsers().size());
        assertFalse(users.getUsers().containsKey(s2.getStudentId()));
        assertTrue(users.getUsers().containsKey(s1.getStudentId()));

        users.deleteUser(s2);
        assertEquals(1, users.getUsers().size());
        assertFalse(users.getUsers().containsKey(s2.getStudentId()));
        assertTrue(users.getUsers().containsKey(s1.getStudentId()));
    }

    @Test
    public void testClearUsers() {
        Student s1 = new Student("s1", "s", "11111111", "A", "A");
        Student s2 = new Student("s2", "s", "22222222", "A", "A");
        users.addUser(s1);
        users.addUser(s2);
        assertEquals(2, users.getUsers().size());
        users.clearAllUsers();
        assertEquals(0, users.getUsers().size());
    }
}
