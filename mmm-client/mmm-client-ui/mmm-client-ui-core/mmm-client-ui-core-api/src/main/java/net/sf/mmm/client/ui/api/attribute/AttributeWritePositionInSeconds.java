/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.util.value.api.ValueOutOfRangeException;

/**
 * This interface gives read and write access to the {@link #getPositionInSeconds() position in seconds}
 * attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWritePositionInSeconds extends AttributeReadPositionInSeconds {

  /**
   * This method sets the {@link #getPositionInSeconds() position in seconds} attribute programatically.
   * 
   * @see #getPositionInSeconds()
   * 
   * @param position is the new value of {@link #getPositionInSeconds()}.
   * @throws ValueOutOfRangeException if the given value is negative.
   */
  void setPositionInSeconds(double position) throws ValueOutOfRangeException;

}
