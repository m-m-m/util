/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.custom.field;

import java.util.LinkedList;
import java.util.List;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidgetDynamicComposite;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetField;

/**
 * This is the abstract base class for a {@link UiWidgetCustomField custom field widget} that is composed out
 * of multiple {@link UiWidgetField field widgets} and potentially other widgets. Extend this class to create
 * a widget for a composite {@link net.sf.mmm.util.lang.api.Datatype}. <br>
 * <b>ATTENTION:</b><br>
 * This class assumes that you create and {@link #addChild(UiWidgetRegular) add} the child widgets in the
 * constructor (of your sub-class) and requires that at least one of them is a {@link UiWidgetField}.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}. Typically a custom
 *        {@link net.sf.mmm.util.lang.api.Datatype}.
 * @param <DELEGATE> is the generic type of the {@link #getDelegate() delegate}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetCustomFieldComposite<VALUE, DELEGATE extends UiWidgetDynamicComposite<UiWidgetRegular>>
    extends UiWidgetCustomField<VALUE, DELEGATE> {

  /** The {@link List} of children that are field widgets. */
  private final List<UiWidgetField<?>> fieldList;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param delegate is the {@link #getDelegate() delegate}.
   * @param valueClass is the {@link #getValueClass() value class}.
   */
  public UiWidgetCustomFieldComposite(UiContext context, DELEGATE delegate, Class<VALUE> valueClass) {

    super(context, delegate, valueClass);
    this.fieldList = new LinkedList<UiWidgetField<?>>();
  }

  /**
   * @see net.sf.mmm.client.ui.api.widget.UiWidgetDynamicComposite#addChild(net.sf.mmm.client.ui.api.widget.UiWidget)
   * 
   * @param child is the child widget to add.
   */
  protected void addChild(UiWidgetRegular child) {

    if (child instanceof UiWidgetField<?>) {
      registerField((UiWidgetField<?>) child);
    }
    getDelegate().addChild(child);
  }

  /**
   * @param field is the {@link UiWidgetField} to register.
   */
  private void registerField(UiWidgetField<?> field) {

    this.fieldList.add(field);
    field.addEventHandler(getEventHandlerAdapter());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isTrimValue() {

    return getFirstField().isTrimValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTrimValue(boolean trimValue) {

    for (UiWidgetField<?> field : this.fieldList) {
      field.setTrimValue(trimValue);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetField<?> getFirstField() {

    return this.fieldList.get(0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected abstract String convertValueToString(VALUE value);

}
