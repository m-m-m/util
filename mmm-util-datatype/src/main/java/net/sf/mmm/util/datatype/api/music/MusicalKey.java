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
   * The {@link MusicalKey} Ces-{@link DiatonicScale#MAJOR major} (with 7 &#9837; signs).
   */
  CES_MAJOR(TonePitch.B_NATURAL, DiatonicScale.MAJOR, "Ces"),

  /**
   * The {@link MusicalKey} as-{@link DiatonicScale#MINOR minor} (with 7 &#9837; signs).
   */
  AS_MINOR(TonePitch.GIS, DiatonicScale.MINOR, "as"),

  /**
   * The {@link MusicalKey} Ges-{@link DiatonicScale#MAJOR major} (with 6 &#9837; signs).
   */
  GES_MAJOR(TonePitch.FIS, DiatonicScale.MAJOR, "Ges"),

  /**
   * The {@link MusicalKey} es-{@link DiatonicScale#MINOR minor} (with 6 &#9837; signs).
   */
  ES_MINOR(TonePitch.DIS, DiatonicScale.MINOR, "es"),

  /**
   * The {@link MusicalKey} Des-{@link DiatonicScale#MAJOR major} (with 5 &#9837; signs).
   */
  DES_MAJOR(TonePitch.CIS, DiatonicScale.MAJOR, "Des"),

  /**
   * The {@link MusicalKey} b&#9837;-{@link DiatonicScale#MINOR minor} (with 5 &#9837; signs).
   */
  B_MINOR(TonePitch.B_FLAT, DiatonicScale.MINOR, "b\u266D"),

  /**
   * The {@link MusicalKey} As-{@link DiatonicScale#MAJOR major} (with 4 &#9837; signs).
   */
  AS_MAJOR(TonePitch.GIS, DiatonicScale.MAJOR, "As"),

  /**
   * The {@link MusicalKey} f-{@link DiatonicScale#MINOR minor} (with 4 &#9837; signs).
   */
  F_MINOR(TonePitch.F, DiatonicScale.MINOR, "f"),

  /**
   * The {@link MusicalKey} Es-{@link DiatonicScale#MAJOR major} (with 3 &#9837; signs).
   */
  ES_MAJOR(TonePitch.DIS, DiatonicScale.MAJOR, "Es"),

  /**
   * The {@link MusicalKey} c-{@link DiatonicScale#MINOR minor} (with 3 &#9837; signs).
   */
  C_MINOR(TonePitch.C, DiatonicScale.MINOR, "c"),

  /**
   * The {@link MusicalKey} B&#9837;-{@link DiatonicScale#MAJOR major} (with 2 &#9837; signs).
   */
  B_MAJOR(TonePitch.B_FLAT, DiatonicScale.MAJOR, "Bb"),

  /**
   * The {@link MusicalKey} g-{@link DiatonicScale#MINOR minor} (with 2 &#9837; signs).
   */
  G_MINOR(TonePitch.G, DiatonicScale.MINOR, "g"),

  /**
   * The {@link MusicalKey} F-{@link DiatonicScale#MAJOR major} (with 1 &#9837; signs).
   */
  F_MAJOR(TonePitch.F, DiatonicScale.MAJOR, "F"),

  /**
   * The {@link MusicalKey} d-{@link DiatonicScale#MINOR minor} (with 1 &#9837; signs).
   */
  D_MINOR(TonePitch.D, DiatonicScale.MINOR, "d"),

  /**
   * The {@link MusicalKey} C-{@link DiatonicScale#MAJOR major} (without any signs).
   */
  C_MAJOR(TonePitch.C, DiatonicScale.MAJOR, "C"),

  /**
   * The {@link MusicalKey} a-{@link DiatonicScale#MINOR minor} (without any signs).
   */
  A_MINOR(TonePitch.A, DiatonicScale.MINOR, "a"),

  /**
   * The {@link MusicalKey} G-{@link DiatonicScale#MAJOR major} (with 1 &#9839; signs).
   */
  G_MAJOR(TonePitch.G, DiatonicScale.MAJOR, "G"),

  /**
   * The {@link MusicalKey} e-{@link DiatonicScale#MINOR minor} (with 1 &#9839; signs).
   */
  E_MINOR(TonePitch.E, DiatonicScale.MINOR, "e"),

  /**
   * The {@link MusicalKey} D-{@link DiatonicScale#MAJOR major} (with 2 &#9839; signs).
   */
  D_MAJOR(TonePitch.D, DiatonicScale.MAJOR, "D"),

  /**
   * The {@link MusicalKey} b&#9838;-{@link DiatonicScale#MINOR minor} (with 2 &#9839; signs).
   */
  H_MINOR(TonePitch.B_NATURAL, DiatonicScale.MINOR, "b\u266E"),

  /**
   * The {@link MusicalKey} A-{@link DiatonicScale#MAJOR major} (with 3 &#9839; signs).
   */
  A_MAJOR(TonePitch.A, DiatonicScale.MAJOR, "A"),

  /**
   * The {@link MusicalKey} fis-{@link DiatonicScale#MINOR minor} (with 3 &#9839; signs).
   */
  FIS_MINOR(TonePitch.FIS, DiatonicScale.MINOR, "fis"),

  /**
   * The {@link MusicalKey} E-{@link DiatonicScale#MAJOR major} (with 4 &#9839; signs).
   */
  E_MAJOR(TonePitch.E, DiatonicScale.MAJOR, "E"),

  /**
   * The {@link MusicalKey} cis-{@link DiatonicScale#MINOR minor} (with 4 &#9839; signs).
   */
  CIS_MINOR(TonePitch.CIS, DiatonicScale.MINOR, "cis"),

  /**
   * The {@link MusicalKey} B&#9838;-{@link DiatonicScale#MAJOR major} (with 5 &#9839; signs).
   */
  H_MAJOR(TonePitch.B_NATURAL, DiatonicScale.MAJOR, "B\u266E"),

  /**
   * The {@link MusicalKey} gis-{@link DiatonicScale#MINOR minor} (with 5 &#9839; signs).
   */
  GIS_MINOR(TonePitch.GIS, DiatonicScale.MINOR, "gis"),

  /**
   * The {@link MusicalKey} Fis-{@link DiatonicScale#MAJOR major} (with 6 &#9839; signs).
   */
  FIS_MAJOR(TonePitch.FIS, DiatonicScale.MAJOR, "Fis"),

  /**
   * The {@link MusicalKey} dis-{@link DiatonicScale#MINOR minor} (with 6 &#9839; signs).
   */
  DIS_MINOR(TonePitch.DIS, DiatonicScale.MINOR, "dis"),

  /**
   * The {@link MusicalKey} Cis-{@link DiatonicScale#MAJOR major} (with 7 &#9839; signs). The enharmonic
   * identical {@link MusicalKey key} is {@link #DES_MAJOR}.
   */
  CIS_MAJOR(TonePitch.CIS, DiatonicScale.MAJOR, "Cis"),

  /**
   * The {@link MusicalKey} ais-{@link DiatonicScale#MAJOR major} (with 7 &#9839; signs). The enharmonic
   * identical {@link MusicalKey key} is {@link #B_MINOR}.
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
  @Override
  public String toString() {

    return getValue() + "-" + this.scale.toString();
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
