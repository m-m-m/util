/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget;

import net.sf.mmm.client.ui.api.feature.UiFeatureValueAndValidation;

/**
 * This is the interface for a {@link UiWidgetRegular regular widget} that has a {@link #getValue() value}.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetWithValue<VALUE> extends AbstractUiWidgetWithValue<VALUE>, UiFeatureValueAndValidation<VALUE>,
    UiWidgetRegular {

  // nothing to add...

}
