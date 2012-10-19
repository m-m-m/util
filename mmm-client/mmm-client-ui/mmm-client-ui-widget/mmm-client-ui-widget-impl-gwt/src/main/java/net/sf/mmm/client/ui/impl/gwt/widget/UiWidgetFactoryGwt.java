/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget;

import net.sf.mmm.client.ui.api.widget.UiDispatcher;
import net.sf.mmm.client.ui.api.widget.UiDisplay;
import net.sf.mmm.client.ui.api.widget.window.UiWidgetMainWindow;
import net.sf.mmm.client.ui.base.widget.AbstractUiWidgetFactoryPlain;
import net.sf.mmm.client.ui.impl.gwt.widget.core.UiWidgetButtonGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.core.UiWidgetImageGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.core.UiWidgetLabelGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.core.UiWidgetTabGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.field.UiWidgetComboBoxGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.field.UiWidgetDateFieldGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.field.UiWidgetDoubleFieldGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.field.UiWidgetIntegerFieldGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.field.UiWidgetLongFieldGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.field.UiWidgetPasswordFieldGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.field.UiWidgetRadioButtonsGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.field.UiWidgetRadioButtonsVerticalGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.field.UiWidgetRichTextAreaGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.field.UiWidgetTextAreaGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.field.UiWidgetTextFieldGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.menu.UiWidgetMenuBarGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.menu.UiWidgetMenuGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.menu.UiWidgetMenuItemClickableGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.panel.UiWidgetHorizontalPanelGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.panel.UiWidgetTabPanelGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.panel.UiWidgetVerticalPanelGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.window.UiWidgetMainWindowGwt;

import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of the {@link net.sf.mmm.client.ui.api.widget.UiWidgetFactory} for GWT widgets.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetFactoryGwt extends AbstractUiWidgetFactoryPlain<Widget> {

  /** @see #getDisplay() */
  private UiDisplay display;

  /** @see #getDispatcher() */
  private UiDispatcher dispatcher;

  /** @see #getMainWindow() */
  private UiWidgetMainWindow mainWindow;

  /**
   * The constructor.
   */
  public UiWidgetFactoryGwt() {

    super();
    // core
    register(new UiWidgetImageGwt.Factory());
    register(new UiWidgetButtonGwt.Factory());
    register(new UiWidgetLabelGwt.Factory());
    // field
    register(new UiWidgetTextFieldGwt.Factory());
    register(new UiWidgetPasswordFieldGwt.Factory());
    register(new UiWidgetTextAreaGwt.Factory());
    register(new UiWidgetRichTextAreaGwt.Factory());
    register(new UiWidgetLongFieldGwt.Factory());
    register(new UiWidgetIntegerFieldGwt.Factory());
    register(new UiWidgetDoubleFieldGwt.Factory());
    register(new UiWidgetDateFieldGwt.Factory());
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

  /**
   * {@inheritDoc}
   */
  @Override
  public UiDisplay getDisplay() {

    if (this.display == null) {
      this.display = createDisplay();
    }
    return this.display;
  }

  /**
   * @return a new instance of {@link UiDisplay}.
   */
  protected UiDisplay createDisplay() {

    return new UiDisplayGwt();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiDispatcher getDispatcher() {

    if (this.dispatcher == null) {
      this.dispatcher = createDispatcher();
    }
    return this.dispatcher;
  }

  /**
   * @return a new instance of {@link UiDispatcher}.
   */
  protected UiDispatcher createDispatcher() {

    return new UiDispatcherGwt();
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

    UiWidgetMainWindowGwt window = new UiWidgetMainWindowGwt(this);
    window.initialize();
    return window;
  }
}
