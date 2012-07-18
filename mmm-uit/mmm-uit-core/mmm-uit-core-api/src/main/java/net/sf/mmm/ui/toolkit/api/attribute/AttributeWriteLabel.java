/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read and write access to the {@link #getLabel() label} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteLabel extends AttributeReadLabel {

  /**
   * This method sets the {@link #getLabel() label} of this object.
   * 
   * @param label is the new {@link #getLabel() label} to set.
   */
  void setLabel(String label);

}
