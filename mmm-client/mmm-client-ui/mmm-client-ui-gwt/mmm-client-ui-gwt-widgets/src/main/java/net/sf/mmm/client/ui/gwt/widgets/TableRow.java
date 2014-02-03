/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Widget;

/**
 * A {@link TableRow} is a {@link CustomPanel} that represents a table row ({@literal <tr>}). You can
 * {@link #add(com.google.gwt.user.client.ui.IsWidget) add} a {@link TableCellAtomic} to it.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class TableRow extends CustomPanel {

  /**
   * The constructor.
   */
  public TableRow() {

    super();
    setElement(Document.get().createTRElement());
  }

  /**
   * @param widget is the {@link Widget} to add as child of a new {@link TableCellAtomic}.
   * @return the {@link TableCellAtomic} wrapping the given widget.
   */
  public TableCellAtomic addCell(Widget widget) {

    TableCellAtomic cell = new TableCellAtomic();
    cell.add(widget);
    add(cell);
    return cell;
  }

}
