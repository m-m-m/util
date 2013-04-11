/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetNative;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.factory.UiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.api.widget.factory.UiWidgetFactoryNative;
import net.sf.mmm.util.component.base.AbstractComponent;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.nls.api.NlsClassCastException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This is the abstract base implementation of {@link net.sf.mmm.client.ui.api.UiContext} using
 * {@link UiSingleWidgetFactoryNative}. This is helpful for implementations that can NOT use reflection.
 * Implementations extending this {@link Class} need to {@link #register(UiSingleWidgetFactoryNative)
 * register} instances of {@link UiSingleWidgetFactoryNative} for all supported types of {@link UiWidget}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetFactoryNative extends AbstractComponent implements UiWidgetFactoryNative {

  /** @see #create(Class) */
  private final Map<Class<? extends UiWidgetNative>, UiSingleWidgetFactoryNative<?>> interface2subFactoryMap;

  /** @see #getContext() */
  private UiContext context;

  /**
   * The constructor.
   */
  public AbstractUiWidgetFactoryNative() {

    super();
    this.interface2subFactoryMap = new HashMap<Class<? extends UiWidgetNative>, UiSingleWidgetFactoryNative<?>>();
  }

  /**
   * @return the instance of {@link UiContext}.
   */
  public UiContext getContext() {

    return this.context;
  }

  /**
   * @param context is the instance of {@link UiContext} to {@link Inject}
   */
  @Inject
  public void setContext(UiContext context) {

    getInitializationState().requireNotInitilized();
    this.context = context;
  }

  /**
   * This method registers the given {@link UiSingleWidgetFactoryNative} as sub-factory of this factory.
   * 
   * @param subFactory is the {@link UiSingleWidgetFactoryNative} to register.
   */
  protected void register(UiSingleWidgetFactoryNative<?> subFactory) {

    UiSingleWidgetFactoryNative<?> oldFactory = this.interface2subFactoryMap.put(subFactory.getWidgetInterface(),
        subFactory);
    if (oldFactory != null) {
      throw new DuplicateObjectException(subFactory, subFactory.getWidgetInterface(), oldFactory);
    }
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public <WIDGET extends UiWidgetNative> WIDGET create(Class<WIDGET> widgetInterface) {

    UiSingleWidgetFactoryNative<?> subFactory = this.interface2subFactoryMap.get(widgetInterface);
    if (subFactory == null) {
      throw new ObjectNotFoundException(UiSingleWidgetFactoryNative.class, widgetInterface);
    }
    UiWidget widget = subFactory.create(this.context);
    NlsNullPointerException.checkNotNull(UiWidget.class, widget);
    try {
      // widgetInterface.cast is not GWT compatible
      return (WIDGET) widget;
    } catch (ClassCastException e) {
      throw new NlsClassCastException(widget, widgetInterface);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object getNativeWidget(UiWidgetRegular widget) {

    NlsNullPointerException.checkNotNull(UiWidgetRegular.class, widget);
    AbstractUiWidget<?> abstractWidget = (AbstractUiWidget<?>) widget;
    Object nativeWidget = abstractWidget.getWidgetAdapter().getToplevelWidget();
    return nativeWidget;
  }

}
