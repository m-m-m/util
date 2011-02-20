/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives write access to the {@link #setStyle(String)
 * style-name(s)} of an {@link net.sf.mmm.ui.toolkit.api.UiObject object}.
 * 
 * @see UiReadStyle
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWriteStyle extends UiReadStyle {

  /**
   * This method sets the {@link #getStyle() style-name(s)} of this object.
   * 
   * @see #getStyle()
   * 
   * @param style is the style of this object. May also be a list of styles
   *        separated by whitespaces. Will be the empty string if NOT set.
   */
  void setStyle(String style);

}
