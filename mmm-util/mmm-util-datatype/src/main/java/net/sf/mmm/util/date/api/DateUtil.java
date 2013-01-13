/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.api;

import java.util.Date;
import java.util.Locale;


/**
 * This is the interface for a collection of utility functions that help with {@link SimpleDate date} and
 * {@link SimpleTime time}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DateUtil {

  /**
   * This method creates a new {@link AdvancedDateMidnight} from the given values.
   * 
   * @param year - see {@link net.sf.mmm.util.date.api.attribute.AttributeReadYear#getYear()}.
   * @param month - see {@link net.sf.mmm.util.date.api.attribute.AttributeReadMonth#getMonth()}.
   * @param day - see {@link net.sf.mmm.util.date.api.attribute.AttributeReadDay#getDay()}.
   * @return the specified date as {@link AdvancedDateMidnight}.
   */
  AdvancedDateMidnight createDateMidnight(int year, Month month, int day);

  /**
   * This method creates a new {@link AdvancedDateMidnight} from the given values.
   * 
   * @param year - see {@link net.sf.mmm.util.date.api.attribute.AttributeReadYear#getYear()}.
   * @param month - see {@link net.sf.mmm.util.date.api.attribute.AttributeReadMonth#getMonth()}.
   * @param day - see {@link net.sf.mmm.util.date.api.attribute.AttributeReadDay#getDay()}.
   * @param locale is the explicit {@link Locale} to use.
   * @return the specified date as {@link AdvancedDateMidnight}.
   */
  AdvancedDateMidnight createDateMidnight(int year, Month month, int day, Locale locale);

  /**
   * This method creates a new {@link AdvancedDateMidnight} from the given values.
   * 
   * @param date is the {@link Date}.
   * @return the specified date as {@link AdvancedDateMidnight}.
   */
  AdvancedDateMidnight createDateMidnight(Date date);

  /**
   * This method creates a new {@link AdvancedDateTime} from the given values.
   * 
   * @param year - see {@link net.sf.mmm.util.date.api.attribute.AttributeReadYear#getYear()}.
   * @param month - see {@link net.sf.mmm.util.date.api.attribute.AttributeReadMonth#getMonth()}.
   * @param day - see {@link net.sf.mmm.util.date.api.attribute.AttributeReadDay#getDay()}.
   * @param hours - see {@link net.sf.mmm.util.date.api.attribute.AttributeReadHours#getHours()}.
   * @param minutes - see
   *        {@link net.sf.mmm.util.date.api.attribute.AttributeReadMinutes#getMinutes()}.
   * @param seconds - see
   *        {@link net.sf.mmm.util.date.api.attribute.AttributeReadSeconds#getSeconds()}.
   * @return the specified date as {@link AdvancedDateTime}.
   */
  AdvancedDateTime createDateTime(int year, Month month, int day, int hours, int minutes, int seconds);

  /**
   * This method creates a new {@link AdvancedDateTime} from the given values.
   * 
   * @param year - see {@link net.sf.mmm.util.date.api.attribute.AttributeReadYear#getYear()}.
   * @param month - see {@link net.sf.mmm.util.date.api.attribute.AttributeReadMonth#getMonth()}.
   * @param day - see {@link net.sf.mmm.util.date.api.attribute.AttributeReadDay#getDay()}.
   * @param hours - see {@link net.sf.mmm.util.date.api.attribute.AttributeReadHours#getHours()}.
   * @param minutes - see
   *        {@link net.sf.mmm.util.date.api.attribute.AttributeReadMinutes#getMinutes()}.
   * @param seconds - see
   *        {@link net.sf.mmm.util.date.api.attribute.AttributeReadSeconds#getSeconds()}.
   * @param milliseconds - see
   *        {@link net.sf.mmm.util.date.api.attribute.AttributeReadMilliseconds#getMilliseconds()}.
   * @return the specified date as {@link AdvancedDateTime}.
   */
  AdvancedDateTime createDateTime(int year, Month month, int day, int hours, int minutes, int seconds, int milliseconds);

  /**
   * This method creates a new {@link AdvancedDateTime} from the given values.
   * 
   * @param year - see {@link net.sf.mmm.util.date.api.attribute.AttributeReadYear#getYear()}.
   * @param month - see {@link net.sf.mmm.util.date.api.attribute.AttributeReadMonth#getMonth()}.
   * @param day - see {@link net.sf.mmm.util.date.api.attribute.AttributeReadDay#getDay()}.
   * @param hours - see {@link net.sf.mmm.util.date.api.attribute.AttributeReadHours#getHours()}.
   * @param minutes - see
   *        {@link net.sf.mmm.util.date.api.attribute.AttributeReadMinutes#getMinutes()}.
   * @param seconds - see
   *        {@link net.sf.mmm.util.date.api.attribute.AttributeReadSeconds#getSeconds()}.
   * @param milliseconds - see
   *        {@link net.sf.mmm.util.date.api.attribute.AttributeReadMilliseconds#getMilliseconds()}.
   * @param locale is the explicit {@link Locale} to use.
   * @return the specified date as {@link AdvancedDateTime}.
   */
  // CHECKSTYLE:OFF (we need 8 parameters here)
  AdvancedDateTime createDateTime(int year, Month month, int day, int hours, int minutes, int seconds,
      int milliseconds, Locale locale);

  // CHECKSTYLE:ON

  /**
   * This method creates a new {@link AdvancedDateTime} from the given values.
   * 
   * @param date is the {@link Date}.
   * @return the specified date as {@link AdvancedDateTime}.
   */
  AdvancedDateTime createDateTime(Date date);

  /**
   * This method creates a new {@link AdvancedDateTime} from the given values.
   * 
   * @param date is the {@link SimpleDate} to use. If time information is available, it will be ignored.
   * @param time is the {@link SimpleTime} to use.
   * @return the specified date as {@link AdvancedDate}.
   */
  AdvancedDateTime createDateTime(SimpleDate date, SimpleTime time);

  /**
   * @return the current date and time as {@link AdvancedDateTime}.
   */
  AdvancedDateTime now();

  /**
   * @return the current date as {@link AdvancedDateMidnight}.
   */
  AdvancedDateMidnight today();

}
