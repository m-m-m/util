/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.date.base.Iso8601UtilImpl;
import net.sf.mmm.util.nls.api.IllegalCaseException;
import net.sf.mmm.util.nls.api.NlsFormatterManager;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.NlsTemplateResolver;
import net.sf.mmm.util.nls.base.AbstractNlsSubFormatter;

/**
 * This is an implementation of {@link net.sf.mmm.util.nls.api.NlsFormatter} for
 * {@link Date}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractNlsFormatterDateIso8601 extends AbstractNlsSubFormatter<Object> {

  /** @see #format(Calendar, Locale, Appendable) */
  private Iso8601Util iso8601Util;

  /**
   * The constructor.
   */
  public AbstractNlsFormatterDateIso8601() {

    this(Iso8601UtilImpl.getInstance());
  }

  /**
   * The constructor.
   * 
   * @param iso8601Util is the {@link Iso8601Util} instance to use.
   */
  public AbstractNlsFormatterDateIso8601(Iso8601Util iso8601Util) {

    super();
    NlsNullPointerException.checkNotNull(Iso8601Util.class, iso8601Util);
    this.iso8601Util = iso8601Util;
  }

  /**
   * This method formats the given <code>calendar</code>.
   * 
   * @param calendar is the calendar to format.
   * @param locale is the locale used for localized formatting.
   * @param buffer is where to append the formatted <code>calendar</code>.
   */
  protected void format(Calendar calendar, Locale locale, Appendable buffer) {

    String type = getType();
    if (NlsFormatterManager.TYPE_DATE.equals(type)) {
      this.iso8601Util.formatDate(calendar, true, buffer);
    } else if (NlsFormatterManager.TYPE_TIME.equals(type)) {
      this.iso8601Util.formatTime(calendar, true, buffer);
      this.iso8601Util.formatTimeZone(calendar, true, buffer);
    } else if (NlsFormatterManager.TYPE_DATETIME.equals(type)) {
      this.iso8601Util.formatDateTime(calendar, true, true, true, buffer);
    } else {
      throw new IllegalCaseException(type);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getStyle() {

    return NlsFormatterManager.STYLE_ISO_8601;
  }

  /**
   * {@inheritDoc}
   */
  public void format(Object object, Locale locale, Map<String, Object> arguments,
      Appendable buffer, NlsTemplateResolver resolver) throws IOException {

    Calendar calendar = null;
    if (object != null) {
      if (object instanceof Calendar) {
        calendar = (Calendar) object;
      } else if (object instanceof Date) {
        calendar = Calendar.getInstance(locale);
        calendar.setTime((Date) object);
      } else if (object instanceof Number) {
        calendar = Calendar.getInstance(locale);
        long millis = ((Number) object).longValue();
        calendar.setTime(new Date(millis));
      }
    }
    if (calendar == null) {
      String string = null;
      if (object != null) {
        string = object.toString();
      }
      if (string == null) {
        string = "null";
      }
      buffer.append(string);
    } else {
      format(calendar, locale, buffer);
    }
  }
}
