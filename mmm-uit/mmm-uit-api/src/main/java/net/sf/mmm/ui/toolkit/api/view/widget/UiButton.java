/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.widget;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteSelected;
import net.sf.mmm.ui.toolkit.api.attribute.UiReadButtonStyle;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteImage;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteValue;

/**
 * This is the interface for a button.<br>
 * According to its {@link #getButtonStyle() style} this can be a
 * {@link net.sf.mmm.ui.toolkit.api.common.ButtonStyle#RADIO radio-},
 * {@link net.sf.mmm.ui.toolkit.api.common.ButtonStyle#CHECK checkbox-},
 * {@link net.sf.mmm.ui.toolkit.api.common.ButtonStyle#TOGGLE toggle-}, or
 * {@link net.sf.mmm.ui.toolkit.api.common.ButtonStyle#DEFAULT regular} button.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiButton extends UiWidget, UiWriteValue<String>, UiReadButtonStyle, UiWriteImage,
    AttributeWriteSelected {

  /** the type of this object */
  String TYPE = "Button";

}
