import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/* 
enum StaffType {
    TREBLE, BASS
}*/

public class StaffPanel extends JPanel {
    //private StaffType staffType;
    private List<Note> notes;

    public StaffPanel() {
        //this.staffType = staffType;
        notes = new ArrayList<>();
        addMouseListener(new NoteMouseListener());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int staffHeight = getHeight() / 2;
        int staffWidth = getWidth();
        int lineSpacing = staffHeight / 10; // Adjust line spacing as needed
        int startX = 50; // Adjust start X position as needed

        // Draw treble clef
        Font font = new Font("SansSerif", Font.PLAIN, getHeight()/4); // Font for clef symbol
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth("\uD834\uDD1E"); // Width of the clef symbol
        int textHeight = fm.getAscent(); // Height of the clef symbol
        g2d.drawString("\uD834\uDD1E", startX, fm.getAscent() + (lineSpacing ) ); // Unicode for treble clef
        // (staffHeight - textHeight) / 2 + fm.getAscent()
        // Draw treble staff lines
        for (int i = 0; i < 5; i++) {
            g2d.drawLine(startX, lineSpacing * i + (staffHeight - lineSpacing * 5) / 2, staffWidth - startX, lineSpacing * i + (staffHeight - lineSpacing * 5) / 2);
        }
        // Draw bass clef
        g2d.setFont(font);
        int staffGap = lineSpacing * 8 + (staffHeight - lineSpacing * 5) / 2;
        g2d.drawString("\uD834\uDD22", startX, fm.getAscent() + lineSpacing +staffGap ); // Unicode for bass clef

        // Draw bass staff lines
        for (int i = 0; i < 5; i++) {
            g2d.drawLine(startX, lineSpacing * i + (staffHeight - lineSpacing * 5) / 2 + staffGap, staffWidth - startX, lineSpacing * i + (staffHeight - lineSpacing * 5) / 2 + staffGap);
        }
        // Draw measure bar lines
        for (int i = 0; i < 5; i++) { // Draw 4 measures
            g2d.drawLine(startX + i * (staffWidth - startX * 2) / 4, (staffHeight - lineSpacing * 5) / 2, startX + i * (staffWidth - startX * 2) / 4, lineSpacing * 4 + (staffHeight - lineSpacing * 5) / 2 + staffGap);
        }

        // Draw notes
        for (Note note : notes) {
            note.draw(g);
        }
    }

    private class NoteMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX(); // Get the x-coordinate of the mouse click
            int y = e.getY(); // Get the y-coordinate of the mouse click

            // Determine which note corresponds to the y-coordinate
            // For instance, you can calculate the position of each line or space of the staff
            // and based on the y-coordinate, determine where the note should be placed.
            // For simplicity, let's assume the note will be added to the staff line where the click occurred.

            int staffHeight = getHeight();
            int lineSpacing = staffHeight / 12; // Adjust line spacing as needed for 4 lines

            // Calculate the closest staff line to the clicked y-coordinate
            int staffLine = Math.round((float) (y - (staffHeight - lineSpacing * 5) / 2) / lineSpacing);

            // Create a new note and add it to the notes list
            Note note = new Note(x, staffLine * lineSpacing + (staffHeight - lineSpacing * 5) / 2);
            notes.add(note);

            // Repaint the panel to reflect the newly added note
            repaint();
        }
    }
}