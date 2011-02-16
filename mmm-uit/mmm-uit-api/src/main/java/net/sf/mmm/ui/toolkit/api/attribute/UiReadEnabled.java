/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read access to the enabled-flag of an
 * {@link net.sf.mmm.ui.toolkit.api.UIObjectRenamed object}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UiReadEnabled {

  /**
   * This method determines if this object is enabled. If it is disabled (NOT
   * enabled), the user can NOT interact with the object.
   * 
   * @return <code>true</code> if this object is enabled, <code>false</code>
   *         if this object is disabled.
   */
  boolean isEnabled();

}
