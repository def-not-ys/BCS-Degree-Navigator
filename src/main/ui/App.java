package ui;

import model.Student;
import model.Users;
import org.json.simple.parser.ParseException;
import persistence.Reader;
import persistence.Writer;
import ui.panels.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;


// References: https://www.youtube.com/watch?v=KOI1WbkKUpQ&t=45s
//            https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html

public class App extends JFrame {

    private AppPanel menu;
    private Student defaultAcc;
    private Users users;

    static final Dimension FRAME_DIMENSION = new Dimension(800,600);

    public App() {
        super("BCS Degree Navigator");

        initializeUsers();

        setLayout(new GridBagLayout());
        setPreferredSize(FRAME_DIMENSION);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        menu = new MenuPanel(this, defaultAcc);

        add(menu);
        addWindowListener(new SaveData());
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    // MODIFIES: this
    // EFFECTS: initializes users for the app
    private void initializeUsers() {
        defaultAcc = new Student("anonymous", "", "00000000", "BCS", "default");
        loadUsers();
    }

    // MODIFIES: this
    // EFFECTS: load the existing users list from data, if not data exists, instantiate new user list
    private void loadUsers() {
        try {
            Reader reader = new Reader();
            users = reader.read();
        } catch (FileNotFoundException e) {
            users = new Users(new HashMap<>());
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: returns the users loaded from data
    public Users getUsers() {
        return users;
    }

    public static void main(String[] args) {
        App app = new App();
    }

    // Reference: https://stackoverflow.com/questions/15198549/popup-for-jframe-close-button
    private class SaveData extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            int option = JOptionPane.showOptionDialog(
                    App.this,
                    "Would you like to save the data?",
                    "Exit", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, null, null);
            if (option == JOptionPane.YES_OPTION) {
                save();
                System.out.println("save data!");
            } else {
                System.out.println("not saved!");
            }
            System.exit(0);
        }
    }

    // EFFECTS: save users data to local file
    public void save() {
        try {
            Writer writer = new Writer();
            writer.write(users);
        } catch (IOException e) {
            // save failure
            e.printStackTrace();
        }
    }
}
