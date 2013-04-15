/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.core;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetCollapsableSection;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetClickable;
import net.sf.mmm.client.ui.base.widget.core.adapter.UiWidgetAdapterCollapsableSection;

/**
 * This is the abstract base implementation of {@link UiWidgetCollapsableSection}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 */
public abstract class AbstractUiWidgetCollapsableSection<ADAPTER extends UiWidgetAdapterCollapsableSection> extends
    AbstractUiWidgetClickable<ADAPTER> implements UiWidgetCollapsableSection {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetCollapsableSection(UiContext context) {

    super(context);
    setPrimaryStyle(PRIMARY_STYLE);
  }

}
