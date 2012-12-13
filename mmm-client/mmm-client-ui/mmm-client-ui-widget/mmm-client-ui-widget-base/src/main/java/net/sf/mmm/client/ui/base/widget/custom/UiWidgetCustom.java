/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.custom;

import net.sf.mmm.client.ui.api.aria.role.Role;
import net.sf.mmm.client.ui.api.common.UiMode;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetComposite;
import net.sf.mmm.client.ui.api.widget.UiWidgetFactory;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidget;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter;
import net.sf.mmm.util.validation.api.ValidationState;

/**
 * This is the abstract base class for <em>custom widgets</em>. A custom widget is a
 * {@link net.sf.mmm.client.ui.api.widget.UiWidget widget} implemented via {@link #getDelegate() delegation}
 * to another widget. It is therefore toolkit independent and can be implemented as a regular class. This
 * makes the programming model of this UI-Toolkit easy to use.<br/>
 * Typical use-cases for custom widgets are {@link net.sf.mmm.client.ui.api.widget.field.UiWidgetField field
 * widgets} for custom datatypes and {@link UiWidgetComposite composites} for editors of particular business
 * objects.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 */
public abstract class UiWidgetCustom<VALUE, DELEGATE extends UiWidget> extends AbstractUiWidget<VALUE> {

  /** @see #getDelegate() */
  private final DELEGATE delegate;

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   * @param delegate is the {@link #getDelegate() delegate}.
   */
  public UiWidgetCustom(UiWidgetFactory<?> factory, DELEGATE delegate) {

    super(factory);
    this.delegate = delegate;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  protected VALUE doGetValue() throws RuntimeException {

    Object value = ((AbstractUiWidget<?>) this.delegate).getValueOrException();
    return (VALUE) value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected abstract void doSetValue(VALUE value);

  /**
   * This method gets the underlying {@link net.sf.mmm.client.ui.api.widget.UiWidget widget} that is adapted
   * by this widget.
   * 
   * @return the adapted {@link net.sf.mmm.client.ui.api.widget.UiWidget widget}.
   */
  protected final DELEGATE getDelegate() {

    return this.delegate;
  }

  /**
   * This method gets the {@link #getDelegate() delegate} of the given widget.
   * 
   * @param <WIDGET> is the generic type for the {@link #getDelegate() delegate} of the given widgets.
   * 
   * @param customWidget is the widget for which the {@link #getDelegate() delegate} is requested.
   * @return the requested {@link #getDelegate() delegate}.
   */
  protected static final <WIDGET extends UiWidget> WIDGET getDelegate(UiWidgetCustom<?, WIDGET> customWidget) {

    return customWidget.getDelegate();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean isModifiedRecursive() {

    if (super.isModifiedRecursive()) {
      return true;
    }
    return this.delegate.isModified();
  }

  // --- delegation methods ---

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapter<?> getWidgetAdapter() {

    return getWidgetAdapter(this.delegate);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasWidgetAdapter() {

    return ((AbstractUiWidget<?>) this.delegate).hasWidgetAdapter();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final UiWidgetComposite<?> getParent() {

    return this.delegate.getParent();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void dispose() {

    this.delegate.dispose();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isDisposed() {

    return this.delegate.isDisposed();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isVisible() {

    return this.delegate.isVisible();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setVisible(boolean visible) {

    this.delegate.setVisible(visible);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isVisibleRecursive() {

    return this.delegate.isVisibleRecursive();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isEnabled() {

    return this.delegate.isEnabled();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setEnabled(boolean enabled) {

    this.delegate.setEnabled(enabled);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final UiMode getMode() {

    return this.delegate.getMode();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMode(UiMode mode) {

    this.delegate.setMode(mode);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final UiMode getModeFixed() {

    return this.delegate.getModeFixed();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setModeFixed(UiMode mode) {

    this.delegate.setModeFixed(mode);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final String getTooltip() {

    return this.delegate.getTooltip();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setTooltip(String tooltip) {

    this.delegate.setTooltip(tooltip);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final String getWidth() {

    return this.delegate.getWidth();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setWidth(String width) {

    this.delegate.setWidth(width);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final String getHeight() {

    return this.delegate.getHeight();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setHeight(String height) {

    this.delegate.setHeight(height);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setSize(String width, String height) {

    this.delegate.setSize(width, height);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setSizeInPixel(int width, int height) {

    this.delegate.setSizeInPixel(width, height);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setWidthInPixel(int width) {

    this.delegate.setWidthInPixel(width);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setHeightInPixel(int height) {

    this.delegate.setHeightInPixel(height);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Role getAriaRole() {

    return this.delegate.getAriaRole();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setPrimaryStyle(String primaryStyle) {

    this.delegate.setPrimaryStyle(primaryStyle);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final String getStyles() {

    return this.delegate.getStyles();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setStyles(String styles) {

    this.delegate.setStyles(styles);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final String getPrimaryStyle() {

    return this.delegate.getPrimaryStyle();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void addStyle(String style) {

    this.delegate.addStyle(style);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean removeStyle(String style) {

    return this.delegate.removeStyle(style);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean hasStyle(String style) {

    return this.delegate.hasStyle(style);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final String getId() {

    return this.delegate.getId();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setId(String id) {

    this.delegate.setId(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void removeFromParent() {

    removeFromParent(this.delegate);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setParent(UiWidgetComposite<?> parent) {

    setParent(this.delegate, parent);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doValidate(ValidationState state) {

    super.doValidate(state);
    this.delegate.validate(state);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getSource() {

    return getId();
  }

}
