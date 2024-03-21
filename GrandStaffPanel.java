import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<StaffPanel> getStaffPanels(){
        List<StaffPanel> staffpanels = new ArrayList<>();
        staffpanels.add(staffPanel1);
        staffpanels.add(staffPanel2);
        staffpanels.add(staffPanel3);
        staffpanels.add(staffPanel4);
        return staffpanels;
    }
}