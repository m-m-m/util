/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;

/**
 * This is a custom {@link com.google.gwt.user.client.ui.Widget} that represents a
 * {@link net.sf.mmm.client.ui.api.widget.panel.UiWidgetBorderPanel} (fieldset-tag).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class BorderPanel extends SingleCompositePanel {

  /** The labeled legend element. */
  private final Element legend;

  /**
   * The constructor.
   */
  public BorderPanel() {

    super();
    setElement(Document.get().createElement("fieldset"));
    this.legend = Document.get().createElement("legend");
    getElement().appendChild(this.legend);
  }

  /**
   * @param label is the label for the border text.
   */
  public void setLabel(String label) {

    // HTML injection???
    this.legend.setInnerText(label);
  }

}
