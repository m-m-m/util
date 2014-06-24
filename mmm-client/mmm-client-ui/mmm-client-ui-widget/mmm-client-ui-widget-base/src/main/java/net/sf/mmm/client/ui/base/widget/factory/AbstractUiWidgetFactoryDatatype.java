/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.factory;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.factory.UiSingleWidgetFactoryDatatype;
import net.sf.mmm.client.ui.api.widget.factory.UiWidgetFactoryDatatype;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetField;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.exception.api.DuplicateObjectException;
import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.exception.api.ObjectNotFoundException;
import net.sf.mmm.util.reflect.api.ReflectionUtilLimited;
import net.sf.mmm.util.reflect.base.ReflectionUtilLimitedImpl;

/**
 * This is the abstract base implementation of {@link UiWidgetFactoryDatatype}. It already contains the
 * implementations for the {@link #registerStandardDatatypes() standard datatypes}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetFactoryDatatype extends AbstractLoggableComponent implements
    UiWidgetFactoryDatatype {

  /** @see #createForDatatype(Class) */
  private final Map<Class<?>, UiSingleWidgetFactoryDatatype<?>> datatype2subFactoryMap;

  /** @see #getContext() */
  private UiContext context;

  /** @see #getReflectionUtil() */
  private ReflectionUtilLimited reflectionUtil;

  /**
   * The constructor.
   */
  public AbstractUiWidgetFactoryDatatype() {

    super();
    this.datatype2subFactoryMap = new HashMap<Class<?>, UiSingleWidgetFactoryDatatype<?>>();
  }

  /**
   * @return the instance of {@link ReflectionUtilLimited}.
   */
  protected ReflectionUtilLimited getReflectionUtil() {

    return this.reflectionUtil;
  }

  /**
   * @param reflectionUtil is the instance of {@link ReflectionUtilLimited} to {@link Inject}.
   */
  @Inject
  public void setReflectionUtil(ReflectionUtilLimited reflectionUtil) {

    this.reflectionUtil = reflectionUtil;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.reflectionUtil == null) {
      ReflectionUtilLimitedImpl impl = new ReflectionUtilLimitedImpl();
      impl.initialize();
      this.reflectionUtil = impl;
    }
  }

  /**
   * This method registers the given {@link UiSingleWidgetFactoryDatatype} as sub-context of this context.
   *
   * @param subFactory is the {@link UiSingleWidgetFactoryDatatype} to register.
   */
  protected void register(UiSingleWidgetFactoryDatatype<?> subFactory) {

    UiSingleWidgetFactoryDatatype<?> oldFactory = this.datatype2subFactoryMap.put(subFactory.getDatatype(), subFactory);
    if (oldFactory != null) {
      throw new DuplicateObjectException(subFactory, subFactory.getDatatype(), oldFactory);
    }
  }

  /**
   * This method {@link #register(UiSingleWidgetFactoryDatatype) registers} the
   * {@link UiWidgetFactoryDatatype} instances for the java standard datatypes ({@link String}, {@link Long},
   * etc.).
   */
  protected void registerStandardDatatypes() {

    register(new UiSingleWidgetFactoryDatatypeString());
    register(new UiSingleWidgetFactoryDatatypeInteger());
    register(new UiSingleWidgetFactoryDatatypeLong());
    register(new UiSingleWidgetFactoryDatatypeDouble());
    register(new UiSingleWidgetFactoryDatatypeBoolean());
    register(new UiSingleWidgetFactoryDatatypeDate());
    // register(new UiSingleWidgetFactoryDatatypeBigDecimal());
    // register(new UiSingleWidgetFactoryDatatypeLocalDate());
    // register(new UiSingleWidgetFactoryDatatypeLocalTime());
    // register(new UiSingleWidgetFactoryDatatypeInstant());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <VALUE> UiWidgetField<VALUE> createForDatatype(Class<VALUE> datatype) {

    Class<?> type;
    if (datatype.isPrimitive()) {
      type = this.reflectionUtil.getNonPrimitiveType(datatype);
    } else {
      type = datatype;
    }
    @SuppressWarnings("unchecked")
    UiSingleWidgetFactoryDatatype<VALUE> subFactory = (UiSingleWidgetFactoryDatatype<VALUE>) this.datatype2subFactoryMap
        .get(type);
    if (subFactory == null) {
      throw new ObjectNotFoundException(UiSingleWidgetFactoryDatatype.class, type);
    }
    UiWidgetField<VALUE> widget = subFactory.create(this.context);
    NlsNullPointerException.checkNotNull(UiWidget.class, widget);
    return widget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDatatypeSupported(Class<?> type) {

    return this.datatype2subFactoryMap.containsKey(type);
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

}
