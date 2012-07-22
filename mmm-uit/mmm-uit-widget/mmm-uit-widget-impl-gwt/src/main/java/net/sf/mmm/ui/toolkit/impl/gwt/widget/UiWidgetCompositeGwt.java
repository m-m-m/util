/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget;

import net.sf.mmm.ui.toolkit.api.widget.UiWidget;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetComposite;

import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link UiWidgetComposite} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 */
public abstract class UiWidgetCompositeGwt<WIDGET extends Widget, CHILD extends UiWidget> extends UiWidgetGwt<WIDGET>
    implements UiWidgetComposite<CHILD> {

  /**
   * The constructor.
   */
  public UiWidgetCompositeGwt() {

    super();
  }

}
