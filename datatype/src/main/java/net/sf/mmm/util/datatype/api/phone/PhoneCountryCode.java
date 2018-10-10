/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.phone;

import net.sf.mmm.util.lang.api.AbstractSimpleDatatype;

/**
 * This class is a {@link net.sf.mmm.util.lang.api.Datatype} that represents the country code for a phone number. The
 * country code is the prefix of the phone number that identifies the country where the phone is located. <br>
 * If formatted as string the country code is typically prefixed by "+". This stands for the actual
 * {@link InternationalCallPrefix}. <br>
 * <b>ATTENTION:</b><br>
 * The international country codes are historically grown. Therefore it does not actually perform its purpose to
 * identify a country as some countries share the same country code (e.g. USA, Canada, and Puerto Rico all have "1").
 * Further, the length of country codes is not normalized. The length varies from 1 to 4 (excluding the
 * {@link InternationalCallPrefix}). Additionally, some country codes are prefixes of other country codes. Since the
 * zero of the area code is suppressed, the country code of a phone number given as string can only be determined by
 * knowledge of the area codes that has to be up-to-date.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class PhoneCountryCode extends AbstractSimpleDatatype<Integer> {

  private static final long serialVersionUID = 6052425985912842711L;

  /** The maximum {@link #getValue() value}. */
  private static final int MAX_VALUE = 9999;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected PhoneCountryCode() {

    super();
  }

  /**
   * The constructor.
   *
   * @param countryCode - see {@link #getCountryCode()}.
   */
  public PhoneCountryCode(int countryCode) {

    super(Integer.valueOf(countryCode));
    if ((countryCode < 0) || (countryCode > MAX_VALUE)) {
      throw new IllegalArgumentException("" + countryCode);
    }
  }

  /**
   * The constructor.
   *
   * @param countryCode - see {@link #toString()}.
   */
  public PhoneCountryCode(String countryCode) {

    this(parseCountryCode(countryCode));
  }

  /**
   * This method parses a {@link PhoneCountryCode} given as {@link String} to its {@link #getCountryCode() int
   * representation}.
   *
   * @param countryCode is the {@link PhoneCountryCode} as {@link String}.
   * @return the {@link PhoneCountryCode} as int.
   */
  private static int parseCountryCode(String countryCode) {

    String normalized = countryCode;
    if (normalized.startsWith(InternationalCallPrefix.PREFIX_PLUS)) {
      normalized = normalized.substring(1);
    } else if (normalized.startsWith(InternationalCallPrefix.PREFIX_00)) {
      normalized = normalized.substring(2);
    } else if (normalized.startsWith(InternationalCallPrefix.PREFIX_011)) {
      normalized = normalized.substring(3);
    }
    if (normalized.startsWith("0")) {
      throw new IllegalArgumentException(countryCode);
    }
    try {
      int cc = Integer.parseInt(normalized);
      return cc;
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(countryCode, e);
    }
  }

  /**
   * This method gets the raw value (the actual country code). E.g. {@code 49} for the country code of Germany.
   *
   * @return the country code.
   */
  public int getCountryCode() {

    return getValue().intValue();
  }

  @Override
  public String toString() {

    return InternationalCallPrefix.PREFIX_PLUS + getValue();
  }

}
