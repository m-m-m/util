/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.adapter.composite;

import net.sf.mmm.ui.toolkit.api.adapter.UiAdapterRegular;

/**
 * TODO: this class ...
 * 
 * @author hohwille
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
public interface UiAdapterRegularComposite<VALUE> extends UiAdapterComposite<VALUE, UiAdapterRegular<?>>,
    UiAdapterRegular<VALUE> {

}
