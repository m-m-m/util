/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.music;

import net.sf.mmm.util.lang.api.SimpleDatatype;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum DiatonicScale implements SimpleDatatype<String> {

  // DORIAN("dor", "dorian"),
  //
  // HYPODORIAN("hdor", "hypodorian"),
  //
  // PHRYGIAN("phr", "phrygian"),
  //
  // HYPOPHRYGIAN("hphr", "hypophrygian"),
  //
  // LYDIAN("lyd", "lydian"),
  //
  // HYPOLYDIAN("hlyd", "hypolydian"),
  //
  // MIXOLYDIAN("mix", "mixolydian"),
  //
  // HYPOMIXOLYDIAN("hmix", "hypomixolydian"),

  /**
   * {@link DiatonicScale Scale} of a major {@link MusicalKey key}. Its scale sequence has semitone intervals
   * from the 3. to the 4. tone as well as from the 7. to the 8. tone (1-1-½-1-1-1-½).
   */
  MAJOR("maj", "major"),

  /**
   * {@link DiatonicScale Scale} of a minor {@link MusicalKey key}. Its scale sequence has semitone intervals
   * from the 2. to the 3. tone as well as from the 6. to the 7. tone (1-½-1-1-½-1).
   */
  MINOR("min", "minor");

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
  private DiatonicScale(String value, String title) {

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
   * This method gets the {@link DiatonicScale} for the given <code>value</code> .
   * 
   * @param value is the {@link #getValue() value} of the requested {@link DiatonicScale}.
   * @return the requested {@link DiatonicScale} or <code>null</code> if no such {@link DiatonicScale} exists.
   */
  public static DiatonicScale fromValue(String value) {

    for (DiatonicScale instance : values()) {
      if (instance.value.equals(value)) {
        return instance;
      }
    }
    return null;
  }

}
