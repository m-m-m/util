/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets;

import com.google.gwt.dom.client.Document;

/**
 * A {@link TableCell} is a {@link SingleCompositePanel} that represents a table data element (a
 * {@literal <td> element}).
 * 
 * @see TableRow
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class TableCell extends AbstractTableCell {

  /**
   * The constructor.
   */
  public TableCell() {

    super();
    setElement(Document.get().createTDElement());
  }

}
