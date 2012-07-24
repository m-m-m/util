/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.panel;

import net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegular;
import net.sf.mmm.ui.toolkit.api.widget.core.UiWidgetTab;
import net.sf.mmm.ui.toolkit.api.widget.panel.UiWidgetTabPanel;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.panel.AbstractUiWidgetTabPanel;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter.UiWidgetAdapterGwtTabLayoutPanel;

/**
 * This is the implementation of {@link UiWidgetTabPanel} using GWT based on
 * {@link UiWidgetAdapterGwtTabLayoutPanel}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetTabPanelGwt extends AbstractUiWidgetTabPanel<UiWidgetAdapterGwtTabLayoutPanel> {

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public UiWidgetTabPanelGwt(AbstractUiWidgetFactory<?> factory) {

    super(factory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtTabLayoutPanel createWidgetAdapter() {

    return new UiWidgetAdapterGwtTabLayoutPanel();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetTab addChild(UiWidgetRegular child, String label) {

    UiWidgetTab tab = getFactory().create(UiWidgetTab.class);
    tab.setLabel(label);
    tab.setChild(child);
    addChild(tab);
    return tab;
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetTabPanel> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetTabPanel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetTabPanel create(AbstractUiWidgetFactory<?> factory) {

      return new UiWidgetTabPanelGwt(factory);
    }

  }

}
