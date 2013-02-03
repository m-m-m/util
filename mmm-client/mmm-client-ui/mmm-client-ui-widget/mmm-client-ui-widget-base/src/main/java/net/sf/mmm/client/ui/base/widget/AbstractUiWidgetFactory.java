/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetFactory;
import net.sf.mmm.client.ui.api.widget.UiWidgetReal;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.util.component.base.AbstractComponent;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.nls.api.NlsClassCastException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This is the abstract base implementation of {@link net.sf.mmm.client.ui.api.UiContext} using
 * {@link UiSingleWidgetFactory}. This is helpful for implementations that can NOT use reflection.
 * Implementations extending this {@link Class} need to {@link #register(UiSingleWidgetFactoryReal) register}
 * instances of {@link UiSingleWidgetFactory} for all supported types of {@link UiWidget}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetFactory extends AbstractComponent implements UiWidgetFactory {

  /** @see #create(Class) */
  private final Map<Class<? extends UiWidgetReal>, UiSingleWidgetFactoryReal<?>> interface2subFactoryMap;

  /** @see #getContext() */
  private AbstractUiContext context;

  /**
   * The constructor.
   */
  public AbstractUiWidgetFactory() {

    super();
    this.interface2subFactoryMap = new HashMap<Class<? extends UiWidgetReal>, UiSingleWidgetFactoryReal<?>>();
  }

  /**
   * @return the context
   */
  public AbstractUiContext getContext() {

    return this.context;
  }

  /**
   * @param context is the context to set
   */
  @Inject
  public void setContext(AbstractUiContext context) {

    getInitializationState().requireNotInitilized();
    this.context = context;
  }

  /**
   * This method registers the given {@link UiSingleWidgetFactoryReal} as sub-factory of this factory.
   * 
   * @param subFactory is the {@link UiSingleWidgetFactoryReal} to register.
   */
  protected void register(UiSingleWidgetFactoryReal<?> subFactory) {

    UiSingleWidgetFactoryReal<?> oldFactory = this.interface2subFactoryMap.put(subFactory.getWidgetInterface(),
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
  public <WIDGET extends UiWidgetReal> WIDGET create(Class<WIDGET> widgetInterface) {

    UiSingleWidgetFactoryReal<?> subFactory = this.interface2subFactoryMap.get(widgetInterface);
    if (subFactory == null) {
      throw new ObjectNotFoundException(UiSingleWidgetFactoryReal.class, widgetInterface);
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
