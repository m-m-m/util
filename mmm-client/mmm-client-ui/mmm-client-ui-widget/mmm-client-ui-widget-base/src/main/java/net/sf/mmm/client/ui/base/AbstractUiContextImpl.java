/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base;

import net.sf.mmm.client.ui.api.widget.UiWidgetFactory;
import net.sf.mmm.client.ui.api.widget.factory.UiWidgetFactoryNative;
import net.sf.mmm.client.ui.base.binding.UiAccessKeyBinding;
import net.sf.mmm.client.ui.base.binding.UiDataBindingFactoryImpl;
import net.sf.mmm.client.ui.base.widget.UiConfigurationDefault;
import net.sf.mmm.client.ui.base.widget.factory.UiWidgetFactoryImpl;
import net.sf.mmm.util.component.api.ResourceMissingException;

/**
 * This class extends {@link AbstractUiContext} with an initialization that falls back to the default
 * implementation of arbitrary components.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiContextImpl extends AbstractUiContext {

  /**
   * The constructor.
   */
  public AbstractUiContextImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (getConfiguration() == null) {
      setConfiguration(new UiConfigurationDefault());
    }

    if (getModeChanger() == null) {
      UiModeChangerImpl uiModeChangerImpl = new UiModeChangerImpl();
      setModeChanger(uiModeChangerImpl);
    }

    if (getWidgetFactory() == null) {
      UiWidgetFactoryNative widgetFactoryNative = getWidgetFactoryNative();
      if (widgetFactoryNative == null) {
        throw new ResourceMissingException(UiWidgetFactory.class.getSimpleName());
      }
      UiWidgetFactoryImpl impl = new UiWidgetFactoryImpl();
      impl.setContext(this);
      impl.setWidgetFactoryNative(widgetFactoryNative);
      impl.initialize();
      setWidgetFactory(impl);
    }

    if (getDataBindingFactory() == null) {
      UiDataBindingFactoryImpl impl = new UiDataBindingFactoryImpl();
      impl.initialize();
      setDataBindingFactory(impl);
    }

    if (getContainer() == null) {
      ComponentContainerContextFallback impl = new ComponentContainerContextFallback(this);
      impl.initialize();
      setContainer(impl);
    }

    if (getAccessKeyBinding() == null) {
      UiAccessKeyBinding impl = new UiAccessKeyBindingImpl();
      setAccessKeyBinding(impl);
    }
  }

}
