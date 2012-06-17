/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.adapter.atomic;

import net.sf.mmm.ui.toolkit.api.adapter.UiAdapterRegular;

/**
 * This is the interface for a {@link UiAdapterRegular regular}
 * {@link UiAdapterAtomic}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
public abstract interface UiAdapterRegularAtomic<VALUE> extends UiAdapterAtomic<VALUE>, UiAdapterRegular<VALUE> {

  // just a marker interface...

}
