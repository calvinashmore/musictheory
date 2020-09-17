package com.icosilune.misc.musictheory.viz;

import com.google.common.collect.ImmutableList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

public class Keyboard extends JPanel {

    private static final ImmutableList<String> NOTE_NAMES = ImmutableList.of("C","D","E","F","G","A","B");
    public static final Color WHITE_HIGHLIGHT = new Color(0xc0, 0xc0, 0xc0);
    public static final Color BLACK_HIGHLIGHT = new Color(0x50, 0x50, 0x50);

    private final int startingOctave;
    private final int totalOctaves;

    private List<Integer> highlightNotes = ImmutableList.of();
    private List<Integer> pressNotes = ImmutableList.of();

    public Keyboard(int startingOctave, int totalOctaves) {
        this.startingOctave = startingOctave;
        this.totalOctaves = totalOctaves;
        highlightNotes = ImmutableList.of(1,3,7);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(7*totalOctaves*20*2,100*2);
    }

    @Override
    public void paint(Graphics g) {
        int w = getWidth();
        int h = getHeight();
        int spacing = 2;

        g.setColor(Color.BLACK);
        g.fillRect(0,0,w, h);

        int octaveWidth = w/totalOctaves;
        int noteWidth = octaveWidth / 7;

        for(int octave = startingOctave; octave < totalOctaves+startingOctave; octave++) {

            for(int i=0;i<7;i++) {

                int noteIndex = 12*octave + whiteKeyIndex(i);

                int x = octave*octaveWidth + i*noteWidth;

                if (highlightNotes.contains(noteIndex)) {
                    g.setColor(WHITE_HIGHLIGHT);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(x+spacing,spacing, noteWidth-spacing, h-spacing);

                g.setColor(Color.BLACK);
                g.drawString(NOTE_NAMES.get(i), x + noteWidth*5/12,h*7/8);
            }

            g.setColor(Color.BLACK);
            for(int i=0;i<7;i++) {
                if (i == 0 || i == 3) continue;
                int x = octave*octaveWidth + i*noteWidth - noteWidth/3;

                int noteIndex = 12*octave + blackKeyIndex(i-1);

                g.setColor(Color.BLACK);
                g.fillRect(x, spacing, noteWidth*2/3, h*2/3);


                if (highlightNotes.contains(noteIndex)) {
                    g.setColor(BLACK_HIGHLIGHT);
                    g.fillRect(x+spacing, spacing, noteWidth*2/3-2*spacing, h*2/3-spacing);
                }
            }

        }

        // *****
        // need way of highlighting keys (both for hover & also for indicating play & in-key)
        // maybe take key as variable and use that.
        // best way to highlight?

    }

    int whiteKeyIndex(int index) {
        // input index is 0-6
        if (index < 3)
            return 2*index;
        if (index == 3)
            return 5;
        return 2*index - 1;
    }

    int blackKeyIndex(int index) {
        // input index is 0-6, ignoring 0 and 3
        if (index < 3)
            return 1+2*index;
        return 2*index;
    }
}
