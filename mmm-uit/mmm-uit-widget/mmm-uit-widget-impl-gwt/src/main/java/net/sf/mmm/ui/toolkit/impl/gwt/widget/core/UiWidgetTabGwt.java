/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.core;

import net.sf.mmm.ui.toolkit.api.widget.core.UiWidgetTab;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.core.AbstractUiWidgetTab;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter.UiWidgetAdapterGwtTab;

/**
 * This is the implementation of {@link UiWidgetTab} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetTabGwt extends AbstractUiWidgetTab<UiWidgetAdapterGwtTab> {

  /**
   * The constructor.
   */
  public UiWidgetTabGwt() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtTab createWidgetAdapter() {

    return new UiWidgetAdapterGwtTab();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetTab> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetTab.class);
    }

    /**
     * {@inheritDoc}
     */
    public UiWidgetTab create() {

      return new UiWidgetTabGwt();
    }

  }

}
