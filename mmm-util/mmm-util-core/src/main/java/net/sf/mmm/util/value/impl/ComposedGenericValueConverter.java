/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;

import net.sf.mmm.util.collection.AdvancedClassHierarchieMap;
import net.sf.mmm.util.collection.MapFactory;
import net.sf.mmm.util.value.api.ValueConverter;
import net.sf.mmm.util.value.base.AbstractComposedValueConverter;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.util.value.api.ComposedValueConverter} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ComposedGenericValueConverter extends AbstractComposedValueConverter {

  /** @see #addConverter(ValueConverter) */
  private final TargetClass2ConverterMap targetClass2converterMap;

  /**
   * The constructor.
   */
  public ComposedGenericValueConverter() {

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
    if (targetConverter == null) {
      targetConverter = new ComposedTargetTypeConverter(targetType);
      this.targetClass2converterMap.put(targetType, targetConverter);
    }
    return targetConverter.addConverter(converter);
  }

  /**
   * {@inheritDoc}
   */
  public Object convert(Object value, Object valueSource, Class<? extends Object> targetClass,
      Type targetType) {

    if (value == null) {
      return null;
    }
    return convertRecursive(value, valueSource, targetClass, targetType, targetClass);
  }

  /**
   * This method performs the
   * {@link #convert(Object, Object, Class, Type) conversion} recursive.
   * 
   * @param value is the value to convert.
   * @param valueSource describes the source of the value. This may be the
   *        filename where the value was read from, an XPath where the value was
   *        located in an XML document, etc. It is used in exceptions thrown if
   *        something goes wrong. This will help to find the problem easier.
   * @param targetClass is the type to convert the <code>value</code> to. It
   *        is the raw-type of the given <code>targetType</code>.
   * @param targetType is the type to convert the <code>value</code> to. It is
   *        potentially generic and therefore contains more detailed information
   *        than <code>targetClass</code>. E.g. the <code>targetClass</code>
   *        may be <code>java.util.List</code> while this
   *        <code>targetType</code> could be
   *        <code>java.util.List&lt;Long&gt;</code>. This could help e.g. if
   *        the <code>value</code> is a string like
   *        <code>"2, 47, 4252525"</code>. The caller may supply the
   *        <code>targetClass</code> again here.
   * @param currentTargetClass is the current
   *        {@link ValueConverter#getTargetType() target-type} to try.
   * @return the converted <code>value</code> or <code>null</code> if the
   *         conversion is NOT possible. The returned value has to be an
   *         {@link Class#isInstance(Object) instance} of the given
   *         <code>targetType</code>.
   */
  @SuppressWarnings("unchecked")
  protected Object convertRecursive(Object value, Object valueSource, Class<?> targetClass,
      Type targetType, Class<?> currentTargetClass) {

    Log logger = getLogger();
    Class<?> currentClass = currentTargetClass;
    while (currentClass != null) {
      if (logger.isTraceEnabled()) {
        logger.trace("searching converter for target-class " + currentClass);
      }
      ValueConverter converter = this.targetClass2converterMap.get(currentClass);
      if ((converter != null) && (converter.getTargetType().isAssignableFrom(targetClass))) {
        if (logger.isDebugEnabled()) {
          logger.debug("trying converter for target-class " + converter.getTargetType());
        }
        Object result = converter.convert(value, valueSource, targetClass, targetType);
        if (result != null) {
          return result;
        }
      }
      for (Class<?> superInterface : currentClass.getInterfaces()) {
        convertRecursive(value, valueSource, targetClass, targetType, superInterface);
      }
      currentClass = currentClass.getSuperclass();
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
    public TARGET convert(Object value, Object valueSource, Class<? extends TARGET> targetClass,
        Type genericTargetType) {

      if (value == null) {
        return null;
      }
      return convertRecursive(value, valueSource, targetClass, genericTargetType, value.getClass());
    }

    /**
     * This method performs the
     * {@link #convert(Object, Object, Class, Type) conversion} recursive.
     * 
     * @param value is the value to convert.
     * @param valueSource describes the source of the value. This may be the
     *        filename where the value was read from, an XPath where the value
     *        was located in an XML document, etc. It is used in exceptions
     *        thrown if something goes wrong. This will help to find the problem
     *        easier.
     * @param targetClass is the type to convert the <code>value</code> to. It
     *        is the raw-type of the given <code>targetType</code>.
     * @param genericTargetType is the type to convert the <code>value</code>
     *        to. It is potentially generic and therefore contains more detailed
     *        information than <code>targetClass</code>. E.g. the
     *        <code>targetClass</code> may be <code>java.util.List</code>
     *        while this <code>targetType</code> could be
     *        <code>java.util.List&lt;Long&gt;</code>. This could help e.g.
     *        if the <code>value</code> is a string like
     *        <code>"2, 47, 4252525"</code>. The caller may supply the
     *        <code>targetClass</code> again here.
     * @param sourceClass is the current
     *        {@link ValueConverter#getSourceType() source-type} to try.
     * @return the converted <code>value</code> or <code>null</code> if the
     *         conversion is NOT possible. The returned value has to be an
     *         {@link Class#isInstance(Object) instance} of the given
     *         <code>targetType</code>.
     */
    @SuppressWarnings("unchecked")
    protected TARGET convertRecursive(Object value, Object valueSource,
        Class<? extends TARGET> targetClass, Type genericTargetType, Class<?> sourceClass) {

      Log logger = getLogger();
      Class<?> currentClass = sourceClass;
      while (currentClass != null) {
        if (logger.isTraceEnabled()) {
          logger.trace("searching converter for source-class " + currentClass);
        }
        ValueConverter<Object, TARGET> converter = (ValueConverter<Object, TARGET>) this.sourceClass2converterMap
            .get(currentClass);
        if (converter != null) {
          if (logger.isDebugEnabled()) {
            logger.debug("trying converter for source-class " + currentClass);
          }
          TARGET result = converter.convert(value, valueSource, targetClass, genericTargetType);
          if (result != null) {
            if (logger.isDebugEnabled()) {
              logger.debug("conversion successful using " + converter.getClass().getName());
            }
            return result;
          }
        }
        for (Class<?> superInterface : currentClass.getInterfaces()) {
          convertRecursive(value, valueSource, targetClass, genericTargetType, superInterface);
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
