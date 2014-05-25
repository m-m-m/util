/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.field;

import java.util.List;

import net.sf.mmm.client.ui.api.aria.role.RoleListbox;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteHeightInRows;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteOptions;
import net.sf.mmm.client.ui.api.widget.UiWidgetListBase;
import net.sf.mmm.client.ui.api.widget.UiWidgetNative;

/**
 * This is the interface for a {@link UiWidgetOptionsField options field widget} that represents a
 * <em>list box</em>. Such widget is a simple list that allows to select one or multiple items out of a list
 * of {@link #getOptions() options}. Its {@link #getValue() value} is therefore the
 * {@link #getSelectedValues() list of selected items}.
 *
 * @see net.sf.mmm.client.ui.api.widget.complex.UiWidgetOptionListTable
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
public interface UiWidgetListBoxField<VALUE> extends UiWidgetListBase<VALUE>, UiWidgetField<List<VALUE>>,
    UiWidgetNative, AttributeWriteOptions<VALUE>, AttributeWriteHeightInRows {

  /**
   * {@inheritDoc}
   */
  @Override
  RoleListbox getAriaRole();

  /**
   * {@inheritDoc}
   */
  @Override
  List<VALUE> getValue();

}
