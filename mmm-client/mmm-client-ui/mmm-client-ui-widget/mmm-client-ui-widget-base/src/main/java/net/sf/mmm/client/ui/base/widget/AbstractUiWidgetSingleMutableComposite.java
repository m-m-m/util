/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetSingleMutableComposite;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapterSingleMutableComposite;

/**
 * This is the abstract base implementation of {@link UiWidgetSingleMutableComposite}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetSingleMutableComposite<ADAPTER extends UiWidgetAdapterSingleMutableComposite<CHILD>, CHILD extends UiWidget>
    extends AbstractUiWidgetSingleComposite<ADAPTER, CHILD> implements UiWidgetSingleMutableComposite<CHILD> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetSingleMutableComposite(AbstractUiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setChild(CHILD child) {

    super.setChild(child);
  }

}
