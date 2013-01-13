/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.api.attribute;

import net.sf.mmm.util.date.api.Weekday;

/**
 * This interface gives read access to the {@link #getWeekday() weekday} attribute of an object related to
 * date.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.1
 */
public interface AttributeReadWeekday {

  /**
   * @return the {@link Weekday} (day of the week).
   */
  Weekday getWeekday();

}
