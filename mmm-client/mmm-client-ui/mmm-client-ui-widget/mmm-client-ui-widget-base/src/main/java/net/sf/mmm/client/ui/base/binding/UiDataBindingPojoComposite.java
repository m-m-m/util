/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.binding;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidget;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.pojo.path.api.TypedProperty;
import net.sf.mmm.util.validation.api.ValidationState;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.base.binding.UiDataBinding} for composite custom
 * widgets.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiDataBindingPojoComposite<VALUE> extends UiDataBindingPojo<VALUE> {

  private final Map<TypedProperty<?>, UiWidgetWithValue<?>> property2widgetMap;

  /**
   * The constructor.
   * 
   * @param widget
   */
  public UiDataBindingPojoComposite(AbstractUiWidget<VALUE> widget) {

    super(widget);
    this.property2widgetMap = new HashMap<TypedProperty<?>, UiWidgetWithValue<?>>();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <P> void bind(TypedProperty<P> property, UiWidgetWithValue<P> widget) {

    UiWidgetWithValue<?> old = this.property2widgetMap.put(property, widget);
    if (old != null) {
      throw new DuplicateObjectException(widget, property, old);
    }
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

  protected String getLabel(TypedProperty<?> property) {

    // TODO hohwille I18N/NLS required...
    return property.getTitle();
  }

  protected <P> UiWidgetWithValue<P> createWidget(TypedProperty<P> property, String label) {

    String labelText = label;
    if (labelText == null) {
      labelText = getLabel(property);
    }

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  protected VALUE doGetValue(VALUE template, ValidationState state) {

    for (TypedProperty property : this.property2widgetMap.keySet()) {
      UiWidgetWithValue widget = this.property2widgetMap.get(property);
      Object propertyValue = getDescriptor().getProperty(template, property);
      propertyValue = widget.getValueDirect(propertyValue, state);
      getDescriptor().setProperty(template, property, propertyValue);
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
      UiWidgetWithValue widget = this.property2widgetMap.get(property);
      Object propertyValue = getDescriptor().getProperty(value, property);
      widget.setValue(propertyValue, forUser);
    }
    super.doSetValue(value, forUser);
  }
}
