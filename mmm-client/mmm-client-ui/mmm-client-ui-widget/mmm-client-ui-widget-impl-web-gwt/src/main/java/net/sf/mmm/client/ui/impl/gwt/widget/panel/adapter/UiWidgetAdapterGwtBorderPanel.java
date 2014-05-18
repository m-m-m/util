/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter;

import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterBorderPanel;
import net.sf.mmm.client.ui.gwt.widgets.Fieldset;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtSingleMutableComposite;

import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link UiWidgetAdapterBorderPanel} using GWT based on {@link Fieldset}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtBorderPanel extends UiWidgetAdapterGwtSingleMutableComposite<Fieldset, UiWidgetRegular>
    implements UiWidgetAdapterBorderPanel {

  /** @see #setChild(UiWidgetRegular) */
  private Widget childWidget;

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtBorderPanel() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Fieldset createToplevelWidget() {

    return new Fieldset();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLabel(String label) {

    getToplevelWidget().setLabel(label);
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
  public void setChild(UiWidgetRegular child) {

    if (this.childWidget != null) {
      getToplevelWidget().remove(this.childWidget);
    }
    Widget newChildWidget = null;
    if (child != null) {
      newChildWidget = getToplevelWidget(child);
      getToplevelWidget().add(newChildWidget);
    }
    this.childWidget = newChildWidget;
  }

}
