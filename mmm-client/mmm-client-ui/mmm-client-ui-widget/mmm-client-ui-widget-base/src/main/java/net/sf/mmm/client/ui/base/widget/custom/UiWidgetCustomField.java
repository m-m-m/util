/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.custom;

import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventFocus;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetComposite;
import net.sf.mmm.client.ui.api.widget.UiWidgetFactory;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetField;

/**
 * This is the abstract base class for a {@link UiWidgetCustom custom widget} that is a {@link UiWidgetField
 * field widget}. The major usecase is to create {@link UiWidgetField field widgets} for custom
 * {@link net.sf.mmm.util.lang.api.Datatype} based on existing {@link UiWidgetField field widgets}.<br/>
 * Instead of extending this class directly you should rather extend one of its sub-classes.
 * 
 * @see UiWidgetFactory#createForDatatype(Class)
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

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   * @param delegate is the {@link #getDelegate() delegate}.
   */
  public UiWidgetCustomField(UiWidgetFactory<?> factory, DELEGATE delegate) {

    super(factory, delegate);
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
  protected String getSource() {

    String source = getFieldLabel();
    if (source == null) {
      source = super.getSource();
      // source may still be null but no useful fallback available...
    }
    return source;
  }

  /**
   * This method gets the first active field contained in this custom field. This can be the
   * {@link #getDelegate() delegate} itself but also a child of the delegate in case of a composite custom
   * field.
   * 
   * @return the {@link #getDelegate()}
   */
  protected abstract UiWidgetField<?> getFirstField();

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
  public void setFocused(boolean focused) {

    getFirstField().setFocused(focused);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addFocusHandler(UiHandlerEventFocus handler) {

    getFirstField().addFocusHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeFocusHandler(UiHandlerEventFocus handler) {

    return getFirstField().removeFocusHandler(handler);
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

}
