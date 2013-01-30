/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteHeightInRows;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteOptions;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteSelectionMode;
import net.sf.mmm.client.ui.api.feature.UiFeatureSelectedValue;

/**
 * This is the interface for a {@link UiWidget widget} that presents a list of {@link #getOptions() options}
 * and allows the end-user to select {@link #setSelectionMode(net.sf.mmm.client.ui.api.common.SelectionMode)
 * one or multiple} out of these options.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getOptions() options}.
 */
public abstract interface UiWidgetListBase<VALUE> extends UiWidget, AttributeWriteOptions<VALUE>,
    UiFeatureSelectedValue<VALUE>, AttributeWriteSelectionMode, AttributeWriteHeightInRows {

  // nothing to add...

}
