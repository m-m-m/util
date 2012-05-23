/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read access to the {@link #isMaximized() maximized} state of an object (window).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface AttributeReadMaximized {

  /**
   * This method determines if this object is maximized.
   * 
   * @return <code>true</code> if the object is maximized, <code>false</code> otherwise.
   */
  boolean isMaximized();

}
