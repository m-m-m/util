/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.phone;

import net.sf.mmm.util.exception.api.NlsParseException;
import net.sf.mmm.util.lang.api.AbstractSimpleDatatype;
import net.sf.mmm.util.value.api.ValueOutOfRangeException;

/**
 * This class is a {@link net.sf.mmm.util.lang.api.Datatype} that represents the area code for a phone number.
 * The area code is the part of the phone number that follows after the {@link PhoneCountryCode} and
 * identifies the area (typically the major city) that is to be called. <br>
 * <b>ATTENTION:</b><br>
 * There are countries like Singapore that do not have the concept of area codes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class AreaCode extends AbstractSimpleDatatype<Integer> {

  /** UID for serialization. */
  private static final long serialVersionUID = -4095519482392043680L;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected AreaCode() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param areaCode - see {@link #getAreaCode()}.
   */
  public AreaCode(int areaCode) {

    super(Integer.valueOf(areaCode));
    ValueOutOfRangeException.checkRange(Integer.valueOf(areaCode), Integer.valueOf(0), Integer.valueOf(99999999),
        "area code");
  }

  /**
   * The constructor.
   * 
   * @param areaCode - see {@link #toString()}.
   */
  public AreaCode(String areaCode) {

    this(parseAreaCode(areaCode));
  }

  /**
   * This method parses a {@link AreaCode} given as {@link String} to its {@link #getAreaCode() int
   * representation}.
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
   * This method gets the raw value (the actual area code). E.g. {@code 69} for the city Frankfurt in
   * Germany or {@code 718} for New York City in USA.
   * 
   * @return the country code.
   */
  public int getAreaCode() {

    return getValue().intValue();
  }

}
