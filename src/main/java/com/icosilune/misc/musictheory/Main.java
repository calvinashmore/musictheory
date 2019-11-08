package com.icosilune.misc.musictheory;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String args[]) throws Exception {

        Key k = Key.minor(0);

        for(int i=0;i<8;i++) {
            List<Integer> ch = k.diatonicChord(i);
            System.out.println(ch);

            Sound.create(chord(1.0, ch), 1.0).playBlocking();
        }
    }

    static Waveform tone(double volume, int index) {
        double C = 261.6;
        double f = C * Math.pow(2,(1.0*index)/12);
        return Waveform.simple(Oscillator.SINE, volume, f);
    }

    static Waveform chord(double volume, List<Integer> tones) {
        return Waveform.add(tones.stream().map(t -> tone(volume, t)).collect(Collectors.toList()));
    }

}
