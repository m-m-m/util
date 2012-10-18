/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget.custom;

import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventFocus;
import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventValueChange;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetFactory;
import net.sf.mmm.ui.toolkit.api.widget.core.UiWidgetLabel;
import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetField;
import net.sf.mmm.ui.toolkit.base.handler.event.ChangeEventSender;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.util.lang.api.attribute.AttributeReadValue;

/**
 * This is the abstract base class for a {@link UiWidgetCustomField custom field widget} that is
 * {@link #getDelegate() build} out of a single {@link UiWidgetField field widget}.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}. Typically a custom
 *        {@link net.sf.mmm.util.lang.api.Datatype}.
 * @param <DELEGATE_VALUE> is the generic type of the {@link #getDelegate() delegates} {@link #getValue()
 *        value}.
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetCustomFieldAtomic<VALUE, DELEGATE_VALUE, DELEGATE extends UiWidgetField<DELEGATE_VALUE>>
    extends UiWidgetCustomField<VALUE, DELEGATE> {

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   * @param delegate is the {@link #getDelegate() delegate}.
   */
  public UiWidgetCustomFieldAtomic(UiWidgetFactory<?> factory, DELEGATE delegate) {

    super(factory, delegate);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected VALUE doGetValue() throws RuntimeException {

    DELEGATE_VALUE value = getDelegate().getValueOrException();
    if (value == null) {
      return getNullValue();
    }
    return convertValueFromDelegate(value);
  }

  /**
   * This method converts the given <code>value</code> from the {@link #getDelegate() delegate widget} to the
   * value of this widget. This typically has to be implemented with your custom datatype conversion. In case
   * that {@literal <VALUE>} and {@literal <DELEGATE_VALUE>} are the same you can just return the given
   * <code>value</code>.<br/>
   * A typical implementation shall look like this:
   * 
   * <pre>
   * protected MyDatatype convertValueFromDelegate(String value) {
   *   return new MyDatatype(value);
   * }
   * </pre>
   * 
   * @param value is the value of the {@link #getDelegate() delegate}. Never <code>null</code>.
   * @return the converted value of this widget.
   */
  protected abstract VALUE convertValueFromDelegate(DELEGATE_VALUE value);

  /**
   * This method converts the given <code>value</code> that is going to be set in this widget to the value of
   * the {@link #getDelegate() delegate widget}. This typically has to be implemented with your custom
   * datatype conversion. In case that {@literal <VALUE>} and {@literal <DELEGATE_VALUE>} are the same you can
   * just return the given <code>value</code>.<br/>
   * A typical implementation shall look like this:
   * 
   * <pre>
   * protected String convertValueForDelegate(MyDatatype value) {
   *   return value.{@link net.sf.mmm.util.lang.api.Datatype#getValue() getValue()}.
   * }
   * </pre>
   * 
   * @param value is the value to convert. Never <code>null</code>.
   * @return the converted value for the {@link #getDelegate() delegate widget}
   */
  protected abstract DELEGATE_VALUE convertValueForDelegate(VALUE value);

  /**
   * This method gets the value for the {@link #getDelegate() delegate widget} in case <code>null</code> is
   * provided as value for this widget. The default implementation return <code>null</code>. Override to
   * change.
   * 
   * @return the <code>null</code>-value.
   */
  protected DELEGATE_VALUE getNullValueForDelegate() {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final void doSetValue(VALUE value) {

    DELEGATE_VALUE delegateValue;
    if (value == null) {
      delegateValue = getNullValueForDelegate();
    } else {
      delegateValue = convertValueForDelegate(value);
    }
    getDelegate().setValue(delegateValue);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final String getLabel() {

    return getDelegate().getLabel();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setLabel(String label) {

    getDelegate().setLabel(label);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetLabel getLabelWidget() {

    return getDelegate().getLabelWidget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void addFocusHandler(UiHandlerEventFocus handler) {

    getDelegate().addFocusHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean removeFocusHandler(UiHandlerEventFocus handler) {

    return getDelegate().removeFocusHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setFocused(boolean focused) {

    getDelegate().setFocused(focused);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isFocused() {

    return getDelegate().isFocused();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ChangeEventSender<VALUE> createChangeEventSender() {

    final ChangeEventSender<VALUE> changeEventSender = new ChangeEventSender<VALUE>(this,
        (AbstractUiWidgetFactory<?>) getFactory());
    getDelegate().addChangeHandler(new UiHandlerEventValueChange<DELEGATE_VALUE>() {

      @Override
      public void onValueChange(AttributeReadValue<DELEGATE_VALUE> source, boolean programmatic) {

        changeEventSender.onValueChange(UiWidgetCustomFieldAtomic.this, programmatic);
      }
    });
    return changeEventSender;
  }

}
