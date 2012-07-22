/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteHandlerObserver;
import net.sf.mmm.ui.toolkit.api.handler.UiHandlerObserver;
import net.sf.mmm.ui.toolkit.api.widget.UiWidget;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetComposite;

/**
 * This is the abstract base implementation of {@link UiWidget}. It is independent of any native toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public abstract class AbstractUiWidget<WIDGET> implements UiWidget, AttributeWriteHandlerObserver {

  /** @see #getWidget() */
  private final WIDGET widget;

  /** @see #getParent() */
  private UiWidgetComposite<?> parent;

  /** @see #getId() */
  private String id;

  /** @see #isVisible() */
  private boolean visible;

  /** @see #isEnabled() */
  private boolean enabled;

  /** @see #getTooltip() */
  private String tooltip;

  /** @see #getHandlerObserver() */
  private UiHandlerObserver handlerObserver;

  /**
   * The constructor.
   */
  public AbstractUiWidget() {

    super();
    this.widget = createWidget();
    this.visible = true;
    this.enabled = true;
  }

  /**
   * This method creates the {@link #getWidget() underlying widget}.<br/>
   * <b>ATTENTION:</b><br/>
   * This method is called from the constructor but implemented in sub-classes. You should NOT access or even
   * modify member variables as they are NOT set at this point (even final members). However, this design is
   * done by intention instead of passing the widget as constructor argument to give more flexibility by
   * overriding and also for potential lazy initialization of the widget.
   * 
   * @return a new instance of the {@link #getWidget() underlying widget}.
   */
  protected abstract WIDGET createWidget();

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
  public UiWidgetComposite<?> getParent() {

    return this.parent;
  }

  /**
   * This method sets the {@link #getParent() parent}.
   * 
   * @param parent is the new {@link #getParent() parent}.
   */
  protected void setParent(UiWidgetComposite<?> parent) {

    this.parent = parent;
  }

  /**
   * This method removes this widget from its {@link #getParent() parent}. The {@link #getParent() parent} is
   * set to <code>null</code> and the native widget is removed from its parent.
   */
  protected void removeFromParent() {

    this.parent = null;
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

  /**
   * {@inheritDoc}
   */
  @Override
  public final UiHandlerObserver getHandlerObserver() {

    return this.handlerObserver;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setHandlerObserver(UiHandlerObserver handlerObserver) {

    this.handlerObserver = handlerObserver;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuilder buffer = new StringBuilder(getClass().getSimpleName());
    if (this.id != null) {
      buffer.append("[");
      buffer.append(this.id);
      buffer.append("]");
    }
    return buffer.toString();
  }

}
