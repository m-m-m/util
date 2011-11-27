/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.phone;

import net.sf.mmm.util.lang.api.Datatype;
import net.sf.mmm.util.nls.api.NlsParseException;
import net.sf.mmm.util.value.api.ValueOutOfRangeException;

/**
 * This class is a {@link Datatype} that represents the country code for a phone
 * number. The country code is the prefix of the phone number that identifies
 * the country where the phone is located.<br/>
 * If formatted as string the country code is typically prefixed by "+". This
 * stands for the actual {@link InternationalCallPrefix}.<br/>
 * <b>ATTENTION:</b><br/>
 * The international country codes are historically grown. Therefore it does not
 * actually perform its purpose to identify a country as some countries share
 * the same country code (e.g. USA, Canada, and Puerto Rico all have "1").
 * Further, the length of country codes is not normalized. The length varies
 * from 1 to 4 (excluding the {@link InternationalCallPrefix}). Additionally,
 * some country codes are prefixes of other country codes. Since the zero of the
 * area code is suppressed, the country code of a phone number given as string
 * can only be determined by knowledge of the area codes that has to be
 * up-to-date.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class CountryCode implements Datatype<Integer> {

  /** UID for serialization. */
  private static final long serialVersionUID = 6052425985912842711L;

  /** @see #getCountryCode() */
  private final int countyCode;

  /**
   * The constructor.
   * 
   * @param countryCode - see {@link #getCountryCode()}.
   */
  public CountryCode(int countryCode) {

    super();
    ValueOutOfRangeException.checkRange(Integer.valueOf(countryCode), Integer.valueOf(0),
        Integer.valueOf(9999), "country code");
    this.countyCode = countryCode;
  }

  /**
   * The constructor.
   * 
   * @param countryCode - see {@link #getTitle()}.
   */
  public CountryCode(String countryCode) {

    this(parseCountryCode(countryCode));
  }

  /**
   * This method parses a {@link CountryCode} given as {@link String} to its
   * {@link #getCountryCode() int representation}.
   * 
   * @param countryCode is the {@link CountryCode} as {@link String}.
   * @return the {@link CountryCode} as int.
   */
  private static int parseCountryCode(String countryCode) {

    String normalized = countryCode;
    if (normalized.startsWith(InternationalCallPrefix.PREFIX_PLUS)) {
      normalized = normalized.substring(1);
    } else if (normalized.startsWith(InternationalCallPrefix.PREFIX_00)) {
      normalized = normalized.substring(2);
    }
    if (normalized.startsWith("0")) {
      throw new NlsParseException(countryCode, CountryCode.class);
    }
    try {
      int cc = Integer.parseInt(normalized);
      return cc;
    } catch (NumberFormatException e) {
      throw new NlsParseException(e, countryCode, CountryCode.class);
    }
  }

  /**
   * This method gets the raw value (the actual country code). E.g.
   * <code>49</code> for the country code of Germany.
   * 
   * @return the country code.
   */
  public int getCountryCode() {

    return this.countyCode;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {

    if ((obj == null) || (obj.getClass() != CountryCode.class)) {
      return false;
    }
    CountryCode other = (CountryCode) obj;
    return (this.countyCode == other.countyCode);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    return this.countyCode;
  }

  /**
   * {@inheritDoc}
   */
  public Integer getValue() {

    return Integer.valueOf(this.countyCode);
  }

  /**
   * {@inheritDoc}
   */
  public String getTitle() {

    return InternationalCallPrefix.PREFIX_PLUS + this.countyCode;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getTitle();
  }

}
