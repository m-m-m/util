/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.widget;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;

import net.sf.mmm.ui.toolkit.api.common.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.view.UiElement;
import net.sf.mmm.ui.toolkit.api.view.UiImage;
import net.sf.mmm.ui.toolkit.api.view.UiNode;
import net.sf.mmm.ui.toolkit.api.view.composite.UiComposite;
import net.sf.mmm.ui.toolkit.api.view.widget.UiButton;
import net.sf.mmm.ui.toolkit.base.view.AbstractUiNode;
import net.sf.mmm.ui.toolkit.impl.swing.UiFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.view.UiImageImpl;
import net.sf.mmm.ui.toolkit.impl.swing.view.UiNodeAdapterSwing;
import net.sf.mmm.util.nls.api.IllegalCaseException;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiButton} interface using Swing.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiButtonImpl extends AbstractUiWidgetSwing<AbstractButton> implements UiButton {

  /** @see #getButtonStyle() */
  private final ButtonStyle style;

  /** @see #getImage() */
  private UiImageImpl image;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   * @param buttonStyle determines the style of the button.
   */
  public UiButtonImpl(UiFactorySwing uiFactory, ButtonStyle buttonStyle) {

    super(uiFactory, createButton(buttonStyle));
    this.style = buttonStyle;
    // this.button.setMargin(BUTTON_INSETS);
    this.image = null;
  }

  /**
   * This method creates the actual swing button.
   * 
   * @param buttonStyle is the {@link ButtonStyle}.
   * @return the button.
   */
  protected static AbstractButton createButton(ButtonStyle buttonStyle) {

    switch (buttonStyle) {
      case DEFAULT:
        return new JButton();
      case CHECK:
        return new JCheckBox();
      case RADIO:
        return new JRadioButton();
      case TOGGLE:
        return new JToggleButton();
      default :
        throw new IllegalCaseException(ButtonStyle.class, buttonStyle);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void setValue(String text) {

    getAdapter().getDelegate().setText(text);
  }

  /**
   * {@inheritDoc}
   */
  public String getValue() {

    return getAdapter().getDelegate().getText();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean doInitializeListener() {

    getAdapter().getDelegate().addActionListener(getAdapter());
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

    return getAdapter().getDelegate().isSelected();
  }

  /**
   * {@inheritDoc}
   */
  public void setSelected(boolean selected) {

    getAdapter().getDelegate().setSelected(selected);
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

    this.image = (UiImageImpl) newIcon;
    if (this.image == null) {
      getAdapter().getDelegate().setIcon(null);
    } else {
      getAdapter().getDelegate().setIcon(this.image.getSwingIcon());
    }
  }

  /**
   * {@inheritDoc}
   */
  public UiImage getImage() {

    return this.image;
  }

  /**
   * This method gets the {@link ButtonGroup} for the given parent
   * <code>node</code>.
   * 
   * @param node is the parent {@link UiNode}.
   * @return the according {@link ButtonGroup}.
   */
  private ButtonGroup getButtonGroup(UiNode node) {

    UiNodeAdapterSwing<?> adapter = (UiNodeAdapterSwing<?>) ((AbstractUiNode<?>) node).getAdapter();
    return adapter.getButtonGroup();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setParent(UiNode newParent) {

    if (this.style == ButtonStyle.RADIO) {
      AbstractButton delegate = getDelegate();
      UiComposite<? extends UiElement> parent = getParent();
      if (parent != null) {
        getButtonGroup(parent).remove(delegate);
      }
      getButtonGroup(newParent).add(delegate);
    }
    super.setParent(newParent);
  }
}
