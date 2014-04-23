/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base;

import javax.inject.Inject;

import net.sf.mmm.client.ui.api.widget.UiWidgetFactory;
import net.sf.mmm.client.ui.api.widget.factory.UiWidgetFactoryDatatype;
import net.sf.mmm.client.ui.api.widget.factory.UiWidgetFactoryNative;
import net.sf.mmm.client.ui.base.binding.UiAccessKeyBinding;
import net.sf.mmm.client.ui.base.binding.UiDataBindingFactoryImpl;
import net.sf.mmm.client.ui.base.widget.factory.UiWidgetFactoryDatatypeSimple;
import net.sf.mmm.client.ui.base.widget.factory.UiWidgetFactoryImpl;
import net.sf.mmm.util.component.api.ResourceMissingException;
import net.sf.mmm.util.lang.base.DatatypeDetectorImpl;

/**
 * This class extends {@link AbstractUiContext} with an initialization that falls back to the default
 * implementation of arbitrary components.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiContextImpl extends AbstractUiContext {

  /** @see #getWidgetFactoryNative() */
  private UiWidgetFactoryNative widgetFactoryNative;

  /** @see #getWidgetFactoryDatatype() */
  private UiWidgetFactoryDatatype widgetFactoryDatatype;

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
      if (this.widgetFactoryNative == null) {
        throw new ResourceMissingException(UiWidgetFactory.class.getSimpleName());
      }
      UiWidgetFactoryImpl impl = new UiWidgetFactoryImpl();
      impl.setContext(this);
      impl.setWidgetFactoryNative(this.widgetFactoryNative);
      if (this.widgetFactoryDatatype == null) {
        UiWidgetFactoryDatatypeSimple factoryDatatype = new UiWidgetFactoryDatatypeSimple();
        factoryDatatype.setContext(this);
        factoryDatatype.initialize();
        this.widgetFactoryDatatype = factoryDatatype;
      }
      impl.setWidgetFactoryDatatype(this.widgetFactoryDatatype);
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
    if (getDatatypeDetector() == null) {
      setDatatypeDetector(new DatatypeDetectorImpl());
    }
  }

  /**
   * @return the instance of {@link UiWidgetFactoryNative}.
   */
  public UiWidgetFactoryNative getWidgetFactoryNative() {

    return this.widgetFactoryNative;
  }

  /**
   * @param widgetFactoryNative is the {@link UiWidgetFactoryNative} to {@link Inject}.
   */
  public void setWidgetFactoryNative(UiWidgetFactoryNative widgetFactoryNative) {

    // see doInitialize()
    getInitializationState().requireNotInitilized();
    this.widgetFactoryNative = widgetFactoryNative;
  }

  /**
   * @return the instance of {@link UiWidgetFactoryDatatype}.
   */
  public UiWidgetFactoryDatatype getWidgetFactoryDatatype() {

    return this.widgetFactoryDatatype;
  }

  /**
   * @param widgetFactoryDatatype is the {@link UiWidgetFactoryDatatype} to {@link Inject}.
   */
  @Inject
  public void setWidgetFactoryDatatype(UiWidgetFactoryDatatype widgetFactoryDatatype) {

    getInitializationState().requireNotInitilized();
    this.widgetFactoryDatatype = widgetFactoryDatatype;
  }

}
