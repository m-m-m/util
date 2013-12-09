/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.music;

import net.sf.mmm.util.lang.api.SimpleDatatype;

/**
 * This enum represents the musical key.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum MusicalKey implements SimpleDatatype<String> {

  /**
   * TODO.
   */
  CES_MAJOR(TonePitch.H, DiatonicScale.MAJOR, "Ces"),

  /**
   * TODO.
   */
  AS_MINOR(TonePitch.GIS, DiatonicScale.MINOR, "as"),

  /**
   * TODO.
   */
  GES_MAJOR(TonePitch.FIS, DiatonicScale.MAJOR, "Ges"),

  /**
   * TODO.
   */
  ES_MINOR(TonePitch.DIS, DiatonicScale.MINOR, "es"),

  /**
   * TODO.
   */
  DES_MAJOR(TonePitch.CIS, DiatonicScale.MAJOR, "Des"),

  /**
   * TODO.
   */
  B_MINOR(TonePitch.BB, DiatonicScale.MINOR, "bb"),

  /**
   * TODO.
   */
  AS_MAJOR(TonePitch.GIS, DiatonicScale.MAJOR, "As"),

  /**
   * TODO.
   */
  F_MINOR(TonePitch.F, DiatonicScale.MINOR, "f"),

  /**
   * TODO.
   */
  ES_MAJOR(TonePitch.DIS, DiatonicScale.MAJOR, "Es"),

  /**
   * TODO.
   */
  C_MINOR(TonePitch.C, DiatonicScale.MINOR, "c"),

  /**
   * TODO.
   */
  B_MAJOR(TonePitch.BB, DiatonicScale.MAJOR, "Bb"),

  /**
   * TODO.
   */
  G_MINOR(TonePitch.G, DiatonicScale.MINOR, "g"),

  /**
   * TODO.
   */
  F_MAJOR(TonePitch.F, DiatonicScale.MAJOR, "F"),

  /**
   * TODO.
   */
  D_MINOR(TonePitch.D, DiatonicScale.MINOR, "d"),

  /**
   * TODO.
   */
  C_MAJOR(TonePitch.C, DiatonicScale.MAJOR, "C"),

  /**
   * TODO.
   */
  A_MINOR(TonePitch.A, DiatonicScale.MINOR, "a"),

  /**
   * TODO.
   */
  G_MAJOR(TonePitch.G, DiatonicScale.MAJOR, "G"),

  /**
   * TODO
   */
  E_MINOR(TonePitch.E, DiatonicScale.MINOR, "e"),

  /**
   * TODO.
   */
  D_MAJOR(TonePitch.D, DiatonicScale.MAJOR, "D"),

  /**
   * TODO.
   */
  H_MINOR(TonePitch.H, DiatonicScale.MINOR, "h"),

  /**
   * TODO.
   */
  A_MAJOR(TonePitch.A, DiatonicScale.MAJOR, "A"),

  /**
   * TODO.
   */
  FIS_MINOR(TonePitch.FIS, DiatonicScale.MINOR, "fis"),

  /**
   * TODO.
   */
  E_MAJOR(TonePitch.BB, DiatonicScale.MAJOR, "E"),

  /**
   * TODO.
   */
  CIS_MINOR(TonePitch.CIS, DiatonicScale.MINOR, "cis"),

  /**
   * TODO.
   */
  H_MAJOR(TonePitch.H, DiatonicScale.MAJOR, "H"),

  /**
   * TODO.
   */
  GIS_MINOR(TonePitch.GIS, DiatonicScale.MINOR, "gis"),

  /**
   * TODO.
   */
  FIS_MAJOR(TonePitch.FIS, DiatonicScale.MAJOR, "Fis"),

  /**
   * TODO.
   */
  DIS_MINOR(TonePitch.DIS, DiatonicScale.MINOR, "dis"),

  /**
   * TODO.
   */
  AIS_MINOR(TonePitch.GIS, DiatonicScale.MINOR, "ais");

  /** @see #getTonika() */
  private final TonePitch tonika;

  /** @see #getScale() */
  private final DiatonicScale scale;

  /** @see #getValue() */
  private final String value;

  /**
   * The constructor.
   * 
   * @param tonika - see {@link #getTonika()}.
   * @param scale - see {@link #getScale()}.
   * @param value - see {@link #getValue()}.
   */
  private MusicalKey(TonePitch tonika, DiatonicScale scale, String value) {

    this.tonika = tonika;
    this.scale = scale;
    this.value = value;
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

    return getValue() + "-" + this.scale.getTitle();
  }

  /**
   * This method gets the {@link DiatonicScale} of this key.
   * 
   * @return the {@link DiatonicScale}.
   */
  public DiatonicScale getScale() {

    return this.scale;
  }

  /**
   * This method gets the {@link TonePitch} of the tonika (base-tone) for this key.
   * 
   * @return the {@link TonePitch}.
   */
  public TonePitch getTonika() {

    return this.tonika;
  }

  // public List<TonePitch> get

  /**
   * This method gets the {@link MusicalKey} for the given <code>value</code>.
   * 
   * @param value is the {@link #getValue() value} of the requested {@link MusicalKey}.
   * @return the requested {@link MusicalKey} or <code>null</code> if no such {@link MusicalKey} exists.
   */
  public static MusicalKey fromValue(String value) {

    for (MusicalKey instance : values()) {
      if (instance.value.equals(value)) {
        return instance;
      }
    }
    return null;
  }

}
