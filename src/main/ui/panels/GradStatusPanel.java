package ui.panels;

import model.Student;
import ui.App;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GradStatusPanel extends AppPanel {

    private JLabel gradStatus = new JLabel();
    private BufferedImage img;
    private JLabel label = new JLabel();

    public GradStatusPanel(App frame, Student student) {
        super(frame, student);
        initializeContents();
        addToPanel();
    }


    // EFFECTS: show congratulation image when user is ready to graduate!
    private void showImage() {
        try {
            img = ImageIO.read(new File("./data/postcard-you-did-it resized.jpg"));
            ImageIcon icon = new ImageIcon(img);
            label.setIcon(icon);

        } catch (IOException e) {
            e.printStackTrace();
        }
        gradStatus.setText("Congratulations! You are ready to graduate! :)");
    }

    @Override
    protected void initializeContents() {
        gradStatus.setFont(new Font("Dialog", Font.BOLD, 20));
    }

    @Override
    protected void addToPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(gradStatus, gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(label, gbc);
    }

    @Override
    protected void initializeInteraction() {

    }

    @Override
    public void updatePanel() {
        student.graduateCheck();
        if (student.getCanGraduate()) {
            showImage();
        } else {
            label.setIcon(null);
            gradStatus.setText("Not quite. You still have " + student.getCoursesToTake().size() + " courses to go.");
        }
    }

}
