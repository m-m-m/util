/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter;

import net.sf.mmm.client.ui.api.widget.panel.UiWidgetGridCell;
import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterGridRow;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtDynamicComposite;
import net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter.UiWidgetAdapterGwtGridRow.GridRow;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of
 * {@link net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterDynamicPanel} using GWT based on
 * {@link GridRow}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtGridRow extends UiWidgetAdapterGwtDynamicComposite<GridRow, UiWidgetGridCell> implements
    UiWidgetAdapterGridRow {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtGridRow() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected GridRow createToplevelWidget() {

    return new GridRow();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChild(UiWidgetGridCell child, int index) {

    Element childElement = getToplevelWidget(child).getElement();
    Element element = getToplevelWidget().getElement();
    if (index < 0) {
      element.appendChild(childElement);
    } else {
      DOM.insertChild(element, childElement, index);
    }
  }

  /**
   * This inner class is a custom {@link Widget} that represents a {@link GridRow} (TR-tag).
   */
  protected static class GridRow extends Widget {

    /**
     * The constructor.
     * 
     */
    public GridRow() {

      super();
      setElement(Document.get().createTRElement());
    }

  }

}
