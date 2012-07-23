/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget;

import net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegularAtomic;
import net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapter;

/**
 * This is the abstract base implementation of {@link UiWidgetRegularAtomic} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 */
public abstract class AbstractUiWidgetRegularAtomic<ADAPTER extends UiWidgetAdapter<?>> extends
    AbstractUiWidgetAtomic<ADAPTER> implements UiWidgetRegularAtomic {

  /**
   * The constructor.
   */
  public AbstractUiWidgetRegularAtomic() {

    super();
  }

}
