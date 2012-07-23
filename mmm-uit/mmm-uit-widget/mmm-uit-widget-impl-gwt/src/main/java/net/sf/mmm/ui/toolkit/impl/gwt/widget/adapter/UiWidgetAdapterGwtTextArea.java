/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter;

import net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterTextArea;
import net.sf.mmm.util.nls.api.NlsUnsupportedOperationException;

import com.google.gwt.user.client.ui.TextArea;

/**
 * This is the implementation of {@link UiWidgetAdapterTextArea} using GWT based on {@link TextArea}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the changed value - typically {@link String}.
 */
public class UiWidgetAdapterGwtTextArea<VALUE> extends UiWidgetAdapterGwtFocusWidgetField<TextArea, VALUE, String>
    implements UiWidgetAdapterTextArea<TextArea, VALUE> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtTextArea() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected TextArea createWidget() {

    return new TextArea();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getHeightInTextLines() {

    return getWidget().getVisibleLines();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeightInTextLines(int lines) {

    getWidget().setVisibleLines(lines);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getMaximumTextLength() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximumTextLength(int length) {

    getWidget().getElement().setAttribute("maxlength", Integer.toString(length));
  }

}
