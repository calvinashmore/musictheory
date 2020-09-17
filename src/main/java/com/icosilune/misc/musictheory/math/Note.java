package com.icosilune.misc.musictheory.math;

import com.google.common.collect.ImmutableList;

/**
 * represents a note within a key
 */
public class Note {

  private static ImmutableList<String> NOTE_NAMES = ImmutableList.of("C", "D", "E", "F", "G", "A", "B");
  private static final String SHARP = "#";
  private static final String FLAT = "♭";
  private static ImmutableList<Boolean> BLACK_KEYS =
      ImmutableList.of(false, true, false, true, false, false, true, false, true, false, true, false);
  private static final ImmutableList<String> KEY_NAMES =
      ImmutableList.of("C", "C#/D♭", "D", "D#/E♭", "E", "F", "F#/G♭", "G", "G#/A♭", "A", "A#/B♭", "B");
  //public static final double MIDDLE_C = 261.6;


  private final Key key;
  private final int position;
  private final int index;

  Note(Key key, int position, int index) {
    this.key = key;
    this.position = position;
    this.index = index;
  }

  /**
   * Position of the note within the key
   */
  public int getPosition() {
    return position;
  }

  /**
   * index of the note on the keyboard, where 0 represents middle C.
   */
  public int getIndex() {
    return index;
  }

  @Override
  public String toString() {
    int normedIndex = index - Key.CHROMATIC_SCALE * Math.floorDiv(index, Key.CHROMATIC_SCALE);

    if (BLACK_KEYS.get(normedIndex)) {
      if (key.usesSharps()) {
        return KEY_NAMES.get(normedIndex - 1) + SHARP;
      } else {
        return KEY_NAMES.get(normedIndex + 1) + FLAT;
      }
    } else {
      return KEY_NAMES.get(normedIndex);
    }
  }
}
