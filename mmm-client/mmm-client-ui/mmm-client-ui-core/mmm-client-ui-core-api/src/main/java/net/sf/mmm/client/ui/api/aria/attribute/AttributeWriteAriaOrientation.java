/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.util.lang.api.Orientation;

/**
 * This interface gives read and write access to the {@link #getOrientation() orientation} attribute of an
 * object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteAriaOrientation extends AttributeReadAriaOrientation {

  /**
   * This method sets the {@link #getOrientation() orientation} attribute of this object.
   * 
   * @param orientation is the new value of {@link #getOrientation()}. May be <code>null</code> to unset.
   */
  void setOrientation(Orientation orientation);

}
