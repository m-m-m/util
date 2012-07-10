/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget;

import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactoryPlain;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.atomic.UiWidgetButtonGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.atomic.UiWidgetImageGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.atomic.UiWidgetLabelGwt;

import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of the {@link net.sf.mmm.ui.toolkit.api.widget.UiWidgetFactory} for GWT widgets.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetFactoryGwt extends AbstractUiWidgetFactoryPlain<Widget> {

  /**
   * The constructor.
   */
  public UiWidgetFactoryGwt() {

    super();
    register(new UiWidgetImageGwt.Factory());
    register(new UiWidgetButtonGwt.Factory());
    register(new UiWidgetLabelGwt.Factory());
  }

}
