/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.panel;

import net.sf.mmm.client.ui.api.aria.role.Role;
import net.sf.mmm.client.ui.api.aria.role.RolePresentation;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetGridPanel;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetGridRow;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterGridPanel;

/**
 * This is the abstract base implementation of {@link UiWidgetGridPanel}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetGridPanel<ADAPTER extends UiWidgetAdapterGridPanel<?>> extends
    AbstractUiWidgetDynamicPanel<ADAPTER, UiWidgetGridRow> implements UiWidgetGridPanel {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetGridPanel(AbstractUiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetGridRow addChildren(UiWidgetRegular... children) {

    UiWidgetGridRow row = getContext().getWidgetFactory().create(UiWidgetGridRow.class);
    for (UiWidgetRegular child : children) {
      row.addChild(child);
    }
    addChild(row);
    return row;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Class<? extends Role> getAriaRoleFixedType() {

    return RolePresentation.class;
  }
}
