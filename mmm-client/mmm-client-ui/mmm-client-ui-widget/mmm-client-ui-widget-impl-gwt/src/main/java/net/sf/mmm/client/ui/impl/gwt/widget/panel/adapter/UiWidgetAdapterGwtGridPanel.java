/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter;

import net.sf.mmm.client.ui.api.widget.panel.UiWidgetGridRow;
import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterGridPanel;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtDynamicComposite;
import net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter.UiWidgetAdapterGwtGridPanel.GridPanel;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of
 * {@link net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterDynamicPanel} using GWT based on
 * {@link GridPanel}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtGridPanel extends UiWidgetAdapterGwtDynamicComposite<GridPanel, UiWidgetGridRow>
    implements UiWidgetAdapterGridPanel<GridPanel> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtGridPanel() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected GridPanel createWidget() {

    return new GridPanel();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChild(UiWidgetGridRow child, int index) {

    Element childElement = getWidget(child).getElement();
    Element element = getWidget().getElement();
    if (index < 0) {
      DOM.appendChild(element, childElement);
    } else {
      DOM.insertChild(element, childElement, index);
    }
  }

  /**
   * This inner class is a custom {@link Widget} that represents a {@link GridPanel} (table-tag).
   */
  protected static class GridPanel extends Widget {

    /**
     * The constructor.
     * 
     */
    public GridPanel() {

      super();
      setElement(Document.get().createTableElement());
    }

  }

}
