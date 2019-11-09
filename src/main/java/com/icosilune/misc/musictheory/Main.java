package com.icosilune.misc.musictheory;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String args[]) throws Exception {

        Key k = Key.major(-1);

//        for(int i=0;i<8;i++) {
//            Chord ch = k.diatonicChord(i);
//            System.out.println(ch);
//
//            Sound.create(chord(1.0, ch), 1.0).playBlocking();
//        }

//        Sound.create(tone(1.0, 0), 1.0).playBlocking();

//        Sound.create(chord(1.0, k.diatonicChord(0)), 1.0).playBlocking();
//        Sound.create(chord(1.0, Chord.invert1(k.diatonicChord(0))), 1.0).playBlocking();
//        Sound.create(chord(1.0, Chord.invert2(k.diatonicChord(0))), 1.0).playBlocking();

//        Sound.create(chord(1.0, Chord.invert1(k.diatonicChord(0))), 1.0).playBlocking();
//        Sound.create(chord(1.0, Chord.invert1(k.diatonicChord(4))), 1.0).playBlocking();
//        Sound.create(chord(1.0, Chord.invert1(k.diatonicChord(5))), 1.0).playBlocking();
//        Sound.create(chord(1.0, Chord.invert1(k.diatonicChord(3))), 1.0).playBlocking();


        Sound.create(chord(0.8, Chord.major(0)), 1.0).playBlocking();
        Sound.create(chord(0.9, Chord.sus2(0)), 1.1).playBlocking();
        Sound.create(chord(1.0, Chord.sus4(0)), 1.0).playBlocking();
        Sound.create(chord(1.0, Chord.invert1(Chord.sus4(0))), 1.0).playBlocking();
        Sound.create(chord(1.0, Chord.minor(0)), 1.2).playBlocking();
//        Sound.create(chord(1.0, Chord.minorSeventh(3)), 1.0).playBlocking();
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
