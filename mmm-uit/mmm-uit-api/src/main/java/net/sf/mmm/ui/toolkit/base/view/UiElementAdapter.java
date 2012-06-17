/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.view;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteSize;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteTooltip;

/**
 * This is the interface that adapts to the native UI object of the underlying
 * toolkit implementation for a {@link net.sf.mmm.ui.toolkit.api.view.UiElement
 * element}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 * @since 1.0.0
 */
public interface UiElementAdapter<DELEGATE> extends UiNodeAdapter<DELEGATE>, AttributeWriteTooltip, AttributeWriteSize {

}
