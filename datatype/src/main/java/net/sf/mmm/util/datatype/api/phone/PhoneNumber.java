/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.datatype.api.phone;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.mmm.util.exception.api.NlsParseException;
import net.sf.mmm.util.lang.api.AbstractSimpleDatatypeBase;

/**
 * This is the interface for a <em>phone number</em>. In this context phone number means an international number that
 * potentially connects to an end-point via the phone network (PTSIN, VoIP, etc.) from anywhere in the world. This means
 * that some numbers accepted by your phone may NOT be valid in the sense of this datatype (e.g. {@code "**6#1"}). On
 * the other hand a phone number that is syntactically correct but does not point to an existing line, will still be
 * accepted (e.g. "+49 69 987654321"). <br>
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class PhoneNumber extends AbstractSimpleDatatypeBase<String> {

  private static final long serialVersionUID = -6395551467951327196L;

  private static final Pattern PATTERN_PHONENUMBER = Pattern.compile(
      "((([+]|00|011)([1-9][0-9]{1,3}))[/ -])?([(][01][)])?([0-9]{1,6})[/ -]([0-9/ -]{1,11})([-]([0-9]{1,8}))?");

  private static final Pattern PATTERN_PHONENUMBER_FREEFORM = Pattern.compile("[+]?[0-9*#~]+[0-9*#/ -]*[0-9*#]+");

  private PhoneCountryCode countryCode;

  private AreaCode areaCode;

  private String localNumber;

  private String extension;

  private String phoneNumber;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected PhoneNumber() {

    super();
  }

  /**
   * The constructor.
   *
   * @param phoneNumber is the phone number as string {@link #getValue() value}.
   */
  public PhoneNumber(String phoneNumber) {

    this(phoneNumber, null);
  }

  /**
   * The constructor.
   *
   * @param phoneNumber is the phone number as string {@link #getValue() value}.
   * @param countryCode is the {@link #getCountryCode() country code} to use if none is supplied by {@code phoneNumber}.
   */
  public PhoneNumber(String phoneNumber, PhoneCountryCode countryCode) {

    super();
    Matcher matcher = PATTERN_PHONENUMBER.matcher(phoneNumber);
    if (matcher.matches()) {
      String countryCodeString = matcher.group(2);
      if (countryCodeString == null) {
        if (countryCode == null) {
          throw new NlsParseException(phoneNumber, PhoneNumber.class);
        }
        this.countryCode = countryCode;
      } else {
        this.countryCode = new PhoneCountryCode(countryCodeString);
      }
      this.areaCode = new AreaCode(matcher.group(6));
      this.localNumber = matcher.group(7);
      this.extension = matcher.group(9);
      this.phoneNumber = formatPhoneNumber();
    } else {
      if (!PATTERN_PHONENUMBER_FREEFORM.matcher(phoneNumber).matches()) {
        throw new NlsParseException(phoneNumber, PATTERN_PHONENUMBER_FREEFORM.pattern(), PhoneNumber.class);
      }
      this.phoneNumber = phoneNumber;
      this.countryCode = null;
      this.areaCode = null;
      this.localNumber = null;
      this.extension = null;
    }
  }

  /**
   * The constructor.
   *
   * @param countryCode - see {@link #getCountryCode()}.
   * @param areaCode - see {@link #getAreaCode()}.
   * @param localNumber - see {@link #getLocalNumber()}.
   * @param extension - see {@link #getExtension()}.
   */
  public PhoneNumber(PhoneCountryCode countryCode, AreaCode areaCode, String localNumber, String extension) {

    super();
    this.countryCode = countryCode;
    this.areaCode = areaCode;
    this.localNumber = localNumber;
    this.extension = extension;
    this.phoneNumber = formatPhoneNumber();
  }

  /**
   * This method formats the phone number as string {@link #getValue() value}.
   *
   * @return the formatted phone number.
   */
  private String formatPhoneNumber() {

    StringBuilder buffer = new StringBuilder();
    buffer.append(this.countryCode);
    buffer.append(' ');
    buffer.append(this.areaCode);
    buffer.append(' ');
    buffer.append(this.localNumber);
    if ((this.extension != null) && (this.extension.length() > 0)) {
      buffer.append('-');
      buffer.append(this.extension);
    }
    return buffer.toString();
  }

  /**
   * This method gets the {@link PhoneCountryCode} of the {@link PhoneNumber}.
   *
   * @return the {@link PhoneCountryCode}.
   */
  public PhoneCountryCode getCountryCode() {

    return this.countryCode;
  }

  /**
   * This method gets the {@link AreaCode} of the {@link PhoneNumber}. Within the county (or set of countries)
   * identified by the {@link #getCountryCode()}.
   *
   * @return the {@link AreaCode}.
   */
  public AreaCode getAreaCode() {

    return this.areaCode;
  }

  /**
   * This method gets the local number (subscriber number) excluding the {@link #getExtension() extension}.
   *
   * @return the subscriber number.
   */
  public String getLocalNumber() {

    return this.localNumber;
  }

  /**
   * This method gets the extension (also called DDI - direct dial in). Phone customers that have multiple phone numbers
   * typically have a common prefix for all their phone numbers and only the last digits differ. In this case these
   * group of digits is called extension. Typically this is separated by a hyphen character ('-') when the phone number
   * is formatted.
   *
   * @return the extension.
   */
  public String getExtension() {

    return this.extension;
  }

  /**
   * This method gets the actual phone number (e.g. "+49 (0)69 987654-321").
   *
   * {@inheritDoc}
   */
  @Override
  public String getValue() {

    return this.phoneNumber;
  }

  @Override
  public String toString() {

    return this.phoneNumber;
  }

}
