/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.panel;

import net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegular;
import net.sf.mmm.ui.toolkit.api.widget.core.UiWidgetTab;
import net.sf.mmm.ui.toolkit.api.widget.panel.UiWidgetTabPanel;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.panel.AbstractUiWidgetTabPanel;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter.UiWidgetAdapterGwtTabLayoutPanel;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.core.UiWidgetTabGwt;

import com.google.gwt.user.client.ui.TabLayoutPanel;

/**
 * This is the implementation of {@link UiWidgetTabPanel} using GWT based on {@link TabLayoutPanel}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetTabPanelGwt extends AbstractUiWidgetTabPanel<UiWidgetAdapterGwtTabLayoutPanel> implements
    UiWidgetTabPanel {

  /**
   * The constructor.
   */
  public UiWidgetTabPanelGwt() {

    super();
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

    UiWidgetTabGwt tab = new UiWidgetTabGwt();
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
    public UiWidgetTabPanel create() {

      return new UiWidgetTabPanelGwt();
    }

  }

}
