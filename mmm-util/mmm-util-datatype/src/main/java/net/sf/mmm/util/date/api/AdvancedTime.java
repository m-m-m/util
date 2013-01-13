/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.date.api;

/**
 * This is the interface for a {@link SimpleTime} that has no {@link SimpleDate date} associated. E.g.
 * "23:59:59.999" is the time one milliseconds before midnight. Such time information is represented by this
 * object. It does NOT carry timezone information.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface AdvancedTime extends Comparable<SimpleTime> {

  /**
   * This method calculates the {@link Duration} from this {@link AdvancedTime} to the given
   * <code>{@link SimpleTime time}</code>.
   * 
   * @param time is the {@link SimpleTime} to subtract.
   * @return the {@link Duration} from this {@link AdvancedTime} to the given <code>time</code>.
   */
  Duration subtract(SimpleTime time);

}
