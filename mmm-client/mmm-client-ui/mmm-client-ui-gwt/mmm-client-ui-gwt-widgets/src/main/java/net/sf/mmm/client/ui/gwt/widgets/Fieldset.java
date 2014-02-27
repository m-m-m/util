/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteLabel;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.FieldSetElement;

/**
 * This is a custom {@link com.google.gwt.user.client.ui.Widget} that represents a <em>fieldset</em>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class Fieldset extends CustomPanel implements AttributeWriteLabel {

  /** The labeled legend element. */
  private final Legend legend;

  /**
   * The constructor.
   */
  public Fieldset() {

    super();
    FieldSetElement element = Document.get().createFieldSetElement();
    setElement(element);
    this.legend = new Legend();
    add(this.legend);
  }

  /**
   * @return the {@link Legend} with the {@link #setLabel(String) label}.
   */
  public Legend getLegend() {

    return this.legend;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLabel() {

    return this.legend.getLabel();
  }

  /**
   * {@inheritDoc}
   */
  public void setLabel(String label) {

    this.legend.setLabel(label);
  }

}
