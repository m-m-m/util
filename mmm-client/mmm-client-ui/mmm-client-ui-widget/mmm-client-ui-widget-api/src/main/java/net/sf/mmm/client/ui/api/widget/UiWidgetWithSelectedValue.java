/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget;

import net.sf.mmm.client.ui.api.feature.UiFeatureSelectedValue;

/**
 * This is the interface for for a {@link UiWidgetRegular regular widget} that is or contains a
 * {@link UiFeatureSelectedValue}.
 *
 * @param <VALUE> is the generic type of the selectable {@link UiFeatureSelectedValue#getSelectedValues()
 *        values}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWidgetWithSelectedValue<VALUE> extends UiWidgetRegular {

  /**
   * @return the {@link UiFeatureSelectedValue}. Either this object itself or a child object contained in this
   *         widget.
   */
  UiFeatureSelectedValue<VALUE> asFeatureSelectedValue();

}
