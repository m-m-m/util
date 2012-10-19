/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read and write access to the {@link #isModified() modified} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteModified extends AttributeReadModified {

  /**
   * This method sets the {@link #isModified() modified} attribute programatically. This should only be used
   * in specific situations where the end-user performs a change indirectly. E.g. if the user clicks on a
   * button that causes a programatical change of the objects value you can explicitly set the object back to
   * modified.
   * 
   * @see #isModified()
   * 
   * @param modified is the new value of {@link #isModified()}.
   */
  void setModified(boolean modified);

}
