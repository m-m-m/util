/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.panel;

import net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegular;
import net.sf.mmm.ui.toolkit.api.widget.panel.UiWidgetPanel;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.UiWidgetDynamicCompositeGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.UiWidgetGwt;

import com.google.gwt.user.client.ui.InsertPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link UiWidgetPanel} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public abstract class UiWidgetPanelGwt<WIDGET extends Panel & InsertPanel> extends
    UiWidgetDynamicCompositeGwt<WIDGET, UiWidgetRegular> implements UiWidgetPanel<UiWidgetRegular> {

  /**
   * The constructor.
   * 
   * @param widget is the {@link #getWidget() widget}.
   */
  public UiWidgetPanelGwt(WIDGET widget) {

    super(widget);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doAddChild(UiWidgetRegular child, int index) {

    super.doAddChild(child, index);
    @SuppressWarnings("unchecked")
    UiWidgetGwt<? extends Widget> childWidget = (UiWidgetGwt<? extends Widget>) child;
    if (index == -1) {
      getWidget().add(childWidget.getWidget());
    } else {
      getWidget().insert(childWidget.getWidget(), index);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doRemoveChild(int index, UiWidgetRegular child) {

    super.doRemoveChild(index, child);
    @SuppressWarnings("unchecked")
    UiWidgetGwt<? extends Widget> childWidget = (UiWidgetGwt<? extends Widget>) child;
    getWidget().remove(childWidget.getWidget());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetEnabled(boolean newEnabled) {

    // nothing to do...
    // propagate to children???
  }

}
