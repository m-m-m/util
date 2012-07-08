/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.widget.gwt.atomic;

import net.sf.mmm.ui.toolkit.api.widget.atomic.UiWidgetAtomicRegular;

import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link UiWidgetAtomicRegular} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public abstract class AbstractUiWidgetAtomicRegular<WIDGET extends Widget> extends AbstractUiWidgetAtomicGwt<WIDGET>
    implements UiWidgetAtomicRegular {

  /**
   * The constructor.
   * 
   * @param widget is the {@link #getWidget() widget}.
   */
  public AbstractUiWidgetAtomicRegular(WIDGET widget) {

    super(widget);
  }

}
