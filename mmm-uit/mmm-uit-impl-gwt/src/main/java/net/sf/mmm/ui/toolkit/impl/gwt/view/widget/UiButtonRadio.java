/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.view.widget;

import net.sf.mmm.ui.toolkit.api.common.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.view.UiNode;
import net.sf.mmm.ui.toolkit.impl.gwt.UiFactoryGwt;

import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.RadioButton;

/**
 * This is the implementation of
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiButton} for
 * {@link ButtonStyle#RADIO} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiButtonRadio extends AbstractUiButton {

  /** @see #getNativeUiObject() */
  private final RadioButton button;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public UiButtonRadio(UiFactoryGwt uiFactory) {

    super(uiFactory);
    this.button = new RadioButton("group");
  }

  /**
   * {@inheritDoc}
   */
  public ButtonStyle getButtonStyle() {

    return ButtonStyle.RADIO;
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

    return this.button.getValue().booleanValue();
  }

  /**
   * {@inheritDoc}
   */
  public void setSelected(boolean selected) {

    this.button.setValue(Boolean.valueOf(selected));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setParent(UiNode newParent) {

    if (newParent != null) {
      this.button.setName(newParent.getId() + "-radio");
    }
    super.setParent(newParent);
  }

}
