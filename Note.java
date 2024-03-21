import java.awt.*;

public class Note {
    private int x;
    private int y;
    private int pitch;
    private int size;
    private boolean isStemUp;
    private int duration;
    private int tick;
    private static final int NOTE_SIZE = 10; // Adjust note size as needed

    public Note(int x, int y, int pitch, int tick, int duration, int size, boolean isStemUp) {
        this.x = x;
        this.y = y;
        this.pitch = pitch;
        this.tick = tick;
        this.duration = duration;
        this.size = size;
        this.isStemUp = isStemUp;
    }

    public void draw(Graphics g) {
        // Draw the note at the specified position
        if(duration == 4){
            drawQuarterNote(g);
        }else if(duration == 8){
            drawHalfNote(g);
        }else{
            drawWholeNote(g);
        }
        if(pitch == 60){
            g.drawLine(x - size/2, y - size/6, x + 2 * size , y - size/6);
        }
    }

    private void drawWholeNote(Graphics g){
        g.fillOval(x, y - size / 2 , size, size);
        g.setColor(Color.WHITE);
        g.fillOval(x + size/3, (y - size / 2) + size/3, size/2, size/2);
        g.setColor(Color.BLACK);
    }

    private void drawHalfNote(Graphics g){
        if(isStemUp){
            g.fillOval(x, y - size / 2 , size, size);
            g.setColor(Color.WHITE);
            g.fillOval(x + size/3, (y - size / 2) + size/3, size/2, size/2);
            g.setColor(Color.BLACK);
        
            // Draw the stem
            g.drawLine(x + size, y , x + size, (int)(y - size * 3.5));
        }else{
            g.fillOval(x, y - size / 2 , size, size);
            g.setColor(Color.WHITE);
            g.fillOval(x + size/3, (y - size / 2) + size/3, size/2, size/2);
            g.setColor(Color.BLACK);
        
            // Draw the stem
            g.drawLine(x, y , x, (int)(y + size * 3.5));
        }
    }

    private void drawQuarterNote(Graphics g){
        if(isStemUp){
            g.fillOval(x, y - size / 2 , size, size);
        
            // Draw the stem
            g.drawLine(x + size, y , x + size, (int)(y - size * 3.5));
        }else{
            g.fillOval(x, y - size / 2 , size, size);
        
            // Draw the stem
            g.drawLine(x, y , x , (int)(y + size * 3.5));
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
        return duration;
    }

    public int getTick(){
        return tick;
    }
}
