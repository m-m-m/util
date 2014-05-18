/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.util.lang.api.Orientation;

/**
 * This interface gives read access to the {@link #getOrientation() orientation} of an object.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadOrientation {

  /**
   * This method gets the orientation of this object. The object is layed out along the axis defined by the
   * orientation.
   *
   * @return the objects orientation.
   */
  Orientation getOrientation();

}
