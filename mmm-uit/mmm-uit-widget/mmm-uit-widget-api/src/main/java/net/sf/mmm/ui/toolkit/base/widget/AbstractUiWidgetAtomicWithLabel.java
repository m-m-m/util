/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteLabel;
import net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterWithLabel;

/**
 * This is the abstract base implementation of {@link net.sf.mmm.ui.toolkit.api.widget.UiWidgetAtomic} in
 * combination with a {@link #getLabel() label}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 */
public abstract class AbstractUiWidgetAtomicWithLabel<ADAPTER extends UiWidgetAdapterWithLabel<?>> extends
    AbstractUiWidgetAtomic<ADAPTER> implements AttributeWriteLabel {

  /** @see #getLabel() */
  private String label;

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public AbstractUiWidgetAtomicWithLabel(AbstractUiWidgetFactory<?> factory) {

    super(factory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.label != null) {
      adapter.setLabel(this.label);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLabel(String label) {

    this.label = label;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setLabel(label);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLabel() {

    return this.label;
  }

}
