/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt;

import net.sf.mmm.client.ui.api.UiDispatcher;
import net.sf.mmm.client.ui.api.UiDisplay;
import net.sf.mmm.client.ui.base.UiContextImpl;
import net.sf.mmm.client.ui.impl.gwt.widget.UiWidgetFactoryNativeGwt;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.api.UiContext} for GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiContextGwt extends UiContextImpl {

  /**
   * The constructor.
   */
  public UiContextGwt() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    if (getWidgetFactoryNative() == null) {
      UiWidgetFactoryNativeGwt factoryGwt = new UiWidgetFactoryNativeGwt();
      factoryGwt.setContext(this);
      factoryGwt.initialize();
      setWidgetFactoryNative(factoryGwt);
    }

    super.doInitialize();

    UiDisplay display = getDisplay();
    if (display == null) {
      display = new UiDisplayGwt();
      setDisplay(display);
    }

    UiDispatcher dispatcher = getDispatcher();
    if (dispatcher == null) {
      dispatcher = new UiDispatcherGwt();
      setDispatcher(dispatcher);
    }

  }

}
