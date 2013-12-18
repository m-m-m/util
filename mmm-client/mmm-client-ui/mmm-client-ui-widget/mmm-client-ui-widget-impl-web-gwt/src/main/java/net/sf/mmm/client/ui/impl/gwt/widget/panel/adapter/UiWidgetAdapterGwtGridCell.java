/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteColumnSpan;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterGridCell;
import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.SingleCompositePanel;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtSingleMutableComposite;
import net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter.UiWidgetAdapterGwtGridCell.GridCell;

import com.google.gwt.dom.client.Document;

/**
 * This is the implementation of
 * {@link net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterDynamicPanel} using GWT based on
 * {@link GridCell}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtGridCell extends UiWidgetAdapterGwtSingleMutableComposite<GridCell, UiWidgetRegular>
    implements UiWidgetAdapterGridCell {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtGridCell() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected GridCell createToplevelWidget() {

    return new GridCell();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setChild(UiWidgetRegular child) {

    getToplevelWidget().setChild(getToplevelWidget(child));
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
  public int getColumnSpan() {

    return getToplevelWidget().getColumnSpan();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setColumnSpan(int columnSpan) {

    getToplevelWidget().setColumnSpan(columnSpan);
  }

  /**
   * This inner class is a custom {@link com.google.gwt.user.client.ui.Widget} that represents a
   * {@link GridCell} (TD-tag).
   */
  protected static class GridCell extends SingleCompositePanel implements
      AttributeWriteColumnSpan {

    /** @see #setColumnSpan(int) */
    private static final String ATTRIBUTE_COLSPAN = "colspan";

    /**
     * The constructor.
     */
    public GridCell() {

      super();
      setElement(Document.get().createTDElement());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getColumnSpan() {

      String colspan = getElement().getAttribute(ATTRIBUTE_COLSPAN);
      if ((colspan == null) || colspan.isEmpty()) {
        return 1;
      }
      return Integer.parseInt(colspan);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setColumnSpan(int columnSpan) {

      getElement().setAttribute(ATTRIBUTE_COLSPAN, Integer.toString(columnSpan));
    }
  }

}
