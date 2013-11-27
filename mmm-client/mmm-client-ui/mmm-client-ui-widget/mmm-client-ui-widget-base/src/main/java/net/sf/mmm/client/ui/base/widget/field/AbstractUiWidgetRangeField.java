/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.aria.role.Role;
import net.sf.mmm.client.ui.api.aria.role.RoleSlider;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetRangeField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterRangeField;

/**
 * This is the abstract base implementation of {@link UiWidgetRangeField}.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetRangeField<ADAPTER extends UiWidgetAdapterRangeField<VALUE>, VALUE> extends
    AbstractUiWidgetField<ADAPTER, VALUE, VALUE> implements UiWidgetRangeField<VALUE> {

  /** @see #getMinimumValue() */
  private VALUE minimumValue;

  /** @see #getMaximumValue() */
  private VALUE maximumValue;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public AbstractUiWidgetRangeField(UiContext context, ADAPTER widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final RoleSlider getAriaRole() {

    return (RoleSlider) super.getAriaRole();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Class<? extends Role> getAriaRoleFixedType() {

    return RoleSlider.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VALUE getMinimumValue() {

    return this.minimumValue;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMinimumValue(VALUE minimum) {

    this.minimumValue = minimum;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setMinimumValue(minimum);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VALUE getMaximumValue() {

    return this.maximumValue;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximumValue(VALUE maximum) {

    this.maximumValue = maximum;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setMaximumValue(maximum);
    }
  }

}
