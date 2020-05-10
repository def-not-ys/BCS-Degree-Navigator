package ui.panels;

import model.Student;
import model.Users;
import ui.App;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public abstract class AppPanel extends JPanel {
    protected App frame;

    protected List<JButton> buttons;
    protected List<AppPanel> panels;

    static Student student;
    protected Users users;

    static final Dimension PANEL_DIMENSION = new Dimension(700,520);

    public AppPanel(App frame, Student user) {
        super(new GridBagLayout());
        this.student = user;
        this.users = frame.getUsers();

        this.frame = frame;
        this.buttons = new ArrayList<>();
        this.panels = new ArrayList<>();

        setPreferredSize(PANEL_DIMENSION);
        setAlignmentX(Component.RIGHT_ALIGNMENT);
        setAlignmentY(Component.TOP_ALIGNMENT);
        setVisible(true);
    }

    // MODIFIES: this, frame
    // EFFECTS: initializes contents
    protected abstract void initializeContents();

    // MODIFIES: this
    // EFFECTS: adds contents to this panel
    protected abstract void addToPanel();

    // MODIFIES: this, frame
    // EFFECTS: assigns according panel to each button
    protected abstract void initializeInteraction();

    // EFFECTS: updates data display on the panel when active
    public abstract void updatePanel();


}
