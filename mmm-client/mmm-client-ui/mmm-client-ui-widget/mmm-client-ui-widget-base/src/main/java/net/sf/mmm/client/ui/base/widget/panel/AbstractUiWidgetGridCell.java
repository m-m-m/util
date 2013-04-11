/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.panel;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetGridCell;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetSingleMutableComposite;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapterSingleMutableComposite;

/**
 * This is the abstract base implementation of {@link UiWidgetGridCell}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetGridCell<ADAPTER extends UiWidgetAdapterSingleMutableComposite<UiWidgetRegular>>
    extends AbstractUiWidgetSingleMutableComposite<ADAPTER, UiWidgetRegular> implements UiWidgetGridCell {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetGridCell(UiContext context) {

    super(context);
  }

}
