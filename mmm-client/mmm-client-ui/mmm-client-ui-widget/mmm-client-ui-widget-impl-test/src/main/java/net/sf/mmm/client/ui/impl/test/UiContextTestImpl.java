/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test;

import javax.inject.Named;

import net.sf.mmm.client.ui.api.UiDispatcher;
import net.sf.mmm.client.ui.api.UiDisplay;
import net.sf.mmm.client.ui.api.UiPopupHelper;
import net.sf.mmm.client.ui.base.UiContextImpl;
import net.sf.mmm.client.ui.impl.test.widget.UiWidgetFactoryNativeTestImpl;
import net.sf.mmm.util.pojo.descriptor.base.AbstractPojoDescriptorBuilderFactory;
import net.sf.mmm.util.pojo.descriptor.impl.PojoDescriptorBuilderFactoryImpl;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.api.UiContext} for testing without any native
 * toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Named
public class UiContextTestImpl extends UiContextImpl {

  /**
   * The constructor.
   */
  public UiContextTestImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    if (getWidgetFactoryNative() == null) {
      UiWidgetFactoryNativeTestImpl impl = new UiWidgetFactoryNativeTestImpl();
      impl.setContext(this);
      impl.initialize();
      setWidgetFactoryNative(impl);
    }

    super.doInitialize();

    UiDisplay display = getDisplay();
    if (display == null) {
      display = new UiDisplayTestImpl();
      setDisplay(display);
    }

    UiDispatcher dispatcher = getDispatcher();
    if (dispatcher == null) {
      UiDispatcherTestImpl impl = new UiDispatcherTestImpl();
      impl.initialize();
      setDispatcher(impl);
    }

    UiPopupHelper popupHelper = getPopupHelper();
    if (popupHelper == null) {
      UiPopupHelperTestImpl impl = new UiPopupHelperTestImpl();
      impl.initialize();
      setPopupHelper(impl);
    }

    if (AbstractPojoDescriptorBuilderFactory.getInstance() == null) {
      PojoDescriptorBuilderFactoryImpl.getInstance();
    }
  }

}
