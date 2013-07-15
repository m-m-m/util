/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test;

import net.sf.mmm.client.ui.api.UiContext;

import org.junit.Assert;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class AbstractUiTest extends Assert {

  /**
   * @return the {@link UiContext}.
   */
  protected UiContext getContext() {

    UiContextTest context = new UiContextTest();
    UiWidgetFactoryDatatypeTest impl = new UiWidgetFactoryDatatypeTest();
    impl.setContext(context);
    impl.initialize();
    context.setWidgetFactoryDatatype(impl);
    context.setDatatypeDetector(new DatatypeDetectorTest());
    context.initialize();
    return context;
  }

}
