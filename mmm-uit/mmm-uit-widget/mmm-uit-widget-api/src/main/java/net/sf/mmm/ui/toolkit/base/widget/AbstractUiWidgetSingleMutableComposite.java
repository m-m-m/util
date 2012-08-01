/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget;

import net.sf.mmm.ui.toolkit.api.widget.UiWidget;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetSingleMutableComposite;
import net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterSingleMutableComposite;

/**
 * This is the abstract base implementation of {@link UiWidgetSingleMutableComposite}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 */
public abstract class AbstractUiWidgetSingleMutableComposite<ADAPTER extends UiWidgetAdapterSingleMutableComposite<?, CHILD>, CHILD extends UiWidget>
    extends AbstractUiWidgetSingleComposite<ADAPTER, CHILD> implements UiWidgetSingleMutableComposite<CHILD> {

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public AbstractUiWidgetSingleMutableComposite(AbstractUiWidgetFactory<?> factory) {

    super(factory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setChild(CHILD child) {

    super.setChild(child);
  }

}
