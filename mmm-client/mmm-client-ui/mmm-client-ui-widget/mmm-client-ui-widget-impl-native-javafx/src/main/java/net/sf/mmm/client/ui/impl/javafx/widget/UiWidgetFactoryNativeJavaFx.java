/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.javafx.widget;

import javafx.scene.Node;

import javax.inject.Named;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.factory.UiWidgetFactoryNative;
import net.sf.mmm.client.ui.api.widget.window.UiWidgetMainWindow;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetFactoryNative;
import net.sf.mmm.client.ui.impl.javafx.widget.core.UiWidgetButtonJavaFx;
import net.sf.mmm.client.ui.impl.javafx.widget.core.UiWidgetImageJavaFx;
import net.sf.mmm.client.ui.impl.javafx.widget.window.UiWidgetMainWindowJavaFx;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This is the implementation of the {@link UiWidgetFactoryNative} for native JavaFx widgets.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Named(UiWidgetFactoryNative.CDI_NAME)
public class UiWidgetFactoryNativeJavaFx extends AbstractUiWidgetFactoryNative {

  /** @see #getMainWindow() */
  private UiWidgetMainWindow mainWindow;

  /**
   * The constructor.
   */
  public UiWidgetFactoryNativeJavaFx() {

    super();
    // core
    register(new UiWidgetImageJavaFx.Factory());
    register(new UiWidgetButtonJavaFx.Factory());
    // register(new UiWidgetLabelJavaFx.Factory());
    // field
    // register(new UiWidgetTextFieldJavaFx.Factory());
    // register(new UiWidgetPasswordFieldJavaFx.Factory());
    // register(new UiWidgetTextAreaJavaFx.Factory());
    // register(new UiWidgetRichTextAreaJavaFx.Factory());
    // register(new UiWidgetLongFieldJavaFx.Factory());
    // register(new UiWidgetIntegerFieldJavaFx.Factory());
    // register(new UiWidgetIntegerRangeFieldJavaFx.Factory());
    // register(new UiWidgetDoubleFieldJavaFx.Factory());
    // register(new UiWidgetDateFieldJavaFx.Factory());
    // register(new UiWidgetRadioButtonsJavaFx.Factory());
    // register(new UiWidgetRadioButtonsVerticalJavaFx.Factory());
    // register(new UiWidgetComboBoxJavaFx.Factory());
    // panels
    // register(new UiWidgetTabJavaFx.Factory());
    // register(new UiWidgetTabPanelJavaFx.Factory());
    // register(new UiWidgetHorizontalPanelJavaFx.Factory());
    // register(new UiWidgetVerticalPanelJavaFx.Factory());
    // register(new UiWidgetGridPanelJavaFx.Factory());
    // register(new UiWidgetGridRowJavaFx.Factory());
    // register(new UiWidgetGridCellJavaFx.Factory());
    // register(new UiWidgetBorderPanelJavaFx.Factory());
    // register(new UiWidgetCustomButtonPanel.Factory());
    // menu
    // register(new UiWidgetMenuBarJavaFx.Factory());
    // register(new UiWidgetMenuJavaFx.Factory());
    // register(new UiWidgetMenuItemClickableJavaFx.Factory());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetMainWindow getMainWindow() {

    if (this.mainWindow == null) {
      this.mainWindow = createMainWindow();
    }
    return this.mainWindow;
  }

  /**
   * @return a new instance of {@link UiWidgetMainWindow}.
   */
  private UiWidgetMainWindow createMainWindow() {

    AbstractUiContext context = getContext();
    NlsNullPointerException.checkNotNull(UiContext.class, context);
    UiWidgetMainWindowJavaFx window = new UiWidgetMainWindowJavaFx(context);
    // TODO hohwille
    // window.initialize();
    return window;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Node getNativeWidget(UiWidgetRegular widget) {

    return (Node) super.getNativeWidget(widget);
  }
}
