/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read access to the {@link #isDisposed() disposed-flag}
 * of an {@link net.sf.mmm.ui.toolkit.api.UiObject object}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiReadDisposed {

  /**
   * This method tests if this object has been disposed. A disposed object can
   * NOT be used or displayed anymore.
   * 
   * @see UiWriteDisposed#dispose()
   * 
   * @return <code>true</code> if this object has been disposed.
   */
  boolean isDisposed();

}
