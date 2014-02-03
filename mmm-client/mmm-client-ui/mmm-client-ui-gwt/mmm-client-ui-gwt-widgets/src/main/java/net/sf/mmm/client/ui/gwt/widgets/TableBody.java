/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets;

import com.google.gwt.dom.client.Document;

/**
 * A {@link TableWidget} is a {@link CustomPanel} that represents the body of a {@link TableWidget table} (
 * {@literal <tbody>} element). You can add {@link TableRow} widgets to it, that typically contain
 * {@link TableCellAtomic} or {@link TableCellComposite} widgets.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class TableBody extends CustomPanel {

  /**
   * The constructor.
   */
  public TableBody() {

    super();
    setElement(Document.get().createTBodyElement());
  }

}
