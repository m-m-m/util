/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read and write access to the enabled-flag of an
 * {@link net.sf.mmm.ui.toolkit.api.UIObject object}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UiWriteEnabled extends UiReadEnabled {

  /**
   * This method sets the enabled status of this object. If it is disabled, the
   * user can not interact with the object.
   * 
   * @param enabled - if <code>true</code> the object will be enabled, else
   *        the object will be disabled.
   */
  void setEnabled(boolean enabled);

}
