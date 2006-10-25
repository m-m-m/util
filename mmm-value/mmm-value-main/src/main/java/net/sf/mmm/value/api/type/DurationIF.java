/* $Id$ */
package net.sf.mmm.value.api.type;

import net.sf.mmm.xml.api.XmlSerializableIF;

/**
 * This is the interface for a duration value. A duration represents the
 * difference of two {@link net.sf.mmm.value.api.type.DateAndTimeIF} values.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface DurationIF extends XmlSerializableIF {

  /**
   * The {@link net.sf.mmm.value.api.ValueManagerIF#getName() name} of this
   * value type.
   */
  String VALUE_NAME = "Duration";

  /**
   * This method gets the days of the duration.
   * 
   * @return the hours in the range of 0-106751991167.
   */
  long getDays();

  /**
   * This method gets the hours of the duration.
   * 
   * @return the hours in the range of 0-23.
   */
  int getHours();

  /**
   * This methode gets the minutes of the duration.
   * 
   * @return the minutes in the range of 0-59.
   */
  int getMinutes();

  /**
   * This method gets the seconds of the duration.
   * 
   * @return the seconds in the range of 0-59.
   */
  int getSeconds();

  /**
   * This method gets the milliseconds of the duration.
   * 
   * @return the milliseconds in the range of 0-999.
   */
  int getMilliseconds();

  /**
   * This method gets the duration in milliseconds.
   * 
   * @return the duration in milliseconds.
   */
  long asMilliseconds();

}
