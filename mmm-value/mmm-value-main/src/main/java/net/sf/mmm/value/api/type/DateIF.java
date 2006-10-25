/* $Id$ */
package net.sf.mmm.value.api.type;

import net.sf.mmm.xml.api.XmlSerializableIF;

/**
 * This is the interface for a date value.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface DateIF extends XmlSerializableIF {

  /** the month january as number */
  public static final int MONTH_JANUARY = 1;

  /** the month february as number */
  public static final int MONTH_FEBRUARY = 2;

  /** the month march as number */
  public static final int MONTH_MARCH = 3;

  /** the month april as number */
  public static final int MONTH_APRIL = 4;

  /** the month may as number */
  public static final int MONTH_MAY = 5;

  /** the month june as number */
  public static final int MONTH_JUNE = 6;

  /** the month july as number */
  public static final int MONTH_JULY = 7;

  /** the month august as number */
  public static final int MONTH_AUGUST = 8;

  /** the month september as number */
  public static final int MONTH_SEPTEMBER = 9;

  /** the month october as number */
  public static final int MONTH_OCTOBER = 10;

  /** the month november as number */
  public static final int MONTH_NOVEMBER = 11;

  /** the month december as number */
  public static final int MONTH_DECEMBER = 12;

  /**
   * The {@link net.sf.mmm.value.api.ValueManagerIF#getName() name} of this
   * value type.
   */
  String VALUE_NAME = "Date";

  /**
   * This method gets the year.
   * 
   * @return the year
   */
  int getYear();

  /**
   * This method gets the month of the year.
   * 
   * @return the month in the range of 1-12.
   */
  int getMonth();

  /**
   * This method gets the day of the month.
   * 
   * @return the day of the month in the range of 1-31 (1-28/29/30/31).
   */
  int getDay();

  /**
   * This method gets the current date in milliseconds.
   * 
   * @see java.util.Date#getTime()
   * 
   * @return the date in milliseconds.
   */
  long asMilliseconds();

  /**
   * This method gets a string representation of this object in the format
   * "yyyy.MM.dd".
   * 
   * @see Object#toString()
   */
  String toString();

}
