/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteLabel;

import com.google.gwt.dom.client.Document;

/**
 * A {@link Legend} is a {@link CustomPanel} for a legend ({@literal <legend>}) of a {@link BorderPanel}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class Legend extends CustomPanel implements AttributeWriteLabel {

  /** @see #getLabelWidget() */
  private final LabelWidget labelWidget;

  /**
   * The constructor.
   */
  public Legend() {

    super();
    setElement(Document.get().createElement("legend"));
    this.labelWidget = new LabelWidget();
    add(this.labelWidget);
  }

  /**
   * @return the {@link LabelWidget}.
   */
  public LabelWidget getLabelWidget() {

    return this.labelWidget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLabel() {

    return this.labelWidget.getText();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLabel(String label) {

    this.labelWidget.setText(label);
  }

}
