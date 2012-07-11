/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget.atomic;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteHandlerObserver;
import net.sf.mmm.ui.toolkit.api.feature.UiFeatureClick;

/**
 * This is the interface for an {@link UiWidgetAtomicRegular adapter} that can be clicked.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetAtomicClickable extends UiWidgetAtomicRegular, UiFeatureClick,
    AttributeWriteHandlerObserver {

  // nothing to add...

}
