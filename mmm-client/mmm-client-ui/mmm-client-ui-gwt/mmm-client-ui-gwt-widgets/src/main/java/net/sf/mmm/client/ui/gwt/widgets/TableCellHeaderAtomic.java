/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets;

import com.google.gwt.dom.client.Document;

/**
 * A {@link TableCellHeaderAtomic} is an {@link AbstractTableCellAtomic atomic cell} that represents a header
 * cell in a {@link TableWidget} (a {@literal <th>} element).
 * 
 * @see TableRow
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class TableCellHeaderAtomic extends AbstractTableCellAtomic {

  /**
   * The constructor.
   */
  public TableCellHeaderAtomic() {

    super();
    setElement(Document.get().createTHElement());
  }

}
