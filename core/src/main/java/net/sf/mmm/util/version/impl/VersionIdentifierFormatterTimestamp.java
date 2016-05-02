/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.version.impl;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.version.api.VersionIdentifier;

/**
 * This is the implementation of {@link net.sf.mmm.util.lang.api.Formatter} for the
 * {@link VersionIdentifier#getTimestamp() timestamp}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class VersionIdentifierFormatterTimestamp extends AbstractVersionIdentifierFormatterString {

  /** The {@link Iso8601Util} instance. */
  private Iso8601Util iso8601Util;

  /** The {@link DateFormat} to use. */
  private DateFormat dateFormat;

  /**
   * The constructor.
   * 
   * @param iso8601Util is the {@link Iso8601Util} instance. May be {@code null} is
   *        {@code dateFormat} is given.
   * @param dateFormat is the {@link DateFormat} to use. If {@code null} {@code iso8601Util} will be
   *        used instead.
   * @param prefix is the static prefix to append before the {@link VersionIdentifier#getTimestamp()
   *        timestamp}. Will be omitted if {@link VersionIdentifier#getTimestamp() timestamp} is
   *        {@code null}.
   */
  public VersionIdentifierFormatterTimestamp(Iso8601Util iso8601Util, DateFormat dateFormat, String prefix) {

    super(prefix, Integer.MAX_VALUE);
    this.iso8601Util = iso8601Util;
    this.dateFormat = dateFormat;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getString(VersionIdentifier value) {

    Date date = value.getTimestamp();
    String result = null;
    if (date != null) {
      if (this.dateFormat != null) {
        result = this.dateFormat.format(date);
      } else {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        result = this.iso8601Util.formatDateTime(calendar, false, false, false);
      }
    }
    return result;
  }

}
