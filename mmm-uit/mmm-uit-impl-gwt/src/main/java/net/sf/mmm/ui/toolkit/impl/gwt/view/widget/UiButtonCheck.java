/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.view.widget;

import net.sf.mmm.ui.toolkit.api.common.ButtonStyle;
import net.sf.mmm.ui.toolkit.impl.gwt.UiFactoryGwt;

import com.google.gwt.user.client.ui.CheckBox;

/**
 * This is the implementation of
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiButton} for
 * {@link ButtonStyle#CHECK} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiButtonCheck extends AbstractUiButton {

  /** @see #getNativeUiObject() */
  private final CheckBox button;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public UiButtonCheck(UiFactoryGwt uiFactory) {

    super(uiFactory);
    this.button = new CheckBox();
  }

  /**
   * {@inheritDoc}
   */
  public ButtonStyle getButtonStyle() {

    return ButtonStyle.CHECK;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected CheckBox getNativeUiObject() {

    return this.button;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isSelected() {

    return this.button.getValue().booleanValue();
  }

  /**
   * {@inheritDoc}
   */
  public void setSelected(boolean selected) {

    this.button.setValue(Boolean.valueOf(selected));
  }

}
