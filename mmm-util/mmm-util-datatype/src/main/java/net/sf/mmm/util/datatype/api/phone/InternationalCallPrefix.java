/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.phone;

import java.util.regex.Pattern;

import net.sf.mmm.util.lang.api.Datatype;
import net.sf.mmm.util.nls.api.NlsParseException;

/**
 * This class is a {@link Datatype} that represents the international call
 * prefix (also called exit code or IDD code) for a phone number. It is the
 * sequence that has to be dialed before the {@link CountryCode} to make an
 * international call. The {@link InternationalCallPrefix} is specific for the
 * county the caller is dialing from. Therefore it is typically indicated by the
 * plus sign (+) in internationalized phone numbers. The
 * {@link InternationalCallPrefix} is typically "00" as suggested by the
 * international telecommunications union (ITU). However, some countries have
 * other codes like "011" (NANPA). In Finland there are multiple international
 * call prefixes for different phone carriers. The longest prefix is (so far) 5
 * digits.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class InternationalCallPrefix implements Datatype<String> {

  /** UID for serialization. */
  private static final long serialVersionUID = 8142361938232405037L;

  /**
   * The international symbol for the actual county specific
   * {@link InternationalCallPrefix}.
   */
  public static final String PREFIX_PLUS = "+";

  /** The default {@link InternationalCallPrefix} as recommended by the ITU. */
  public static final String PREFIX_00 = "00";

  /** The {@link Pattern} used to validate the {@link #getValue() value}. */
  private static final Pattern PATTERN_PREFIX = Pattern.compile("[0-9]{1,5}");

  /** @see #getValue() */
  private final String prefix;

  /**
   * The constructor.
   * 
   * @param prefix - see {@link #getValue()}.
   */
  public InternationalCallPrefix(String prefix) {

    super();
    if (!PATTERN_PREFIX.matcher(prefix).matches()) {
      throw new NlsParseException(prefix, PATTERN_PREFIX.pattern(), InternationalCallPrefix.class);
    }
    this.prefix = prefix;
  }

  /**
   * {@inheritDoc}
   */
  public String getValue() {

    return this.prefix;
  }

  /**
   * {@inheritDoc}
   */
  public String getTitle() {

    return this.prefix;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {

    if ((obj == null) || (obj.getClass() != CountryCode.class)) {
      return false;
    }
    InternationalCallPrefix other = (InternationalCallPrefix) obj;
    return (this.prefix == other.prefix);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    return this.prefix.hashCode();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getTitle();
  }

}
