/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.core;

import net.sf.mmm.client.ui.api.widget.UiWidgetNative;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.UiWidgetSingleMutableComposite;

/**
 * This is the interface for a {@link UiWidgetSingleMutableComposite single composite widget} that represents
 * a <code>slot</code>. A slot is a placeholder for any sub-dialog that may be embedded at this position. The
 * widget itself will have no visual effect than showing its {@link #getChild() child} (if
 * {@link #setChild(UiWidgetRegular) set}).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetSlot extends UiWidgetSingleMutableComposite<UiWidgetRegular>, UiWidgetRegular, UiWidgetNative {

  /** The default {@link #getPrimaryStyle() primary style} of this widget. */
  String PRIMARY_STYLE = "Slot";

}
