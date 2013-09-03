/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.event.UiEventClick;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.client.ui.api.widget.UiWidgetClickable;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetImage;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapterClickable;

/**
 * This is the abstract base implementation of a {@link #click() clickable}
 * {@link net.sf.mmm.client.ui.api.widget.UiWidget}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * @param <VALUE> is the generic type of the {@link #getValue() value}. Use {@link Void} for no value.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetClickable<ADAPTER extends UiWidgetAdapterClickable, VALUE> extends
    AbstractUiWidgetActive<ADAPTER, VALUE> implements UiWidgetClickable {

  /** @see #getLabel() */
  private String label;

  /** @see #getImage() */
  private UiWidgetImage image;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetClickable(UiContext context) {

    super(context);
    this.image = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.label != null) {
      adapter.setLabel(this.label);
    }
    if (this.image != null) {
      adapter.setImage(this.image);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetImage getImage() {

    return this.image;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setImage(UiWidgetImage image) {

    if (this.image == image) {
      return;
    }
    this.image = image;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setImage(image);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addClickHandler(UiHandlerEventClick handler) {

    addEventHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeClickHandler(UiHandlerEventClick handler) {

    return removeEventHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void click() {

    click(true);
  }

  /**
   * @see #click()
   * 
   * @param programmatic - see {@link UiEventClick#isProgrammatic()}.
   */
  protected void click(boolean programmatic) {

    fireEvent(new UiEventClick(this, programmatic));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLabel(String label) {

    this.label = label;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setLabel(label);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLabel() {

    return this.label;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onAccessKeyPressed(boolean programmatic) {

    click(programmatic);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    if (this.label == null) {
      return super.toString();
    }
    return super.toString() + "[" + this.label + "]";
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getSource() {

    if (this.label != null) {
      return this.label;
    }
    return super.getSource();
  }

}
