/* $ Id: $ */
package net.sf.mmm.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is a collection of utility functions for dealing with {@link Date}
 * objects.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DateUtil {

  /** the default date format as string pattern: {@value} */
  private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

  /** thread-local for date format */
  private static ThreadLocal<DateFormat> DATE_FORMAT = new ThreadLocal<DateFormat>() {

    /**
     * @see java.lang.ThreadLocal#initialValue()
     */
    @Override
    protected DateFormat initialValue() {

      return new SimpleDateFormat(DEFAULT_DATE_FORMAT);
    }

  };

  /**
   * The constructor.
   */
  private DateUtil() {

    super();
  }

  /**
   * This method converts the given <code>date</code> to the according string
   * in the {@link #DEFAULT_DATE_FORMAT default format}.
   * 
   * @param date
   *        is the date to format.
   * @return the date as string.
   */
  public static String format(Date date) {

    return getDefaultDateFormat().format(date);
  }

  /**
   * This method parses the <code>date</code> given as string in the
   * {@link #DEFAULT_DATE_FORMAT default format}.
   * 
   * @param date
   *        is the date value as string.
   * @return the parsed <code>date</code> value.
   * @throws ParseException
   *         if the given <code>date</code> is NOT in
   *         {@link #DEFAULT_DATE_FORMAT default format}.
   */
  public static Date parse(String date) throws ParseException {

    return getDefaultDateFormat().parse(date);
  }

  /**
   * This method gets the default date format. It will return an individual
   * instance per calling {@link Thread} so no further synchronization is
   * needed.
   * 
   * @return the default date format.
   */
  public static DateFormat getDefaultDateFormat() {

    return DATE_FORMAT.get();
  }

}
