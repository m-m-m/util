/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.view.widget;

import net.sf.mmm.ui.toolkit.api.common.ButtonStyle;
import net.sf.mmm.ui.toolkit.impl.gwt.UiFactoryGwt;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ButtonBase;

/**
 * This is the implementation of
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiButton} for
 * {@link ButtonStyle#DEFAULT} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiButtonDefault extends AbstractUiButton {

  /** @see #getNativeUiObject() */
  private final Button button;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public UiButtonDefault(UiFactoryGwt uiFactory) {

    super(uiFactory);
    this.button = new Button();
  }

  /**
   * {@inheritDoc}
   */
  public ButtonStyle getButtonStyle() {

    return ButtonStyle.DEFAULT;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ButtonBase getNativeUiObject() {

    return this.button;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isSelected() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  public void setSelected(boolean selected) {

    // click event?
  }

}
