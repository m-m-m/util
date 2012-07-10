/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.atomic;

import net.sf.mmm.ui.toolkit.api.widget.atomic.UiWidgetAtomic;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.AbstractUiWidgetGwt;

import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the abstract base implementation of {@link UiWidgetAtomic} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getToplevelWidget()}.
 */
public abstract class UiWidgetAtomicGwt<WIDGET extends UIObject> extends AbstractUiWidgetGwt<WIDGET> implements
    UiWidgetAtomic {

  /**
   * The constructor.
   * 
   * @param widget is the {@link #getToplevelWidget() widget}.
   */
  public UiWidgetAtomicGwt(Widget widget) {

    super(widget);
  }

}
