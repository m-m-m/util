/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field;

import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetTextArea;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.field.AbstractUiWidgetTextArea;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtTextArea;

/**
 * This is the implementation of {@link UiWidgetTextArea} using GWT based on
 * {@link UiWidgetAdapterGwtTextArea}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetTextAreaGwt extends AbstractUiWidgetTextArea<UiWidgetAdapterGwtTextArea<String>> {

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public UiWidgetTextAreaGwt(AbstractUiWidgetFactory<?> factory) {

    super(factory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtTextArea<String> createWidgetAdapter() {

    return new UiWidgetAdapterGwtTextArea<String>();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetTextArea> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetTextArea.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetTextArea create(AbstractUiWidgetFactory<?> factory) {

      return new UiWidgetTextAreaGwt(factory);
    }

  }

}
