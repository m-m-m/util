/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.core;

import net.sf.mmm.ui.toolkit.api.widget.core.UiWidgetLabel;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.core.AbstractUiWidgetLabel;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.core.adapter.UiWidgetAdapterGwtLabel;

/**
 * This is the implementation of {@link UiWidgetLabel} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetLabelGwt extends AbstractUiWidgetLabel<UiWidgetAdapterGwtLabel> {

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public UiWidgetLabelGwt(AbstractUiWidgetFactory<?> factory) {

    super(factory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtLabel createWidgetAdapter() {

    return new UiWidgetAdapterGwtLabel();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetLabel> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetLabel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetLabel create(AbstractUiWidgetFactory<?> factory) {

      return new UiWidgetLabelGwt(factory);
    }

  }

}
