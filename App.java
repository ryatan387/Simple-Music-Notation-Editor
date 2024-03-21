import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

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
        setSize(1200, 800);
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
                // Add logic to play back the melody using synthesized sound
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