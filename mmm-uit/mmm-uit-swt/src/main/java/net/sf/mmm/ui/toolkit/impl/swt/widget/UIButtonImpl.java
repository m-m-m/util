/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.widget;

import org.eclipse.swt.SWT;

import net.sf.mmm.ui.toolkit.api.UIPicture;
import net.sf.mmm.ui.toolkit.api.widget.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.widget.UIButton;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.UIPictureImpl;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncButtonAccess;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIButton} interface using SWT as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIButtonImpl extends AbstractUIWidget implements UIButton {

  /** the style of the button */
  private final ButtonStyle style;

  /** the synchronous access to the button */
  private final SyncButtonAccess syncAccess;

  /** the icon */
  private UIPictureImpl icon;

  /**
   * The constructor.
   * 
   * @param uiFactory
   *        is the UIFactorySwt instance.
   * @param parentObject
   *        is the parent of this object (may be <code>null</code>).
   * @param buttonStyle
   *        determines the style of the button.
   */
  public UIButtonImpl(UIFactorySwt uiFactory, UISwtNode parentObject, ButtonStyle buttonStyle) {

    super(uiFactory, parentObject);
    this.style = buttonStyle;
    int swtStyle = UIFactorySwt.convertButtonStyle(buttonStyle);
    this.syncAccess = new SyncButtonAccess(uiFactory, swtStyle);
    this.icon = null;
  }

  /**
   * {@inheritDoc}
   */
  public String getText() {

    return this.syncAccess.getText();
  }

  /**
   * {@inheritDoc}
   */
  public void setText(String text) {

    this.syncAccess.setText(text);
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
  @Override
  protected boolean doInitializeListener() {

    this.syncAccess.addListener(SWT.Selection, createSwtListener());
    return true;
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
  public boolean isSelected() {

    return this.syncAccess.isSelected();
  }

  /**
   * {@inheritDoc}
   */
  public void setSelected(boolean selected) {

    this.syncAccess.setSelected(selected);
  }

  /**
   * {@inheritDoc}
   */
  public UIPictureImpl getIcon() {

    return this.icon;
  }

  /**
   * {@inheritDoc}
   */
  public void setIcon(UIPicture newIcon) {

    this.icon = (UIPictureImpl) newIcon;
    if (this.icon == null) {
      this.syncAccess.setImage(null);
    } else {
      this.syncAccess.setImage(this.icon.getSwtImage());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SyncButtonAccess getSyncAccess() {

    return this.syncAccess;
  }

}
