package com.icosilune.misc.musictheory;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String args[]) throws Exception {

        Key k = Key.major(0);

        System.out.println(k.notes());

        for(int i : k.notes()) {
            Chord ch = Chord.sus2(i);
            System.out.println(i+": "+ch + ": "+k.contains(ch));
        }


//        Sound.create(chord(0.8, Chord.major(0)), 1.0).playBlocking();
//        Sound.create(chord(0.9, Chord.sus2(0)), 1.1).playBlocking();
//        Sound.create(chord(1.0, Chord.sus4(0)), 1.0).playBlocking();
//        Sound.create(chord(1.0, Chord.invert1(Chord.sus4(0))), 1.0).playBlocking();
//        Sound.create(chord(1.0, Chord.minor(0)), 1.2).playBlocking();
    }

    static Waveform tone(double volume, int index) {
        double C = 261.6;
        double f = C * Math.pow(2,(1.0*index)/12);
        return Waveform.simple(Oscillator.SINE, volume, f);
    }

    static Waveform chord(double volume, Chord chord) {
        return Waveform.add(chord.indices().stream().map(t -> tone(volume, t)).collect(Collectors.toList()));
    }

}
