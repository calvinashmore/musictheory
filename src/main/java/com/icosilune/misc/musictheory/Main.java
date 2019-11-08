package com.icosilune.misc.musictheory;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class Main {

    public static void main(String args[]) throws Exception {

        Main main = new Main();

        double C = 261.6;

        Sound.create(note(1.0,0),1.0 ).playBlocking();
        Sound.create(note(1.0,4),1.0 ).playBlocking();
        Sound.create(note(1.0,7),1.0 ).playBlocking();

        Sound.create(
                Waveform.add(
                        note(1.0,0),
                        note(1.0,4),
                        note(1.0,7)

                ),2.0

        ).playBlocking();

//        for(int i=0;i<12;i++) {
//
//
////            createTone(1.0,f,1.0).playBlocking();
//        }

//        createTone(1.0, C*1.0, 1.0).playBlocking();
//        createTone(1.0, C*1.1, 1.0).playBlocking();
//        createTone(1.0, C*1.2, 1.0).playBlocking();
    }

    static Waveform note(double volume, int index) {
        double C = 261.6;
        double f = C * Math.pow(2,(1.0*index)/12);
        return Waveform.simple(Oscillator.SINE, volume, f);
    }

//    static Sound createTone(double volume, double frequency, double length) throws LineUnavailableException, IOException {
//        float sampleRate = 11025.0f;
//
//        Clip clip = AudioSystem.getClip();
//
//        AudioFormat af = new AudioFormat(
//                sampleRate,
//                8,  // sample size in bits
//                2,  // channels
//                true,  // signed
//                false  // bigendian
//        );
//
//
//        byte[] tone = new byte[(int) (sampleRate * length)];
//
//        for (int i = 0; i<tone.length; i++) {
//            double t = (1.0*i) / sampleRate;
//            double v = Math.sin(t*frequency*Math.PI)*volume;
//
//            tone[i] = (byte) (127.0*v);
//        }
//
//        AudioInputStream ais = new AudioInputStream(
//                new ByteArrayInputStream(tone),
//                af,
//                tone.length );
//
//        clip.open( ais );
//        return new Sound(clip);
//    }

}
