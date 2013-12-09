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
   * TODO.
   */
  MAJOR("maj", "major"),

  /**
   * TODO.
   */
  MINOR("min", "minor");

  /** @see #getValue() */
  private final String value;

  /** @see #getTitle() */
  private final String title;

  /**
   * The constructor.
   * 
   * @param value - see {@link #getValue()}.
   * @param title - see {@link #getTitle()}.
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
  public String getTitle() {

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
