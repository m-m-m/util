/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.music;

import net.sf.mmm.util.exception.api.IllegalCaseException;
import net.sf.mmm.util.lang.api.SimpleDatatype;

/**
 * This enum represents the {@link TonePitch} of a musical note. It is based on the twelve tone music system
 * and only represents a single octave instead of an absolute pitch value.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum TonePitch implements SimpleDatatype<String> {

  /**
   * The base tone of the common {@link MusicalKey#C_MAJOR} key.
   */
  C("C", "C"),

  /**
   * A semitone (half step) higher than the pitch {@link #C}. The enharmonic change of this pitch is
   * <em>des</em> (<code>d&#9837;</code>).
   */
  CIS("C#", "Cis"),

  /**
   * Two semitones (half steps) higher than the pitch {@link #C}.
   */
  D("D", "D"),

  /**
   * Three semitones (half steps) higher than the pitch {@link #C}. The enharmonic change of this pitch is
   * <em>es</em> (<code>e&#9837;</code>).
   */
  DIS("D#", "Dis"),

  /**
   * Four semitones (half steps) higher than the pitch {@link #C}. The enharmonic change of this pitch is
   * <em>fes</em> (<code>f&#9837;</code>).
   */
  E("E", "E"),

  /**
   * Five semitones (half steps) higher than the pitch {@link #C}. The enharmonic change of this pitch is
   * <em>eis</em> (<code>e&#9839;</code>).
   */
  F("F", "F"),

  /**
   * Six semitones (half steps) higher than the pitch {@link #C}. The enharmonic change of this pitch is
   * <em>ges</em> (<code>g&#9837;</code>).
   */
  FIS("F#", "Fis"),

  /**
   * Seven semitones (half steps) higher than the pitch {@link #C}.
   */
  G("G", "G"),

  /**
   * Eight semitones (half steps) higher than the pitch {@link #C}. The enharmonic change of this pitch is
   * <em>as</em> (<code>a&#9837;</code>).
   */
  GIS("G#", "Gis"),

  /**
   * Nine semitones (half steps) higher than the pitch {@link #C}. The middle a (a<sup>1</sup>, Concert A
   * reference) is normalized to 440Hz.
   */
  A("A", "A"),

  /**
   * Ten semitones (half steps) higher than the pitch {@link #C}. In most countries this pitch is simply
   * called <em>B</em>. See {@link #B_NATURAL} for further details.
   */
  B_FLAT("Bb", "B\u266D"),

  /**
   * Eleven semitones (half steps) higher than the pitch {@link #C}. The enharmonic change of this pitch is
   * <em>ces</em> (<code>c&#9837;</code>). In most countries this pitch is simply called <em>H</em>. However
   * the Americans use <em>B</em> instead of <em>H</em> what can cause confusion with {@link #B_FLAT}. The
   * international notation therefore suggests the following notation:
   * <table border="1">
   * <tr>
   * <th>International</th>
   * <th>American</th>
   * <th>European</th>
   * </tr>
   * <tr>
   * <td>B&#9838;</td>
   * <td>B</td>
   * <td>H</td>
   * </tr>
   * <tr>
   * <td>B&#9837;</td>
   * <td>B&#9837;</td>
   * <td>B</td>
   * </tr>
   * </table>
   */
  B_NATURAL("H", "B\u266E");

  /** @see #getValue() */
  private final String value;

  /** @see #toString() */
  private final String title;

  /**
   * The constructor.
   * 
   * @param value - see {@link #getValue()}.
   * @param title - see {@link #toString()}.
   */
  private TonePitch(String value, String title) {

    this.value = value;
    this.title = title;
  }

  /**
   * {@inheritDoc}
   */
  public String getValue() {

    return this.value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.title;
  }

  /**
   * This method gets the {@link TonePitch} for the given <code>value</code>.
   * 
   * @param value is the {@link #getValue() value} of the requested {@link TonePitch} .
   * @return the requested {@link TonePitch} or <code>null</code> if no such {@link TonePitch} exists.
   */
  public static TonePitch fromValue(String value) {

    for (TonePitch instance : values()) {
      if (instance.value.equals(value)) {
        return instance;
      }
    }
    return null;
  }

  /**
   * This method transposes this {@link TonePitch} by the given number of <code>semitoneSteps</code>. The
   * {@link TonePitch} will wrap so {@link #transpose(int) transpose(12)} will return the {@link TonePitch}
   * itself (this) just like {@link #transpose(int) transpose(0)} or e.g. {@link #transpose(int)
   * transpose(-24)}.
   * 
   * @param semitoneSteps is the number of semitone steps to transpose. A positive value transposes towards a
   *        higher pitch, a negative value transposes towards a lower pitch. A value of zero (<code>0</code>)
   *        will have no change.
   * @return the transposed {@link TonePitch}.
   */
  public TonePitch transpose(int semitoneSteps) {

    int ordinal = (ordinal() + semitoneSteps) % 12;
    if (ordinal < 0) {
      ordinal = ordinal + 12;
    }
    for (TonePitch pitch : values()) {
      if (pitch.ordinal() == ordinal) {
        return pitch;
      }
    }
    throw new IllegalCaseException(this.title + "+" + semitoneSteps);
  }
}
