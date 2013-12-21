/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteColumnSpan;

import com.google.gwt.dom.client.Document;

/**
 * A {@link GridCell} is a {@link SingleCompositePanel} that represents a table data element ({@literal <td>}
 * for a cell in a {@link GridRow}).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class GridCell extends SingleCompositePanel implements AttributeWriteColumnSpan {

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
