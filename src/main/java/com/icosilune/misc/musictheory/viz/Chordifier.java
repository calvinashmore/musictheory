package com.icosilune.misc.musictheory.viz;

import com.google.common.collect.ImmutableList;
import com.icosilune.misc.musictheory.Oscillator;
import com.icosilune.misc.musictheory.Sound;
import com.icosilune.misc.musictheory.Waveform;
import com.icosilune.misc.musictheory.math.Chord;
import com.icosilune.misc.musictheory.math.Key;
import com.icosilune.misc.musictheory.math.Note;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.io.IOException;
import java.util.stream.Collectors;

public class Chordifier extends JPanel {

  public static void main(String args[]) {
    JFrame frame = new JFrame();
    frame.add(new Chordifier());
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }

  private final Keyboard keyboard = new Keyboard(-2, 4);
  private final KeySelector keySelector = new KeySelector();
  private final ChordSelector chordSelector = new ChordSelector();

  public Chordifier() {

    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    add(keyboard);
    add(keySelector);
    add(chordSelector);

    keySelector.setKeyListener(new KeySelector.KeyListener() {
      @Override
      public void onHover(Key key, boolean hovering) {
        if (hovering) {
          keyboard.setHighlightNotes(key.notes().stream().map(Note::getIndex).collect(ImmutableList.toImmutableList()));
        } else {
          keyboard.setHighlightNotes(ImmutableList.of());
        }
      }

      @Override
      public void onClick(Key key) {
        chordSelector.setKey(key);
      }
    });

    chordSelector.setChordListener(new ChordSelector.ChordListener() {
      @Override
      public void onHover(Chord chord, boolean hovering) {
        if (hovering) {
          keyboard.setHighlightNotes(chord.indices());
        } else {
          keyboard.setHighlightNotes(ImmutableList.of());
        }
      }

      @Override
      public void onClick(Chord chord) {
        try {
          Sound.create(chord(1.0, chord), 1.0).playBlocking();
        } catch (IOException | LineUnavailableException | InterruptedException ex) {
          // ...
        }
      }
    });
  }

  static Waveform tone(double volume, int index) {
    double C = 261.6;
    double f = C * Math.pow(2, (1.0 * index) / 12);
    return Waveform.simple(Oscillator.SINE, volume, f);
  }

  static Waveform chord(double volume, Chord chord) {
    return Waveform.add(chord.indices().stream().map(t -> tone(volume, t)).collect(Collectors.toList()));
  }

}
