package com.icosilune.misc.musictheory;

/**
 * General waveform interface. Functionally identical to Oscillator, but contract is different.
 * Still expect output to be clamped to 1, but no other constraints. Could potentially apply Linear to this.
 */
public interface Waveform {
    double evaluate(double t);

    static Waveform simple(Oscillator osc, double volume, double frequency) {
        return t-> volume * osc.evaluate(t*frequency);
    }

    static Waveform add(Waveform... waves) {
        return t -> {
            double v = 0;
            for(Waveform wave : waves) v+= wave.evaluate(t);
            return v / waves.length;
        };
    }
}
