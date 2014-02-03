/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets;

import com.google.gwt.dom.client.Document;

/**
 * A {@link TableHead} is a {@link CustomPanel} that represents the head of a {@link TableWidget table} (
 * {@literal <thead>} element). You can add {@link TableRow} widgets to it, that typically contain
 * {@link TableCellHeaderAtomic} or {@link TableCellHeaderComposite} widgets.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class TableHead extends CustomPanel {

  /**
   * The constructor.
   */
  public TableHead() {

    super();
    setElement(Document.get().createTHeadElement());
  }

}
