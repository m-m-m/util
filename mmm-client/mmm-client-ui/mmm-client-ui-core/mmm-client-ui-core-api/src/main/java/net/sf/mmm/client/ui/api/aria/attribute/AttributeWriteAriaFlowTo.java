/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.client.ui.api.aria.datatype.AriaIdList;

/**
 * This interface gives read and write access to the {@link #getFlowTo() flowTo} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteAriaFlowTo extends AttributeReadAriaFlowTo {

  /**
   * This method sets the {@link #getFlowTo() flowTo} attribute of this object.
   * 
   * @param flowTo is value of {@link #getFlowTo()}. May be <code>null</code> to unset.
   */
  void setFlowTo(AriaIdList flowTo);

  /**
   * This method sets the {@link #getFlowTo() flowTo} attribute of this object.
   * 
   * @param flowTo is value of {@link #getFlowTo()} as {@link String}. May be <code>null</code> or the empty
   *        string to unset.
   */
  void setFlowTo(String flowTo);

}
