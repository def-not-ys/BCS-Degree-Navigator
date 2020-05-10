package persistence;

import model.Student;
import model.Users;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

// represents a class that writes data to a JSON file
// Reference: http://stleary.github.io/JSON-java/index.html
//            https://howtodoinjava.com/library/json-simple-read-write-json-examples/
//            TellerApp (https://github.students.cs.ubc.ca/CPSC210/TellerApp.git)
public class Writer {

    String fileNm = "./data/data.json";
    //String fileFormat = ".json";
    FileWriter file;

    // REQUIRES: file name is the student's 8-digit student number
    public Writer() throws IOException {
        file = new FileWriter(fileNm);
    }

    // REQUIRES: file name is the student's 8-digit student number
    // NOTE: write file with given file name
    public Writer(String fn) throws IOException {
        file = new FileWriter(fn);
    }

    // MODIFIES: this
    // EFFECTS: writes the users into JSON file
    public void write(Users users) throws IOException {

        HashMap<String, Student> listOfUsers = users.getUsers();
        JSONObject  allUsers = new JSONObject();
        for (String key: listOfUsers.keySet()) {
            JSONObject data = listOfUsers.get(key).studentDetails();
            allUsers.put(key, data);
        }

        file.write(allUsers.toJSONString());
        file.flush();
    }

    // MODIFIES: this
    // EFFECTS: close the print write
    // NOTE: MUST be called when done writing
    public void close() throws IOException {
        file.close();
    }

}
