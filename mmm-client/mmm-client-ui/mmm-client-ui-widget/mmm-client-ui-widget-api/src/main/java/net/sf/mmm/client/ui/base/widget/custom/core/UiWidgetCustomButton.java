/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.custom.core;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventFocus;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetImage;
import net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustomAtomicNoValue;

/**
 * This is the abstract base class for a {@link UiWidgetCustomAtomicNoValue atomic custom widget} that adapts
 * a {@link UiWidgetButton}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetCustomButton extends UiWidgetCustomAtomicNoValue<UiWidgetButton> implements
    UiWidgetButton {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetCustomButton(UiContext context) {

    super(context, context.getWidgetFactory().create(UiWidgetButton.class));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addFocusHandler(UiHandlerEventFocus handler) {

    getDelegate().addFocusHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeFocusHandler(UiHandlerEventFocus handler) {

    return getDelegate().removeFocusHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isFocused() {

    return getDelegate().isFocused();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLabel() {

    return getDelegate().getLabel();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetImage getImage() {

    return getDelegate().getImage();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFocused() {

    getDelegate().setFocused();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAccessKey(char accessKey) {

    getDelegate().setAccessKey(accessKey);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char getAccessKey() {

    return getDelegate().getAccessKey();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addClickHandler(UiHandlerEventClick handler) {

    getDelegate().addClickHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeClickHandler(UiHandlerEventClick handler) {

    return getDelegate().removeClickHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void click() {

    getDelegate().click();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setImage(UiWidgetImage image) {

    getDelegate().setImage(image);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLabel(String label) {

    getDelegate().setLabel(label);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetValue(Void value, boolean forUser) {

    // nothing to do...
  }

}
