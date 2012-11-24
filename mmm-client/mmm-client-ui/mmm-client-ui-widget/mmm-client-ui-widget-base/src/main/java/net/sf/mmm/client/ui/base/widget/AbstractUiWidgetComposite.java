/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetComposite;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter;

/**
 * This is the abstract base implementation of {@link UiWidgetComposite}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 */
public abstract class AbstractUiWidgetComposite<ADAPTER extends UiWidgetAdapter<?>, CHILD extends UiWidget> extends
    AbstractUiWidget<ADAPTER> implements UiWidgetComposite<CHILD> {

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public AbstractUiWidgetComposite(AbstractUiWidgetFactory<?> factory) {

    super(factory);
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