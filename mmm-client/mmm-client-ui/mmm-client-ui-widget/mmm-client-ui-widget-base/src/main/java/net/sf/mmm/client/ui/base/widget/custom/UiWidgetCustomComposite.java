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
 * patterns or forms to edit business objects (see {@link #doGetValue(Object, ValidationState)} and {@link #doSetValue(Object)}).
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetCustomComposite<VALUE, CHILD extends UiWidget, DELEGATE extends UiWidgetComposite<CHILD>>
    extends UiWidgetCustom<VALUE, DELEGATE> implements UiWidgetComposite<CHILD> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param delegate is the {@link #getDelegate() delegate}.
   */
  public UiWidgetCustomComposite(UiContext context, DELEGATE delegate) {

    super(context, delegate);
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

    return getDelegate().getChild(index);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CHILD getChild(String id) {

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
   * 
   * <br/>
   * An implementation should get the values from the {@link #getChild(int) child-widgets} and compose the
   * requested value out of these attributes.<br/>
   * An example implementation may look like this:
   * 
   * <pre>
   * protected Person doGetValue() {
   *   Person result = new Person();
   *   result.setFirstName(this.widgetFirstName.getValue());
   *   result.setLastName(this.widgetLastName.getValue());
   *   ...
   *   return result;
   * }
   * </pre>
   */
  @Override
  protected abstract VALUE doGetValue(VALUE template, ValidationState state) throws RuntimeException;

  /**
   * {@inheritDoc}
   * 
   * <br/>
   * An implementation should set the attributes of the given <code>value</code> in the {@link #getChild(int)
   * child-widgets}.<br/>
   * An example implementation may look like this:
   * 
   * <pre>
   * protected void doSetValue(Person value) {
   *   this.widgetFirstName.setValue(value?.getFirstName());
   *   this.widgetLastName.setValue(value?.getLastName());
   *   ...
   * }
   * </pre>
   */
  @Override
  protected abstract void doSetValue(VALUE value);

}
