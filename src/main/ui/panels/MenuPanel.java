package ui.panels;

import model.Student;
import ui.App;
import ui.buttonactions.NavigateAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Represents the main main panel
// References:
// https://docs.oracle.com/javase/tutorial/uiswing/events/windowlistener.html

public class MenuPanel extends AppPanel {

    private JLabel title;
    private JLabel welcomeMsg;
    private JLabel msg1;
    private List<JButton> homeButtons;

    public MenuPanel(App frame, Student student) {
        super(frame, student);

        homeButtons = new ArrayList<>();

        initializeContents();
        initializeInteraction();
        addToPanel();

    }

    // MODIFIES: this
    // EFFECTS: initializes and adds menu buttons to lists
    private void addButtons() {
        JButton btn1 = new JButton("look up a course");
        buttons.add(btn1);
        JButton btn2 = new JButton("check my course");
        buttons.add(btn2);
        JButton btn3 = new JButton("check my graduation status");
        buttons.add(btn3);
        JButton btn4 = new JButton("log in");
        buttons.add(btn4);
        JButton btn5 = new JButton("account manager");
        buttons.add(btn5);
        JButton btn0 = new JButton("save and exit");
        buttons.add(btn0);
        btn0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.save();
                System.exit(0);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: initializes panels
    private void initializePanels() {
        panels.add(new LookupPanel(frame, student));
        panels.add(new MyCoursePanel(frame, student));
        panels.add(new GradStatusPanel(frame, student));
        panels.add(new LoginPanel(frame, student));
        panels.add(new AccountMgrPanel(frame, student));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.insets = new Insets(3,3,10,3);
        gbc.gridx = 3;
        gbc.gridy = 3;

        for (AppPanel p: panels) {
            JButton back = new JButton("back");
            homeButtons.add(back);
            p.add(back, gbc);
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes back buttons on all sub panels
    private void initializeHomeButtons() {
        for (int i = 0; i < 5; i++) {
            homeButtons.get(i).addActionListener(new NavigateAction(frame, panels.get(i), this));
        }
    }

    @Override
    protected void initializeContents() {
        addButtons();
        title = new JLabel("BCS Degree Navigator");
        welcomeMsg = new JLabel("Hi " + student.getStudentName() + "! Welcome to the app!");
        msg1 = new JLabel("What would you like to do today?");
        title.setFont(new Font("Dialog", Font.BOLD, 20));
        welcomeMsg.setFont(new Font("Dialog", Font.PLAIN, 13));
        msg1.setFont(new Font("Dialog", Font.PLAIN, 12));

        initializePanels();
        initializeHomeButtons();

    }

    // MODIFIES: this
    // EFFECTS: adds buttons to this pane
    @Override
    protected void addToPanel() {
        GridBagConstraints bgc = new GridBagConstraints();

        bgc.insets = new Insets(5,3,3,3);
        bgc.anchor = GridBagConstraints.FIRST_LINE_START;
        bgc.gridx = 0;
        bgc.gridy = 0;
        add(title, bgc);
        bgc.gridx = 0;
        bgc.gridy = 1;
        add(welcomeMsg, bgc);
        bgc.insets = new Insets(0,1,1,1);
        bgc.gridx = 0;
        bgc.gridy = 2;
        add(msg1, bgc);

        bgc.gridx = 3;
        bgc.anchor = GridBagConstraints.PAGE_END;
        bgc.insets = new Insets(3,10,3,3);
        int y = 2;
        for (JButton btn: buttons) {
            bgc.gridy = y;
            this.add(btn,bgc);
            y++;
        }
    }

    // MODIFIES: this, frame
    // EFFECTS: assigns according panel to each button
    @Override
    protected void initializeInteraction() {
        for (int i = 0; i < panels.size(); i++) {
            NavigateAction mal = new NavigateAction(frame, this, panels.get(i));
            buttons.get(i).addActionListener(mal);
        }
    }

    @Override
    public void updatePanel() {
        welcomeMsg.setText("Hi " + student.getStudentName() + "! Welcome to the app!");
    }
}
