/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.swt.view.widget;

import net.sf.mmm.ui.toolkit.api.common.ButtonStyle;
import net.sf.mmm.ui.toolkit.api.view.UiImage;
import net.sf.mmm.ui.toolkit.api.view.widget.UiButton;
import net.sf.mmm.ui.toolkit.impl.swt.UiFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.view.UiImageImpl;
import net.sf.mmm.ui.toolkit.impl.swt.view.sync.SyncButtonAccess;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiButton} interface using SWT as
 * the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UIButtonImpl extends AbstractUiWidgetSwt<Button> implements UiButton {

  /** @see #getButtonStyle() */
  private final ButtonStyle buttonStyle;

  /** @see #getAdapter() */
  private final SyncButtonAccess adapter;

  /** the icon */
  private UiImageImpl icon;

  /**
   * The constructor.
   * 
   * @param uiFactory is the {@link #getFactory() factory} instance.
   * @param buttonStyle determines the style of the button.
   */
  public UIButtonImpl(UiFactorySwt uiFactory, ButtonStyle buttonStyle) {

    super(uiFactory);
    this.buttonStyle = buttonStyle;
    int swtStyle = UiFactorySwt.convertButtonStyle(buttonStyle);
    this.adapter = new SyncButtonAccess(uiFactory, this, swtStyle);
    this.icon = null;
  }

  /**
   * {@inheritDoc}
   */
  public String getValue() {

    return this.adapter.getText();
  }

  /**
   * {@inheritDoc}
   */
  public void setValue(String text) {

    this.adapter.setText(text);
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

    this.adapter.addListener(SWT.Selection, getAdapter());
    return true;
  }

  /**
   * {@inheritDoc}
   */
  public ButtonStyle getButtonStyle() {

    return this.buttonStyle;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isSelected() {

    return this.adapter.isSelected();
  }

  /**
   * {@inheritDoc}
   */
  public void setSelected(boolean selected) {

    this.adapter.setSelected(selected);
  }

  /**
   * {@inheritDoc}
   */
  public UiImageImpl getImage() {

    return this.icon;
  }

  /**
   * {@inheritDoc}
   */
  public void setImage(UiImage newIcon) {

    this.icon = (UiImageImpl) newIcon;
    if (this.icon == null) {
      this.adapter.setImage(null);
    } else {
      this.adapter.setImage(this.icon.getSwtImage());
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SyncButtonAccess getAdapter() {

    return this.adapter;
  }

}
