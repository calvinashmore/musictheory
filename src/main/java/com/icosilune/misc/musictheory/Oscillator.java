package com.icosilune.misc.musictheory;

/**
 * Contract of class:
 * periodic w period 1: evaluate(t) = evaluate(t)+1
 * Math.abs(evaluate(t)) <= 1
 */
public interface Oscillator {
    Oscillator SINE = t -> Math.sin(t*2*Math.PI);

    double evaluate(double t);
}
