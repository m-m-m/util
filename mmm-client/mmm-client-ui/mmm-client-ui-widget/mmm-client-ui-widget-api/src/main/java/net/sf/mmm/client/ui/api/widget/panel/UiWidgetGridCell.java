/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.panel;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteColumnSpan;
import net.sf.mmm.client.ui.api.widget.UiWidgetNative;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.UiWidgetSingleMutableComposite;

/**
 * This is the interface for a {@link UiWidgetSingleMutableComposite single composite} that represents the
 * cell of a {@link UiWidgetGridRow}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetGridCell extends UiWidgetSingleMutableComposite<UiWidgetRegular>, AttributeWriteColumnSpan,
    UiWidgetNative {

  /**
   * This method creates a {@link net.sf.mmm.client.ui.api.widget.panel.UiWidgetHorizontalPanel} adds the
   * given <code>children</code> to it and {@link #setChild(UiWidgetRegular) sets} it it as child of this
   * cell.<br/>
   * 
   * @see #setChild(UiWidgetRegular)
   * 
   * @param children are the children to add.
   */
  void setChildren(UiWidgetRegular... children);

}
