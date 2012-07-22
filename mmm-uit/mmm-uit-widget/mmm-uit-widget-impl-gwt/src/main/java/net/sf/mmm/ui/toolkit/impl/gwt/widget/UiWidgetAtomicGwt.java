/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget;

import net.sf.mmm.ui.toolkit.api.widget.UiWidgetAtomic;

import com.google.gwt.user.client.ui.UIObject;

/**
 * This is the abstract base implementation of {@link UiWidgetAtomic} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public abstract class UiWidgetAtomicGwt<WIDGET extends UIObject> extends UiWidgetGwt<WIDGET> implements UiWidgetAtomic {

  /**
   * The constructor.
   */
  public UiWidgetAtomicGwt() {

    super();
  }

}
