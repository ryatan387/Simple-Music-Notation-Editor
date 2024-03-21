import javax.sound.midi.*;
import java.util.List;

public class MusicPlayer {
    private static Synthesizer synthesizer;
    private static Sequencer sequencer;
    private static float bpmFactor = 1.0f;
    public static void playMusic(GrandStaffPanel grandStaffPanel){
        try {
            // Get the default synthesizer
            synthesizer = MidiSystem.getSynthesizer();
            
            // Open the synthesizer
            synthesizer.open();
            
            // Create a MIDI sequence
            Sequence sequence = new Sequence(Sequence.PPQ, 4);
            Track track = sequence.createTrack();
            
            // Add note events to the track
            List<StaffPanel> tempStaffPanels = grandStaffPanel.getStaffPanels();
            int offset = 0;
            for(StaffPanel s : tempStaffPanels){
                List<Note> tempNotes = s.getNotes();
                for(Note n : tempNotes){
                    addNotesToSynth(track, n.getPitch(), n.getTick() + offset, n.getDuration());
                }
                offset += 64;
            }
            
            // Get a sequencer
            sequencer = MidiSystem.getSequencer();
            sequencer.open();
            
            // Set the sequence for the sequencer
            sequencer.setSequence(sequence);
            sequencer.setTempoFactor(bpmFactor);
            
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
    private static void addNotesToSynth(Track track, int note, int tick, int duration) throws InvalidMidiDataException{
        ShortMessage noteOn = new ShortMessage();
        noteOn.setMessage(ShortMessage.NOTE_ON, 0, note, 100);
        MidiEvent noteOnEvent = new MidiEvent(noteOn, tick);
        track.add(noteOnEvent);
        
        // Choose a duration for the chord (e.g., 16 ticks) 
        ShortMessage noteOff = new ShortMessage();
        noteOff.setMessage(ShortMessage.NOTE_OFF, 0, note, 100);
        MidiEvent noteOffEvent = new MidiEvent(noteOff, tick + duration);
        track.add(noteOffEvent);
    }

    public static void setBPM(float bpm) {
        if (sequencer != null) {
            bpmFactor = bpm / 120.0f; // Adjust 120.0f as the default BPM
        }
    }
}
