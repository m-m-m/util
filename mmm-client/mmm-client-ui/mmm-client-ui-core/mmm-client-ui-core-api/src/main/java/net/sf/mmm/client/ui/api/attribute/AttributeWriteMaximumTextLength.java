/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read and write access to the {@link #getMaximumTextLength() maximum text length} of an
 * object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteMaximumTextLength extends AttributeReadMaximumTextLength {

  /**
   * This method sets the {@link #getMaximumTextLength() maximum text length} of this object.<br/>
   * <b>ATTENTION:</b><br/>
   * If you are setting this property on a UI field, the typical behavior is that after the maximum text
   * length is reached, any additional input is ignored. This also applies for copy and paste operations. For
   * usability this might be undesired in many cases as users can not append something and then remove a
   * prefix afterwards if the length would be exceeded. Please consider using a validator instead and only use
   * this feature in situations where it is really suitable.
   * 
   * @param length is the new {@link #getMaximumTextLength() maximum text length} to set. Use
   *        {@link Integer#MAX_VALUE} to unset.
   */
  void setMaximumTextLength(int length);

}
