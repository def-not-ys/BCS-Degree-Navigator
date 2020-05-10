package ui.panels;

import model.Student;
import ui.App;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

// References:
// https://docs.oracle.com/javase/7/docs/api/javax/swing/JOptionPane.html
// https://stackoverflow.com/questions/12810460/joptionpane-input-dialog-box-program

public class AccountMgrPanel extends AppPanel {

    protected JList<Student> userList;
    protected JScrollPane currUserPane;

    protected JButton btn;
    protected JLabel status = new JLabel();

    protected Student selectedUser;
    protected Student currUser;

    private JLabel label;

    public AccountMgrPanel(App frame, Student student) {
        super(frame, student);

        initializeLists();
        initializeContents();
        initializeInteraction();
        addToPanel();

    }

    // MODIFIES: this
    // EFFECTS: initialize the users list from loaded user data
    protected void initializeLists() {
        userList = new JList<>();
        updateUsers();
    }

    // MODIFIES: this
    // EFFECTS: update the users loaded from saved data
    protected void updateUsers() {
        DefaultListModel<Student> model = new DefaultListModel<>();
        model.removeAllElements();
        HashMap<String, Student> existingUsers = users.getUsers();
        for (String key: existingUsers.keySet()) {
            model.addElement(existingUsers.get(key));
        }
        try {
            userList.setModel(model);
        } catch (NullPointerException e) {
            // can't figure out why it's throwing exception
        }
        status.setText("Hi " + student.getStudentName() + " !");
        currUser = student;
    }

    // MODIFIES: this
    // EFFECTS: initializes button interactions
    private void initButtonInteractions() {
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currUser.getStudentId() == selectedUser.getStudentId()) {

                    student = new Student("anonymous", "", "00000000", "BCS", "default");
                    status.setText(selectedUser.getStudentName() + "has been deleted! "
                            + "\n You are using temporary account now.");
                    users.deleteUser(selectedUser);
                } else if (selectedUser != null) {
                    users.deleteUser(selectedUser);
                    status.setText(selectedUser.getStudentName() + "has been deleted! "
                            + "\n You are using temporary account now.");
                }
                updatePanel();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: initializes list interactions
    private void initListInteractions() {
        ListSelectionListener selection = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedUser = userList.getSelectedValue();
            }
        };
        userList.addListSelectionListener(selection);
    }

    @Override
    protected void initializeContents() {
        updateUsers();
        currUserPane = new JScrollPane(userList);
        currUserPane.setPreferredSize(new Dimension(200,400));
        currUserPane.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));

        btn = new JButton("delete user");
        label = new JLabel("existing users:");
    }

    @Override
    protected void addToPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(10,10,0,10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(label, gbc);

        gbc.insets = new Insets(0,10,10,10);
        gbc.gridy = 1;
        gbc.gridheight = 3;
        add(currUserPane, gbc);

        gbc.insets = new Insets(10,10,0,10);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        add(status, gbc);

        gbc.gridy = 2;
        add(btn, gbc);
    }

    @Override
    protected void initializeInteraction() {
        initListInteractions();
        initButtonInteractions();
    }

    @Override
    public void updatePanel() {
        updateUsers();
        status.setText("You are currently logged in as " + student.getStudentName());
    }

}
