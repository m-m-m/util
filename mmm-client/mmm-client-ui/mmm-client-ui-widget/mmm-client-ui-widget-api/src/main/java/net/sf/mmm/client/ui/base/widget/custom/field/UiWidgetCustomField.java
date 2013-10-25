/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.custom.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.event.UiEvent;
import net.sf.mmm.client.ui.api.feature.UiFeature;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEvent;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventFocus;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetComposite;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetLabel;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetField;
import net.sf.mmm.client.ui.base.widget.custom.UiWidgetCustom;

/**
 * This is the abstract base class for a {@link UiWidgetCustom custom widget} that is a {@link UiWidgetField
 * field widget}. The major usecase is to create {@link UiWidgetField field widgets} for custom
 * {@link net.sf.mmm.util.lang.api.Datatype} based on existing {@link UiWidgetField field widgets}.<br/>
 * Instead of extending this class directly you should rather extend one of its sub-classes.
 * 
 * @see net.sf.mmm.client.ui.api.widget.factory.UiWidgetFactoryDatatype#createForDatatype(Class)
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}. Typically a custom
 *        {@link net.sf.mmm.util.lang.api.Datatype}.
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetCustomField<VALUE, DELEGATE extends UiWidgetComposite<?>> extends
    UiWidgetCustom<VALUE, DELEGATE> implements UiWidgetField<VALUE> {

  /** @see #getEventHandlerAdapter() */
  private final EventHandlerAdapter eventHandlerAdapter;

  /** The sub-field that currently has the focus or <code>null</code>. */
  private UiFeature focusField;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param delegate is the {@link #getDelegate() delegate}.
   * @param valueClass is the {@link #getValueClass() value class}.
   */
  public UiWidgetCustomField(UiContext context, DELEGATE delegate, Class<VALUE> valueClass) {

    super(context, delegate, valueClass);
    this.eventHandlerAdapter = new EventHandlerAdapter();
  }

  /**
   * @return the {@link EventHandlerAdapter}.
   */
  EventHandlerAdapter getEventHandlerAdapter() {

    return this.eventHandlerAdapter;
  }

  /**
   * This method gets the value in case the {@link #getDelegate() delegate widget} returns <code>null</code>
   * as value. The default implementation return <code>null</code>. Override to change.
   * 
   * @return the <code>null</code>-value.
   */
  protected VALUE getNullValue() {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    String fieldLabel = getLabel();
    if (fieldLabel == null) {
      return super.toString();
    } else {
      return super.toString() + ":" + fieldLabel;
    }
  }

  /**
   * This method gets the first active field contained in this custom field. This can be the
   * {@link #getDelegate() delegate} itself but also a child of the delegate in case of a composite custom
   * field.
   * 
   * @return the {@link #getDelegate()}
   */
  abstract UiWidgetField<?> getFirstField();

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLabel() {

    UiWidgetField<?> firstField = getFirstField();
    if (firstField == null) {
      return null;
    }
    return firstField.getLabel();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLabel(String label) {

    getFirstField().setLabel(label);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetLabel getLabelWidget() {

    UiWidgetField<?> firstField = getFirstField();
    if (firstField == null) {
      return null;
    }
    return firstField.getLabelWidget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getChildIndex(UiWidget child) {

    return getDelegate().getChildIndex(child);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidget getChild(int index) throws IndexOutOfBoundsException {

    return getDelegate().getChild(index);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidget getChild(String id) {

    return getDelegate().getChild(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getChildCount() {

    return getDelegate().getChildCount();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean setFocused() {

    if (this.focusField != null) {
      return ((UiWidgetField<?>) this.focusField).setFocused();
    } else {
      return getFirstField().setFocused();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isFocused() {

    return (this.focusField != null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addFocusHandler(UiHandlerEventFocus handler) {

    addEventHandler(handler);
    // getFirstField().addFocusHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeFocusHandler(UiHandlerEventFocus handler) {

    return removeEventHandler(handler);
    // return getFirstField().removeFocusHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAccessKey(char accessKey) {

    getFirstField().setAccessKey(accessKey);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char getAccessKey() {

    return getFirstField().getAccessKey();
  }

  /**
   * This inner class is an {@link UiHandlerEvent event handler} that adapts from the internal fields to this
   * composite custom widget.
   */
  public class EventHandlerAdapter implements UiHandlerEvent {

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEvent(UiEvent event) {

      boolean fireEvent;
      switch (event.getType()) {
        case FOCUS_GAIN:
          fireEvent = (UiWidgetCustomField.this.focusField == null);
          UiWidgetCustomField.this.focusField = event.getSource();
          break;
        case FOCUS_LOSS:
          // TODO hohwille revisit this code... Might be buggy...
          if (event.getSource() == UiWidgetCustomField.this.focusField) {
            UiWidgetCustomField.this.focusField = null;
            fireEvent = true;
          } else {
            fireEvent = false;
          }
          break;
        case VALUE_CHANGE:
          // we ignore programmatic events from the adapted widget(s) to prevent event duplication
          // TODO hohwille if value of adapted widget is set internally, no event will be send then - buggy?
          boolean programmatic = event.isProgrammatic();
          fireEvent = !programmatic;
          break;
        default :
          fireEvent = true;
          break;
      }
      if (fireEvent) {
        fireEvent(event);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValidationFailure(String validationFailure) {

    getFirstField().setValidationFailure(validationFailure);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getValidationFailure() {

    // actually we need to append the validation failure from all fields...
    return getFirstField().getValidationFailure();
  }

}
