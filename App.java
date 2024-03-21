import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Simple Paint Application using Java Swing.
 * Allows users to draw, save, load, and clear drawings.
 *
 * @author ChatGPT
 */
public class App extends JFrame {
    private JToolBar toolBar;
    private JButton playButton, stopButton, quarterNoteButton, halfNoteButton, wholeNoteButton;
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
        // Get the graphics environment
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gdArray = ge.getScreenDevices();

        // Get the maximum width and height of the monitor
        int maxWidth = 0;
        int maxHeight = 0;
        for (GraphicsDevice gd : gdArray) {
            DisplayMode dm = gd.getDisplayMode();
            int width = dm.getWidth();
            int height = dm.getHeight();
            if (width > maxWidth) {
                maxWidth = width;
            }
            if (height > maxHeight) {
                maxHeight = height;
            }
        }

        // Calculate the window size
        int windowWidth = maxWidth - 150; // Subtract 150 pixels from the maximum width
        int windowHeight = maxHeight - 150; // Subtract 150 pixels from the maximum height

        // Set the window size
        setSize(windowWidth, windowHeight);
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

    /**
     * 
     */
    public void setUpToolBar(){
        toolBar = new JToolBar();
        playButton = new JButton("Play");
        stopButton = new JButton("Stop");
        wholeNoteButton = new JButton("\uD834\uDD5D"); // Whole note Unicode
        halfNoteButton = new JButton("\uD834\uDD5E"); // Half note Unicode
        quarterNoteButton = new JButton("\u2669"); // Quarter note Unicode
        JSlider bpmSlider = new JSlider(JSlider.HORIZONTAL, 40, 240, 120);
        bpmSlider.setMajorTickSpacing(20);
        bpmSlider.setPaintTicks(true);
        
        toolBar.add(playButton);
        toolBar.add(wholeNoteButton);
        toolBar.add(halfNoteButton);
        toolBar.add(quarterNoteButton);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        // Add a panel for the BPM slider with BorderLayout
        JPanel bpmPanel = new JPanel(new BorderLayout());
        bpmPanel.add(bpmSlider, BorderLayout.CENTER);
        
        // Add the BPM panel to the toolbar
        toolBar.add(bpmPanel);
        add(toolBar, BorderLayout.NORTH);

        // Grand Staff Panel
        grandStaffPanel = new GrandStaffPanel();
        add(grandStaffPanel, BorderLayout.CENTER);

        // Add action listeners
        // prob take this out of setUpToolBar
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MusicPlayer.playMusic(grandStaffPanel);
            }
        });

        wholeNoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<StaffPanel> tempStaffPanels = grandStaffPanel.getStaffPanels();
                for(StaffPanel sPanel : tempStaffPanels){
                    sPanel.setNoteType(16);
                }
            }
        });

        halfNoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<StaffPanel> tempStaffPanels = grandStaffPanel.getStaffPanels();
                for(StaffPanel sPanel : tempStaffPanels){
                    sPanel.setNoteType(8);
                }
            }
        });

        quarterNoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<StaffPanel> tempStaffPanels = grandStaffPanel.getStaffPanels();
                for(StaffPanel sPanel : tempStaffPanels){
                    sPanel.setNoteType(4);
                }
            }
        });

        bpmSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                float bpm = bpmSlider.getValue();
                MusicPlayer.setBPM(bpm);
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