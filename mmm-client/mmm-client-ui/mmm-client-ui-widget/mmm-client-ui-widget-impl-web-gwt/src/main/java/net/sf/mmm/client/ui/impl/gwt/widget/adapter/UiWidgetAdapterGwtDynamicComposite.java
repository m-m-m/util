/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.adapter;

import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapterDynamicComposite;

import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.InsertPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link UiWidgetAdapterDynamicComposite} using GWT.
 * 
 * @param <WIDGET> is the generic type of {@link #getToplevelWidget()}.
 * @param <CHILD> is the generic type of the {@link #addChild(UiWidget, int) children}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetAdapterGwtDynamicComposite<WIDGET extends Widget, CHILD extends UiWidget> extends
    UiWidgetAdapterGwtWidget<WIDGET> implements UiWidgetAdapterDynamicComposite<CHILD> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtDynamicComposite() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param toplevelWidget is the {@link #getToplevelWidget() toplevel widget}.
   */
  public UiWidgetAdapterGwtDynamicComposite(WIDGET toplevelWidget) {

    super(toplevelWidget);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    // nothing to do...
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeChild(CHILD child, int index) {

    // nothing to do (removeFromParent() is already sufficient)
  }

  /**
   * This is the abstract base class for a custom {@link ComplexPanel panel}.
   */
  public abstract static class CustomPanel extends ComplexPanel implements InsertPanel.ForIsWidget {

    /**
     * The constructor.
     * 
     */
    public CustomPanel() {

      super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(Widget w) {

      add(w, getElement());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(Widget widget, int beforeIndex) {

      insert(widget, getElement(), beforeIndex, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(IsWidget widget, int beforeIndex) {

      insert(asWidgetOrNull(widget), beforeIndex);
    }

  }

}
