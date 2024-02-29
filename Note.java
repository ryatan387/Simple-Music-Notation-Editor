import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Note {
    private int x;
    private int y;

    public Note(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        // Draw the note at the specified position
        g.setColor(Color.BLACK);
        g.fillOval(x - 5, y - 5, 10, 10); // Adjust note size as needed
    }
}
