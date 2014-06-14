/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl.formatter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.exception.api.IllegalCaseException;
import net.sf.mmm.util.lang.api.Formatter;
import net.sf.mmm.util.lang.base.AbstractFormatter;
import net.sf.mmm.util.nls.api.NlsFormatterManager;

/**
 * This class provides {@link Formatter}s for various types. It contains the formatting logic for various
 * implementations of {@link net.sf.mmm.util.nls.base.SimpleNlsFormatter}. This way it is easier to maintain
 * GWT compatibility by replacing this class.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public final class FormatterProvider {

  /**
   * Construction prohibited.
   */
  private FormatterProvider() {

    super();
  }

  /**
   * This method converts the given {@link net.sf.mmm.util.nls.api.NlsFormatterPlugin#getStyle() style} to the
   * according {@link DateFormat}-style constant.
   * 
   * @param style is the {@link net.sf.mmm.util.nls.api.NlsFormatterPlugin#getStyle() style} to convert.
   * @return the according {@link DateFormat}-style constant (e.g. {@link DateFormat#MEDIUM}).
   */
  private static int convertStyle(String style) {

    if (style.equals(NlsFormatterManager.STYLE_SHORT)) {
      return DateFormat.SHORT;
    } else if (style.equals(NlsFormatterManager.STYLE_LONG)) {
      return DateFormat.LONG;
    } else if (style.equals(NlsFormatterManager.STYLE_MEDIUM)) {
      return DateFormat.MEDIUM;
    } else if (style.equals(NlsFormatterManager.STYLE_FULL)) {
      return DateFormat.FULL;
    } else {
      throw new IllegalCaseException(style);
    }
  }

  /**
   * @param locale is the {@link Locale}.
   * @param type is the {@link net.sf.mmm.util.nls.api.NlsFormatterPlugin#getType() type} (must be one of
   *        {@link NlsFormatterManager#TYPE_DATE}, {@link NlsFormatterManager#TYPE_TIME},
   *        {@link NlsFormatterManager#TYPE_DATETIME}).
   * @param style is the {@link net.sf.mmm.util.nls.api.NlsFormatterPlugin#getStyle() style}.
   * @return the {@link FormatterProvider} for {@link Integer}.
   */
  public static Formatter<Object> getDateFormatter(Locale locale, String type, String style) {

    DateFormat format;
    int dateStyle = convertStyle(style);
    if (NlsFormatterManager.TYPE_DATE.equals(type)) {
      format = DateFormat.getDateInstance(dateStyle, locale);
    } else if (NlsFormatterManager.TYPE_TIME.equals(type)) {
      format = DateFormat.getTimeInstance(dateStyle, locale);
    } else if (NlsFormatterManager.TYPE_DATETIME.equals(type)) {
      format = DateFormat.getDateTimeInstance(dateStyle, dateStyle, locale);
    } else {
      throw new IllegalCaseException(type);
    }
    return new FormatFormatter(format);
  }

  /**
   * @param locale is the {@link Locale}.
   * @param pattern is the custom format pattern.
   * @return the {@link FormatterProvider} for {@link Integer}.
   */
  public static Formatter<Object> getDateFormatter(Locale locale, String pattern) {

    return new FormatFormatter(new SimpleDateFormat(pattern, locale));
  }

  /**
   * @param locale is the {@link Locale}.
   * @param type is the {@link net.sf.mmm.util.nls.api.NlsFormatterPlugin#getType() type} (must be one of
   *        {@link NlsFormatterManager#TYPE_DATE}, {@link NlsFormatterManager#TYPE_TIME},
   *        {@link NlsFormatterManager#TYPE_DATETIME}).
   * @param iso8601Util is the {@link Iso8601Util}.
   * @return the {@link FormatterProvider} for {@link Integer}.
   */
  public static Formatter<Object> getDateFormatter(Locale locale, String type, Iso8601Util iso8601Util) {

    return new Iso8601Formatter(locale, iso8601Util, type);
  }

  /**
   * @param locale is the {@link Locale}.
   * @return the {@link FormatterProvider} for {@link Integer}.
   */
  public static Formatter<Object> getIntegerFormatter(Locale locale) {

    return new FormatFormatter(NumberFormat.getIntegerInstance(locale));
  }

  /**
   * @param locale is the {@link Locale}.
   * @return the {@link FormatterProvider} for {@link Number}.
   */
  public static Formatter<Object> getNumberFormatter(Locale locale) {

    return new FormatFormatter(NumberFormat.getInstance(locale));
  }

  /**
   * @param locale is the {@link Locale}.
   * @param pattern is the {@link DecimalFormat format pattern} for the {@link Number}.
   * @return the {@link FormatterProvider} for {@link Number}.
   */
  public static Formatter<Object> getNumberFormatter(Locale locale, String pattern) {

    // only available since java 6
    // DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance(locale)
    DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
    return new FormatFormatter(new DecimalFormat(pattern, symbols));
  }

  /**
   * @param locale is the {@link Locale}.
   * @return the {@link FormatterProvider} for a {@link Number} as currency.
   */
  public static Formatter<Object> getCurrencyFormatter(Locale locale) {

    return new FormatFormatter(NumberFormat.getCurrencyInstance(locale));
  }

  /**
   * @param locale is the {@link Locale}.
   * @return the {@link FormatterProvider} for a {@link Number} in percent.
   */
  public static Formatter<Object> getPercentFormatter(Locale locale) {

    return new FormatFormatter(NumberFormat.getPercentInstance(locale));
  }

  /**
   * {@link Formatter} for {@link Date} or {@link Calendar} using {@link Iso8601Util}.
   */
  private static class Iso8601Formatter extends AbstractFormatter<Object> {

    /** Is the {@link Iso8601Util}. */
    private final Iso8601Util iso8601Util;

    /** The {@link net.sf.mmm.util.nls.api.NlsFormatterPlugin#getType() type}. */
    private final String type;

    /** The {@link Locale} to use. */
    private final Locale locale;

    /**
     * The constructor.
     * 
     * @param locale is the {@link Locale} to use.
     * @param iso8601Util is the {@link Iso8601Util}.
     * @param type is the {@link net.sf.mmm.util.nls.api.NlsFormatterPlugin#getType() type}.
     */
    public Iso8601Formatter(Locale locale, Iso8601Util iso8601Util, String type) {

      super();
      this.locale = locale;
      this.iso8601Util = iso8601Util;
      this.type = type;
    }

    /**
     * 
     * @param object is the value to format that should be converted to {@link Calendar}.
     * @return the {@link Calendar}.
     */
    private Calendar convertObject(Object object) {

      Calendar calendar = null;
      if (object != null) {
        if (object instanceof Calendar) {
          calendar = (Calendar) object;
        } else if (object instanceof Date) {
          calendar = Calendar.getInstance(this.locale);
          calendar.setTime((Date) object);
        } else if (object instanceof Number) {
          calendar = Calendar.getInstance(this.locale);
          long millis = ((Number) object).longValue();
          calendar.setTime(new Date(millis));
        }
      }
      return calendar;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doFormat(Object value, Appendable buffer) throws IOException {

      Calendar calendar = convertObject(value);
      if (calendar == null) {
        // fallback
        buffer.append(value.toString());
      } else {
        if (NlsFormatterManager.TYPE_DATE.equals(this.type)) {
          this.iso8601Util.formatDate(calendar, true, buffer);
        } else if (NlsFormatterManager.TYPE_TIME.equals(this.type)) {
          this.iso8601Util.formatTime(calendar, true, buffer);
          this.iso8601Util.formatTimeZone(calendar, true, buffer);
        } else if (NlsFormatterManager.TYPE_DATETIME.equals(this.type)) {
          this.iso8601Util.formatDateTime(calendar, true, true, true, buffer);
        } else {
          throw new IllegalCaseException(this.type);
        }
      }
    }
  }

  /**
   * {@link Formatter} using {@link Format}.
   */
  private static class FormatFormatter extends AbstractFormatter<Object> {

    /** @see #format(Object) */
    private final Format format;

    /**
     * The constructor.
     * 
     * @param format is the {@link Format} to adapt.
     */
    public FormatFormatter(Format format) {

      super();
      this.format = format;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doFormat(Object value, Appendable buffer) throws IOException {

      buffer.append(this.format.format(value));
    }

  }

}
