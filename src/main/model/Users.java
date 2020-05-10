package model;


import java.util.HashMap;


// represents a collections of all users that have registered in the app
// students are queried by the 8-digit student number

public class Users {
    private HashMap<String, Student> users;

    public Users(HashMap<String, Student> existingUsers) {
        users = existingUsers;
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: add the student in the list of users
    public void addUser(Student student) {
        users.put(student.getStudentId(), student);
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: delete the student from the list of users
    public void deleteUser(Student student) {
        users.remove(student.getStudentId());
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: clears all users in the list
    public void clearAllUsers() {
        users.clear();
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: clears all users in the list
    public HashMap<String, Student> getUsers() {
        return users;
    }

}
