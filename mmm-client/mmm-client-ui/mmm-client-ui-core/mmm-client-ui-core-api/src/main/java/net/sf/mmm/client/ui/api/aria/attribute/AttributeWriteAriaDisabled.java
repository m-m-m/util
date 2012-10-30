/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

/**
 * This interface gives read and write access to the {@link #isDisabled() disabled} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteAriaDisabled extends AttributeReadAriaDisabled {

  /**
   * This method sets the {@link #isDisabled() disabled} state of this object.
   * 
   * @param disabled is the new value of {@link #isDisabled()}.
   */
  void setDisabled(boolean disabled);

}
