/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import net.sf.mmm.ui.toolkit.api.common.ButtonStyle;

/**
 * This interface gives read access to the {@link #getButtonStyle()
 * button-style} of an {@link net.sf.mmm.ui.toolkit.api.UiObject}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiReadButtonStyle {

  /**
   * This method gets the style of this object. The style determines the
   * visualization and the behavior.
   * 
   * @return the button style.
   */
  ButtonStyle getButtonStyle();

}
