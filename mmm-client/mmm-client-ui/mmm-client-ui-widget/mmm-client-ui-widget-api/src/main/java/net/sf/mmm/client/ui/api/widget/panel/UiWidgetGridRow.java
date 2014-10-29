/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.panel;

import net.sf.mmm.client.ui.api.widget.UiWidgetNative;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;

/**
 * This is the interface for a a {@link UiWidgetDynamicPanel dynamic panel} that represents a row of a
 * {@link UiWidgetGridPanel} and consists of {@link UiWidgetGridCell cells}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetGridRow extends UiWidgetDynamicPanel<UiWidgetGridCell>, UiWidgetNative {

  /**
   * This method creates a {@link UiWidgetGridCell}, {@link UiWidgetGridCell#setChild(UiWidgetRegular) sets}
   * the given <code>child</code> and {@link #addChild(UiWidgetGridCell) adds} it to this row. <br>
   * <b>ATTENTION:</b><br>
   * If the given <code>child</code> is a {@link net.sf.mmm.client.ui.api.widget.field.UiWidgetField}, this
   * method will also attach the
   * {@link net.sf.mmm.client.ui.api.widget.field.UiWidgetField#getLabelWidget() label} before. This is
   * done on purpose for convenience. If you want to prevent this,
   * {@link net.sf.mmm.client.ui.api.widget.factory.UiWidgetFactoryNative#create(Class) create} a
   * {@link UiWidgetGridCell} directly and {@link UiWidgetGridCell#setChild(UiWidgetRegular) set} the
   * {@link net.sf.mmm.client.ui.api.widget.field.UiWidgetField} directly before you
   * {@link #addChild(UiWidgetGridCell) add} it.
   * 
   * @see #addChild(UiWidgetGridCell)
   * 
   * @param child is the child to add.
   * @return the {@link UiWidgetGridCell}.
   */
  UiWidgetGridCell addChild(UiWidgetRegular child);

  /**
   * This method creates a {@link UiWidgetGridCell}, {@link UiWidgetGridCell#setChild(UiWidgetRegular) sets}
   * the given <code>children</code> composed to a single widget and {@link #addChild(UiWidgetGridCell) adds}
   * it to this row. <br>
   * 
   * @see #addChild(UiWidgetGridCell)
   * @see UiWidgetGridCell#setChildren(UiWidgetRegular...)
   * 
   * @param children are the children to add.
   * @return the {@link UiWidgetGridCell}.
   */
  UiWidgetGridCell addChildren(UiWidgetRegular... children);

}
