/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.custom;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetComposite;
import net.sf.mmm.util.validation.api.ValidationState;

/**
 * This is the abstract base class for a {@link UiWidgetCustom custom widget} that is also a
 * {@link UiWidgetComposite composite widget}. It supports creating reusable high-level widgets for UI
 * patterns or forms to edit business objects (see {@link #doGetValue(Object, ValidationState)} and
 * {@link #doSetValue(Object, boolean)}).
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetCustomComposite<VALUE, CHILD extends UiWidget, DELEGATE extends UiWidgetComposite<? super CHILD>>
    extends UiWidgetCustom<VALUE, DELEGATE> implements UiWidgetComposite<CHILD> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param delegate is the {@link #getDelegate() delegate}.
   * @param valueClass is the {@link #getValueClass() value class}.
   */
  public UiWidgetCustomComposite(UiContext context, DELEGATE delegate, Class<VALUE> valueClass) {

    super(context, delegate, valueClass);
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
  public CHILD getChild(int index) {

    return (CHILD) getDelegate().getChild(index);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CHILD getChild(String id) {

    return (CHILD) getDelegate().getChild(id);
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
   * 
   * <br/>
   * <br/>
   * If you do not want to use the advanced {@link #getDataBinding() data-binding} you could override this
   * method and manually implement the binding like in the following example:<br/>
   * 
   * <pre>
   * protected Person doGetValue(Person template, {@link ValidationState} state) {
   *
   *   Person result = super.doGetValue(template, state);
   *   // if the attribute is a datatype we can supply null (instead of result.getFirstName())
   *   result.setFirstName(this.widgetFirstName.{@link #getValueDirect(Object, ValidationState) getValueDirect}(null, state));
   *   result.setLastName(this.widgetLastName.{@link #getValueDirect(Object, ValidationState) getValueDirect}(null, state));
   *
   *   Address address = this.widgetAddressPanel.{@link #getValueDirect(Object, ValidationState) getValueDirect}(result.getAddress(), state);
   *   this.widgetAddressExtraPanel.{@link #getValueDirect(Object, ValidationState) getValueDirect}(address, state);
   *   result.setAddress(address);
   *   ...
   *
   *   return result;
   * }
   * </pre>
   * 
   * You may also mix the advanced data-binding with custom logic implemented in this method.
   */
  @Override
  protected VALUE doGetValue(VALUE template, ValidationState state) throws RuntimeException {

    return super.doGetValue(template, state);
  }

  /**
   * {@inheritDoc}
   * 
   * <br/>
   * <br/>
   * If you do not want to use the advanced {@link #getDataBinding() data-binding} you could override this
   * method and manually implement the binding like in the following example:<br/>
   * 
   * <pre>
   * protected void doSetValue(Person value) {
   *   this.widgetFirstName.{@link #setValue(Object) setValue}(value?.getFirstName());
   *   this.widgetLastName.{@link #setValue(Object) setValue}(value?.getLastName());
   *   ...
   * }
   * </pre>
   */
  @Override
  protected void doSetValue(VALUE value, boolean forUser) {

    super.doSetValue(value, forUser);
  }
}
