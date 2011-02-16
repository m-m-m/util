/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.widget;

import javax.swing.JComponent;
import javax.swing.JLabel;

import net.sf.mmm.ui.toolkit.api.UINodeRenamed;
import net.sf.mmm.ui.toolkit.api.UiImage;
import net.sf.mmm.ui.toolkit.api.widget.UILabel;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.UIPictureImpl;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UILabel} interface using Swing as the
 * UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UILabelImpl extends AbstractUIWidget implements UILabel {

  /** the actual Swing label */
  private final JLabel label;

  /** the icon */
  private UIPictureImpl icon;

  /**
   * The constructor.
   * 
   * @param uiFactory
   * @param parentObject
   */
  public UILabelImpl(UIFactorySwing uiFactory, UINodeRenamed parentObject) {

    super(uiFactory, parentObject);
    this.label = new JLabel();
    this.icon = null;
  }

  /**
   * {@inheritDoc}
   */
  public @Override
  JComponent getSwingComponent() {

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
  public void setText(String text) {

    this.label.setText(text);
  }

  /**
   * {@inheritDoc}
   */
  public String getText() {

    return this.label.getText();
  }

  /**
   * {@inheritDoc}
   */
  public void setIcon(UiImage newIcon) {

    this.icon = (UIPictureImpl) newIcon;
    if (this.icon == null) {
      this.label.setIcon(null);
    } else {
      this.label.setIcon(this.icon.getSwingIcon());
    }
  }

  /**
   * {@inheritDoc}
   */
  public UiImage getIcon() {

    return this.icon;
  }

}
