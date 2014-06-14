/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget;

import javax.inject.Named;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.factory.UiWidgetFactoryNative;
import net.sf.mmm.client.ui.api.widget.window.UiWidgetMainWindow;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetFactoryNative;
import net.sf.mmm.client.ui.impl.test.widget.complex.UiWidgetTreeTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.core.UiWidgetButtonTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.core.UiWidgetCollapsableSectionTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.core.UiWidgetImageTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.core.UiWidgetLabelTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.core.UiWidgetLinkTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.core.UiWidgetSectionTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.core.UiWidgetSlotTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.core.UiWidgetTabTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.field.UiWidgetCheckboxFieldTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.field.UiWidgetCodeAreaFieldTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.field.UiWidgetColorFieldTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.field.UiWidgetComboboxFieldTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.field.UiWidgetDateFieldTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.field.UiWidgetDoubleFieldTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.field.UiWidgetIntegerFieldTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.field.UiWidgetIntegerSliderFieldTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.field.UiWidgetLongFieldTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.field.UiWidgetPasswordFieldTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.field.UiWidgetRadioButtonsTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.field.UiWidgetRadioButtonsVerticalFieldTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.field.UiWidgetRichTextFieldTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.field.UiWidgetTextAreaFieldTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.field.UiWidgetTextFieldTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.field.UiWidgetTimeFieldTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.menu.UiWidgetMenuBarTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.menu.UiWidgetMenuItemClickableTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.menu.UiWidgetMenuItemSeparatorTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.menu.UiWidgetMenuTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.panel.UiWidgetBorderPanelTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.panel.UiWidgetGridCellTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.panel.UiWidgetGridPanelTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.panel.UiWidgetGridRowTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.panel.UiWidgetHorizontalPanelTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.panel.UiWidgetTabPanelTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.panel.UiWidgetVerticalPanelTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.window.UiWidgetMainWindowTestImpl;
import net.sf.mmm.client.ui.impl.test.widget.window.UiWidgetPopupTestImpl;
import net.sf.mmm.util.exception.api.NlsNullPointerException;

/**
 * This is the implementation of the {@link net.sf.mmm.client.ui.api.UiContext} for testing without a native
 * toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Named(UiWidgetFactoryNative.CDI_NAME)
public class UiWidgetFactoryNativeTestImpl extends AbstractUiWidgetFactoryNative {

  /**
   * The constructor.
   */
  public UiWidgetFactoryNativeTestImpl() {

    super();
    // core
    register(new UiWidgetImageTestImpl.Factory());
    register(new UiWidgetButtonTestImpl.Factory());
    register(new UiWidgetLabelTestImpl.Factory());
    register(new UiWidgetLinkTestImpl.Factory());
    register(new UiWidgetSectionTestImpl.Factory());
    register(new UiWidgetCollapsableSectionTestImpl.Factory());
    register(new UiWidgetSlotTestImpl.Factory());
    register(new UiWidgetTabTestImpl.Factory());
    // field
    register(new UiWidgetTextFieldTestImpl.Factory());
    register(new UiWidgetPasswordFieldTestImpl.Factory());
    register(new UiWidgetTextAreaFieldTestImpl.Factory());
    register(new UiWidgetCodeAreaFieldTestImpl.Factory());
    register(new UiWidgetCheckboxFieldTestImpl.Factory());
    register(new UiWidgetRichTextFieldTestImpl.Factory());
    register(new UiWidgetLongFieldTestImpl.Factory());
    register(new UiWidgetIntegerFieldTestImpl.Factory());
    register(new UiWidgetIntegerSliderFieldTestImpl.Factory());
    register(new UiWidgetDoubleFieldTestImpl.Factory());
    register(new UiWidgetDateFieldTestImpl.Factory());
    register(new UiWidgetTimeFieldTestImpl.Factory());
    register(new UiWidgetRadioButtonsTestImpl.Factory());
    register(new UiWidgetRadioButtonsVerticalFieldTestImpl.Factory());
    register(new UiWidgetComboboxFieldTestImpl.Factory());
    register(new UiWidgetColorFieldTestImpl.Factory());
    // panels
    register(new UiWidgetTabPanelTestImpl.Factory());
    register(new UiWidgetHorizontalPanelTestImpl.Factory());
    register(new UiWidgetVerticalPanelTestImpl.Factory());
    register(new UiWidgetGridPanelTestImpl.Factory());
    register(new UiWidgetGridRowTestImpl.Factory());
    register(new UiWidgetGridCellTestImpl.Factory());
    register(new UiWidgetBorderPanelTestImpl.Factory());
    // TODO: ButtonPanel, Toolbar, ButtonGroup
    // menu
    register(new UiWidgetMenuBarTestImpl.Factory());
    register(new UiWidgetMenuTestImpl.Factory());
    register(new UiWidgetMenuItemClickableTestImpl.Factory());
    register(new UiWidgetMenuItemSeparatorTestImpl.Factory());
    // complex
    register(new UiWidgetTreeTestImpl.Factory());
    // window
    register(new UiWidgetPopupTestImpl.Factory());
  }

  /**
   * @return a new instance of {@link UiWidgetMainWindow}.
   */
  @Override
  protected UiWidgetMainWindow createMainWindow() {

    UiContext context = getContext();
    NlsNullPointerException.checkNotNull(UiContext.class, context);
    UiWidgetMainWindow window = new UiWidgetMainWindowTestImpl(context);
    window.setVisible(true);
    return window;
  }
}
