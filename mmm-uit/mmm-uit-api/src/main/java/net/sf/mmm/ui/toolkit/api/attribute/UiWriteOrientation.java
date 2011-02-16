/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import net.sf.mmm.ui.toolkit.api.composite.Orientation;

/**
 * This interface gives {@link UiReadOrientation#getOrientation() read} and
 * {@link #setOrientation(Orientation) write} access to the
 * {@link net.sf.mmm.ui.toolkit.api.composite.Orientation} of an
 * {@link net.sf.mmm.ui.toolkit.api.UIObjectRenamed object}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UiWriteOrientation extends UiReadOrientation {

  /**
   * This method sets the orientation to the given value.
   * 
   * @see UiReadOrientation#getOrientation()
   * 
   * @param orientation is the new orientation.
   */
  void setOrientation(Orientation orientation);

}
