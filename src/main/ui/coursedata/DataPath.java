package ui.coursedata;


import model.exceptions.FacultyNotFoundException;

// represents the http paths of faculty courses
public class DataPath {
    public DataPath() {

    }

    // returns the http path of the faculty
    public String findPath(String faculty) {
        try {
            return findFacultyDataPath(faculty);
        } catch (FacultyNotFoundException e) {
            return "error";
        }
    }

    //REQUIRES: faculty must be one of "CPSC", "MATH", "CPEN", "STAT", "ENGL", "ELEC"
    // EFFECTS: returns the data path for this faculty
    private String findFacultyDataPath(String faculty) throws FacultyNotFoundException {
        // Acknowledgement: received help from TA to set up file data path directory
        switch (faculty) {
            case "CPSC":
                return "./data/CPSCCourseRawData.txt";
            case "MATH":
                return "./data/MATHCourseRawData.txt";
            case "STAT":
                return "./data/STATCourseRawData.txt";
            case "CPEN":
                return "./data/CPENCourseRawData.txt";
            case "ENGL":
                return "./data/ENGLCourseRawData.txt";
            case "ELEC":
                return "./data/ElectiveCourseList.txt";
            default:
                throw new FacultyNotFoundException();
        }
    }
}
