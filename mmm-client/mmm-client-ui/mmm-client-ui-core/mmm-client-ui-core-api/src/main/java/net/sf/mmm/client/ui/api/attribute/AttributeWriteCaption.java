/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read and write access to the {@link #getCaption() caption} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteCaption extends AttributeReadCaption {

  /**
   * This method sets the {@link #getCaption() caption} of this object.
   * 
   * @param caption is the new {@link #getCaption() caption} to set.
   */
  void setCaption(String caption);

}
