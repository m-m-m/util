/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.address;

import java.util.Locale;
import java.util.regex.Pattern;

import net.sf.mmm.util.lang.api.AbstractSimpleDatatype;
import net.sf.mmm.util.nls.api.NlsParseException;

/**
 * This class is a {@link net.sf.mmm.util.lang.api.Datatype} that represents a 2 letter code representing a
 * country of the world according to ISO 3116.
 * 
 * @see java.util.Locale#getCountry()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class Iso2CountryCode extends AbstractSimpleDatatype<String> {

  /** UID for serialization. */
  private static final long serialVersionUID = 6243639424166029515L;

  /** @see #Iso2CountryCode(String) */
  private static final Pattern PATTERN_COUNTRY_CODE = Pattern.compile("[A-Z]{2}");

  /** @see #toString() */
  private String title;

  /**
   * The constructor for de-serialization.
   */
  protected Iso2CountryCode() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() value} and has to consist of exactly two Latin letters.
   */
  public Iso2CountryCode(String value) {

    super(value.toUpperCase(Locale.US));
    if (!PATTERN_COUNTRY_CODE.matcher(getValue()).matches()) {
      throw new NlsParseException(value, PATTERN_COUNTRY_CODE.pattern(), Iso2CountryCode.class);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    if (this.title == null) {
      this.title = new Locale("", getValue()).getDisplayCountry();
    }
    return this.title;
  }
}
