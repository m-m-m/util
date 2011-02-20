/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.widget;

import net.sf.mmm.ui.toolkit.api.attribute.UiWriteImage;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteSelected;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteValue;

/**
 * This is the interface for a button.<br>
 * According to its {@link #getButtonStyle() style} this can be a
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.ButtonStyle#RADIO radio-},
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.ButtonStyle#CHECK checkbox-},
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.ButtonStyle#TOGGLE toggle-}, or
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.ButtonStyle#DEFAULT regular}
 * button.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiButton extends UiWidget, UiWriteValue<String>, UiWriteImage, UiWriteSelected {

  /** the type of this object */
  String TYPE = "Button";

  /**
   * This method gets the style of this button. The style determines the
   * visualization and the behavior of the button.
   * 
   * @return the button style.
   */
  ButtonStyle getButtonStyle();

}
