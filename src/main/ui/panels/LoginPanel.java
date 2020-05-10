package ui.panels;

import model.Student;
import ui.App;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginPanel extends AccountMgrPanel {

    private JButton login;
    private JLabel label;

    private JTextField firstName;
    private JTextField lastName;
    private JTextField studentId;

    public LoginPanel(App frame, Student student) {
        super(frame, student);
    }

    // MODIFIES: this
    // EFFECTS: register new student
    // Reference: https://stackoverflow.com/questions/21290288/is-it-possible-to-put-multiple-input-in-joptionpane-showinputdialog
    private void registerNewUser() throws IOException {

        Object[] message = {"First name:", firstName, "Last name:", lastName, "8-digit student number:", studentId};
        int option = JOptionPane.showConfirmDialog(LoginPanel.this, message,
                "Registering you", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String firstNameText = firstName.getText();
            String lastNameText = lastName.getText();
            String studentIdText = studentId.getText();

            if (users.getUsers().containsKey(studentIdText)) {
                JOptionPane.showMessageDialog(this,
                        "This account has already be registered. Will log in to this account for you.",
                        "existing account", JOptionPane.PLAIN_MESSAGE);
                student = users.getUsers().get(studentIdText);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Hi " + firstNameText + ", your account is all set up!",
                        "successful registered", JOptionPane.PLAIN_MESSAGE);
                student = new Student(firstNameText, lastNameText, studentIdText, "BCS", "CPSC");
                users.addUser(student);
            }
        }
        status.setText("Hi " + student.getStudentName() + " !");
        updatePanel();
    }

    // MODIFIES: this
    // EFFECTS: initializes register button
    private void initializeRegisterButton() {
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    registerNewUser();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: initializes login button
    private void initializeLoginButton() {
        login = new JButton("log me in");
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedUser != null) {
                    student = selectedUser;
                    updateUsers();
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: initializes list selection
    private void initializeListSelection() {
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
        super.initializeContents();
        btn = new JButton("register me");
        label = new JLabel("choose an existing user to log in or register a new account");

        firstName = new JTextField();
        lastName = new JTextField();
        studentId = new JTextField();
    }

    @Override
    protected void initializeInteraction() {
        initializeRegisterButton();
        initializeLoginButton();
        initializeListSelection();
    }

    @Override
    protected void addToPanel() {
        super.addToPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        add(label, gbc);

        gbc.gridy = 3;
        add(login, gbc);
    }

    @Override
    public void updatePanel() {
        super.updatePanel();
        firstName.setText("");
        lastName.setText("");
        studentId.setText("");
    }
}
