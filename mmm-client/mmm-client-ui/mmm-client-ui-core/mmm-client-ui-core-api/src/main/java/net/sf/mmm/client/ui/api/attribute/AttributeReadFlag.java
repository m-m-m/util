/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read access to a generic {@link #getFlag() flag}.
 * 
 * @see AttributeWriteVisible
 * @see AttributeWriteEnabled
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadFlag {

  /**
   * This method reads the flag value. This is a generic attribute. The meaning of the flag needs to be
   * defined by the JavaDoc of the accessor to get the flag instance.
   * 
   * @return <code>true</code> if the flag is set, <code>false</code> otherwise.
   */
  boolean getFlag();

}
