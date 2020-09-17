package com.icosilune.misc.musictheory.math;

import com.google.common.collect.ImmutableList;

import java.util.List;

abstract public class Key {

  public static final int CHROMATIC_SCALE = 12;

  public static Key major(int rootNote) {
    return new MajorKey(rootNote);
  }

  public static Key minor(int rootNote) {
    return new MinorKey(rootNote);
  }


  public abstract int rootNote();

  /**
   * Notes in the scale, in indices, values >=0 < 12.
   * Later on maybe figure out how to make naming work...
   */
  abstract public ImmutableList<Note> notes();

  /**
   * how many notes there are in the key, within an octave. Generally 7...
   */
  public abstract int size();

  /**
   * index+size goes up an octave
   */
  public abstract Chord diatonicChord(int position);

  abstract boolean usesSharps();

  public abstract String shortName();

  @Override
  public abstract String toString();

  public boolean contains(int value) {
    value = value - CHROMATIC_SCALE * Math.floorDiv(value, CHROMATIC_SCALE);
    return notes().contains(value);
  }

  public boolean contains(Chord chord) {
    return chord.indices().stream().allMatch(this::contains);
  }

  abstract static class SimpleKey extends Key {
    private final int rootNote;

    SimpleKey(int rootNote) {
      // always keep rootNote [0,12)
      this.rootNote = rootNote - CHROMATIC_SCALE * Math.floorDiv(rootNote, CHROMATIC_SCALE);
    }

    @Override
    public int rootNote() {
      return rootNote;
    }

    @Override
    public int size() {
      return 7;
    }
  }

  static class MajorKey extends SimpleKey {
    private ImmutableList<Note> notes;

    MajorKey(int rootNote) {
      super(rootNote);
      notes = ImmutableList.of(new Note(this, 0, rootNote), // root
          new Note(this, 1, rootNote + 2), // whole step
          new Note(this, 2, rootNote + 4), // whole step
          new Note(this, 3, rootNote + 5), // half step
          new Note(this, 4, rootNote + 7), // whole step
          new Note(this, 5, rootNote + 9), // whole step
          new Note(this, 6, rootNote + 11) // whole step
          // next octave is half step.
      );
    }

    @Override
    public ImmutableList<Note> notes() {
      return notes;
    }

    @Override
    public Chord diatonicChord(int position) {

      int octave = Math.floorDiv(position, 7);
      position = position - octave * 7; // relative shift within octave

      int R = notes().get(position).getIndex() + octave * CHROMATIC_SCALE;

      switch (position) {
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

    @Override
    public String shortName() {
      switch (rootNote()) {
        case 0: return "C";
        case 1: return "D♭";
        case 2: return "D";
        case 3: return "E♭";
        case 4: return "E";
        case 5: return "F";
        case 6: return "F#"; // or G♭
        case 7: return "G";
        case 8: return "A♭";
        case 9: return "A";
        case 10: return "B♭";
        case 11: return "B";
      }
      return "N/A";
    }

    @Override
    public String toString() {
      return shortName() + " maj";
    }

    @Override
    boolean usesSharps() {
      switch (rootNote()) {
        case 0: return false; // C maj, 0 sharps
        case 1: return false; // D♭ maj, 5 flats
        case 2: return true; // D maj, 2 sharps
        case 3: return false; // E♭ maj, 3 flats
        case 4: return true; // E maj, 4 sharps
        case 5: return false; // F maj, 1 flat
        case 6: return true; // F# maj / G♭ maj, 6 of either
        case 7: return true; // G maj, 1 sharp
        case 8: return false; // A♭ maj, 4 flats
        case 9: return true; // A maj, 3 sharps
        case 10: return false; // B♭ maj, 2 flats
        case 11: return true; // B maj, 5 sharps
      }
      return false; // shouldn't get here
    }
  }

  static class MinorKey extends SimpleKey {
    private ImmutableList<Note> notes;

    MinorKey(int rootNote) {
      super(rootNote);
      notes = ImmutableList.of(new Note(this, 0, rootNote), // root
          new Note(this, 1, rootNote + 2), // whole step
          new Note(this, 2, rootNote + 3), // half step
          new Note(this, 3, rootNote + 5), // whole step
          new Note(this, 4, rootNote + 7), // whole step
          new Note(this, 5, rootNote + 8), // half step
          new Note(this, 6, rootNote + 10) // whole step
          // next octave is whole step.
      );
    }

    @Override
    public ImmutableList<Note> notes() {
      return notes;
    }

    @Override
    public Chord diatonicChord(int position) {

      int octave = position / 7;
      position = position - octave * 7; // relative shift within octave

      int R = notes().get(position).getIndex() + octave * CHROMATIC_SCALE;

      switch (position) {
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

    @Override
    public String shortName() {
      switch (rootNote()) {
        case 0: return "C";
        case 1: return "C#";
        case 2: return "D";
        case 3: return "D#"; // Or E♭
        case 4: return "E";
        case 5: return "F";
        case 6: return "F#";
        case 7: return "G";
        case 8: return "G#";
        case 9: return "A";
        case 10: return "B♭";
        case 11: return "B";
      }
      return "N/A";
    }

    @Override
    public String toString() {
      return shortName() + " min";
    }

    @Override
    boolean usesSharps() {
      switch (rootNote()) {
        case 0: return false; // C min, 3 flats
        case 1: return true; // C# min, 4 sharps
        case 2: return false; // D min, 1 flat
        case 3: return true; // D# min / E♭ min, 6 of either
        case 4: return true; // E min, 1 sharp
        case 5: return false; // F min, 4 flats
        case 6: return true; // F# min, 3 sharps
        case 7: return false; // G min, 2 flats
        case 8: return true; // G# min, 5 sharps
        case 9: return false; // A min, 0 sharps
        case 10: return false; // B♭ min, 5 flats
        case 11: return true; // B min, 2 sharps
      }
      return false; // shouldn't get here
    }
  }
}
