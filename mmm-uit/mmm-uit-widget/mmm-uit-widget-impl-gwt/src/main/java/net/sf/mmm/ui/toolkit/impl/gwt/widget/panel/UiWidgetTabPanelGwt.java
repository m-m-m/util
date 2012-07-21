/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.panel;

import net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegular;
import net.sf.mmm.ui.toolkit.api.widget.core.UiWidgetTab;
import net.sf.mmm.ui.toolkit.api.widget.panel.UiWidgetTabPanel;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.UiWidgetDynamicCompositeGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.core.UiWidgetTabGwt;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.TabLayoutPanel;

/**
 * This is the implementation of {@link UiWidgetTabPanel} using GWT based on {@link TabLayoutPanel}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetTabPanelGwt extends UiWidgetDynamicCompositeGwt<TabLayoutPanel, UiWidgetTab> implements
    UiWidgetTabPanel {

  /**
   * The constructor.
   */
  public UiWidgetTabPanelGwt() {

    super(new TabLayoutPanel(2, Unit.EM));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showChild(int index) {

    getWidget().selectTab(index);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean showChild(UiWidgetTab child) {

    int index = getChildIndex(child);
    if (index >= 0) {
      getWidget().selectTab(index);
      return true;
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetTab addChild(UiWidgetRegular child, String title) {

    UiWidgetTabGwt tab = new UiWidgetTabGwt();
    tab.setTitle(title);
    tab.setChild(child);
    addChild(tab);
    return tab;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doAddChild(UiWidgetTab child, int index) {

    super.doAddChild(child, index);
    UiWidgetTabGwt tab = (UiWidgetTabGwt) child;
    if (index == -1) {
      getWidget().add(tab.getContentPanel(), tab.getWidget());
    } else {
      getWidget().insert(tab.getContentPanel(), tab.getWidget(), index);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetEnabled(boolean newEnabled) {

    // getWidget().getTabBar().setTabEnabled(index, this.enabled);
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
