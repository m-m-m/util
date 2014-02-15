/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.custom;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.aria.role.Role;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteFlagAdvanced;
import net.sf.mmm.client.ui.api.common.Length;
import net.sf.mmm.client.ui.api.common.LengthProperty;
import net.sf.mmm.client.ui.api.common.UiMode;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetComposite;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
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
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetCustom<VALUE, DELEGATE extends UiWidget> extends AbstractUiWidget<VALUE> implements
    UiWidgetWithValue<VALUE> {

  /** @see #getDelegate() */
  private final DELEGATE delegate;

  /** @see #getValueClass() */
  private final Class<VALUE> valueClass;

  /** @see #initialize() */
  private boolean initialized;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param delegate is the {@link #getDelegate() delegate}.
   * @param valueClass is the {@link #getValueClass() value class}.
   */
  public UiWidgetCustom(UiContext context, DELEGATE delegate, Class<VALUE> valueClass) {

    super(context);
    this.delegate = delegate;
    this.valueClass = valueClass;
  }

  /**
   * @return the valueClass
   */
  @Override
  public Class<VALUE> getValueClass() {

    return this.valueClass;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clearMessages() {

    super.clearMessages();
    this.delegate.clearMessages();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean setFocused() {

    return ((AbstractUiWidget<?>) getDelegate()).setFocused();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void clearValidity() {

    super.clearValidity();
    AbstractUiWidget.AccessHelper.clearValidity((AbstractUiWidget<?>) this.delegate);
  }

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
  protected UiWidgetAdapter getWidgetAdapter() {

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
  public void setVisible(boolean visible, boolean programmatic) {

    this.delegate.setVisible(visible, programmatic);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AttributeWriteFlagAdvanced getVisibleFlag() {

    return this.delegate.getVisibleFlag();
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
  public final void setMode(UiMode mode) {

    super.setMode(mode);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setMode(UiMode mode, boolean programmatic) {

    // only call doSetMode if mode will actually change...
    if (this.delegate.getMode() == mode) {
      // mode not changed, nothing to do...
      return;
    }
    if (this.delegate.getModeFixed() != null) {
      // fixed mode prevents changing the mode...
      return;
    }
    doSetMode(mode);
  }

  /**
   * This method actually changes the {@link #getMode() mode}.
   * 
   * @param mode is the new {@link UiMode} to set.
   */
  protected void doSetMode(UiMode mode) {

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
  public final Length getWidth() {

    return this.delegate.getWidth();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setWidth(Length width) {

    this.delegate.setWidth(width);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final Length getHeight() {

    return this.delegate.getHeight();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setHeight(Length height) {

    this.delegate.setHeight(height);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setSize(Length width, Length height) {

    this.delegate.setSize(width, height);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Length getLength(LengthProperty property) {

    return this.delegate.getLength(property);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLength(LengthProperty property, Length value) {

    this.delegate.setLength(property, value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getWidthInPixel() {

    return getDelegate().getWidthInPixel();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getHeightInPixel() {

    return getDelegate().getHeightInPixel();
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
  public final boolean addStyle(String style) {

    return this.delegate.addStyle(style);
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

    initialize();
    setParent(this.delegate, parent);
  }

  /**
   * This method initializes this widget. It is automatically called from
   * {@link #setParent(UiWidgetComposite)} so initialization is performed before the widget is actually
   * attached to the screen for the first time. The first call of this method delegates to
   * {@link #doInitialize()}. Further calls of this method will have no effect.<br/>
   * <b>ATTENTION:</b><br/>
   * You should not call this method directly unless you are absolutely aware of what you are doing.
   */
  protected final void initialize() {

    if (!this.initialized) {
      doInitialize();
      this.initialized = true;
    }
  }

  /**
   * This method is called from {@link #initialize()} but only if called for the first time. You may override
   * this method to add additional initialization logic. Then do not forget the <code>super</code> call.<br/>
   * <b>ATTENTION:</b><br/>
   * Never call this method directly.
   */
  protected void doInitialize() {

    // nothing by default...
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doValidate(ValidationState state, VALUE value) {

    super.doValidate(state, value);
    ((AbstractUiWidget<?>) this.delegate).getValueDirect(null, state);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getSource() {

    return ((AbstractUiWidget<?>) getDelegate()).getSource();
  }

}
