/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field;

import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetRichTextArea;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.RichTextArea;

/**
 * This is the implementation of {@link UiWidgetRichTextArea} using GWT based on {@link RichTextArea}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetRichTextAreaGwt extends UiWidgetFieldGwtFocusWidget<String, RichTextArea> implements
    UiWidgetRichTextArea {

  /** @see #getHeightInTextLines() */
  private int heightInTextLines;

  /**
   * The constructor.
   */
  public UiWidgetRichTextAreaGwt() {

    super(new RichTextArea());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getValueOrException() throws RuntimeException {

    return getWidget().getHTML();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void registerChangeHandler(ChangeHandler handler) {

    // getWidget().add
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetValue(String value) {

    getWidget().setHTML(value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getMaximumTextLength() {

    return Integer.MAX_VALUE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximumTextLength(int length) {

    // not supported...
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getHeightInTextLines() {

    return this.heightInTextLines;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeightInTextLines(int lines) {

    this.heightInTextLines = lines;
    getWidget().setHeight(lines + "em");
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetRichTextArea> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetRichTextArea.class);
    }

    /**
     * {@inheritDoc}
     */
    public UiWidgetRichTextArea create() {

      return new UiWidgetRichTextAreaGwt();
    }

  }

}
