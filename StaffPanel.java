import javax.swing.*;
import java.awt.*;

enum StaffType {
    TREBLE, BASS
}

public class StaffPanel extends JPanel {
    private StaffType staffType;

    public StaffPanel(StaffType staffType) {
        this.staffType = staffType;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int staffHeight = getHeight() / 2;
        int staffWidth = getWidth();
        int lineSpacing = staffHeight / 10; // Adjust line spacing as needed
        int startX = 50; // Adjust start X position as needed

        if (staffType == StaffType.TREBLE) {
            // Draw treble clef
            Font font = new Font("SansSerif", Font.PLAIN, 60); // Font for clef symbol
            g2d.setFont(font);
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth("\uD834\uDD1E"); // Width of the clef symbol
            int textHeight = fm.getAscent(); // Height of the clef symbol
            g2d.drawString("\uD834\uDD1E", 10, (staffHeight - textHeight) / 2 + fm.getAscent()); // Unicode for treble clef

            // Draw treble staff lines
            for (int i = 0; i < 5; i++) {
                g2d.drawLine(startX, lineSpacing * i + (staffHeight - lineSpacing * 5) / 2, staffWidth - startX, lineSpacing * i + (staffHeight - lineSpacing * 5) / 2);
            }
        } else if (staffType == StaffType.BASS) {
            // Draw bass clef
            Font font = new Font("SansSerif", Font.PLAIN, 60); // Font for clef symbol
            g2d.setFont(font);
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth("\uD834\uDD22"); // Width of the clef symbol
            int textHeight = fm.getAscent(); // Height of the clef symbol
            g2d.drawString("\uD834\uDD22", 10, (staffHeight - textHeight) / 2 + fm.getAscent()); // Unicode for bass clef

            // Draw bass staff lines
            for (int i = 0; i < 5; i++) {
                g2d.drawLine(startX, lineSpacing * i + (staffHeight - lineSpacing * 5) / 2, staffWidth - startX, lineSpacing * i + (staffHeight - lineSpacing * 5) / 2);
            }
        }
    }
}