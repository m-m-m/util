/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read and write access to the {@link #isEnabled() enabled} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteEnabled extends AttributeReadEnabled {

  /**
   * This method sets the {@link #isEnabled() enabled} status of this object. If it is disabled, the user can
   * not interact with the object.
   * 
   * @param enabled - if <code>true</code> the object will be enabled, else the object will be disabled.
   */
  void setEnabled(boolean enabled);

}
