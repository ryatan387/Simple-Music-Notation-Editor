import javax.swing.*;
import java.awt.*;

public class GrandStaffPanel extends JPanel {
    private StaffPanel staffPanel1, staffPanel2, staffPanel3, staffPanel4;

    public GrandStaffPanel() {
        setLayout(new GridLayout(4 , 1));
        staffPanel1 = new StaffPanel();
        staffPanel2 = new StaffPanel();
        staffPanel3 = new StaffPanel();
        staffPanel4 = new StaffPanel();
        add(staffPanel1);
        add(staffPanel2);
        add(staffPanel3);
        add(staffPanel4);
    }
}