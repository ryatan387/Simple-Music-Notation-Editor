import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import java.util.Map;
import java.util.TreeMap;

public class StaffPanel extends JPanel {
    //private StaffType staffType;
    private List<Note> notes;
    private int noteType = 4; // 4 for quarter note, 8 for half note, 16 for whole note. number represents the duration of tick.
    private TreeMap<Integer, Integer> validYPosition = new TreeMap<>();

    private static Integer[] trebleClefPitches = {77, 76, 74, 72, 71, 69, 67, 65, 64, 62, 60};
    private static Integer[] bassClefPitches = {59, 57, 55, 53, 52, 50, 48, 47, 45, 43, 41, 40};
    List<TreeMap<Integer, Integer>> measures = new ArrayList<>(); // stores valid x positions and corresponding tick.
    List<TreeMap<Integer, Note>> placedNotesTreble = new ArrayList<>();
    List<TreeMap<Integer, Note>> placedNotesBass = new ArrayList<>();

    public StaffPanel() {
        //this.staffType = staffType;
        notes = new ArrayList<>();
        for(int i = 0 ; i < 4; i++){
            TreeMap<Integer, Integer> measure = new TreeMap<>();
            measures.add(measure);
            TreeMap<Integer, Note> trebleNotes = new TreeMap<>();
            TreeMap<Integer, Note> bassNotes = new TreeMap<>();
            placedNotesTreble.add(trebleNotes);
            placedNotesBass.add(bassNotes);
        }
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
        g2d.drawString("\uD834\uDD1E", startX, fm.getAscent() + (lineSpacing ) ); // Unicode for treble clef

        // Draw treble staff lines
        int treblePosition = 0;
        for (int i = 0; i < 5; i++) {
            int currentY = lineSpacing * i + (staffHeight - lineSpacing * 5) / 2;
            g2d.drawLine(startX, currentY, staffWidth - startX, currentY);
            validYPosition.put(currentY, trebleClefPitches[treblePosition++]);
            validYPosition.put(currentY + (lineSpacing / 2), trebleClefPitches[treblePosition++]);
        }
        // Middle C
        validYPosition.put(lineSpacing * 5 + (staffHeight - lineSpacing * 5) / 2, trebleClefPitches[treblePosition]);
        // Draw bass clef
        g2d.setFont(font);
        int staffGap = lineSpacing * 8 + (staffHeight - lineSpacing * 5) / 2;
        g2d.drawString("\uD834\uDD22", startX, fm.getAscent() + lineSpacing + staffGap ); // Unicode for bass clef

        // Draw bass staff lines
        int bassPosition = 0;
        for (int i = 0; i < 5; i++) {
            int currentY = lineSpacing * i + (staffHeight - lineSpacing * 5) / 2 + staffGap;
            g2d.drawLine(startX, currentY, staffWidth - startX, currentY);
            validYPosition.put(currentY - (lineSpacing / 2), bassClefPitches[bassPosition++]);
            validYPosition.put(currentY, bassClefPitches[bassPosition++]);
        }
        validYPosition.put(lineSpacing * 5 + (staffHeight - lineSpacing * 5) / 2 + staffGap - (lineSpacing / 2), bassClefPitches[bassPosition++]);
        // Draw measure bar lines
        for (int i = 0; i < 5; i++) { // Draw 4 measures
            int yPosition = (staffHeight - lineSpacing * 5) / 2;
            g2d.drawLine(startX + i * (staffWidth - startX * 2) / 4, yPosition, startX + i * (staffWidth - startX * 2) / 4, lineSpacing * 4 + yPosition + staffGap);
            if(i<4){
                int xPosition = ((startX + 2 * (staffWidth - startX * 2) / 4) - (startX + (staffWidth - startX * 2) / 4)) / 5;
                int tick = 4 + (16 * i);
                for(int j = 1; j < 5; j++){
                    measures.get(i).put(startX + i * (staffWidth - startX * 2) / 4 + j * xPosition, tick);
                    tick += 4;
                }
            }
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
            
            int staffHeight = getHeight() / 2;
            int lineSpacing = staffHeight / 10; // Adjust line spacing as needed for 4 lines

            if(x > 50 && x < 50 + 4 * (getWidth() - 50 * 2) / 4){
                System.out.println("in measure");
            }else{
                return;
            }

            int index = 3;
            if(x < 50 + 1 * (getWidth() - 50 * 2) / 4){
                index = 0;
            }else if(x < 50 + 2 * (getWidth() - 50 * 2) / 4){
                index = 1;
            }else if(x < 50 + 3 * (getWidth() - 50 * 2) / 4){
                index = 2;
            }else{
                index = 3;
            }
            Map.Entry<Integer, Integer> lowX = measures.get(index).floorEntry(x);
            Map.Entry<Integer, Integer> highX = measures.get(index).ceilingEntry(x);

            Map.Entry<Integer, Integer> lowY = validYPosition.floorEntry(y);
            Map.Entry<Integer, Integer> highY = validYPosition.ceilingEntry(y);

            int xPosition = 0;
            int yPosition = 0;
            int pitch = 0;

            if (lowX != null && highX != null){
                xPosition = Math.abs(x-lowX.getKey()) < Math.abs(x-highX.getKey())
                ? lowX.getKey()
                : highX.getKey();
            } else if (lowX != null || highX != null){
                xPosition = lowX != null ? lowX.getKey() : highX.getKey();
            }

            if (lowY != null && highY != null){
                yPosition = Math.abs(y-lowY.getKey()) < Math.abs(y-highY.getKey())
                ? lowY.getKey()
                : highY.getKey();
                pitch = Math.abs(y-lowY.getKey()) < Math.abs(y-highY.getKey())
                ? lowY.getValue()
                : highY.getValue();
            } else if (lowY != null || highY != null){
                yPosition = lowY != null ? lowY.getKey() : highY.getKey();
                pitch = lowY != null ? lowY.getValue() : highY.getValue();
            }
            // Create a new note and add it to the notes list

            Note note;
            int staffGap = lineSpacing * 8 + (staffHeight - lineSpacing * 5) / 2;
            
            if(yPosition <= lineSpacing * 2 + (staffHeight - lineSpacing * 5) / 2){
                note = new Note(xPosition, yPosition, pitch, measures.get(index).get(xPosition), noteType, lineSpacing, false);
            }else if(yPosition <= staffGap){
                note = new Note(xPosition, yPosition, pitch, measures.get(index).get(xPosition), noteType, lineSpacing, true);
            }else if(yPosition <= lineSpacing * 2 + (staffHeight - lineSpacing * 5) / 2 + staffGap){
                note = new Note(xPosition, yPosition, pitch, measures.get(index).get(xPosition), noteType, lineSpacing, false);
            }else{
                note = new Note(xPosition, yPosition, pitch, measures.get(index).get(xPosition), noteType, lineSpacing, true);
            }
            System.out.println(pitch);
            System.out.println(yPosition);
            if(yPosition < (staffHeight - lineSpacing * 5) / 2 + staffGap - (lineSpacing / 2)){
                if(placedNotesTreble.get(index).get(xPosition) != null){
                    notes.remove(placedNotesTreble.get(index).get(xPosition));
                    placedNotesTreble.get(index).remove(xPosition);
                    System.out.println("remove");
                }else{
                    placedNotesTreble.get(index).put(xPosition, note);
                    notes.add(note);
                }
                System.out.println("treble");
            }else{
                if(placedNotesBass.get(index).get(xPosition) != null){
                    notes.remove(placedNotesBass.get(index).get(xPosition));
                    placedNotesBass.get(index).remove(xPosition);
                    System.out.println("remove");
                }else{
                    placedNotesBass.get(index).put(xPosition, note);
                    notes.add(note);
                }
                System.out.println("bass");
            }
            // Repaint the panel to reflect the newly added note
            repaint();

        }
    }

    public List<Note> getNotes(){
        return notes;
    }

    public void setNoteType(int newNote){
        noteType = newNote;
    }
}