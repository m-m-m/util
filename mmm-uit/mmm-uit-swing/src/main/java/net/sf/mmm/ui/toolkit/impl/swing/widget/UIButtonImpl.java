/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.widget;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;

import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.UIPicture;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.widget.UIButton;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.UIPictureImpl;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIButton} interface using Swing as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIButtonImpl extends AbstractUIWidget implements UIButton {

  /** the insets of the button */
  // private static final Insets BUTTON_INSETS = new Insets(4, 4, 4, 4);
  /** the actual Swing button */
  private final AbstractButton button;

  /** the style of the button */
  private final ButtonStyle style;

  /** the icon */
  private UIPictureImpl icon;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwing instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   * @param buttonStyle
   *        determines the style of the button.
   */
  public UIButtonImpl(UIFactorySwing uiFactory, UINode parentObject, ButtonStyle buttonStyle) {

    super(uiFactory, parentObject);
    AbstractButton b;
    this.style = buttonStyle;
    switch (this.style) {
      case DEFAULT:
        b = new JButton();
        break;
      case CHECK:
        b = new JCheckBox();
        break;
      case RADIO:
        b = new JRadioButton();
        break;
      case TOGGLE:
        b = new JToggleButton();
        break;
      default :
        throw new IllegalArgumentException("Unknown style: " + this.style);
    }
    this.button = b;
    // this.button.setMargin(BUTTON_INSETS);
    this.icon = null;
  }

  /**
   * {@inheritDoc}
   */
  public JComponent getSwingComponent() {

    return this.button;
  }

  /**
   * {@inheritDoc}
   */
  public void setText(String text) {

    this.button.setText(text);
  }

  /**
   * {@inheritDoc}
   */
  public String getText() {

    return this.button.getText();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean doInitializeListener() {

    this.button.addActionListener(createActionListener());
    return true;
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
  public boolean isSelected() {

    return this.button.isSelected();
  }

  /**
   * {@inheritDoc}
   */
  public void setSelected(boolean selected) {

    this.button.setSelected(selected);
  }

  /**
   * {@inheritDoc}
   */
  public ButtonStyle getStyle() {

    return this.style;
  }

  /**
   * {@inheritDoc}
   */
  public void setIcon(UIPicture newIcon) {

    this.icon = (UIPictureImpl) newIcon;
    if (this.icon == null) {
      this.button.setIcon(null);
    } else {
      this.button.setIcon(this.icon.getSwingIcon());
    }
  }

  /**
   * {@inheritDoc}
   */
  public UIPicture getIcon() {

    return this.icon;
  }

}
