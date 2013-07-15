/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter;

import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterBorderPanel;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtSingleMutableComposite;
import net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter.UiWidgetAdapterGwtBorderPanel.BorderPanel;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of
 * {@link net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterBorderPanel} using GWT based on
 * {@link net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter.UiWidgetAdapterGwtBorderPanel.BorderPanel}.
 * 
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtBorderPanel extends
    UiWidgetAdapterGwtSingleMutableComposite<BorderPanel, UiWidgetRegular> implements UiWidgetAdapterBorderPanel {

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
  protected BorderPanel createToplevelWidget() {

    return new BorderPanel();
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

    Widget childWidget = null;
    if (child != null) {
      childWidget = getToplevelWidget(child);
    }
    getToplevelWidget().setChild(childWidget);
  }

  /**
   * This inner class is a custom {@link Widget} that represents a
   * {@link net.sf.mmm.client.ui.api.widget.panel.UiWidgetBorderPanel} (fieldset-tag).
   */
  protected static class BorderPanel extends UiWidgetAdapterGwtSingleMutableComposite.SingleCompositePanel {

    /** The labeled legend element. */
    private final Element legend;

    /**
     * The constructor.
     */
    public BorderPanel() {

      super();
      setElement(Document.get().createElement("fieldset"));
      this.legend = Document.get().createElement("legend");
      getElement().appendChild(this.legend);
    }

    /**
     * @param label is the label for the border text.
     */
    public void setLabel(String label) {

      // HTML injection???
      this.legend.setInnerText(label);
    }

  }

}
