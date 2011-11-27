/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.phone;

import net.sf.mmm.util.lang.api.Datatype;
import net.sf.mmm.util.nls.api.NlsParseException;
import net.sf.mmm.util.value.api.ValueOutOfRangeException;

/**
 * This class is a {@link Datatype} that represents the area code for a phone
 * number. The area code is the part of the phone number that follows after the
 * {@link CountryCode} and identifies the area (typically the major city) that
 * is to be called.<br/>
 * <b>ATTENTION:</b><br/>
 * There are countries like Singapore that do not have the concept of area
 * codes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class AreaCode implements Datatype<Integer> {

  /** UID for serialization. */
  private static final long serialVersionUID = -4095519482392043680L;

  /** @see #getAreaCode() */
  private final int areaCode;

  /**
   * The constructor.
   * 
   * @param areaCode - see {@link #getAreaCode()}.
   */
  public AreaCode(int areaCode) {

    super();
    ValueOutOfRangeException.checkRange(Integer.valueOf(areaCode), Integer.valueOf(0),
        Integer.valueOf(99999999), "area code");
    this.areaCode = areaCode;
  }

  /**
   * The constructor.
   * 
   * @param areaCode - see {@link #getTitle()}.
   */
  public AreaCode(String areaCode) {

    this(parseAreaCode(areaCode));
  }

  /**
   * This method parses a {@link AreaCode} given as {@link String} to its
   * {@link #getAreaCode() int representation}.
   * 
   * @param areaCode is the {@link AreaCode} as {@link String}.
   * @return the {@link AreaCode} as int.
   */
  private static int parseAreaCode(String areaCode) {

    String normalized = areaCode;
    if (normalized.startsWith("(0)")) {
      normalized = normalized.substring(3);
    } else if (normalized.startsWith("(1)")) {
      normalized = normalized.substring(3);
    } else if (normalized.startsWith("0")) {
      normalized = normalized.substring(1);
    }
    if (normalized.startsWith("0")) {
      throw new NlsParseException(areaCode, AreaCode.class);
    }
    try {
      int ac = Integer.parseInt(normalized);
      return ac;
    } catch (NumberFormatException e) {
      throw new NlsParseException(e, areaCode, AreaCode.class);
    }
  }

  /**
   * This method gets the raw value (the actual area code). E.g. <code>69</code>
   * for the city Frankfurt in Germany or <code>718</code> for New York City in
   * USA.
   * 
   * @return the country code.
   */
  public int getAreaCode() {

    return this.areaCode;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {

    if ((obj == null) || (obj.getClass() != AreaCode.class)) {
      return false;
    }
    AreaCode other = (AreaCode) obj;
    return (this.areaCode == other.areaCode);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    return this.areaCode;
  }

  /**
   * {@inheritDoc}
   */
  public Integer getValue() {

    return Integer.valueOf(this.areaCode);
  }

  /**
   * {@inheritDoc}
   */
  public String getTitle() {

    return Integer.toString(this.areaCode);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getTitle();
  }

}
