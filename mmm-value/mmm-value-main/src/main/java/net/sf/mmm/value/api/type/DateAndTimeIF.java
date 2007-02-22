/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.api.type;

/**
 * This is the interface for a value containing date and time information.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface DateAndTimeIF extends DateIF, TimeIF {

  /**
   * The {@link net.sf.mmm.value.api.ValueManager#getName() name} of this
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
