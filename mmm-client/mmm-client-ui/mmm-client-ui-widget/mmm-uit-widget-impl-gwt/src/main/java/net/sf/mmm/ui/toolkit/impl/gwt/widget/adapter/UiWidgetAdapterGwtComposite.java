/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter;

import net.sf.mmm.ui.toolkit.api.widget.UiWidget;
import net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterComposite;

import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link UiWidgetAdapterComposite} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 * @param <CHILD> is the generic type of the children.
 */
public abstract class UiWidgetAdapterGwtComposite<WIDGET extends Widget, CHILD extends UiWidget> extends
    UiWidgetAdapterGwtWidget<WIDGET> implements UiWidgetAdapterComposite<WIDGET, CHILD> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtComposite() {

    super();
  }
}
