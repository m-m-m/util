/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.binding;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.mmm.client.ui.api.widget.UiWidgetFactory;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetField;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidget;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.pojo.path.api.TypedProperty;
import net.sf.mmm.util.validation.api.ValidationState;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.base.binding.UiDataBinding} for composite custom
 * widgets.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiDataBindingPojoComposite<VALUE> extends UiDataBindingPojo<VALUE> {

  /** @see #bind(TypedProperty, UiWidgetWithValue) */
  private final Map<TypedProperty<?>, List<UiWidgetWithValue<?>>> property2widgetMap;

  /**
   * The constructor.
   * 
   * @param widget is the {@link #getWidget() widget} to bind.
   * @param adapter is the {@link UiDataBindingAdapter}.
   */
  public UiDataBindingPojoComposite(AbstractUiWidget<VALUE> widget, UiDataBindingAdapter<VALUE> adapter) {

    super(widget, adapter);
    this.property2widgetMap = new HashMap<TypedProperty<?>, List<UiWidgetWithValue<?>>>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <P> void bind(TypedProperty<P> property, UiWidgetWithValue<P> widget) {

    List<UiWidgetWithValue<?>> list = this.property2widgetMap.get(property);
    if (list == null) {
      list = new LinkedList<UiWidgetWithValue<?>>();
      this.property2widgetMap.put(property, list);
    }
    assert verifyNotInList(list, widget, property);
    list.add(widget);
  }

  /**
   * Verifies that the given <code>widget</code> is NOT already {@link List#contains(Object) contained} in the
   * given <code>list</code>.
   * 
   * @param list is the {@link List} to check.
   * @param widget is the {@link UiWidgetWithValue} to check.
   * @param property is the {@link TypedProperty} used for the potential error mesasage.
   * @return <code>true</code>.
   */
  private boolean verifyNotInList(List<UiWidgetWithValue<?>> list, UiWidgetWithValue<?> widget,
      TypedProperty<?> property) {

    if (list.contains(widget)) {
      throw new DuplicateObjectException(widget, property);
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <P> UiWidgetWithValue<P> createAndBind(TypedProperty<P> property, String label) {

    UiWidgetWithValue<P> widget = createWidget(property, label);
    bind(property, widget);
    return widget;
  }

  /**
   * Determines the label to use for the given <code>property</code> (including I18N, etc.).
   * 
   * @see TypedProperty#getTitle()
   * 
   * @param property is the {@link TypedProperty}.
   * @return the label to use for the property.
   */
  protected String getLabel(TypedProperty<?> property) {

    // return NlsAccess.getFactory().createDirect(BUNDLE_PROPERTY_LABELS,
    // property.getTitle()).getLocalizedMessage();
    return property.getTitle();
  }

  /**
   * Creates the {@link UiWidgetWithValue} for the given <code>property</code> using the given
   * <code>label</code>.
   * 
   * @param <P> is the generic {@link TypedProperty#getPropertyType() property type}.
   * 
   * @param property is the {@link TypedProperty}.
   * @param label is the label for the widget or <code>null</code> to determine automatically.
   * @return the new {@link UiWidgetWithValue} for the given input.
   */
  protected <P> UiWidgetWithValue<P> createWidget(TypedProperty<P> property, String label) {

    String labelText = label;
    if (labelText == null) {
      labelText = getLabel(property);
    }

    AbstractUiWidget<VALUE> widget = getWidget();
    Class<P> propertyType = getAdapter().getPropertyType(property);
    UiWidgetFactory widgetFactory = widget.getContext().getWidgetFactory();

    if ((propertyType == List.class) || (propertyType == Collection.class)) {
      throw new IllegalStateException("TODO: cannot ...");
    }
    UiWidgetField<P> childWidget = widgetFactory.createForDatatype(propertyType);
    childWidget.setFieldLabel(labelText);
    return childWidget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  protected VALUE doGetValue(VALUE template, ValidationState state) {

    for (TypedProperty property : this.property2widgetMap.keySet()) {
      List<UiWidgetWithValue<?>> widgetList = this.property2widgetMap.get(property);
      if (!widgetList.isEmpty()) {
        Object propertyValue;
        if (property != null) {
          propertyValue = getAdapter().getPropertyValue(template, property);
        } else {
          propertyValue = template;
        }
        for (UiWidgetWithValue widget : widgetList) {
          propertyValue = widget.getValueDirect(propertyValue, state);
        }
        if (property != null) {
          getAdapter().setPropertyValue(template, property, propertyValue);
        }
      }
    }
    return super.doGetValue(template, state);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  protected void doSetValue(VALUE value, boolean forUser) {

    for (TypedProperty property : this.property2widgetMap.keySet()) {
      List<UiWidgetWithValue<?>> widgetList = this.property2widgetMap.get(property);
      if (!widgetList.isEmpty()) {
        Object propertyValue;
        if (property != null) {
          propertyValue = getAdapter().getPropertyValue(value, property);
        } else {
          propertyValue = value;
        }
        for (UiWidgetWithValue widget : widgetList) {
          widget.setValue(propertyValue, forUser);
        }
      }
    }
    super.doSetValue(value, forUser);
  }
}
