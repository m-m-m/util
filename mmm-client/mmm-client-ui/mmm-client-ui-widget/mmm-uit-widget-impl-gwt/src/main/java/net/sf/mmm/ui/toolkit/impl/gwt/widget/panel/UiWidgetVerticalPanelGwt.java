/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.panel;

import net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegular;
import net.sf.mmm.ui.toolkit.api.widget.panel.UiWidgetVerticalPanel;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactoryReal;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetDynamicComposite;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.panel.adapter.UiWidgetAdapterGwtVerticalPanel;

/**
 * This is the implementation of {@link UiWidgetVerticalPanel} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetVerticalPanelGwt extends
    AbstractUiWidgetDynamicComposite<UiWidgetAdapterGwtVerticalPanel, UiWidgetRegular> implements UiWidgetVerticalPanel {

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public UiWidgetVerticalPanelGwt(AbstractUiWidgetFactory<?> factory) {

    super(factory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtVerticalPanel createWidgetAdapter() {

    return new UiWidgetAdapterGwtVerticalPanel();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryReal factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryReal<UiWidgetVerticalPanel> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetVerticalPanel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetVerticalPanel create(AbstractUiWidgetFactory<?> factory) {

      return new UiWidgetVerticalPanelGwt(factory);
    }
  }

}
