package com.icosilune.misc.musictheory;

import com.icosilune.misc.musictheory.math.Chord;
import com.icosilune.misc.musictheory.math.Key;
import com.icosilune.misc.musictheory.math.Note;

import java.util.stream.Collectors;

public class Main {

    public static void main(String args[]) throws Exception {


//        double C = 261.6 *2;
////        double f1 = C * 1 / 2;
////        double f2 = C * 44 / 73;
//        Waveform w = Waveform.add(
//        Waveform.simple(Oscillator.SINE, 1.0, f1),
//                Waveform.simple(Oscillator.SINE, 1.0, f2));
//        Sound.create(w, 2.0).playBlocking();


        Key k = Key.major(1);

        System.out.println(k.notes());

        for(Note i : k.notes()) {
            //Chord ch = Chord.sus2(i.getIndex());
            System.out.println(k+"   "+i+": "+k.diatonicChord(i.getPosition()));
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
