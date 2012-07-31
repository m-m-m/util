/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget.field;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteModified;
import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventFocus;
import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventValueChange;
import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetField;
import net.sf.mmm.ui.toolkit.base.handler.event.ChangeEventSender;
import net.sf.mmm.ui.toolkit.base.handler.event.FocusEventSender;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetRegularAtomic;
import net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterField;

/**
 * This is the abstract base implementation of {@link UiWidgetField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @param <ADAPTER_VALUE> is the generic type of the {@link #getWidgetAdapter() adapter} value.
 */
public abstract class AbstractUiWidgetField<ADAPTER extends UiWidgetAdapterField<?, VALUE, ADAPTER_VALUE>, VALUE, ADAPTER_VALUE>
    extends AbstractUiWidgetRegularAtomic<ADAPTER> implements UiWidgetField<VALUE>, AttributeWriteModified {

  /** @see #addChangeHandler(UiHandlerEventValueChange) */
  private ChangeEventSender<VALUE> changeEventSender;

  /** @see #addFocusHandler(UiHandlerEventFocus) */
  private FocusEventSender focusEventSender;

  /** @see #getValue() */
  private VALUE value;

  /** @see #getOriginalValue() */
  private VALUE originalValue;

  /** @see #isModified() */
  private boolean modified;

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public AbstractUiWidgetField(AbstractUiWidgetFactory<?> factory) {

    super(factory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.changeEventSender != null) {
      adapter.setChangeEventSender(this, this.changeEventSender);
    }
    if (this.focusEventSender != null) {
      adapter.setFocusEventSender(this, this.focusEventSender);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void addChangeHandler(UiHandlerEventValueChange<VALUE> handler) {

    if (this.changeEventSender == null) {
      this.changeEventSender = new ChangeEventSender<VALUE>(this, getFactory());
      if (hasWidgetAdapter()) {
        getWidgetAdapter().setChangeEventSender(this, this.changeEventSender);
      }
    }
    this.changeEventSender.addHandler(handler);
  }

  /**
   * @return the {@link ChangeEventSender} or <code>null</code> if NOT yet created.
   */
  protected final ChangeEventSender<VALUE> getChangeEventSender() {

    return this.changeEventSender;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean removeChangeHandler(UiHandlerEventValueChange<VALUE> handler) {

    if (this.changeEventSender != null) {
      return this.changeEventSender.removeHandler(handler);
    }
    return false;
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
  public void setValueForUser(VALUE userValue) {

    setValue(userValue, true);
  }

  /**
   * Implementation of {@link #setValue(Object)} and {@link #setValueForUser(Object)}.
   * 
   * @param newValue is the new {@link #getValue() value}.
   * @param forUser <code>true</code> if called from {@link #setValueForUser(Object)}, <code>false</code> if
   *        set from {@link #setValue(Object)}.
   */
  private void setValue(VALUE newValue, boolean forUser) {

    this.value = newValue;
    if (!forUser) {
      this.originalValue = newValue;
    }
    if (hasWidgetAdapter()) {
      ADAPTER_VALUE adapterValue;
      if (newValue == null) {
        adapterValue = getNullValue();
      } else {
        adapterValue = convertFromValue(newValue);
      }
      getWidgetAdapter().setValue(adapterValue);
    }
    setModified(forUser);
    if (this.changeEventSender != null) {
      this.changeEventSender.onValueChange(this, true);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void resetValue() {

    setValue(this.originalValue);
  }

  /**
   * This method converts the given <code>adapterValue</code> from the {@link #getWidgetAdapter() widget
   * adapter} to the type of this {@link #getValue() value}. The default implementation simply performs a
   * cast. You have to override this method if {@literal <VALUE>} and {@literal <WIDGET_VALUE>} differ.
   * 
   * @param adapterValue is the value to convert. Will NOT be null.
   * @return the converted value.
   */
  @SuppressWarnings("unchecked")
  protected VALUE convertToValue(ADAPTER_VALUE adapterValue) {

    return (VALUE) adapterValue;
  }

  /**
   * This method gets the result of {@link #convertFromValue(Object)} for the value <code>null</code>. It is
   * called for <code>null</code> values instead of {@link #convertFromValue(Object)} to prevent
   * {@link NullPointerException}s. The default is <code>null</code> - override to change.
   * 
   * @return the value representation of <code>null</code> for the {@link #getWidgetAdapter() widget adapter}.
   */
  protected ADAPTER_VALUE getNullValue() {

    return null;
  }

  /**
   * This method converts the given <code>widgetValue</code> to the type used by the
   * {@link #getWidgetAdapter() widget adapter}. The default implementation simply performs a cast. You have
   * to override this method if {@literal <VALUE>} and {@literal <WIDGET_VALUE>} differ.
   * 
   * @param widgetValue is the value to convert. Will NOT be <code>null</code> (See {@link #getNullValue()}).
   * @return the converted value.
   */
  @SuppressWarnings("unchecked")
  protected ADAPTER_VALUE convertFromValue(VALUE widgetValue) {

    return (ADAPTER_VALUE) widgetValue;
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
  public VALUE getValueOrException() throws RuntimeException {

    if (hasWidgetAdapter()) {
      ADAPTER_VALUE adapterValue = getWidgetAdapter().getValue();
      if (adapterValue == null) {
        return null;
      }
      return convertToValue(adapterValue);
    }
    return this.value;
  }

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
  public final void addFocusHandler(UiHandlerEventFocus handler) {

    if (this.focusEventSender == null) {
      this.focusEventSender = new FocusEventSender(this, getFactory());
      if (hasWidgetAdapter()) {
        getWidgetAdapter().setFocusEventSender(this, this.focusEventSender);
      }
    }
    this.focusEventSender.addHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFocused(boolean focused) {

    if (hasWidgetAdapter()) {
      // if (this.focusEventSender != null) {
      // this.focusEventSender.setProgrammatic();
      // }
      getWidgetAdapter().setFocused(focused);
    } else if (this.focusEventSender != null) {
      this.focusEventSender.onFocusChange(this, true, !focused);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean removeFocusHandler(UiHandlerEventFocus handler) {

    if (this.focusEventSender != null) {
      return this.focusEventSender.removeHandler(handler);
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isFocused() {

    if (this.focusEventSender != null) {
      return this.focusEventSender.isFocused();
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isModifiedLocal() {

    return this.modified;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setModified(boolean modified) {

    this.modified = modified;
  }

}
