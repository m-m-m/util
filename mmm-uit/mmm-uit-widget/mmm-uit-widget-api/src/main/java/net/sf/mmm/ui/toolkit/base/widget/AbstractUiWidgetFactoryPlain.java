/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.ui.toolkit.api.widget.UiWidget;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetReal;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.nls.api.NlsClassCastException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This is the abstract base implementation of {@link net.sf.mmm.ui.toolkit.api.widget.UiWidgetFactory} using
 * {@link UiSingleWidgetFactory}. This is helpful for implementations that can NOT use reflection.
 * Implementations extending this {@link Class} need to {@link #register(UiSingleWidgetFactory) register}
 * instances of {@link UiSingleWidgetFactory} for all supported types of {@link UiWidget}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <NATIVE_WIDGET> is the generic top-level type of the underlying
 *        {@link #getNativeWidget(net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegular) widgets}.
 */
public abstract class AbstractUiWidgetFactoryPlain<NATIVE_WIDGET> extends AbstractUiWidgetFactory<NATIVE_WIDGET> {

  /** @see #create(Class) */
  private final Map<Class<? extends UiWidget>, UiSingleWidgetFactory<? extends UiWidget>> interface2subFactoryMap;

  /**
   * The constructor.
   */
  public AbstractUiWidgetFactoryPlain() {

    super();
    this.interface2subFactoryMap = new HashMap<Class<? extends UiWidget>, UiSingleWidgetFactory<? extends UiWidget>>();
  }

  /**
   * This method registers the given {@link UiSingleWidgetFactory} as sub-factory of this factory.
   * 
   * @param subFactory is the {@link UiSingleWidgetFactory} to register.
   */
  protected void register(UiSingleWidgetFactory<? extends UiWidget> subFactory) {

    UiSingleWidgetFactory<? extends UiWidget> oldFactory = this.interface2subFactoryMap.put(
        subFactory.getWidgetInterface(), subFactory);
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

    UiSingleWidgetFactory<? extends UiWidget> subFactory = this.interface2subFactoryMap.get(widgetInterface);
    if (subFactory == null) {
      throw new ObjectNotFoundException(UiSingleWidgetFactory.class, widgetInterface);
    }
    UiWidget widget = subFactory.create();
    NlsNullPointerException.checkNotNull(UiWidget.class, widget);
    try {
      return (WIDGET) widget;
    } catch (ClassCastException e) {
      throw new NlsClassCastException(widget, widgetInterface);
    }
  }

}
