/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.widget;

import javax.swing.JTextField;

import net.sf.mmm.ui.toolkit.api.view.widget.UiTextField;
import net.sf.mmm.ui.toolkit.impl.swing.UiFactorySwing;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiTextField} interface using
 * Swing as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiTextFieldImpl extends AbstractUiWidgetSwing<JTextField> implements UiTextField {

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public UiTextFieldImpl(UiFactorySwing uiFactory) {

    super(uiFactory, new JTextField());
  }

  /**
   * {@inheritDoc}
   */
  public String getType() {

    return TYPE;
  }

  /**
   * {@inheritDoc}
   */
  public void setValue(String text) {

    getDelegate().setText(text);
  }

  /**
   * {@inheritDoc}
   */
  public String getValue() {

    return getDelegate().getText();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isEditable() {

    return getDelegate().isEditable();
  }

  /**
   * {@inheritDoc}
   */
  public void setEditable(boolean editableFlag) {

    getDelegate().setEditable(editableFlag);
  }

}
