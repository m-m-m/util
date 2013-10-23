/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget;

import javax.inject.Named;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.factory.UiWidgetFactoryNative;
import net.sf.mmm.client.ui.api.widget.window.UiWidgetMainWindow;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetFactoryNative;
import net.sf.mmm.client.ui.impl.test.widget.complex.UiWidgetTreeTest;
import net.sf.mmm.client.ui.impl.test.widget.core.UiWidgetButtonTest;
import net.sf.mmm.client.ui.impl.test.widget.core.UiWidgetCollapsableSectionTest;
import net.sf.mmm.client.ui.impl.test.widget.core.UiWidgetImageTest;
import net.sf.mmm.client.ui.impl.test.widget.core.UiWidgetLabelTest;
import net.sf.mmm.client.ui.impl.test.widget.core.UiWidgetLinkTest;
import net.sf.mmm.client.ui.impl.test.widget.core.UiWidgetSectionTest;
import net.sf.mmm.client.ui.impl.test.widget.core.UiWidgetSlotTest;
import net.sf.mmm.client.ui.impl.test.widget.core.UiWidgetTabTest;
import net.sf.mmm.client.ui.impl.test.widget.field.UiWidgetComboBoxTest;
import net.sf.mmm.client.ui.impl.test.widget.field.UiWidgetDateFieldTest;
import net.sf.mmm.client.ui.impl.test.widget.field.UiWidgetDoubleFieldTest;
import net.sf.mmm.client.ui.impl.test.widget.field.UiWidgetIntegerFieldTest;
import net.sf.mmm.client.ui.impl.test.widget.field.UiWidgetIntegerSliderFieldTest;
import net.sf.mmm.client.ui.impl.test.widget.field.UiWidgetLongFieldTest;
import net.sf.mmm.client.ui.impl.test.widget.field.UiWidgetPasswordFieldTest;
import net.sf.mmm.client.ui.impl.test.widget.field.UiWidgetRadioButtonsTest;
import net.sf.mmm.client.ui.impl.test.widget.field.UiWidgetRadioButtonsVerticalTest;
import net.sf.mmm.client.ui.impl.test.widget.field.UiWidgetRichTextFieldTest;
import net.sf.mmm.client.ui.impl.test.widget.field.UiWidgetTextAreaFieldTest;
import net.sf.mmm.client.ui.impl.test.widget.field.UiWidgetTextFieldTest;
import net.sf.mmm.client.ui.impl.test.widget.menu.UiWidgetMenuBarTest;
import net.sf.mmm.client.ui.impl.test.widget.menu.UiWidgetMenuItemClickableTest;
import net.sf.mmm.client.ui.impl.test.widget.menu.UiWidgetMenuItemSeparatorTest;
import net.sf.mmm.client.ui.impl.test.widget.menu.UiWidgetMenuTest;
import net.sf.mmm.client.ui.impl.test.widget.panel.UiWidgetBorderPanelTest;
import net.sf.mmm.client.ui.impl.test.widget.panel.UiWidgetGridCellTest;
import net.sf.mmm.client.ui.impl.test.widget.panel.UiWidgetGridPanelTest;
import net.sf.mmm.client.ui.impl.test.widget.panel.UiWidgetGridRowTest;
import net.sf.mmm.client.ui.impl.test.widget.panel.UiWidgetHorizontalPanelTest;
import net.sf.mmm.client.ui.impl.test.widget.panel.UiWidgetTabPanelTest;
import net.sf.mmm.client.ui.impl.test.widget.panel.UiWidgetVerticalPanelTest;
import net.sf.mmm.client.ui.impl.test.widget.window.UiWidgetMainWindowTest;
import net.sf.mmm.client.ui.impl.test.widget.window.UiWidgetPopupTest;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This is the implementation of the {@link net.sf.mmm.client.ui.api.UiContext} for testing without a native
 * toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Named(UiWidgetFactoryNative.CDI_NAME)
public class UiWidgetFactoryNativeTest extends AbstractUiWidgetFactoryNative {

  /**
   * The constructor.
   */
  public UiWidgetFactoryNativeTest() {

    super();
    // core
    register(new UiWidgetImageTest.Factory());
    register(new UiWidgetButtonTest.Factory());
    register(new UiWidgetLabelTest.Factory());
    register(new UiWidgetLinkTest.Factory());
    register(new UiWidgetSectionTest.Factory());
    register(new UiWidgetCollapsableSectionTest.Factory());
    register(new UiWidgetSlotTest.Factory());
    register(new UiWidgetTabTest.Factory());
    // field
    register(new UiWidgetTextFieldTest.Factory());
    register(new UiWidgetPasswordFieldTest.Factory());
    register(new UiWidgetTextAreaFieldTest.Factory());
    register(new UiWidgetRichTextFieldTest.Factory());
    register(new UiWidgetLongFieldTest.Factory());
    register(new UiWidgetIntegerFieldTest.Factory());
    register(new UiWidgetIntegerSliderFieldTest.Factory());
    register(new UiWidgetDoubleFieldTest.Factory());
    register(new UiWidgetDateFieldTest.Factory());
    register(new UiWidgetRadioButtonsTest.Factory());
    register(new UiWidgetRadioButtonsVerticalTest.Factory());
    register(new UiWidgetComboBoxTest.Factory());
    // panels
    register(new UiWidgetTabPanelTest.Factory());
    register(new UiWidgetHorizontalPanelTest.Factory());
    register(new UiWidgetVerticalPanelTest.Factory());
    register(new UiWidgetGridPanelTest.Factory());
    register(new UiWidgetGridRowTest.Factory());
    register(new UiWidgetGridCellTest.Factory());
    register(new UiWidgetBorderPanelTest.Factory());
    // TODO: ButtonPanel, Toolbar, ButtonGroup
    // menu
    register(new UiWidgetMenuBarTest.Factory());
    register(new UiWidgetMenuTest.Factory());
    register(new UiWidgetMenuItemClickableTest.Factory());
    register(new UiWidgetMenuItemSeparatorTest.Factory());
    // complex
    register(new UiWidgetTreeTest.Factory());
    // window
    register(new UiWidgetPopupTest.Factory());
  }

  /**
   * @return a new instance of {@link UiWidgetMainWindow}.
   */
  @Override
  protected UiWidgetMainWindow createMainWindow() {

    UiContext context = getContext();
    NlsNullPointerException.checkNotNull(UiContext.class, context);
    UiWidgetMainWindow window = new UiWidgetMainWindowTest(context);
    window.setVisible(true);
    return window;
  }
}
