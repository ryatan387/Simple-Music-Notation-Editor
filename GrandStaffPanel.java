import javax.swing.*;
import java.awt.*;

public class GrandStaffPanel extends JPanel {
    private StaffPanel trebleStaffPanel, bassStaffPanel;

    public GrandStaffPanel() {
        setLayout(new GridLayout(2, 1));
        trebleStaffPanel = new StaffPanel(StaffType.TREBLE);
        bassStaffPanel = new StaffPanel(StaffType.BASS);
        add(trebleStaffPanel);
        add(bassStaffPanel);
    }
}