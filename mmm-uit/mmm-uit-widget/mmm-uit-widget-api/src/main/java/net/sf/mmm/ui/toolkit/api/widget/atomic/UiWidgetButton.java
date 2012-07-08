/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget.atomic;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteImage;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteLabel;

/**
 * This is the interface for an {@link UiWidgetAtomicClickable clickable widget} that represents a button.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetButton extends UiWidgetAtomicClickable, AttributeWriteLabel,
    AttributeWriteImage<UiWidgetImage> {

  // nothing to add...

}
