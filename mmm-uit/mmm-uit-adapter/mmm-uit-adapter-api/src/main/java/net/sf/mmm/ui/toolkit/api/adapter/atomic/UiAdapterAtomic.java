/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.adapter.atomic;

import net.sf.mmm.ui.toolkit.api.adapter.UiAdapter;

/**
 * This is the interface for an atomic {@link UiAdapter}. Unlike
 * {@link net.sf.mmm.ui.toolkit.api.adapter.composite.UiAdapterComposite} such
 * adapter can not have
 * {@link net.sf.mmm.ui.toolkit.api.adapter.composite.UiAdapterComposite#getChild(int)
 * children}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
public abstract interface UiAdapterAtomic<VALUE> extends UiAdapter<VALUE> {

  // just a marker interface...

}
