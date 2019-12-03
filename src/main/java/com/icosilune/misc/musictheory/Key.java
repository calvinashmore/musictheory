package com.icosilune.misc.musictheory;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

import java.util.List;

abstract public class Key {

    private static final int CHROMATIC_SCALE = 12;
    private static final double MIDDLE_C = 261.6;


    public static Key major(int index) {
        return new MajorKey(index);
    }
    public static Key minor(int index) {
        return new MinorKey(index);
    }


    abstract int rootNote();

    /**
     * Notes in the scale, in indices, values >=0 < 12.
     * Later on maybe figure out how to make naming work...
     */
    abstract public List<Integer> notes();

    /**
     * how many notes there are in the key, within an octave. Generally 7...
     */
    abstract int size();

    /**
     * index+size goes up an octave
     */
    abstract Chord diatonicChord(int index);

    boolean contains(int value) {
        value = value - CHROMATIC_SCALE * Math.floorDiv(value, CHROMATIC_SCALE);
        return notes().contains(value);
    }

    boolean contains(Chord chord) {
        return chord.indices().stream().allMatch(this::contains);
    }

    abstract static class SimpleKey extends Key {
        private final int rootNote;

        SimpleKey(int rootNote) {
            this.rootNote = rootNote;
        }

        @Override
        public int rootNote() {
            return rootNote;
        }

        @Override
        int size() {
            return 7;
        }
    }

    static class MajorKey extends SimpleKey {
        private ImmutableList<Integer> notes;
        MajorKey(int rootNote) {
            super(rootNote);
            notes = ImmutableList.of(
                    rootNote,
                    rootNote+2, // whole step
                    rootNote+4, // whole step
                    rootNote+5, // half step
                    rootNote+7, // whole step
                    rootNote+9, // whole step
                    rootNote+11 // whole step
                    // next octave is half step.
            );
        }

        @Override
        public ImmutableList<Integer> notes() {
            return notes;
        }

        @Override
        Chord diatonicChord(int index) {

            int octave = Math.floorDiv(index, 7);
            index = index - octave*7; // relative shift within octave

            int R = notes().get(index) + octave*CHROMATIC_SCALE;

            switch (index) {
                case 0: return Chord.major(R);
                case 1: return Chord.minor(R);
                case 2: return Chord.minor(R);
                case 3: return Chord.major(R);
                case 4: return Chord.major(R);
                case 5: return Chord.minor(R);
                case 6: return Chord.diminished(R);
            }

            throw new IllegalStateException();
        }
    }

    static class MinorKey extends SimpleKey {
        private ImmutableList<Integer> notes;
        MinorKey(int rootNote) {
            super(rootNote);
            notes = ImmutableList.of(
                    rootNote,
                    rootNote+2, // whole step
                    rootNote+3, // half step
                    rootNote+5, // whole step
                    rootNote+7, // whole step
                    rootNote+8, // half step
                    rootNote+10 // whole step
                    // next octave is whole step.
            );
        }

        @Override
        public ImmutableList<Integer> notes() {
            return notes;
        }

        @Override
        Chord diatonicChord(int index) {

            int octave = index / 7;
            index = index - octave*7; // relative shift within octave

            int R = notes().get(index) + octave*CHROMATIC_SCALE;

            switch (index) {
                case 0: return Chord.minor(R);
                case 1: return Chord.diminished(R);
                case 2: return Chord.major(R);
                case 3: return Chord.minor(R);
                case 4: return Chord.minor(R);
                case 5: return Chord.major(R);
                case 6: return Chord.major(R);
            }

            throw new IllegalStateException();
        }
    }
}
