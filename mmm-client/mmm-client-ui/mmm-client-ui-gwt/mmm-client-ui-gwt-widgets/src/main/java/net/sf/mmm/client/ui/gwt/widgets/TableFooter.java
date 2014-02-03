/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets;

import com.google.gwt.dom.client.Document;

/**
 * A {@link TableFooter} is a {@link CustomPanel} that represents the footer of a {@link TableWidget table} (
 * {@literal <tfoot>} element). You can add {@link TableRow} widgets to it.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class TableFooter extends CustomPanel {

  /**
   * The constructor.
   */
  public TableFooter() {

    super();
    setElement(Document.get().createTFootElement());
  }

}
