/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget;

import net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegularAtomic;

import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link UiWidgetRegularAtomic} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public abstract class UiWidgetRegularAtomicGwt<WIDGET extends Widget> extends UiWidgetAtomicGwt<WIDGET> implements
    UiWidgetRegularAtomic {

  /**
   * The constructor.
   */
  public UiWidgetRegularAtomicGwt() {

    super();
  }

}
