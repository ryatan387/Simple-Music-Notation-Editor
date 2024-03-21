import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Simple Paint Application using Java Swing.
 * Allows users to draw, save, load, and clear drawings.
 *
 * @author ChatGPT
 */
public class App extends JFrame {
    private JToolBar toolBar;
    private JButton playButton, quarterNoteButton, halfNoteButton, wholeNoteButton;
    private GrandStaffPanel grandStaffPanel;

    /**
     * Constructor to initialize the application.
     */
    public App() {
        super("Simple Music Notation Editor");
        initUI();
    }

    /**
     * Initializes the User Interface components of the application.
     */
    private void initUI() {
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setLayout(new BorderLayout());
        setupMenuBar();
        setUpToolBar();
        //pack();
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }


    /**
     * Shows an About dialog with information about the application.
     */
    private void showAbout() {
        JOptionPane.showMessageDialog(this, "Simple Music Notation Editor\nVersion 1.0\nCreated by ChatGPT", "About", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Sets up the menu bar with File, Edit, and Help menus.
     */
    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // File Menu
        JMenu fileMenu = new JMenu("File");

        fileMenu.add(new JSeparator()); // Separator

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        // Edit Menu
        JMenu editMenu = new JMenu("Edit");

        // Help Menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAbout());
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }
    private void addNotesToSynth(Track track, int note, int tick) throws InvalidMidiDataException{
        ShortMessage noteOn = new ShortMessage();
        noteOn.setMessage(ShortMessage.NOTE_ON, 0, note, 100);
        MidiEvent noteOnEvent = new MidiEvent(noteOn, tick);
        track.add(noteOnEvent);
        
        // Choose a duration for the chord (e.g., 16 ticks)
        long chordDuration = 16;
        
        ShortMessage noteOff = new ShortMessage();
        noteOff.setMessage(ShortMessage.NOTE_OFF, 0, note, 100);
        MidiEvent noteOffEvent = new MidiEvent(noteOff, tick + 4);
        track.add(noteOffEvent);
    }
    /**
     * 
     */
    public void setUpToolBar(){
        toolBar = new JToolBar();
        playButton = new JButton("Play");
        quarterNoteButton = new JButton("\u2669"); // Quarter note Unicode
        halfNoteButton = new JButton("\u266A"); // Half note Unicode
        wholeNoteButton = new JButton("\u266B"); // Whole note Unicode

        toolBar.add(playButton);
        toolBar.add(quarterNoteButton);
        toolBar.add(halfNoteButton);
        toolBar.add(wholeNoteButton);
        add(toolBar, BorderLayout.NORTH);

        // Grand Staff Panel
        grandStaffPanel = new GrandStaffPanel();
        add(grandStaffPanel, BorderLayout.CENTER);

        // Add action listeners
        // prob take this out of setUpToolBar
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get the default synthesizer
                    Synthesizer synthesizer = MidiSystem.getSynthesizer();
                    
                    // Open the synthesizer
                    synthesizer.open();
                    
                    // Create a MIDI sequence
                    Sequence sequence = new Sequence(Sequence.PPQ, 4);
                    Track track = sequence.createTrack();
                    
                    // Add note events to the track
                    List<StaffPanel> tempStaffPanels = grandStaffPanel.getStaffPanels();
                    int tick = 0;
                    for(StaffPanel s : tempStaffPanels){
                        List<Note> tempNotes = s.getNotes();
                        for(Note n : tempNotes){
                            addNotesToSynth(track, n.getPitch(), tick);
                            tick += 4;
                        }
                    }
                    
                    // Get a sequencer
                    Sequencer sequencer = MidiSystem.getSequencer();
                    sequencer.open();
                    
                    // Set the sequence for the sequencer
                    sequencer.setSequence(sequence);
                    
                    // Start playback
                    sequencer.start();
                    
                    // Wait until playback is finished
                    while (sequencer.isRunning()) {
                        Thread.sleep(1000); // Sleep for 1 second
                    }
                    
                    // Close the sequencer and synthesizer
                    sequencer.close();
                    synthesizer.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Display the frame
        setVisible(true);
    }
    /**
     * Main method to run the application.
     *
     * @param args Command line arguments (not used).
     */
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App().setVisible(true));
    }
}