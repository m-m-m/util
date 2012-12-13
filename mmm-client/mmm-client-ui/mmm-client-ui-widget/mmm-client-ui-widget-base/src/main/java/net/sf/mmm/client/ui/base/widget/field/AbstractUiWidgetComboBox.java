/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.field;

import net.sf.mmm.client.ui.api.aria.role.Role;
import net.sf.mmm.client.ui.api.aria.role.RoleCombobox;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetComboBox;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterOptionsField;

/**
 * This is the abstract base implementation of {@link UiWidgetComboBox}.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetComboBox<ADAPTER extends UiWidgetAdapterOptionsField<?, VALUE>, VALUE> extends
    AbstractUiWidgetOptionsField<ADAPTER, VALUE> implements UiWidgetComboBox<VALUE> {

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public AbstractUiWidgetComboBox(AbstractUiWidgetFactory<?> factory) {

    super(factory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final RoleCombobox getAriaRole() {

    return (RoleCombobox) super.getAriaRole();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Class<? extends Role> getAriaRoleFixedType() {

    return RoleCombobox.class;
  }

}
