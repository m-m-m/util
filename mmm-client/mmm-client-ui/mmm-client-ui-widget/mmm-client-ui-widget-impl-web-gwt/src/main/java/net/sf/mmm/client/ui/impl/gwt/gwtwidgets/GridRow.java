/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets;

import com.google.gwt.dom.client.Document;

/**
 * A {@link GridRow} is a {@link CustomPanel} that represents a table row ({@literal <tr>}). You can
 * {@link #add(com.google.gwt.user.client.ui.IsWidget) add} a {@link GridCell} to it.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class GridRow extends CustomPanel {

  /**
   * The constructor.
   */
  public GridRow() {

    super();
    setElement(Document.get().createTRElement());
  }

}
