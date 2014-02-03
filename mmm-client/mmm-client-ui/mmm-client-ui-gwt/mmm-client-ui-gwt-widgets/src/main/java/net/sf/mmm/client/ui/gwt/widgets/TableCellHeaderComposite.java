/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets;

import com.google.gwt.dom.client.Document;

/**
 * A {@link TableCellHeaderComposite} is an {@link AbstractTableCellComposite composite cell} that represents
 * a header cell in a {@link TableWidget} (a {@literal <th>} element).
 * 
 * @see TableRow
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class TableCellHeaderComposite extends AbstractTableCellComposite {

  /**
   * The constructor.
   */
  public TableCellHeaderComposite() {

    super();
    setElement(Document.get().createTHElement());
  }

}
