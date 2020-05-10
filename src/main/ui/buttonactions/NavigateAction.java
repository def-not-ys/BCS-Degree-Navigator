package ui.buttonactions;

import ui.App;
import ui.panels.AppPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavigateAction implements ActionListener {

    private App frame;
    private AppPanel thisPanel;
    private AppPanel nextPanel;

    public NavigateAction(App frame, AppPanel thisPanel, AppPanel nextPanel) {
        this.frame = frame;
        this.thisPanel = thisPanel;
        this.nextPanel = nextPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        nextPanel.updatePanel();
        nextPanel.setVisible(true);
        frame.setContentPane(nextPanel);
        thisPanel.setVisible(false);
        frame.validate();
    }
}