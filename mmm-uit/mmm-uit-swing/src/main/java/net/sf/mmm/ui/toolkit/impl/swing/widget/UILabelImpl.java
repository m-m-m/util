/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swing.widget;

import javax.swing.JComponent;
import javax.swing.JLabel;

import net.sf.mmm.ui.toolkit.api.UINode;
import net.sf.mmm.ui.toolkit.api.UIPicture;
import net.sf.mmm.ui.toolkit.api.widget.UILabel;
import net.sf.mmm.ui.toolkit.impl.swing.UIFactorySwing;
import net.sf.mmm.ui.toolkit.impl.swing.UIPictureImpl;

/**
 * This class is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UILabel} interface using Swing as
 * the UI toolkit.
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
  public UILabelImpl(UIFactorySwing uiFactory, UINode parentObject) {

    super(uiFactory, parentObject);
    this.label = new JLabel();
    this.icon = null;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swing.AbstractUIComponent#getSwingComponent()
   */
  public @Override
  JComponent getSwingComponent() {

    return this.label;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteText#setText(java.lang.String)
   */
  public void setText(String text) {

    this.label.setText(text);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadText#getText()
   */
  public String getText() {

    return this.label.getText();
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteIcon#setIcon(net.sf.mmm.ui.toolkit.api.UIPicture)
   */
  public void setIcon(UIPicture newIcon) {

    this.icon = (UIPictureImpl) newIcon;
    if (this.icon == null) {
      this.label.setIcon(null);
    } else {
      this.label.setIcon(this.icon.getSwingIcon());
    }
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadIcon#getIcon()
   */
  public UIPicture getIcon() {

    return this.icon;
  }

}
