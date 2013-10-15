/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.panel;

import net.sf.mmm.client.ui.api.common.ButtonContainer;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;

/**
 * This is the interface for a {@link UiWidgetDynamicPanel dynamic panel} that shows a number of
 * {@link net.sf.mmm.client.ui.api.widget.core.UiWidgetButton buttons}. To group related buttons in a
 * {@link UiWidgetButtonGroup} you may use {@link #startGroup()} and {@link #endGroup()}.<br/>
 * <b>ATTENTION:</b><br/>
 * For flexibility there is no technical restriction so that {@link #addChild(UiWidgetRegular)} will only take
 * buttons and related widgets. For usability you should however not misuse this to add fields or other
 * arbitrary widgets to this widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWidgetAbstractButtonContainer extends UiWidgetDynamicPanel<UiWidgetRegular>,
    ButtonContainer {

  /**
   * {@inheritDoc}
   * 
   * A button-group is realized by {@link UiWidgetButtonGroup}. this toolbar.
   * 
   * @see #addChild(UiWidgetRegular)
   */
  @Override
  void startGroup() throws IllegalStateException;

}
