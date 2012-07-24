/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget;

import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactoryPlain;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.core.UiWidgetButtonGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.core.UiWidgetImageGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.core.UiWidgetLabelGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.core.UiWidgetTabGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.field.UiWidgetComboBoxGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.field.UiWidgetDoubleFieldGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.field.UiWidgetIntegerFieldGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.field.UiWidgetLongFieldGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.field.UiWidgetPasswordFieldGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.field.UiWidgetRadioButtonsGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.field.UiWidgetRadioButtonsVerticalGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.field.UiWidgetRichTextAreaGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.field.UiWidgetTextAreaGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.field.UiWidgetTextFieldGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.menu.UiWidgetMenuBarGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.menu.UiWidgetMenuGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.menu.UiWidgetMenuItemClickableGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.panel.UiWidgetHorizontalPanelGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.panel.UiWidgetTabPanelGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.panel.UiWidgetVerticalPanelGwt;

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
    // atomic
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
    // composite
    register(new UiWidgetTabGwt.Factory());
    register(new UiWidgetTabPanelGwt.Factory());
    register(new UiWidgetHorizontalPanelGwt.Factory());
    register(new UiWidgetVerticalPanelGwt.Factory());
    // menu
    register(new UiWidgetMenuBarGwt.Factory());
    register(new UiWidgetMenuGwt.Factory());
    register(new UiWidgetMenuItemClickableGwt.Factory());
  }

}
