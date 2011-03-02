/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.widget;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;

import net.sf.mmm.ui.toolkit.api.common.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.view.UiImage;
import net.sf.mmm.ui.toolkit.api.view.widget.UiButton;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.view.UiImageImpl;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiButton} interface using Swing
 * as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UIButtonImpl extends AbstractUiWidget implements UiButton {

  /** the insets of the button */
  // private static final Insets BUTTON_INSETS = new Insets(4, 4, 4, 4);
  /** the actual Swing button */
  private final AbstractButton button;

  /** the style of the button */
  private final ButtonStyle style;

  /** the icon */
  private UiImageImpl icon;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   * @param buttonStyle determines the style of the button.
   */
  public UIButtonImpl(UIFactorySwing uiFactory, ButtonStyle buttonStyle) {

    super(uiFactory);
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
  @Override
  public JComponent getSwingComponent() {

    return this.button;
  }

  /**
   * {@inheritDoc}
   */
  public void setValue(String text) {

    this.button.setText(text);
  }

  /**
   * {@inheritDoc}
   */
  public String getValue() {

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
  public ButtonStyle getButtonStyle() {

    return this.style;
  }

  /**
   * {@inheritDoc}
   */
  public void setImage(UiImage newIcon) {

    this.icon = (UiImageImpl) newIcon;
    if (this.icon == null) {
      this.button.setIcon(null);
    } else {
      this.button.setIcon(this.icon.getSwingIcon());
    }
  }

  /**
   * {@inheritDoc}
   */
  public UiImage getImage() {

    return this.icon;
  }

}
