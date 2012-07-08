/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.widget.gwt.atomic;

import net.sf.mmm.ui.toolkit.api.widget.atomic.UiWidgetButton;

import com.google.gwt.user.client.ui.Button;

/**
 * This is the implementation of {@link UiWidgetButton} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetButtonGwt extends AbstractUiWidgetAtomicClickableGwt<Button> implements UiWidgetButton {

  /**
   * The constructor.
   */
  public UiWidgetButtonGwt() {

    super(new Button());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLabel(String label) {

    getWidget().setText(label);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLabel() {

    return getWidget().getText();
  }

}
