/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.person;

import net.sf.mmm.util.lang.api.Datatype;

/**
 * The enum represents the gender of a natural person.
 * 
 * @see #MALE
 * @see #FEMALE
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum Gender implements Datatype<String> {

  /**
   * The gender of a male person (a mister).
   */
  MALE("M", "male"),

  /**
   * The gender of a female person (a misses).
   */
  FEMALE("F", "female");

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
  private Gender(String value, String title) {

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
   * This method gets the {@link Gender} for the given <code>value</code>.
   * 
   * @param value is the {@link #getValue() value} of the requested
   *        {@link Gender}.
   * @return the requested {@link Gender} or <code>null</code> if no such
   *         {@link Gender} exists.
   */
  public static Gender fromValue(String value) {

    for (Gender gender : values()) {
      if (gender.value.equals(value)) {
        return gender;
      }
    }
    return null;
  }

}
