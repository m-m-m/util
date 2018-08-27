/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl.formatter;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

import net.sf.mmm.util.date.api.Iso8601Util;
import net.sf.mmm.util.lang.api.Formatter;
import net.sf.mmm.util.lang.base.AbstractFormatter;
import net.sf.mmm.util.nls.api.NlsFormatterManager;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.i18n.client.NumberFormat;

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
   * @param locale is the {@link Locale}.
   * @param type is the {@link net.sf.mmm.util.nls.api.NlsFormatterPlugin#getType() type} (must be one of
   *        {@link NlsFormatterManager#TYPE_DATE}, {@link NlsFormatterManager#TYPE_TIME},
   *        {@link NlsFormatterManager#TYPE_DATETIME}).
   * @param style is the {@link net.sf.mmm.util.nls.api.NlsFormatterPlugin#getStyle() style}.
   * @return the {@link FormatterProvider} for {@link Integer}.
   */
  public static Formatter<Object> getDateFormatter(Locale locale, String type, String style) {

    PredefinedFormat format = null;
    if (NlsFormatterManager.TYPE_DATE.equals(type)) {
      if (NlsFormatterManager.STYLE_FULL.equals(style)) {
        format = PredefinedFormat.DATE_FULL;
      } else if (NlsFormatterManager.STYLE_LONG.equals(style)) {
        format = PredefinedFormat.DATE_LONG;
      } else if (NlsFormatterManager.STYLE_MEDIUM.equals(style)) {
        format = PredefinedFormat.DATE_MEDIUM;
      } else if (NlsFormatterManager.STYLE_SHORT.equals(style)) {
        format = PredefinedFormat.DATE_SHORT;
      }
    } else if (NlsFormatterManager.TYPE_TIME.equals(type)) {
      if (NlsFormatterManager.STYLE_FULL.equals(style)) {
        format = PredefinedFormat.TIME_FULL;
      } else if (NlsFormatterManager.STYLE_LONG.equals(style)) {
        format = PredefinedFormat.TIME_LONG;
      } else if (NlsFormatterManager.STYLE_MEDIUM.equals(style)) {
        format = PredefinedFormat.TIME_MEDIUM;
      } else if (NlsFormatterManager.STYLE_SHORT.equals(style)) {
        format = PredefinedFormat.TIME_SHORT;
      }
    } else if (NlsFormatterManager.TYPE_DATETIME.equals(type)) {
      if (NlsFormatterManager.STYLE_FULL.equals(style)) {
        format = PredefinedFormat.DATE_TIME_FULL;
      } else if (NlsFormatterManager.STYLE_LONG.equals(style)) {
        format = PredefinedFormat.DATE_TIME_LONG;
      } else if (NlsFormatterManager.STYLE_MEDIUM.equals(style)) {
        format = PredefinedFormat.DATE_TIME_MEDIUM;
      } else if (NlsFormatterManager.STYLE_SHORT.equals(style)) {
        format = PredefinedFormat.DATE_TIME_SHORT;
      }
    } else {
      throw new IllegalArgumentException(type);
    }
    if (format == null) {
      throw new IllegalArgumentException(style);
    }
    return new DateTimeFormatFormatter(DateTimeFormat.getFormat(format));
  }

  /**
   * @param locale is the {@link Locale}.
   * @param pattern is the custom format pattern.
   * @return the {@link FormatterProvider} for {@link Integer}.
   */
  public static Formatter<Object> getDateFormatter(Locale locale, String pattern) {

    return new DateTimeFormatFormatter(DateTimeFormat.getFormat(pattern));
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

    return new Iso8601Formatter(iso8601Util, type);
  }

  /**
   * @param locale is the {@link Locale}.
   * @return the {@link FormatterProvider} for {@link Integer}.
   */
  public static Formatter<Object> getIntegerFormatter(Locale locale) {

    return new NumberFormatFormatter(NumberFormat.getDecimalFormat());
  }

  /**
   * @param locale is the {@link Locale}.
   * @return the {@link FormatterProvider} for {@link Number}.
   */
  public static Formatter<Object> getNumberFormatter(Locale locale) {

    return new NumberFormatFormatter(NumberFormat.getDecimalFormat());
  }

  /**
   * @param locale is the {@link Locale}.
   * @param pattern is the {@link NumberFormat#getFormat(String) format pattern} for the {@link Number}.
   * @return the {@link FormatterProvider} for {@link Number}.
   */
  public static Formatter<Object> getNumberFormatter(Locale locale, String pattern) {

    return new NumberFormatFormatter(NumberFormat.getFormat(pattern));
  }

  /**
   * @param locale is the {@link Locale}.
   * @return the {@link FormatterProvider} for a {@link Number} as currency.
   */
  public static Formatter<Object> getCurrencyFormatter(Locale locale) {

    return new NumberFormatFormatter(NumberFormat.getCurrencyFormat());
  }

  /**
   * @param locale is the {@link Locale}.
   * @return the {@link FormatterProvider} for a {@link Number} in percent.
   */
  public static Formatter<Object> getPercentFormatter(Locale locale) {

    return new NumberFormatFormatter(NumberFormat.getPercentFormat());
  }

  /**
   * {@link Formatter} for {@link Date} using {@link Iso8601Util}.
   */
  private static class Iso8601Formatter extends AbstractFormatter<Object> {

    /** Is the {@link Iso8601Util}. */
    private final Iso8601Util iso8601Util;

    /** The {@link net.sf.mmm.util.nls.api.NlsFormatterPlugin#getType() type}. */
    private final String type;

    /**
     * The constructor.
     *
     * @param iso8601Util is the {@link Iso8601Util}.
     * @param type is the {@link net.sf.mmm.util.nls.api.NlsFormatterPlugin#getType() type}.
     */
    public Iso8601Formatter(Iso8601Util iso8601Util, String type) {

      super();
      this.iso8601Util = iso8601Util;
      this.type = type;
    }

    /**
     *
     * @param object is the value to format that should be converted to {@link Date}.
     * @return the {@link Date}.
     */
    private Date convertObject(Object object) {

      Date date = null;
      if (object != null) {
        if (object instanceof Date) {
          date = (Date) object;
        } else if (object instanceof Number) {
          long millis = ((Number) object).longValue();
          date = new Date(millis);
        }
      }
      return date;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void doFormat(Object value, Appendable buffer) throws IOException {

      Date date = convertObject(value);
      if (date == null) {
        // fallback
        buffer.append(value.toString());
      } else {
        if (NlsFormatterManager.TYPE_DATE.equals(this.type)) {
          this.iso8601Util.formatDate(date, true, buffer);
        } else if (NlsFormatterManager.TYPE_TIME.equals(this.type)) {
          this.iso8601Util.formatTime(date, true, buffer);
          this.iso8601Util.formatTimeZone(date.getTimezoneOffset(), true, buffer);
        } else if (NlsFormatterManager.TYPE_DATETIME.equals(this.type)) {
          this.iso8601Util.formatDateTime(date, true, true, true, buffer);
        } else {
          throw new IllegalStateException(this.type);
        }
      }
    }
  }

  /**
   * {@link Formatter} using {@link NumberFormat}.
   */
  private static class NumberFormatFormatter extends AbstractFormatter<Object> {

    private  final NumberFormat format;

    /**
     * The constructor.
     *
     * @param format is the {@link NumberFormat} to adapt.
     */
    public NumberFormatFormatter(NumberFormat format) {

      super();
      this.format = format;
    }

    @Override
    protected void doFormat(Object value, Appendable buffer) throws IOException {

      if (value instanceof Number) {
        buffer.append(this.format.format((Number) value));
      } else {
        buffer.append(value.toString());
      }
    }
  }

  /**
   * {@link Formatter} using {@link DateTimeFormat}.
   */
  private static class DateTimeFormatFormatter extends AbstractFormatter<Object> {

    private  final DateTimeFormat format;

    /**
     * The constructor.
     *
     * @param format is the {@link DateTimeFormat} to adapt.
     */
    public DateTimeFormatFormatter(DateTimeFormat format) {

      super();
      this.format = format;
    }

    @Override
    protected void doFormat(Object value, Appendable buffer) throws IOException {

      if (value instanceof Date) {
        buffer.append(this.format.format((Date) value));
      } else {
        buffer.append(value.toString());
      }
    }
  }

}
