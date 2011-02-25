/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.widget;

import javax.swing.JComponent;
import javax.swing.JTextField;

import net.sf.mmm.ui.toolkit.api.UiNode;
import net.sf.mmm.ui.toolkit.api.view.widget.UiTextField;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiTextField} interface using Swing as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UITextFieldImpl extends AbstractUIWidget implements UiTextField {

  /** the native swing text field */
  private final JTextField textField;

  /**
   * The constructor.
   * 
   * @param uiFactory is the UIFactorySwing instance.
   * @param parentObject is the parent of this object (may be <code>null</code>).
   */
  public UITextFieldImpl(UIFactorySwing uiFactory, UiNode parentObject) {

    super(uiFactory, parentObject);
    this.textField = new JTextField();
  }

  /**
   * {@inheritDoc}
   */
  public @Override
  JComponent getSwingComponent() {

    return this.textField;
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

    this.textField.setText(text);
  }

  /**
   * {@inheritDoc}
   */
  public String getValue() {

    return this.textField.getText();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isEditable() {

    return this.textField.isEditable();
  }

  /**
   * {@inheritDoc}
   */
  public void setEditable(boolean editableFlag) {

    this.textField.setEditable(editableFlag);
  }

}
