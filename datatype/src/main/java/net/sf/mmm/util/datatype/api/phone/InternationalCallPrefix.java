/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.phone;

import java.util.regex.Pattern;

import net.sf.mmm.util.lang.api.AbstractSimpleDatatype;

/**
 * This class is a {@link net.sf.mmm.util.lang.api.Datatype} that represents the international call prefix (also called
 * exit code or IDD code) for a phone number. It is the sequence that has to be dialed before the
 * {@link PhoneCountryCode} to make an international call. The {@link InternationalCallPrefix} is specific for the
 * county the caller is dialing from. Therefore it is typically indicated by the plus sign (+) in internationalized
 * phone numbers. The {@link InternationalCallPrefix} is typically "00" as suggested by the international
 * telecommunications union (ITU). However, some countries have other codes like "011" (NANPA). In Finland there are
 * multiple international call prefixes for different phone carriers. The longest prefix is (so far) 5 digits.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class InternationalCallPrefix extends AbstractSimpleDatatype<String> {

  private static final long serialVersionUID = 8142361938232405037L;

  /**
   * The international symbol for the actual county specific {@link InternationalCallPrefix}.
   */
  public static final String PREFIX_PLUS = "+";

  /** The default {@link InternationalCallPrefix} as recommended by the ITU. */
  public static final String PREFIX_00 = "00";

  /**
   * The {@link InternationalCallPrefix} of the NANPA (North American Numbering Plan).
   */
  public static final String PREFIX_011 = "011";

  /** The {@link Pattern} used to validate the {@link #getValue() value}. */
  private static final Pattern PATTERN_PREFIX = Pattern.compile("[0-9]{1,5}");

  /**
   * The constructor for de-serialization in GWT.
   */
  protected InternationalCallPrefix() {

    super();
  }

  /**
   * The constructor.
   *
   * @param prefix - see {@link #getValue()}.
   */
  public InternationalCallPrefix(String prefix) {

    super(prefix);
    if (!PATTERN_PREFIX.matcher(prefix).matches()) {
      throw new IllegalArgumentException(prefix);
    }
  }

}
