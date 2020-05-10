package ui.panels;

import model.Course;
import model.GradCheckList;
import model.Student;
import ui.App;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class MyCoursePanel extends AppPanel {

    private JScrollPane coursePane;
    private JTextArea textArea = new JTextArea();
    private JList<Course> courseList;

    private JButton takenListBtn = new JButton("taken courses");
    private JButton toTakeListBtn = new JButton("to take courses");
    private JButton grdChkBtn = new JButton("mandatory courses");
    private JLabel userStatus = new JLabel();

    private JButton addBtn = new JButton("add to my list");
    private JButton rmvBtn = new JButton("remove from my list");

    private HashMap<String, Course> currList = new HashMap<>();

    private Course selectedCourse;

    private GridBagConstraints gbc = new GridBagConstraints();

    public MyCoursePanel(App frame, Student student) {
        super(frame, student);

        initializeLists();
        initializeInteraction();
        initializeContents();

        addToPanel();
    }

    // MODIFIES: this
    // EFFECTS: initialize lists on this panel
    private void initializeLists() {
        courseList = new JList<>();

        updateCoursePane();
    }

    // MODIFIES: this
    // EFFECTS: updates courses in the course pane
    private void updateCoursePane() {
        updateStatus();

        DefaultListModel<Course> model = new DefaultListModel();
        model.removeAllElements();

        for (String key: currList.keySet()) {
            model.addElement(currList.get(key));
        }

        try {
            courseList.setModel(model);
        } catch (NullPointerException e) {
            // can't figure out why it's throwing exception
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the label showing students' status
    private void updateStatus() {
        int completed = student.getCoursesTaken().size();
        int totake = student.getCoursesToTake().size();
        int credit = student.getCurrCredits();
        userStatus.setText("You have taken " + completed + " courses. You total credit is " + credit
                + " Still have " + totake + " courses to go! ");
    }

    // MODIFIES: this
    // EFFECTS: adds status label on this panel
    private void addStatus() {
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10,10,10,10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.0;
        gbc.gridheight = 1;
        gbc.gridwidth = 3;
        add(userStatus,gbc);
    }

    // MODIFIES: this
    // EFFECTS: adds option buttons on this panel
    private void addOptionButtons() {
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        add(takenListBtn, gbc);

        gbc.gridy = 2;
        add(toTakeListBtn, gbc);

        gbc.gridy = 3;
        add(grdChkBtn, gbc);
    }

    // MODIFIES: this
    // EFFECTS: adds course pane on this panel
    private void addCoursePane() {

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 3;
        add(coursePane, gbc);

        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 2;

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 1;
        gbc.gridheight = 3;
        add(textArea, gbc);
    }

    // MODIFIES: this
    // EFFECTS: adds operation buttons on this panel
    private void addOperationButtons() {
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx = 3;
        gbc.gridy = 1;
        add(addBtn, gbc);
        gbc.gridy = 2;
        add(rmvBtn, gbc);
    }

    // MODIFIES: this
    // EFFECTS: initializes options button interactions
    private void initOptionButtons() {
        takenListBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currList = student.getCoursesTaken();
                updateCoursePane();
            }
        });

        toTakeListBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currList = student.getCoursesToTake();
                updateCoursePane();
            }
        });

        grdChkBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currList = GradCheckList.getInstance().getRequiredCourses();
                updateCoursePane();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: initializes operation button interactions
    private void initAddButtons() {
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedCourse != null) {
                    if (student.addTakenCourse(selectedCourse)) {
                        textArea.setText(selectedCourse.getCourseId() + " is now added to your list!");
                        updateStatus();
                        updateCoursePane();
                    } else {
                        textArea.setText(selectedCourse.getCourseId() + " is already in your list!");
                    }
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: initializes operation button interactions
    private void initRmvButtons() {
        rmvBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedCourse != null) {
                    if (!student.dropTakenCourse(selectedCourse)) {
                        textArea.setText("Can't remove " + selectedCourse.getCourseId()
                                + "\nYou haven't taken this course yet");
                    } else {
                        textArea.setText(selectedCourse.getCourseId() + " is removed from your list!");
                    }
                    updateStatus();
                    updateCoursePane();
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: initializes list interactions
    private void initListInteraction() {
        ListSelectionListener selection = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedCourse = courseList.getSelectedValue();
                textArea.setText(selectedCourse.courseToText());
            }
        };
        courseList.addListSelectionListener(selection);
    }



    @Override
    protected void initializeContents() {
        updateStatus();
        coursePane = new JScrollPane(courseList);
        coursePane.setPreferredSize(new Dimension(100,400));

        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setPreferredSize(new Dimension(220,350));
    }

    @Override
    protected void addToPanel() {
        addStatus();
        addOptionButtons();
        addCoursePane();
        addOperationButtons();
    }

    @Override
    protected void initializeInteraction() {
        initOptionButtons();
        initListInteraction();
        initAddButtons();
        initRmvButtons();
    }

    @Override
    public void updatePanel() {
        currList = new HashMap<>();
        textArea.setText("");
        updateCoursePane();
    }
}
