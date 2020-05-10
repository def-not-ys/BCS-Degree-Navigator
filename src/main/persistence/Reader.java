package persistence;



import model.Faculty;
import model.Student;
import model.Users;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

// represents a class that reads data from a JSON file
// Reference: http://stleary.github.io/JSON-java/index.html
//            https://howtodoinjava.com/library/json-simple-read-write-json-examples/
//            TellerApp (https://github.students.cs.ubc.ca/CPSC210/TellerApp.git)
public class Reader {

    FileReader file;
    String fileNm = "./data/data.json";
    //String fileFormat = ".json";
    Student student;
    Users users;

    public Reader() throws FileNotFoundException {
        file = new FileReader(fileNm);
    }

    // NOTE: reads file with the given file name
    public Reader(String fn) throws FileNotFoundException {
        file = new FileReader(fn);
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: returns the user list constructed from reading the JSON file and
    public Users read() throws IOException, ParseException {
        users = new Users(new HashMap<>());

        JSONParser jsonParser = new JSONParser();
        JSONObject data = (JSONObject) jsonParser.parse(file);

        HashMap<String, JSONObject> tempMap = new HashMap();
        tempMap.putAll((HashMap<String, JSONObject>)data);

        for (String k: tempMap.keySet()) {
            Student temp = parseStudent(tempMap.get(k));
            users.addUser(temp);
        }
        return users;
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: returns the student constructed by parsing the JSON file
    private Student parseStudent(JSONObject data) throws IOException {
        Faculty faculty = Faculty.getInstance();

        String fn = (String) data.get("First name");
        String ln = (String) data.get("Last name");
        String id = (String) data.get("StudentID");
        String pg = (String) data.get("Program");
        String fc = (String) data.get("Faculty");

        student = new Student(fn, ln, id, pg, fc);

        JSONArray rawTaken = (JSONArray) data.get("Taken");
        JSONArray rawExpt = (JSONArray) data.get("Exempted");

        parseTakenCourseList(rawTaken, student, faculty);
        parseExptCourseList(rawExpt, student, faculty);

        return student;
    }


    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: parse the list of taken course from the JSON file and adds to the student
    private void parseTakenCourseList(JSONArray rawData, Student student, Faculty faculty) {
        for (Object k: rawData) {
            String cn = (String)k;
            student.addTakenCourse(faculty.findCourse(cn));
        }
    }

    // REQUIRES:
    // MODIFIES: this
    // EFFECTS: parse the list of taken course from the JSON file and adds to the student
    private void parseExptCourseList(JSONArray rawData, Student student, Faculty faculty) {
        for (Object k: rawData) {
            String cn = (String)k;
            student.addExemptedCourse(faculty.findCourse(cn));
        }
    }
}
