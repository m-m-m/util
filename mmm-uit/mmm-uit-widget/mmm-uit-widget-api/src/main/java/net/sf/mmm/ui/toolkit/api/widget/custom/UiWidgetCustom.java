/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget.custom;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteValueAdvanced;
import net.sf.mmm.ui.toolkit.api.common.UiMode;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetComposite;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetFactory;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetReal;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetWithValue;
import net.sf.mmm.util.validation.api.ValidationState;

/**
 * This is the abstract base class for <em>custom widgets</em>. A custom widget is a
 * {@link net.sf.mmm.ui.toolkit.api.widget.UiWidget widget} implemented via {@link #getDelegate() delegation}
 * to another widget. It is therefore toolkit independent and can be implemented as a regular class. This
 * makes the programming model of this UI-Toolkit easy to use.<br/>
 * Typical use-cases for custom widgets are {@link net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetField field
 * widgets} for custom datatypes and {@link UiWidgetComposite composites} for editors of particular business
 * objects.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 */
public abstract class UiWidgetCustom<VALUE, DELEGATE extends UiWidgetReal> implements UiWidgetWithValue<VALUE>,
    AttributeWriteValueAdvanced<VALUE> {

  /** @see #getFactory() */
  private final UiWidgetFactory<?> factory;

  /** @see #getDelegate() */
  private final DELEGATE delegate;

  /** @see #getOriginalValue() */
  private VALUE originalValue;

  /** @see #isModified() */
  private boolean modified;

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   * @param delegate is the {@link #getDelegate() delegate}.
   */
  public UiWidgetCustom(UiWidgetFactory<?> factory, DELEGATE delegate) {

    super();
    this.factory = factory;
    this.delegate = delegate;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final UiWidgetFactory<?> getFactory() {

    return this.factory;
  }

  /**
   * This method gets the underlying {@link net.sf.mmm.ui.toolkit.api.widget.UiWidget widget} that is adapted
   * by this widget.
   * 
   * @return the adapted {@link net.sf.mmm.ui.toolkit.api.widget.UiWidget widget}.
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
  protected static final <WIDGET extends UiWidgetReal> WIDGET getDelegate(UiWidgetCustom<?, WIDGET> customWidget) {

    return customWidget.getDelegate();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final VALUE getValue() {

    try {
      return getValueOrException();
    } catch (RuntimeException e) {
      // ATTENTION: This is one of the very rare cases where we intentionally ignore an exception.
      return null;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setValue(VALUE value) {

    setValue(value, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final VALUE getValueOrException() throws RuntimeException {

    return doGetValue();
  }

  /**
   * This method is called from {@link #getValueOrException()}. It has to be implemented with the custom logic
   * to get the value from the view. The returned value needs to be created as new object rather than
   * modifying the {@link #getOriginalValue() original value} that has been previously set. This is required
   * to support operations such as {@link #resetValue()}. The implementation of this method has to correspond
   * with the implementation of {@link #doSetValue(Object)}.
   * 
   * @see #doSetValue(Object)
   * 
   * @return a new value reflecting the current data as specified by the end-user.
   * @throws RuntimeException if something goes wrong (e.g. validation failure).
   */
  protected abstract VALUE doGetValue() throws RuntimeException;

  /**
   * {@inheritDoc}
   */
  @Override
  public final VALUE getOriginalValue() {

    return this.originalValue;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setValueForUser(VALUE value) {

    setValue(value, true);
  }

  /**
   * Implementation of {@link #setValue(Object)} and {@link #setValueForUser(Object)}.
   * 
   * @param newValue is the new {@link #getValue() value}.
   * @param forUser <code>true</code> if called from {@link #setValueForUser(Object)}, <code>false</code> if
   *        set from {@link #setValue(Object)}.
   */
  private void setValue(VALUE newValue, boolean forUser) {

    setModified(forUser);
    if (!forUser) {
      this.originalValue = newValue;
    }
    doSetValue(newValue);
    // if (this.changeEventSender != null) {
    // this.changeEventSender.onValueChange(this, true);
    // }
  }

  /**
   * This method is called from {@link #setValue(Object)} and {@link #setValueForUser(Object)}. It has to be
   * implemented with the custom logic to set the value in the view. The implementation of this method has to
   * correspond with the implementation of {@link #doGetValue()}.
   * 
   * @see #doGetValue()
   * 
   * @param value is the value to set. Typically a composite object (e.g. java bean) so its attributes are set
   *        to {@link net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetField atomic fields}.
   */
  protected abstract void doSetValue(VALUE value);

  /**
   * {@inheritDoc}
   */
  @Override
  public final void resetValue() {

    setValue(this.originalValue);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isModified() {

    if (this.modified) {
      return true;
    }
    return this.delegate.isModified();
  }

  /**
   * @param modified <code>true</code> if this widget is modified (locally), <code>false</code> otherwise.
   */
  protected final void setModified(boolean modified) {

    this.modified = modified;
  }

  // --- delegation methods ---

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
  public final void setAriaRole(String ariaRole) {

    this.delegate.setAriaRole(ariaRole);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final String getAriaRole() {

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
  public final boolean validate(ValidationState state) {

    this.delegate.validate(state);
    validateLocal(state);
    return state.isValid();
  }

  /**
   * This method performs the local validation of this widget excluding validation performed by the
   * {@link #getDelegate()} and its children.
   * 
   * @param state is the {@link ValidationState}.
   */
  protected void validateLocal(ValidationState state) {

    // nothing to do...
  }

}
