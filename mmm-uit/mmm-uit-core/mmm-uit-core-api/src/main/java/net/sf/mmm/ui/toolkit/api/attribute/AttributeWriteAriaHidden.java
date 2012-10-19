/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read and write access to the {@link #isAriaHidden() ARIA hidden} attribute of an
 * object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteAriaHidden extends AttributeReadAriaHidden {

  /**
   * This method sets the {@link #isAriaHidden() ARIA hidden} attribute of this object.<br/>
   * <b>NOTE:</b><br/>
   * If you are using the UI-Toolkit (UiWidget) the roles are automatically set for according widgets.
   * 
   * @see #isAriaHidden()
   * 
   * @param ariaRole is the new value of {@link #isAriaHidden() ARIA hidden}.
   */
  void setAriaRole(String ariaRole);

}
