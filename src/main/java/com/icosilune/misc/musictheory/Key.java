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
     */
    abstract public List<Integer> notes();

    /**
     * how many notes there are in the key, within an octave
     */
    abstract int size();

    /**
     * index+size goes up an octave
     */
    abstract List<Integer> diatonicChord(int index);

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
        List<Integer> diatonicChord(int index) {

            int octave = index / 7;
            index = index - octave*7; // relative shift within octave

            int R = notes().get(index) + octave*CHROMATIC_SCALE;

            switch (index) {
                case 0: return chordMajor(R);
                case 1: return chordMinor(R);
                case 2: return chordMinor(R);
                case 3: return chordMajor(R);
                case 4: return chordMajor(R);
                case 5: return chordMinor(R);
                case 6: return chordDiminished(R);
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
        List<Integer> diatonicChord(int index) {

            int octave = index / 7;
            index = index - octave*7; // relative shift within octave

            int R = notes().get(index) + octave*CHROMATIC_SCALE;

            switch (index) {
                case 0: return chordMinor(R);
                case 1: return chordDiminished(R);
                case 2: return chordMajor(R);
                case 3: return chordMinor(R);
                case 4: return chordMinor(R);
                case 5: return chordMajor(R);
                case 6: return chordMajor(R);
            }

            throw new IllegalStateException();
        }
    }

    private static List<Integer> chordMajor(int root) {
        return ImmutableList.of(root, root+4, root+7);
    }

    private static List<Integer> chordMinor(int root) {
        return ImmutableList.of(root, root+3, root+7);
    }

    private static List<Integer> chordDiminished(int root) {
        return ImmutableList.of(root, root+3, root+6);
    }
}
