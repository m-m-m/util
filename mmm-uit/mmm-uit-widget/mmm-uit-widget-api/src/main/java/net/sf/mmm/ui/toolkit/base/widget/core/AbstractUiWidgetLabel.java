/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget.core;

import net.sf.mmm.ui.toolkit.api.widget.core.UiWidgetLabel;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetAtomicWithLabel;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.core.adapter.UiWidgetAdapterLabel;

/**
 * This is the abstract base implementation of {@link UiWidgetLabel}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 */
public abstract class AbstractUiWidgetLabel<ADAPTER extends UiWidgetAdapterLabel<?>> extends
    AbstractUiWidgetAtomicWithLabel<ADAPTER> implements UiWidgetLabel {

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public AbstractUiWidgetLabel(AbstractUiWidgetFactory<?> factory) {

    super(factory);
  }

}
