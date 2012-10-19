/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives write access to the {@link #getPrimaryStyle() primary style} of an object.
 * 
 * @see AttributeWriteStyles
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWritePrimaryStyle extends AttributeReadPrimaryStyle {

  /**
   * This method sets the {@link #getPrimaryStyle() primary style}.<br/>
   * <b>ATTENTION:</b><br/>
   * You should avoid setting the primary style to <code>null</code> or the empty string. This will
   * {@link AttributeWriteStylesAdvanced#removeStyle(String) remove} the current primary style and replace it
   * with the next additional style. This can be totally confusing if you do {@link #setPrimaryStyle(String)}
   * and afterwards expect {@link #getPrimaryStyle()} to return the value you have set before.
   * 
   * @see AttributeReadStyles#getStyles()
   * 
   * @param primaryStyle is the new value for {@link #getPrimaryStyle() primary style}.
   */
  void setPrimaryStyle(String primaryStyle);

}
