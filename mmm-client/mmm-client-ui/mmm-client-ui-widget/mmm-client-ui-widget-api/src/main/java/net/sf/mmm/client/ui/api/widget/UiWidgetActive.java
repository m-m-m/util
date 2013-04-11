/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteAccessKey;
import net.sf.mmm.client.ui.api.feature.UiFeatureFocus;

/**
 * This is the interface for a {@link UiWidgetRegular regular widget} that can be {@link #setFocused()
 * focused} and allows to {@link #setAccessKey(char) assign an access-key}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetActive extends UiWidget, AttributeWriteAccessKey, UiFeatureFocus {

  // nothing to add...

}
