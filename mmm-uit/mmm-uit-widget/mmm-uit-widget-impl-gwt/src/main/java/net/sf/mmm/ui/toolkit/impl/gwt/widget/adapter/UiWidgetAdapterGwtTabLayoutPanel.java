/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter;

import net.sf.mmm.ui.toolkit.api.widget.core.UiWidgetTab;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidget;
import net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterTabPanel;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.core.UiWidgetTabGwt;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.TabLayoutPanel;

/**
 * This is the implementation of {@link net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterPanel} using
 * GWT based on {@link TabLayoutPanel}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtTabLayoutPanel extends UiWidgetAdapterGwtDynamicComposite<TabLayoutPanel, UiWidgetTab>
    implements UiWidgetAdapterTabPanel<TabLayoutPanel> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtTabLayoutPanel() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected TabLayoutPanel createWidget() {

    return new TabLayoutPanel(1.5, Unit.EM);
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
  public void addChild(UiWidgetTab child, int index) {

    UiWidgetTabGwt tabWidget = (UiWidgetTabGwt) child;
    UiWidgetAdapterGwtTab widgetAdapter = AbstractUiWidget.getWidgetAdapter(tabWidget);
    if (index >= 0) {
      getWidget().insert(widgetAdapter.getContentPanel(), widgetAdapter.getWidget(), index);
    } else {
      getWidget().add(widgetAdapter.getContentPanel(), widgetAdapter.getWidget());
    }
  }

}
