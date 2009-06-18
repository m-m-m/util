/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import net.sf.mmm.util.nls.base.AbstractNlsFormatter;

/**
 * This is an implementation of {@link net.sf.mmm.util.nls.api.NlsFormatter} for
 * {@link Date}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractNlsFormatterDate extends AbstractNlsFormatter<Object> {

  /**
   * The constructor.
   */
  public AbstractNlsFormatterDate() {

    super();
  }

  /**
   * This method formats the given <code>calendar</code>.
   * 
   * @param calendar is the calendar to format.
   * @param locale is the locale used for localized formatting.
   * @param buffer is where to append the formatted <code>calendar</code>.
   */
  protected abstract void format(Calendar calendar, Locale locale, Appendable buffer);

  /**
   * {@inheritDoc}
   */
  public void format(Object object, Locale locale, Appendable buffer) {

    try {
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
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }
}
