/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.factory;

import javax.inject.Inject;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidgetFactory;
import net.sf.mmm.client.ui.api.widget.UiWidgetReal;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.factory.UiWidgetFactoryDatatype;
import net.sf.mmm.client.ui.api.widget.factory.UiWidgetFactoryNative;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetField;
import net.sf.mmm.client.ui.api.widget.window.UiWidgetMainWindow;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.util.component.api.ResourceMissingException;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;

/**
 * This is the implementation of {@link UiWidgetFactory}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetFactory extends AbstractLoggableComponent implements UiWidgetFactory {

  /** @see #getContext() */
  private UiContext context;

  /** @see #setWidgetFactoryNative(UiWidgetFactoryNative) */
  private UiWidgetFactoryNative widgetFactoryNative;

  /** @see #setWidgetFactoryDatatype(UiWidgetFactoryDatatype) */
  private UiWidgetFactoryDatatype widgetFactoryDatatype;

  /**
   * The constructor.
   */
  public AbstractUiWidgetFactory() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.context == null) {
      throw new ResourceMissingException(UiContext.class.getSimpleName());
    }
    if (this.widgetFactoryNative == null) {
      this.widgetFactoryNative = this.context.getContainer().get(UiWidgetFactoryNative.class);
      if (this.widgetFactoryNative == null) {
        throw new ResourceMissingException(UiWidgetFactoryNative.class.getSimpleName());
      }
    }
    if (this.widgetFactoryDatatype == null) {
      this.widgetFactoryDatatype = this.context.getContainer().get(UiWidgetFactoryDatatype.class);
      if (this.widgetFactoryDatatype == null) {
        UiWidgetFactoryDatatypeSimple impl = new UiWidgetFactoryDatatypeSimple();
        impl.setContext((AbstractUiContext) this.context);
        impl.initialize();
        this.widgetFactoryDatatype = impl;
      }
    }
  }

  /**
   * @return the {@link UiContext} instance.
   */
  protected UiContext getContext() {

    return this.context;
  }

  /**
   * @param context is the {@link UiContext} to {@link Inject}.
   */
  @Inject
  public void setContext(UiContext context) {

    getInitializationState().requireNotInitilized();
    this.context = context;
  }

  /**
   * @param widgetFactoryDatatype is the {@link UiWidgetFactoryDatatype} to {@link Inject}.
   */
  @Inject
  public void setWidgetFactoryDatatype(UiWidgetFactoryDatatype widgetFactoryDatatype) {

    getInitializationState().requireNotInitilized();
    this.widgetFactoryDatatype = widgetFactoryDatatype;
  }

  /**
   * @param widgetFactoryNative is the {@link UiWidgetFactoryNative} to {@link Inject}.
   */
  @Inject
  public void setWidgetFactoryNative(UiWidgetFactoryNative widgetFactoryNative) {

    this.widgetFactoryNative = widgetFactoryNative;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetMainWindow getMainWindow() {

    return this.widgetFactoryNative.getMainWindow();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <WIDGET extends UiWidgetReal> WIDGET create(Class<WIDGET> widgetInterface) {

    return this.widgetFactoryNative.create(widgetInterface);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getNativeWidget(UiWidgetRegular widget) {

    return this.widgetFactoryNative.getNativeWidget(widget);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <VALUE> UiWidgetField<VALUE> createForDatatype(Class<VALUE> datatype) {

    return this.widgetFactoryDatatype.createForDatatype(datatype);
  }

}
