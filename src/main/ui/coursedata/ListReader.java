package ui.coursedata;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

// a reader with scanner to read list
// Reference: Oracle Java Documentation

public class ListReader {
    private LinkedList<String> list = new LinkedList<>();

    public ListReader() {}

    // MODIFIES: this
    // EFFECTS: returns a list of lines read from the file
    public LinkedList<String> getList(String path) throws IOException {
        File txtFile = new File(path);
        Scanner file = new Scanner(txtFile);
        while (file.hasNext()) {
            String item = file.nextLine();
            list.add(item);
        }
        return list;
    }
}
