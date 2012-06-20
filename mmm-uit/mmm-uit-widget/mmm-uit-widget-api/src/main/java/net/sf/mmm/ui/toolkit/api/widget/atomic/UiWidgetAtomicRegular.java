/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget.atomic;

import net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegular;

/**
 * This is the interface for a {@link UiWidgetRegular regular} {@link UiWidgetAtomic}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
public abstract interface UiWidgetAtomicRegular<VALUE> extends UiWidgetAtomic<VALUE>, UiWidgetRegular<VALUE> {

  // nothing to add...

}
