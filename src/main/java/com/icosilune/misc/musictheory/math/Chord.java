package com.icosilune.misc.musictheory.math;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Streams;

import java.util.Arrays;
import java.util.Objects;

public class Chord {
  // this class operates on chromatic scale: assume octave is 12 steps

  private ImmutableList<Integer> indices;

  /**
   * indices are in increasing order (first is lowest), non-repeating, and N usually 3 or 4. No repeats
   */
  Chord(Iterable<Integer> indices) {
    this.indices = Streams.stream(indices).distinct().sorted().collect(ImmutableList.toImmutableList());
  }

  Chord(int... indices) {
    this(Arrays.stream(indices).boxed().collect(ImmutableList.toImmutableList()));
  }

  // This does not correctly represent roots of inversions
  int root() {
    return indices.get(0);
  }

  public ImmutableList<Integer> indices() {
    return indices;
  }

  @Override
  public String toString() {
    return indices.toString();
  }

  public static Chord major(int root) {
    return new Chord(root, root + 4, root + 7);
  }

  public static Chord minor(int root) {
    return new Chord(root, root + 3, root + 7);
  }

  public static Chord diminished(int root) {
    return new Chord(root, root + 3, root + 6);
  }

  public static Chord majorSeventh(int root) {
    return new Chord(root, root + 4, root + 7, root + 11);
  }

  public static Chord minorSeventh(int root) {
    return new Chord(root, root + 3, root + 7, root + 10);
  }

  public static Chord dominantSeventh(int root) {
    return new Chord(root, root + 4, root + 7, root + 10);
  }

  public static Chord invert1(Chord other) {
    ImmutableList.Builder inv = ImmutableList.builder();
    for (int i = 1; i < other.indices.size(); i++) {
      inv.add(other.indices.get(i));
    }
    inv.add(other.root() + 12);
    return new Chord(inv.build());
  }

  public static Chord invert2(Chord other) {
    ImmutableList.Builder inv = ImmutableList.builder();
    for (int i = 2; i < other.indices.size(); i++) {
      inv.add(other.indices.get(i));
    }
    inv.add(other.root() + 12);
    inv.add(other.indices.get(1) + 12);
    return new Chord(inv.build());
  }

  public static Chord sus4(int root) {
    return new Chord(root, root + 5, root + 7);
  }

  public static Chord sus2(int root) {
    return new Chord(root, root + 2, root + 7);
  }

  public static Chord harmonizeUp(Chord other) {
    return new Chord(Iterables.concat(other.indices, ImmutableList.of(other.indices.get(0) + 12)));
  }

  public static Chord harmonizeDown(Chord other) {
    return new Chord(Iterables.concat(other.indices, ImmutableList.of(other.indices.get(0) - 12)));
  }

  public static Chord shiftOctave(Chord other, int octaveShift) {
    return new Chord(other.indices.stream()
        .map(index -> index + octaveShift * 12)
        .collect(ImmutableList.toImmutableList()));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Chord chord = (Chord) o;
    return indices.equals(chord.indices);
  }

  @Override
  public int hashCode() {
    return Objects.hash(indices);
  }
}
