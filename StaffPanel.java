import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import java.util.Map;
import java.util.TreeMap;

/* 
enum StaffType {
    TREBLE, BASS
}*/

public class StaffPanel extends JPanel {
    //private StaffType staffType;
    private List<Note> notes;
    private Note selectedNote;
    private int offsetX, offsetY;
    private TreeMap<Integer, String> treeMap = new TreeMap<>();

    private static String[] trebleClefPitches = {"F5", "E5", "D5", "C5", "B4", "A4", "G4", "F4", "E4", "D4", "C4"};
    private static String[] bassClefPitches = {"A3", "G3", "F3", "E3", "D3", "C3", "B2", "A2", "G2", "F2", "E2"};

    public StaffPanel() {
        //this.staffType = staffType;
        notes = new ArrayList<>();
        addMouseListener(new NoteMouseListener());
        addMouseMotionListener(new NoteMouseMotionListener());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int staffHeight = 1200 / 2;
        int staffWidth = 800;
        int lineSpacing = staffHeight / 10; // Adjust line spacing as needed
        int startX = 50; // Adjust start X position as needed

        // Draw treble clef
        Font font = new Font("SansSerif", Font.PLAIN, 1200/4); // Font for clef symbol
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth("\uD834\uDD1E"); // Width of the clef symbol
        int textHeight = fm.getAscent(); // Height of the clef symbol
        g2d.drawString("\uD834\uDD1E", startX, fm.getAscent() + (lineSpacing ) ); // Unicode for treble clef
        // (staffHeight - textHeight) / 2 + fm.getAscent()
        // Draw treble staff lines
        int treblePosition = 0;
        for (int i = 0; i < 5; i++) {
            int currentY = lineSpacing * i + (staffHeight - lineSpacing * 5) / 2;
            g2d.drawLine(startX, currentY, staffWidth - startX, currentY);
            treeMap.put(currentY, trebleClefPitches[treblePosition++]);
            treeMap.put(currentY + (lineSpacing / 2), trebleClefPitches[treblePosition++]);

        }
        // Draw bass clef
        g2d.setFont(font);
        int staffGap = lineSpacing * 8 + (staffHeight - lineSpacing * 5) / 2;
        g2d.drawString("\uD834\uDD22", startX, fm.getAscent() + lineSpacing + staffGap ); // Unicode for bass clef

        // Draw bass staff lines
        int bassPosition = 0;
        for (int i = 0; i < 5; i++) {
            int currentY = lineSpacing * i + (staffHeight - lineSpacing * 5) / 2 + staffGap;
            g2d.drawLine(startX, currentY, staffWidth - startX, currentY);
            treeMap.put(currentY, bassClefPitches[bassPosition++]);
            treeMap.put(currentY + (lineSpacing / 2), bassClefPitches[bassPosition++]);
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
            
            int staffHeight = 600;
            int lineSpacing = 60; // Adjust line spacing as needed for 4 lines
    
            Map.Entry<Integer, String> low = treeMap.floorEntry(y);
            Map.Entry<Integer, String> high = treeMap.floorEntry(y);

            int yPosition = 0;
            String pitch = "";
            if (low != null && high != null){
                yPosition = Math.abs(y-low.getKey()) < Math.abs(y-high.getKey())
                ? low.getKey()
                : high.getKey();
                pitch = Math.abs(y-low.getKey()) < Math.abs(y-high.getKey())
                ? low.getValue()
                : high.getValue();
            } else if (low != null || high != null){
                yPosition = low != null ? low.getKey() : high.getKey();
                pitch = low != null ? low.getValue() : high.getValue();
            }
            // Create a new note and add it to the notes list
            Note note;
            int staffGap = lineSpacing * 8 + (staffHeight - lineSpacing * 5) / 2;
            if(yPosition <= lineSpacing * 2 + (staffHeight - lineSpacing * 5) / 2){
                note = new Note(x, yPosition, pitch, lineSpacing, false);
            }else if(yPosition <= staffGap){
                note = new Note(x, yPosition, pitch, lineSpacing, true);
            }else if(yPosition <= lineSpacing * 2 + (staffHeight - lineSpacing * 5) / 2 + staffGap){
                note = new Note(x, yPosition, pitch, lineSpacing, false);
            }else{
                note = new Note(x, yPosition, pitch, lineSpacing, true);
            }
            System.out.println(pitch);
            notes.add(note);
    
            // Repaint the panel to reflect the newly added note
            repaint();

        }
    }

    private class NoteMouseMotionListener extends MouseMotionAdapter {
        @Override
        public void mouseDragged(MouseEvent e) {
            if (selectedNote != null) {
                int x = e.getX(); // Get the x-coordinate of the mouse drag
                int y = e.getY(); // Get the y-coordinate of the mouse drag

                // Update the position of the selected note
                selectedNote.setX(x - offsetX);
                selectedNote.setY(y - offsetY);
                repaint();
            }
        }
    }
}