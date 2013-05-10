/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter;

/**
 * This is the abstract base implementation of {@link UiWidgetRegular} for atomic widgets (widgets that are
 * not composite and therefore never have children).
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetRegularAtomic<ADAPTER extends UiWidgetAdapter> extends
    AbstractUiWidgetNative<ADAPTER, Void> implements UiWidgetRegular {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetRegularAtomic(UiContext context) {

    super(context);
  }

}
