/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.view.widget;

import javax.swing.JComponent;
import javax.swing.JLabel;

import net.sf.mmm.ui.toolkit.api.view.UiImage;
import net.sf.mmm.ui.toolkit.api.view.widget.UiLabel;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.view.UiImageImpl;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiLabel} interface using Swing
 * as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UILabelImpl extends AbstractUiWidget implements UiLabel {

  /** the actual Swing label */
  private final JLabel label;

  /** the icon */
  private UiImageImpl icon;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   */
  public UILabelImpl(UIFactorySwing uiFactory) {

    super(uiFactory);
    this.label = new JLabel();
    this.icon = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public JComponent getSwingComponent() {

    return this.label;
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

    this.label.setText(text);
  }

  /**
   * {@inheritDoc}
   */
  public String getValue() {

    return this.label.getText();
  }

  /**
   * {@inheritDoc}
   */
  public void setImage(UiImage newIcon) {

    this.icon = (UiImageImpl) newIcon;
    if (this.icon == null) {
      this.label.setIcon(null);
    } else {
      this.label.setIcon(this.icon.getSwingIcon());
    }
  }

  /**
   * {@inheritDoc}
   */
  public UiImage getImage() {

    return this.icon;
  }

}
