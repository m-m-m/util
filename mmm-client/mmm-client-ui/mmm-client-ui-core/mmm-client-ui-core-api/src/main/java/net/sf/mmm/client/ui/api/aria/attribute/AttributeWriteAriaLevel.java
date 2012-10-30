/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

/**
 * This interface gives read and write access to the {@link #getLevel() level} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteAriaLevel extends AttributeReadAriaLevel {

  /**
   * This method sets the {@link #getLevel() level} property of this object.
   * 
   * @param level is the new value of {@link #getLevel()}.
   */
  void setLevel(Integer level);

}
