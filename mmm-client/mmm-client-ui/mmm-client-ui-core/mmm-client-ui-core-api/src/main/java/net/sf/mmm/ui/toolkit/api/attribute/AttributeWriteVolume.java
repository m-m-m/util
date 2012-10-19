/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import net.sf.mmm.util.value.api.ValueOutOfRangeException;

/**
 * This interface gives read and write access to the {@link #getVolume() volume} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteVolume extends AttributeReadVolume {

  /**
   * This method sets the {@link #getVolume() volume} attribute programatically.
   * 
   * @see #getVolume()
   * 
   * @param volume is the new value of {@link #getVolume()}.
   * @throws ValueOutOfRangeException if the given volume is NOT in the inclusive range from <code>0.0</code>
   *         to <code>1.0</code>.
   */
  void setVolume(double volume) throws ValueOutOfRangeException;

}
