/* $Id$ */
package net.sf.mmm.value.api.type;

/**
 * This is the interface for a value containing date and time information.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface DateAndTimeIF extends DateIF, TimeIF {

  /**
   * The {@link net.sf.mmm.value.api.ValueManagerIF#getName() name} of this
   * value type.
   */
  String VALUE_NAME = "DateTime";

  /**
   * This method gets the timezone offset in minutes relative to UTC
   * (Greenwich).
   * 
   * @return the timezone offset in minutes relative to UTC.
   */
  int getTimezoneOffset();

  /**
   * This method gets a string representation of this object in the format
   * "yyyy.MM.dd'T'HH:mm:ss".
   * 
   * @see java.lang.Object#toString()
   */
  public String toString();

}
