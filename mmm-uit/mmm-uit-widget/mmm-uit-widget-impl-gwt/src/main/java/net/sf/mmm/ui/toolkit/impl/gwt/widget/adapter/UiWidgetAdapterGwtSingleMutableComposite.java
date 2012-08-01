/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter;

import net.sf.mmm.ui.toolkit.api.widget.UiWidget;
import net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterSingleMutableComposite;

import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link UiWidgetAdapterSingleMutableComposite} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 * @param <CHILD> is the generic type of the {@link #setChild(UiWidget) child}.
 */
public abstract class UiWidgetAdapterGwtSingleMutableComposite<WIDGET extends Widget, CHILD extends UiWidget> extends
    UiWidgetAdapterGwtWidget<WIDGET> implements UiWidgetAdapterSingleMutableComposite<WIDGET, CHILD> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtSingleMutableComposite() {

    super();
  }
}
