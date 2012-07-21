/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field;

import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetTextArea;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;

import com.google.gwt.user.client.ui.TextArea;

/**
 * This is the implementation of {@link UiWidgetTextArea} using GWT based on {@link TextArea}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetTextAreaGwt extends UiWidgetInputFieldGwt<String, TextArea> implements UiWidgetTextArea {

  /**
   * The constructor.
   */
  public UiWidgetTextAreaGwt() {

    super(new TextArea());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetMaximumTextLength(int length) {

    getWidget().getElement().setAttribute("maxlength", Integer.toString(length));
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
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetTextArea> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetTextArea.class);
    }

    /**
     * {@inheritDoc}
     */
    public UiWidgetTextArea create() {

      return new UiWidgetTextAreaGwt();
    }

  }

}
