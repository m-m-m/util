/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;

import net.sf.mmm.util.collection.AdvancedClassHierarchieMap;
import net.sf.mmm.util.collection.MapFactory;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.value.api.ValueConverter;
import net.sf.mmm.util.value.api.ValueException;
import net.sf.mmm.util.value.api.ValueParseGenericException;
import net.sf.mmm.util.value.base.AbstractComposedValueConverter;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.util.value.api.ComposedValueConverter} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ComposedValueConverterImpl extends AbstractComposedValueConverter {

  /** @see #addConverter(ValueConverter) */
  private final TargetClass2ConverterMap targetClass2converterMap;

  /**
   * The constructor.
   */
  public ComposedValueConverterImpl() {

    super();
    this.targetClass2converterMap = new TargetClass2ConverterMap();
  }

  /**
   * This method registers the given <code>converter</code> to this composed
   * converter.
   * 
   * @param converter is the converter to add.
   * @return the converter with the same
   *         {@link ValueConverter#getSourceType() source-type} and
   *         {@link ValueConverter#getTargetType() target-type} that has been
   *         replaced by <code>converter</code> or <code>null</code> if no
   *         converter has been replaced.
   */
  @SuppressWarnings("unchecked")
  public ValueConverter<?, ?> addConverter(ValueConverter<?, ?> converter) {

    getInitializationState().requireNotInitilized();
    Class<?> targetType = converter.getTargetType();
    ComposedTargetTypeConverter targetConverter = this.targetClass2converterMap.get(targetType);
    if ((targetConverter == null) || !targetType.equals(targetConverter.getTargetType())) {
      targetConverter = new ComposedTargetTypeConverter(targetType);
      this.targetClass2converterMap.put(targetType, targetConverter);
    }
    return targetConverter.addConverter(converter);
  }

  /**
   * {@inheritDoc}
   */
  public Object convert(Object value, Object valueSource, GenericType<? extends Object> targetType) {

    if (value == null) {
      return null;
    }
    Log logger = getLogger();
    if (logger.isTraceEnabled()) {
      logger.trace("starting conversion of '" + value + "' from '" + value.getClass().getName()
          + "' to '" + targetType + "'");
    }
    Class<? extends Object> targetClass = targetType.getUpperBound();
    if (targetClass.isInstance(value)) {
      return value;
    }
    return convertRecursive(value, valueSource, targetType, targetClass, null);
  }

  /**
   * This method performs the
   * {@link #convert(Object, Object, GenericType) conversion} recursive.
   * 
   * @param value is the value to convert.
   * @param valueSource describes the source of the value. This may be the
   *        filename where the value was read from, an XPath where the value was
   *        located in an XML document, etc. It is used in exceptions thrown if
   *        something goes wrong. This will help to find the problem easier.
   * @param targetType is the {@link GenericType} to convert the
   *        <code>value</code> to.
   * @param currentTargetClass is the current
   *        {@link ValueConverter#getTargetType() target-type} to try.
   * @param previousConverter is the converter that has been tried last time
   *        without success. It is used to avoid trying the same converter
   *        again. Will initially be <code>null</code>.
   * @return the converted <code>value</code> or <code>null</code> if the
   *         conversion is NOT possible. The returned value has to be an
   *         {@link Class#isInstance(Object) instance} of the given
   *         <code>targetType</code>.
   */
  @SuppressWarnings("unchecked")
  protected Object convertRecursive(Object value, Object valueSource, GenericType<?> targetType,
      Class<?> currentTargetClass, ValueConverter previousConverter) {

    Log logger = getLogger();
    ValueConverter lastConverter = previousConverter;
    Class<?> currentClass = currentTargetClass;
    Object result = null;
    try {
      while (currentClass != null) {
        if (logger.isTraceEnabled()) {
          logger.trace("searching converter for target-type '" + currentClass + "'");
        }
        ValueConverter converter = this.targetClass2converterMap.get(currentClass);
        if ((converter != null) && (converter != lastConverter)
            && (converter.getTargetType().isAssignableFrom(targetType.getUpperBound()))) {
          if (logger.isTraceEnabled()) {
            StringWriter sw = new StringWriter(50);
            sw.append("trying converter for target-type '");
            sw.append(converter.getTargetType().toString());
            sw.append("'");
            if (!converter.getTargetType().equals(currentClass)) {
              sw.append(" for current-type '");
              sw.append(currentClass.toString());
              sw.append("'");
            }
            logger.trace(sw);
          }
          result = converter.convert(value, valueSource, targetType);
          if (result != null) {
            return result;
          }
          lastConverter = converter;
        }
        for (Class<?> superInterface : currentClass.getInterfaces()) {
          result = convertRecursive(value, valueSource, targetType, superInterface, lastConverter);
          if (result != null) {
            return result;
          }
        }
        currentClass = currentClass.getSuperclass();
      }
    } catch (ValueException e) {
      throw e;
    } catch (RuntimeException e) {
      throw new ValueParseGenericException(e, value, targetType, valueSource);
    }
    return null;
  }

  /**
   * This inner class is a composed converter for all {@link ValueConverter}s
   * with the same {@link ValueConverter#getTargetType() target-type}.
   * 
   * @param <TARGET> is the generic {@link #getTargetType() target-type}.
   */
  protected class ComposedTargetTypeConverter<TARGET> implements ValueConverter<Object, TARGET> {

    /** @see #getTargetType() */
    private final Class<TARGET> targetType;

    /** @see #addConverter(ValueConverter) */
    private final Map<Class<?>, ValueConverter<?, TARGET>> sourceClass2converterMap;

    /**
     * The constructor.
     * 
     * @param targetType is the {@link #getTargetType() target-type} of this
     *        converter.
     */
    public ComposedTargetTypeConverter(Class<TARGET> targetType) {

      super();
      this.sourceClass2converterMap = new HashMap<Class<?>, ValueConverter<?, TARGET>>();
      this.targetType = targetType;
    }

    /**
     * {@inheritDoc}
     */
    public Class<Object> getSourceType() {

      return Object.class;
    }

    /**
     * {@inheritDoc}
     */
    public Class<TARGET> getTargetType() {

      return this.targetType;
    }

    /**
     * This method registers the given <code>converter</code> to this composed
     * converter.
     * 
     * @param converter is the converter to add.
     * @return the converter with the same
     *         {@link ValueConverter#getSourceType() source-type} that has been
     *         replaced by <code>converter</code> or <code>null</code> if no
     *         converter has been replaced.
     */
    public ValueConverter<?, TARGET> addConverter(ValueConverter<?, TARGET> converter) {

      return this.sourceClass2converterMap.put(converter.getSourceType(), converter);
    }

    /**
     * {@inheritDoc}
     */
    public TARGET convert(Object value, Object valueSource, Class<? extends TARGET> targetClass)
        throws ValueException {

      return convert(value, valueSource, getReflectionUtil().createGenericType(targetClass));
    }

    /**
     * {@inheritDoc}
     */
    public TARGET convert(Object value, Object valueSource,
        GenericType<? extends TARGET> genericTargetType) {

      if (value == null) {
        return null;
      }
      return convertRecursive(value, valueSource, genericTargetType, value.getClass());
    }

    /**
     * This method performs the
     * {@link #convert(Object, Object, GenericType) conversion} recursive.
     * 
     * @param value is the value to convert.
     * @param valueSource describes the source of the value. This may be the
     *        filename where the value was read from, an XPath where the value
     *        was located in an XML document, etc. It is used in exceptions
     *        thrown if something goes wrong. This will help to find the problem
     *        easier.
     * @param genericTargetType is the {@link GenericType} to convert the
     *        <code>value</code> to.
     * @param sourceClass is the current
     *        {@link ValueConverter#getSourceType() source-type} to try.
     * @return the converted <code>value</code> or <code>null</code> if the
     *         conversion is NOT possible. The returned value has to be an
     *         {@link Class#isInstance(Object) instance} of the given
     *         <code>targetType</code>.
     */
    @SuppressWarnings("unchecked")
    protected TARGET convertRecursive(Object value, Object valueSource,
        GenericType<? extends TARGET> genericTargetType, Class<?> sourceClass) {

      Log logger = getLogger();
      Class<?> currentClass = sourceClass;
      while (currentClass != null) {
        if (logger.isTraceEnabled()) {
          logger.trace("searching converter for source-type '" + currentClass + "'");
        }
        ValueConverter<Object, TARGET> converter = (ValueConverter<Object, TARGET>) this.sourceClass2converterMap
            .get(currentClass);
        if (converter != null) {
          if (logger.isTraceEnabled()) {
            logger.debug("trying converter for source-type '" + currentClass + "': "
                + converter.getClass().getSimpleName());
          }
          TARGET result = converter.convert(value, valueSource, genericTargetType);
          if (result != null) {
            if (logger.isTraceEnabled()) {
              logger.debug("conversion successful using '" + converter.getClass().getName() + "'");
            }
            return result;
          }
        }
        for (Class<?> superInterface : currentClass.getInterfaces()) {
          convertRecursive(value, valueSource, genericTargetType, superInterface);
        }
        currentClass = currentClass.getSuperclass();
      }
      return null;
    }
  }

  /**
   * This inner class is an {@link AdvancedClassHierarchieMap} for
   * {@link ComposedTargetTypeConverter}s.
   */
  protected static class TargetClass2ConverterMap extends
      AdvancedClassHierarchieMap<ComposedTargetTypeConverter<?>> {

    /**
     * The constructor.
     * 
     */
    public TargetClass2ConverterMap() {

      super();
    }

    /**
     * The constructor.
     * 
     * @param mapFactory is the factory used to create the internal {@link Map}.
     */
    @SuppressWarnings("unchecked")
    public TargetClass2ConverterMap(MapFactory<Map> mapFactory) {

      super(mapFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<?> getClass(ComposedTargetTypeConverter<?> element) {

      return element.getTargetType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ComposedTargetTypeConverter<?> put(Class<?> type, ComposedTargetTypeConverter<?> element) {

      return super.put(type, element);
    }

  }

}
