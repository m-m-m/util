/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.ui.toolkit.api.widget.UiWidget;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetReal;
import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetField;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.nls.api.NlsClassCastException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This is the abstract base implementation of {@link net.sf.mmm.ui.toolkit.api.widget.UiWidgetFactory} using
 * {@link UiSingleWidgetFactory}. This is helpful for implementations that can NOT use reflection.
 * Implementations extending this {@link Class} need to {@link #register(UiSingleWidgetFactoryReal) register}
 * instances of {@link UiSingleWidgetFactory} for all supported types of {@link UiWidget}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <NATIVE_WIDGET> is the generic top-level type of the underlying
 *        {@link #getNativeWidget(net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegular) widgets}.
 */
public abstract class AbstractUiWidgetFactoryPlain<NATIVE_WIDGET> extends AbstractUiWidgetFactory<NATIVE_WIDGET> {

  /** @see #create(Class) */
  private final Map<Class<? extends UiWidgetReal>, UiSingleWidgetFactoryReal<?>> interface2subFactoryMap;

  /** @see #createForDatatype(Class) */
  private final Map<Class<?>, UiSingleWidgetFactoryDatatype<?>> datatype2subFactoryMap;

  /**
   * The constructor.
   */
  public AbstractUiWidgetFactoryPlain() {

    super();
    this.interface2subFactoryMap = new HashMap<Class<? extends UiWidgetReal>, UiSingleWidgetFactoryReal<?>>();
    this.datatype2subFactoryMap = new HashMap<Class<?>, UiSingleWidgetFactoryDatatype<?>>();
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
    UiWidget widget = subFactory.create(this);
    NlsNullPointerException.checkNotNull(UiWidget.class, widget);
    try {
      // widgetInterface.cast is not GWT compatible
      return (WIDGET) widget;
    } catch (ClassCastException e) {
      throw new NlsClassCastException(widget, widgetInterface);
    }
  }

  /**
   * This method registers the given {@link UiSingleWidgetFactoryReal} as sub-factory of this factory.
   * 
   * @param subFactory is the {@link UiSingleWidgetFactoryReal} to register.
   */
  protected void register(UiSingleWidgetFactoryDatatype<?> subFactory) {

    UiSingleWidgetFactoryDatatype<?> oldFactory = this.datatype2subFactoryMap.put(subFactory.getDatatype(), subFactory);
    if (oldFactory != null) {
      throw new DuplicateObjectException(subFactory, subFactory.getDatatype(), oldFactory);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <VALUE> UiWidgetField<VALUE> createForDatatype(Class<VALUE> datatype) {

    UiSingleWidgetFactoryDatatype<VALUE> subFactory = (UiSingleWidgetFactoryDatatype<VALUE>) this.datatype2subFactoryMap
        .get(datatype);
    if (subFactory == null) {
      throw new ObjectNotFoundException(UiSingleWidgetFactoryDatatype.class, datatype);
    }
    UiWidgetField<VALUE> widget = subFactory.create(this);
    NlsNullPointerException.checkNotNull(UiWidget.class, widget);
    return widget;
  }

}
