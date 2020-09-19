package com.icosilune.misc.musictheory.viz;

import com.google.common.collect.ImmutableList;
import com.icosilune.misc.musictheory.math.Chord;
import com.icosilune.misc.musictheory.math.Key;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChordSelector extends JPanel {

  interface ChordListener {
    void onHover(Chord chord, boolean hovering);

    void onClick(Chord chord);
  }

  private static final ImmutableList<String> MAJOR_NAMES = ImmutableList.of("I", "ii", "iii", "IV", "V", "vi", "vii°");
  private static final ImmutableList<String> MINOR_NAMES = ImmutableList.of("i", "ii°", "III", "iv", "v", "VI", "VII");
  private static final ImmutableList<String> INVERSIONS = ImmutableList.of("", "₁", "₂");

  private Key key;
  private int octaveOffset = 0;
  private ChordListener chordListener;

  private final ChordButton[][] diatonics = new ChordButton[3][7];

  public void setChordListener(ChordListener chordListener) {
    this.chordListener = chordListener;
  }

  ChordSelector() {

    JPanel diatonicsPanel = new JPanel();
    diatonicsPanel.setLayout(new GridLayout(3, 7));

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 7; j++) {
        ChordButton chordButton = new ChordButton();
        diatonics[i][j] = chordButton;
        diatonicsPanel.add(chordButton);
      }
    }
    add(diatonicsPanel);

    JPanel octavePanel = new JPanel();
    octavePanel.setLayout(new BoxLayout(octavePanel, BoxLayout.Y_AXIS));
    ButtonGroup octaveButtonGroup = new ButtonGroup();
    for (int i = -2; i < 2; i++) {
      OctaveButton octaveButton = new OctaveButton(i);
      octavePanel.add(octaveButton);
      octaveButtonGroup.add(octaveButton);
    }
    add(octavePanel);

    setKey(Key.major(0));
  }

  public void setKey(Key key) {
    this.key = key;

    updateDiatonics();
  }

  private void updateDiatonics() {

    for (int j = 0; j < 7; j++) {
      Chord chord = key.diatonicChord(j);
      for (int i = 0; i < 3; i++) {
        ChordButton chordButton = diatonics[i][j];
        chordButton.chord = chord;

        if (key instanceof Key.MajorKey) {
          chordButton.setText(MAJOR_NAMES.get(j) + INVERSIONS.get(i));
        } else {
          chordButton.setText(MINOR_NAMES.get(j) + INVERSIONS.get(i));
        }
        chord = Chord.invert1(chord);
      }
    }

  }

  private class ChordButton extends JButton {
    private Chord chord;

    // Calculates the chord given the octave
    private Chord calcChord() {
      return Chord.shiftOctave(chord, octaveOffset);
    }

    ChordButton() {

      addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
          if (chordListener != null) {
            chordListener.onHover(calcChord(), true);
          }
        }

        @Override
        public void mouseExited(MouseEvent e) {
          if (chordListener != null) {
            chordListener.onHover(calcChord(), false);
          }
        }
      });

      addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          if (chordListener != null) {
            chordListener.onClick(calcChord());
          }
        }
      });
    }
  }

  private class OctaveButton extends JRadioButton {
    private final int octave;

    OctaveButton(int octave) {
      super("oct " + octave);
      this.octave = octave;

      addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
          octaveOffset = octave;
        }
      });
    }
  }
}
