/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget;

import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactoryPlain;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.atomic.UiWidgetButtonGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.atomic.UiWidgetComboBoxGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.atomic.UiWidgetDoubleFieldGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.atomic.UiWidgetImageGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.atomic.UiWidgetIntegerFieldGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.atomic.UiWidgetLabelGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.atomic.UiWidgetLongFieldGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.atomic.UiWidgetPasswordFieldGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.atomic.UiWidgetRadioButtonsGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.atomic.UiWidgetRadioButtonsVerticalGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.atomic.UiWidgetRichTextAreaGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.atomic.UiWidgetTextAreaGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.atomic.UiWidgetTextFieldGwt;

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
    register(new UiWidgetTextFieldGwt.Factory());
    register(new UiWidgetPasswordFieldGwt.Factory());
    register(new UiWidgetTextAreaGwt.Factory());
    register(new UiWidgetRichTextAreaGwt.Factory());
    register(new UiWidgetLongFieldGwt.Factory());
    register(new UiWidgetIntegerFieldGwt.Factory());
    register(new UiWidgetDoubleFieldGwt.Factory());
    register(new UiWidgetRadioButtonsGwt.Factory());
    register(new UiWidgetRadioButtonsVerticalGwt.Factory());
    register(new UiWidgetComboBoxGwt.Factory());
  }

}
