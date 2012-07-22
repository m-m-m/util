/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.core;

import net.sf.mmm.ui.toolkit.api.widget.core.UiWidgetLabel;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.UiWidgetRegularAtomicGwt;

import com.google.gwt.user.client.ui.Label;

/**
 * This is the implementation of {@link UiWidgetLabel} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetLabelGwt extends UiWidgetRegularAtomicGwt<Label> implements UiWidgetLabel {

  /** @ee {@link #getLabel()} */
  private String label;

  /**
   * The constructor.
   */
  public UiWidgetLabelGwt() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Label createWidget() {

    return new Label();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetEnabled(boolean newEnabled) {

    // nothing to do/not supported...
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLabel(String label) {

    this.label = label;
    getWidget().setText(label);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLabel() {

    return this.label;
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetLabel> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetLabel.class);
    }

    /**
     * {@inheritDoc}
     */
    public UiWidgetLabel create() {

      return new UiWidgetLabelGwt();
    }

  }

}
