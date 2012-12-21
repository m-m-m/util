/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetComposite;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter;

/**
 * This is the abstract base implementation of {@link UiWidgetComposite}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetComposite<ADAPTER extends UiWidgetAdapter<?>, CHILD extends UiWidget> extends
    AbstractUiWidgetReal<ADAPTER, Void> implements UiWidgetComposite<CHILD> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetComposite(AbstractUiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CHILD getChild(String id) {

    return (CHILD) super.getChild(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract CHILD getChild(int index);

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract int getChildIndex(UiWidget child);

}
