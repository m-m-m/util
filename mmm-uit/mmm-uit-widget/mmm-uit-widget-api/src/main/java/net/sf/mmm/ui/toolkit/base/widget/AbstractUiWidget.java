/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget;

import net.sf.mmm.ui.toolkit.api.widget.UiWidget;

/**
 * This is the abstract base implementation of {@link UiWidget}. It is independent of any native toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public abstract class AbstractUiWidget<WIDGET> implements UiWidget {

  /** @see #getWidget() */
  private final WIDGET widget;

  /** @see #getId() */
  private String id;

  /** @see #isVisible() */
  private boolean visible;

  /** @see #isEnabled() */
  private boolean enabled;

  /** @see #getTooltip() */
  private String tooltip;

  /**
   * The constructor.
   * 
   * @param widget is the {@link #getWidget() widget}.
   */
  public AbstractUiWidget(WIDGET widget) {

    super();
    this.widget = widget;
    this.visible = true;
    this.enabled = true;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.UiWidgetFactory#getNativeWidget(net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegular)
   * 
   * @return the native widget.
   */
  public WIDGET getWidget() {

    return this.widget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setId(String newId) {

    if (((this.id == null) && (newId != null)) || (!this.id.equals(newId))) {
      doSetId(newId);
      this.id = newId;
    }
  }

  /**
   * Called from {@link #setId(String)} to actually set the {@link #getId() ID} in the underlying
   * {@link #getWidget() widget}.
   * 
   * @param newId is the new ID.
   */
  protected abstract void doSetId(String newId);

  /**
   * {@inheritDoc}
   */
  @Override
  public String getId() {

    return this.id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setVisible(boolean visible) {

    if (this.visible != visible) {
      doSetVisible(visible);
      this.visible = visible;
    }
  }

  /**
   * Called from {@link #setVisible(boolean)} to actually set the {@link #isVisible() visibility} in the
   * underlying {@link #getWidget() widget}.
   * 
   * @param newVisible is the new {@link #isVisible() visible} flag.
   */
  protected abstract void doSetVisible(boolean newVisible);

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isVisible() {

    return this.visible;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTooltip(String tooltip) {

    if (((this.tooltip == null) && (tooltip != null)) || (!this.tooltip.equals(tooltip))) {
      doSetTooltip(tooltip);
      this.tooltip = tooltip;
    }
  }

  /**
   * Called from {@link #setTooltip(String)} to actually set the {@link #getTooltip() tooltip} in the
   * underlying {@link #getWidget() widget}.
   * 
   * @param newTooltip is the new {@link #getTooltip() tooltip}.
   */
  protected abstract void doSetTooltip(String newTooltip);

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTooltip() {

    return this.tooltip;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    if (this.enabled != enabled) {
      doSetEnabled(enabled);
      this.enabled = enabled;
    }
  }

  /**
   * Called from {@link #setEnabled(boolean)} to actually set the {@link #isEnabled() enabled flag} in the
   * underlying {@link #getWidget() widget}.
   * 
   * @param newEnabled is the new {@link #isEnabled() enabled} flag.
   */
  protected abstract void doSetEnabled(boolean newEnabled);

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEnabled() {

    return this.enabled;
  }

}
