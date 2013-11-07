/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.factory.UiSingleWidgetFactoryDatatype;
import net.sf.mmm.client.ui.api.widget.factory.UiWidgetFactoryDatatype;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetComboboxField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetDateField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetDateTimeField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetDoubleField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetIntegerField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetLongField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetRadioButtonsField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetTextField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetTimeField;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryDatatype;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.lang.api.Formatter;
import net.sf.mmm.util.lang.base.BooleanFormatter;
import net.sf.mmm.util.lang.base.FormatterToString;
import net.sf.mmm.util.nls.api.DuplicateObjectException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;
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
    register(new UiSingleWidgetFactoryDatatypeLocalDate());
    register(new UiSingleWidgetFactoryDatatypeLocalTime());
    register(new UiSingleWidgetFactoryDatatypeInstant());
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

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryDatatype context} for the datatype
   * {@link String}.
   */
  public static class UiSingleWidgetFactoryDatatypeString extends AbstractUiSingleWidgetFactoryDatatype<String> {

    /**
     * The constructor.
     */
    public UiSingleWidgetFactoryDatatypeString() {

      super(String.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetField<String> create(UiContext context) {

      return context.getWidgetFactory().create(UiWidgetTextField.class);
    }
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryDatatype context} for the datatype
   * {@link Long}.
   */
  public static class UiSingleWidgetFactoryDatatypeLong extends AbstractUiSingleWidgetFactoryDatatype<Long> {

    /**
     * The constructor.
     */
    public UiSingleWidgetFactoryDatatypeLong() {

      super(Long.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetField<Long> create(UiContext context) {

      return context.getWidgetFactory().create(UiWidgetLongField.class);
    }
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryDatatype context} for the datatype
   * {@link Integer}.
   */
  public static class UiSingleWidgetFactoryDatatypeInteger extends AbstractUiSingleWidgetFactoryDatatype<Integer> {

    /**
     * The constructor.
     */
    public UiSingleWidgetFactoryDatatypeInteger() {

      super(Integer.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetField<Integer> create(UiContext context) {

      return context.getWidgetFactory().create(UiWidgetIntegerField.class);
    }
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryDatatype context} for the datatype
   * {@link Double}.
   */
  public static class UiSingleWidgetFactoryDatatypeDouble extends AbstractUiSingleWidgetFactoryDatatype<Double> {

    /**
     * The constructor.
     */
    public UiSingleWidgetFactoryDatatypeDouble() {

      super(Double.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetField<Double> create(UiContext context) {

      return context.getWidgetFactory().create(UiWidgetDoubleField.class);
    }
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryDatatype context} for the datatype
   * {@link Long}.
   */
  public static class UiSingleWidgetFactoryDatatypeBoolean extends AbstractUiSingleWidgetFactoryDatatype<Boolean> {

    /**
     * The constructor.
     */
    public UiSingleWidgetFactoryDatatypeBoolean() {

      super(Boolean.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetField<Boolean> create(UiContext context) {

      UiWidgetRadioButtonsField<Boolean> radioButtons = context.getWidgetFactory().create(
          UiWidgetRadioButtonsField.class);
      radioButtons.setFormatter(BooleanFormatter.getInstance());
      radioButtons.setOptions(Arrays.asList(Boolean.TRUE, Boolean.FALSE));
      return radioButtons;
    }
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryDatatype context} for the datatype
   * {@link LocalDate}.
   */
  public static class UiSingleWidgetFactoryDatatypeLocalDate extends AbstractUiSingleWidgetFactoryDatatype<LocalDate> {

    /**
     * The constructor.
     */
    public UiSingleWidgetFactoryDatatypeLocalDate() {

      super(LocalDate.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetField<LocalDate> create(UiContext context) {

      UiWidgetDateField widget = context.getWidgetFactory().create(UiWidgetDateField.class);
      return widget;
    }
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryDatatype context} for the datatype
   * {@link LocalDate}.
   */
  public static class UiSingleWidgetFactoryDatatypeLocalTime extends AbstractUiSingleWidgetFactoryDatatype<LocalTime> {

    /**
     * The constructor.
     */
    public UiSingleWidgetFactoryDatatypeLocalTime() {

      super(LocalTime.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetField<LocalTime> create(UiContext context) {

      UiWidgetTimeField widget = context.getWidgetFactory().create(UiWidgetTimeField.class);
      return widget;
    }
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryDatatype context} for the datatype
   * {@link LocalDate}.
   */
  public static class UiSingleWidgetFactoryDatatypeInstant extends AbstractUiSingleWidgetFactoryDatatype<Instant> {

    /**
     * The constructor.
     */
    public UiSingleWidgetFactoryDatatypeInstant() {

      super(Instant.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetField<Instant> create(UiContext context) {

      UiWidgetDateTimeField widget = context.getWidgetFactory().create(UiWidgetDateTimeField.class);
      return widget;
    }
  }

  /**
   * This inner class is the abstract {@link AbstractUiSingleWidgetFactoryDatatype context} for {@link Enum}
   * datatypes.
   * 
   * @param <E> is the generic type of the {@link Enum}.
   */
  public static class UiSingleWidgetFactoryDatatypeEnum<E extends Enum<E>> extends
      AbstractUiSingleWidgetFactoryDatatype<E> {

    /** @see #create(UiContext) */
    private final Formatter<E> formatter;

    /**
     * The constructor.
     * 
     * @param enumType is the type of the {@link Enum}.
     */
    public UiSingleWidgetFactoryDatatypeEnum(Class<E> enumType) {

      this(enumType, FormatterToString.<E> getInstance());
    }

    /**
     * The constructor.
     * 
     * @param enumType is the type of the {@link Enum}.
     * @param formatter is the {@link Formatter} used to get the display titles of the {@link Enum} constants.
     */
    public UiSingleWidgetFactoryDatatypeEnum(Class<E> enumType, Formatter<E> formatter) {

      super(enumType);
      this.formatter = formatter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetField<E> create(UiContext context) {

      UiWidgetComboboxField<E> radioButtons = context.getWidgetFactory().create(UiWidgetComboboxField.class);
      radioButtons.setFormatter(this.formatter);
      radioButtons.setOptions(getOptionList());
      return radioButtons;
    }

    /**
     * @return the {@link List} of enum-options. Override to filter options or to add <code>null</code>.
     */
    protected List<E> getOptionList() {

      return Arrays.asList(getDatatype().getEnumConstants());
    }
  }

}
