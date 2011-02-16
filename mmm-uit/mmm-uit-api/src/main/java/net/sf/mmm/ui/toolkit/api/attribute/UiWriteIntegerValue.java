/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read and write access to an integer
 * {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWriteIntegerValue extends UiReadIntegerValue {

  /**
   * This method sets the current value to the given one.
   * 
   * @param newValue is the new value to set.
   */
  void setValue(int newValue);

}
