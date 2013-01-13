/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.api.attribute;

/**
 * This interface gives read access to the {@link #getWeek() week} attribute of an object related to date.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.1
 */
public interface AttributeReadWeek {

  /**
   * @return the week of the year in the range from 1 to 53.
   */
  int getWeek();

}
