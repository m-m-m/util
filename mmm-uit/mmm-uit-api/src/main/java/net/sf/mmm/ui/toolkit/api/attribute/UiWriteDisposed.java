/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This is the interface for an object of the UI framework that can be disposed.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWriteDisposed extends UiReadDisposed {

  /**
   * This method disposes this object. It will be removed from the UI. The
   * resources of the object are deallocated and the object can not be used or
   * displayed anymore.
   */
  void dispose();

}
