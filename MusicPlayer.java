import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MusicPlayer {
    public static void playMusic(GrandStaffPanel grandStaffPanel){
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
    private static void addNotesToSynth(Track track, int note, int tick) throws InvalidMidiDataException{
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
}
