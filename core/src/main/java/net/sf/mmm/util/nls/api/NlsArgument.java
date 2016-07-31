/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.util.Locale;

import net.sf.mmm.util.nls.impl.formatter.NlsFormatterDefault;
import net.sf.mmm.util.text.api.Justification;

/**
 * This class represents an argument of an {@link NlsMessage}. An argument is a place-holder for a dynamic parameter
 * filled into the message. It is resolved when the message is finally
 * {@link NlsMessage#getLocalizedMessage(java.util.Locale) localized} as {@link String} for a specific
 * {@link java.util.Locale}. The {@link #getKey() key} is used to {@link NlsMessage#getArgument(String) lookup} the
 * argument value to fill in. If that value itself implements {@link NlsObject} it will be also translated using the
 * same {@link Locale}.<br>
 * The syntax for an {@link NlsArgument} in an {@link NlsMessage#getInternationalizedMessage() i18n message} is
 * specified as following:
 *
 * <pre>
 * '{' key [ ',' type [ ',' style ] ] [ '{' justification '}' ] '}'
 * </pre>
 *
 * The literals are explained in this table.
 * <table border="1">
 * <tr>
 * <th>literal</th>
 * <th>explanation</th>
 * </tr>
 * <tr>
 * <td>key</td>
 * <td>is the key of the parameter (may be numeric for legacy support)</td>
 * </tr>
 * <tr>
 * <td>type</td>
 * <td>a type specifying how to format the value (see below). Should fit to the {@link Object#getClass() type} of the
 * {@link NlsMessage#getArgument(String) argument value} to fill in.
 * </tr>
 * <tr>
 * <td>style</td>
 * <td>a style according to {@code type} (see below).</td>
 * </tr>
 * <tr>
 * <td>justification</td>
 * <td>a {@link net.sf.mmm.util.text.api.JustificationBuilder justification}</td>
 * </tr>
 * </table>
 *
 * <br>
 * The following table shows the supported variants of {@code type} and {@code style}:
 * <table border="1">
 * <tr>
 * <th>type</th>
 * <th>style</th>
 * <th>Example</th>
 * <th>Comment</th>
 * </tr>
 * <tr>
 * <td>-</td>
 * <td>-</td>
 * <td>value as string</td>
 * <td>default string representation of the value</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_NUMBER number}</td>
 * <td>-</td>
 * <td>123,456.78</td>
 * <td>Numeric representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_NUMBER number}</td>
 * <td>{@link NlsFormatterManager#STYLE_PERCENT percent}</td>
 * <td>12,345,678%</td>
 * <td>Number in percent</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_NUMBER number}</td>
 * <td>{@link NlsFormatterManager#STYLE_CURRENCY currency}</td>
 * <td>123,456.78 �</td>
 * <td>Number as currency</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_NUMBER number}</td>
 * <td>'#'##.##</td>
 * <td>#123456.78</td>
 * <td>Any other style is treated as pattern for {@link java.text.DecimalFormat}</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_DATE date}</td>
 * <td>-</td>
 * <td>31-Dec-1999</td>
 * <td>Date default representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_DATE date}</td>
 * <td>{@link NlsFormatterManager#STYLE_SHORT short}</td>
 * <td>31/12/99</td>
 * <td>Date short representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_DATE date}</td>
 * <td>{@link NlsFormatterManager#STYLE_MEDIUM medium}</td>
 * <td>31-Dec-1999</td>
 * <td>Date medium representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_DATE date}</td>
 * <td>{@link NlsFormatterManager#STYLE_LONG long}</td>
 * <td>31 December 1999</td>
 * <td>Date long representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_DATE date}</td>
 * <td>{@link NlsFormatterManager#STYLE_FULL full}</td>
 * <td>Friday, 31 December 1999</td>
 * <td>Date full representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_DATE date}</td>
 * <td>{@link NlsFormatterManager#STYLE_ISO_8601 iso8601}</td>
 * <td>1999-12-31</td>
 * <td>Date ISO-8601 representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_DATE date}</td>
 * <td>yyyy.MM.dd</td>
 * <td>2010.01.01</td>
 * <td>Any other style is treated as pattern for {@link java.text.SimpleDateFormat}</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_TIME time}</td>
 * <td>-</td>
 * <td>23:59:59</td>
 * <td>Time default representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_TIME time}</td>
 * <td>{@link NlsFormatterManager#STYLE_SHORT short}</td>
 * <td>23:59</td>
 * <td>Time short representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_TIME time}</td>
 * <td>{@link NlsFormatterManager#STYLE_MEDIUM medium}</td>
 * <td>23:59:59</td>
 * <td>Time medium representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_TIME time}</td>
 * <td>{@link NlsFormatterManager#STYLE_LONG long}</td>
 * <td>23:59:59 GMT+01:00</td>
 * <td>Time long representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_TIME time}</td>
 * <td>{@link NlsFormatterManager#STYLE_FULL full}</td>
 * <td>23:59:59 o'clock GMT+01:00</td>
 * <td>Time full representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_TIME time}</td>
 * <td>{@link NlsFormatterManager#STYLE_ISO_8601 iso8601}</td>
 * <td>23:59:59+01:00</td>
 * <td>Time ISO-8601 representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_TIME time}</td>
 * <td>HH:mm:ss</td>
 * <td>23:59:59</td>
 * <td>Any other style is treated as pattern for {@link java.text.SimpleDateFormat}</td>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_DATETIME datetime}</td>
 * <td>-</td>
 * <td>31-Dec-1999 23:59:59</td>
 * <td>Date+time default representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_DATETIME datetime}</td>
 * <td>{@link NlsFormatterManager#STYLE_SHORT short}</td>
 * <td>31/12/99 23:59</td>
 * <td>Date+time short representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_DATETIME datetime}</td>
 * <td>{@link NlsFormatterManager#STYLE_MEDIUM medium}</td>
 * <td>31-Dec-1999 23:59:59</td>
 * <td>Date+time medium representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_DATETIME datetime}</td>
 * <td>{@link NlsFormatterManager#STYLE_LONG long}</td>
 * <td>31 December 1999 23:59:59 GMT+01:00</td>
 * <td>Date+time long representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_DATETIME datetime}</td>
 * <td>{@link NlsFormatterManager#STYLE_FULL full}</td>
 * <td>Friday, 31 December 1999 23:59:59 o'clock GMT+01:00</td>
 * <td>Date+time full representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_DATETIME datetime}</td>
 * <td>{@link NlsFormatterManager#STYLE_ISO_8601 iso8601}</td>
 * <td>1999-12-31T23:59:59+01:00</td>
 * <td>Date+time ISO-8601 representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_DATE date}</td>
 * <td>yyyy.MM.dd-HH:mm:ss</td>
 * <td>2010.01.01-23:59:59</td>
 * <td>Any other style is treated as pattern for {@link java.text.SimpleDateFormat}</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_TYPE type}</td>
 * <td>-</td>
 * <td>java.util.List</td>
 * <td>{@link java.lang.reflect.Type} medium representation (omit {@code java.lang})</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_TYPE type}</td>
 * <td>{@link NlsFormatterManager#STYLE_SHORT short}</td>
 * <td>List</td>
 * <td>{@link java.lang.reflect.Type} short representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_TYPE type}</td>
 * <td>{@link NlsFormatterManager#STYLE_MEDIUM medium}</td>
 * <td>java.util.List</td>
 * <td>{@link java.lang.reflect.Type} medium representation (omit {@code java.lang})</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_TYPE type}</td>
 * <td>{@link NlsFormatterManager#STYLE_LONG long}</td>
 * <td>java.util.List&lt;String&gt;</td>
 * <td>{@link java.lang.reflect.Type} long representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_TYPE type}</td>
 * <td>{@link NlsFormatterManager#STYLE_FULL full}</td>
 * <td>java.util.List&lt;java.lang.String&gt;</td>
 * <td>{@link java.lang.reflect.Type} full representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_CHOICE choice}</td>
 * <td>(?==1)'error'(else)'errors'</td>
 * <td>error</td>
 * <td>A choice to distinguish singular/plural forms.</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_CHOICE choice}</td>
 * <td>(?>=5){key,choice,(?==true)'a'(else){key2}}(else)'c'</td>
 * <td>a</td>
 * <td>A choice that results to 'c' if the value is NOT greater or equal to 5. Otherwise it results to 'a' if the value
 * of the argument "key" is {@link Boolean#TRUE}. Otherwise it results to the value of the argument "key2". - This is a
 * wired example. It shows the power of the choice format as well as how things turn out unmaintainable if the power is
 * missused.</td>
 * </tr>
 * </table>
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class NlsArgument {

  private final String key;

  private final NlsFormatterPlugin<?> formatter;

  private final Justification justification;

  /**
   * The constructor.
   *
   * @param key is the {@link #getKey() key}.
   * @param formatter is the {@link #getFormatter() formatter}.
   * @param justification is the {@link #getJustification() justification}.
   */
  public NlsArgument(String key, NlsFormatterPlugin<?> formatter, Justification justification) {

    super();
    this.key = key;
    this.formatter = formatter;
    this.justification = justification;
  }

  /**
   * This method gets the key of the argument to format.
   *
   * @return the key
   */
  public String getKey() {

    return this.key;
  }

  /**
   * Is the formatter used to format the {@link #getKey() argument}.
   *
   * @return the formatter
   */
  public NlsFormatterPlugin<?> getFormatter() {

    return this.formatter;
  }

  /**
   * This method gets the optional {@link Justification}.
   *
   * @return the justification or {@code null} for none.
   */
  public Justification getJustification() {

    return this.justification;
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    sb.append(NlsArgumentParser.START_EXPRESSION);
    sb.append(this.key);
    if (!(this.formatter instanceof NlsFormatterDefault)) {
      sb.append(NlsArgumentParser.FORMAT_SEPARATOR);
      sb.append(this.formatter);
    }
    if (this.justification != null) {
      sb.append(NlsArgumentParser.START_EXPRESSION);
      sb.append(this.justification);
      sb.append(NlsArgumentParser.END_EXPRESSION);
    }
    sb.append(NlsArgumentParser.END_EXPRESSION);
    return sb.toString();
  }

}
