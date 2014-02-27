/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteText;

import com.google.gwt.dom.client.Document;

/**
 * A {@link Caption} is a {@link CustomPanel} for a legend ({@literal <legend>}) of a {@link Fieldset}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class Caption extends CustomPanel implements AttributeWriteText {

  /** @see #getLabelWidget() */
  private final LabelWidget labelWidget;

  /**
   * The constructor.
   */
  public Caption() {

    super();
    setElement(Document.get().createCaptionElement());
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
  public String getText() {

    return this.labelWidget.getText();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setText(String text) {

    this.labelWidget.setText(text);
  }

}
