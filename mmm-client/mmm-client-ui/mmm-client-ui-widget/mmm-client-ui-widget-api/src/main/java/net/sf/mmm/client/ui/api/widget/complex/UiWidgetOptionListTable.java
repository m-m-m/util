/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.complex;

import java.util.List;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteOptions;
import net.sf.mmm.client.ui.api.widget.UiWidgetNative;

/**
 * This is the interface for an {@link #setOptions(List) option}-based {@link UiWidgetAbstractListTable list
 * table widget}. Its {@link #getValue() value} is the actual {@link #getSelectedValues() selection} the
 * end-user can select from the available {@link #getOptions() options}.
 * 
 * @param <ROW> is the generic type of a row in the {@link #getValue() value list}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetOptionListTable<ROW> extends UiWidgetAbstractListTable<ROW>, AttributeWriteOptions<ROW>,
    UiWidgetNative {

  /**
   * Here the value is the actual {@link #getSelectedValues() selection} the end-user can select from the
   * available {@link #getOptions() options}.
   * 
   * {@inheritDoc}
   */
  @Override
  List<ROW> getValue();

}
