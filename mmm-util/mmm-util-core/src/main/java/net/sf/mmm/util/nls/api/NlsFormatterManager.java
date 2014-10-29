/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

/**
 * This is the interface for a manager of {@link NlsFormatter}s. <br>
 * A legal implementation of this interface has to be thread-safe.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface NlsFormatterManager {

  /** @see java.text.NumberFormat */
  String TYPE_NUMBER = "number";

  /** @see java.text.DateFormat#getDateInstance(int, java.util.Locale) */
  String TYPE_DATE = "date";

  /** @see java.text.DateFormat#getTimeInstance(int, java.util.Locale) */
  String TYPE_TIME = "time";

  /** @see java.text.DateFormat#getDateTimeInstance(int, int, java.util.Locale) */
  String TYPE_DATETIME = "datetime";

  /** @see java.text.ChoiceFormat */
  String TYPE_CHOICE = "choice";

  /** Format for {@link java.lang.reflect.Type} */
  String TYPE_TYPE = "type";

  /** @see java.text.DateFormat#SHORT */
  String STYLE_SHORT = "short";

  /** @see java.text.DateFormat#MEDIUM */
  String STYLE_MEDIUM = "medium";

  /** @see java.text.DateFormat#LONG */
  String STYLE_LONG = "long";

  /** @see java.text.DateFormat#FULL */
  String STYLE_FULL = "full";

  /** @see java.text.NumberFormat#getIntegerInstance() */
  String STYLE_INTEGER = "integer";

  /** @see java.text.NumberFormat#getCurrencyInstance() */
  String STYLE_CURRENCY = "currency";

  /** @see java.text.NumberFormat#getPercentInstance() */
  String STYLE_PERCENT = "percent";

  /** @see net.sf.mmm.util.date.api.Iso8601Util */
  String STYLE_ISO_8601 = "iso8601";

  /**
   * This method gets the default {@link NlsFormatter}. <br>
   * 
   * @see #getFormatter(String, String)
   * 
   * @return the default {@link NlsFormatter} instance.
   */
  NlsFormatter<Object> getFormatter();

  /**
   * This method gets the {@link NlsFormatter} for the given <code>formatType</code>. <br>
   * 
   * @see #getFormatter(String, String)
   * 
   * @param formatType is the type to be formatted.
   * @return the according {@link NlsFormatter} instance.
   */
  NlsFormatter<?> getFormatter(String formatType);

  /**
   * This method gets the {@link NlsFormatter} for the given <code>formatType</code> and
   * <code>formatStyle</code>. <br>
   * To be compliant with {@link java.text.MessageFormat} the following types and styles need to be supported
   * by the implementation: <br>
   * <br>
   * <code>formatType</code>:
   * <ul>
   * <li>{@link #TYPE_NUMBER number}</li>
   * <li>{@link #TYPE_DATE date}</li>
   * <li>{@link #TYPE_TIME time}</li>
   * <li>{@link #TYPE_CHOICE choice}</li>
   * <li>{@link #TYPE_DATETIME datetime}</li>
   * </ul>
   * <br>
   * <code>formatStyle</code>:
   * <ul>
   * <li>{@link #STYLE_SHORT short}</li>
   * <li>{@link #STYLE_MEDIUM medium}</li>
   * <li>{@link #STYLE_LONG long}</li>
   * <li>{@link #STYLE_FULL full}</li>
   * <li>{@link #STYLE_INTEGER integer}</li>
   * <li>{@link #STYLE_CURRENCY currency}</li>
   * <li>{@link #STYLE_PERCENT percent}</li>
   * <li><em>additional custom styles (named [a-z]*)</em></li>
   * <li><em>anything else will be treated as SubformatPattern</em></li>
   * </ul>
   * 
   * <b>ATTENTION:</b><br>
   * The support for {@link java.text.ChoiceFormat}s is NOT provided in a compatible way as by hacking
   * internal arrays of {@link java.text.MessageFormat}. Instead this implementation provides a clean
   * configuration via <code>formatStyle</code> when <code>formatType</code> is <code>choice</code> (see
   * {@link net.sf.mmm.util.nls.impl.formatter.NlsFormatterChoice}).
   * 
   * @see java.text.MessageFormat
   * 
   * @param formatType is the type to be formatted.
   * @param formatStyle is the style defining details of formatting.
   * @return the according {@link NlsFormatter} instance.
   */
  NlsFormatter<?> getFormatter(String formatType, String formatStyle);

}
