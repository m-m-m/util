/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets;

import com.google.gwt.dom.client.Document;

/**
 * A {@link GridPanel} is a {@link CustomPanel} that represents a table (&lt;table&gt;). You can
 * {@link #add(com.google.gwt.user.client.ui.Widget)} {@link TableRow rows} to it.
 * 
 * @see TableWidget
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class GridPanel extends CustomPanel {

  /**
   * The constructor.
   */
  public GridPanel() {

    super();
    setElement(Document.get().createTableElement());
  }

}
