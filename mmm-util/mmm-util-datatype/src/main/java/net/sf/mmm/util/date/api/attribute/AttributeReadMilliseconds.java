/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.api.attribute;

/**
 * This interface gives read access to the {@link #getMilliseconds() milliseconds} attribute of an object
 * related to time.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.1
 */
public interface AttributeReadMilliseconds {

  /**
   * @return the milliseconds in the range from 0 to 999.
   */
  int getMilliseconds();

}
