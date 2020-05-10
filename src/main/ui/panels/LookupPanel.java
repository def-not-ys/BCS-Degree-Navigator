package ui.panels;

import model.Course;
import model.Faculty;
import model.Student;
import ui.App;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


// Reference: https://www.oreilly.com/library/view/learning-java-4th/9781449372477/ch19s06.html

public class LookupPanel extends AppPanel implements ListSelectionListener {

    private JScrollPane facultyPane;
    private JScrollPane coursePane;
    private JList<String> facultyList;
    private JList<Course> courseList;

    private JTextField textField = new JTextField();
    private JTextArea textArea = new JTextArea();
    private JButton addBtn = new JButton("add to my list");
    private JButton search = new JButton("search");

    private JLabel facultyListTitles = new JLabel("choose from faculty:");
    private JLabel courseListTitles = new JLabel("choose course:");
    private JLabel searchLabel = new JLabel("search course id in format xxxx123 (e.g. cpsc110) :");


    private Course selectedCourse;

    private HashMap<String, Course> currList;
    private List<String> faculties;

    private KeyListener keyInput;
    private ListSelectionListener selectCourse;

    private GridBagConstraints gbc = new GridBagConstraints();


    public LookupPanel(App frame, Student student) {
        super(frame, student);

        currList = new HashMap<>();
        faculties = Arrays.asList(Faculty.getInstance().getFacultyList());

        initializeInteraction();
        initializeLists();
        initializeContents();

        addToPanel();
    }

    // MODIFIES: this
    // EFFECTS: initializes faculty list and course list
    private void initializeLists() {

        DefaultListModel<String> fl = new DefaultListModel<>();
        facultyList = new JList<>();
        facultyList.setModel(fl);

        for (int i = 0; i < faculties.size(); i++) {
            fl.addElement(faculties.get(i));
        }

        facultyList.addListSelectionListener(this);
        courseList = new JList<>();
        updateCoursePane();
        courseList.addListSelectionListener(selectCourse);
    }

    // MODIFIES: this
    // EFFECTS: update the course list pane based on current faculty selection
    private void updateCoursePane() {
        DefaultListModel<Course> cl = new DefaultListModel<>();

        for (String key: currList.keySet()) {
            cl.addElement(currList.get(key));
        }

        try {
            courseList.setModel(cl);
        } catch (NullPointerException exp) {
            // can't figure out why it's throwing exception
        }
    }

    // EFFECTS: returns valid course id from raw input
    private Course getCourseIdFromInput(String input) {
        String courseId = "";
        Course c;
        try {
            String fn = input.substring(0, 4).toUpperCase();
            if (faculties.contains(fn)) {
                courseId = fn + " " + input.substring(4, 7);
            } else {
                textArea.setText("I don't recognize this course id. Please try again.");
            }
        } catch (StringIndexOutOfBoundsException e) {
            textArea.setText("I don't recognize this input. Please try again.");
        }
        try {
            c = Faculty.getInstance().findCourse(courseId);
            return c;
        } catch (NullPointerException e) {
            textArea.setText("Opps.. This course does not exist at the moment.");
        }
        return null;
    }

    // EFFECTS: processes course id from user input and updates course detail
    private void processInput() {
        String rawInput = textField.getText();
        selectedCourse = getCourseIdFromInput(rawInput);
        if (selectedCourse == null) {
            textArea.setText("Opps.. This course does not exist at the moment.");
        } else {
            textArea.setText(selectedCourse.courseToText());
        }
    }

    // MODIFIES: this
    // EFFECTS: adds faculty list pane to the panel
    private void addFacultyPane() {
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10,5,0,5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        add(facultyListTitles, gbc);

        gbc.gridy = 1;
        gbc.gridheight = 3;
        add(facultyPane,gbc);
    }

    // MODIFIES: this
    // EFFECTS: adds course list pane to the panel
    private void addCoursePane() {
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        add(courseListTitles, gbc);

        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.gridheight = 3;
        add(coursePane, gbc);
    }

    // MODIFIES: this
    // EFFECTS: adds search pane to the panel
    private void addSearchPane() {
        gbc.insets = new Insets(10,5,5,5);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weighty = 0.0;
        add(searchLabel,gbc);

        gbc.gridy = 2;
        add(textField, gbc);

        gbc.insets = new Insets(15,5,10,5);
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.weighty = 1.0;
        add(textArea, gbc);
    }

    // MODIFIES: this
    // EFFECTS: adds buttons to the panel
    private void addButtons() {
        gbc.gridx = 3;
        gbc.weighty = 0.0;
        gbc.insets = new Insets(15,5,10,5);
        gbc.gridy = 0;
        add(search, gbc);
        gbc.gridy = 1;
        add(addBtn, gbc);
    }

    // MODIFIES: this
    // EFFECTS: initializes button interactions
    private void initButtons() {
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processInput();
            }
        });
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedCourse != null) {
                    if (student.addTakenCourse(selectedCourse)) {
                        textArea.setText(selectedCourse.getCourseId() + " is now added to your list!");
                    } else {
                        textArea.setText(selectedCourse.getCourseId() + " is already in your list.");
                    }
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: initialized list interactions
    private void initList() {
        selectCourse = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedCourse = courseList.getSelectedValue();
                textArea.setText(selectedCourse.courseToText());
            }
        };
    }

    // MODIFIES: this
    // EFFECTS: initialized key input interactions
    private void initKeyInput() {
        keyInput = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    processInput();
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };
    }

    @Override
    protected void initializeContents() {
        facultyPane = new JScrollPane(facultyList);

        coursePane = new JScrollPane(courseList);
        coursePane.setPreferredSize(new Dimension(100,400));

        textField.setPreferredSize(new Dimension(100,20));
        textField.addKeyListener(keyInput);

        textArea.setPreferredSize(new Dimension(300,300));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
    }

    @Override
    protected void addToPanel() {
        addFacultyPane();
        addCoursePane();
        addSearchPane();
        addButtons();
    }

    @Override
    protected void initializeInteraction() {
        initList();
        initKeyInput();
        initButtons();

    }

    @Override
    public void updatePanel() {
        textField.setText("");
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

        String fc = facultyList.getSelectedValue();
        currList = Faculty.getInstance().getCoursesByFaculty(fc);

        updateCoursePane();
    }
}
