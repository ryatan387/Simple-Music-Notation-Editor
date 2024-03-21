import javax.swing.*;

import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.ArrayList;

public class Note {
    private int x;
    private int y;
    private int pitch;
    private int size;
    private boolean isStemUp;
    private static final int NOTE_SIZE = 10; // Adjust note size as needed

    public Note(int x, int y, int pitch, int size, boolean isStemUp) {
        this.x = x;
        this.y = y;
        this.pitch = pitch;
        this.size = size;
        this.isStemUp = isStemUp;
    }

    public void draw(Graphics g) {
        // Draw the note at the specified position
        /* 
        Font font = new Font("SansSerif", Font.PLAIN, size*2);
        g.setFont(font);
        g.drawString("\u2669", x , y+(size/4)); // Quarter note Unicode character
        */
        if(isStemUp){
            if(pitch == 60){
                g.fillOval(x, y - size / 2 , size, size);

                // Draw the stem
                g.drawLine(x + size, y , x + size, (int)(y - size * 3.5));
                g.drawLine(x - size/2, y - size/6, x + 2 * size , y - size/6);
            }else{
                g.fillOval(x, y - size / 2 , size, size);

                // Draw the stem
                g.drawLine(x + size, y , x + size, (int)(y - size * 3.5));
            }
        }else{
            g.fillOval(x, y - size / 2 , size, size);

            // Draw the stem
            g.drawLine(x, y, x, (int)(y + size * 3.5));
        }
    }

    public boolean contains(int x, int y) {
        // Check if the given point (x, y) is inside the bounds of the note
        return x >= this.x - NOTE_SIZE && x <= this.x + NOTE_SIZE && y >= this.y - NOTE_SIZE && y <= this.y + NOTE_SIZE;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getPitch(){
        return pitch;
    }

    public int getDuration(){
        return 0;
    }
}
