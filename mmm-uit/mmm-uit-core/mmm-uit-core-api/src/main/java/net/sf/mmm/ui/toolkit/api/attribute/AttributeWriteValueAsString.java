/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read and write access to the {@link #getValueAsString() valueAsString} attribute of an
 * object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteValueAsString extends AttributeReadValueAsString {

  /**
   * This method sets the {@link #getValueAsString() valueAsString} of this object. <br/>
   * <b>ATTENTION:</b><br/>
   * This method shall only be used in very specific situations. Use it if you explicitly want to enter
   * invalid data.
   * 
   * @param url is the new {@link #getValueAsString() value} as it would have been entered by the user.
   */
  void setUrl(String url);

}
