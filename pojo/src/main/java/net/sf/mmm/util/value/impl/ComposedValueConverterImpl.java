/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import net.sf.mmm.util.collection.api.MapFactory;
import net.sf.mmm.util.collection.base.AdvancedClassHierarchyMap;
import net.sf.mmm.util.component.api.ResourceMissingException;
import net.sf.mmm.util.exception.api.NlsParseException;
import net.sf.mmm.util.exception.api.ValueException;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.value.api.ComposedValueConverter;
import net.sf.mmm.util.value.api.ValueConverter;
import net.sf.mmm.util.value.base.AbstractComposedValueConverter;

/**
 * This is the implementation of the {@link net.sf.mmm.util.value.api.ComposedValueConverter} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class ComposedValueConverterImpl extends AbstractComposedValueConverter {

  private final TargetClass2ConverterMap targetClass2converterMap;

  private final TargetClass2ConverterMap targetArrayClass2converterMap;

  private List<ValueConverter<?, ?>> converters;

  /**
   * The constructor.
   */
  public ComposedValueConverterImpl() {

    super();
    this.targetClass2converterMap = new TargetClass2ConverterMap();
    this.targetArrayClass2converterMap = new TargetClass2ConverterMap();
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.converters == null) {
      throw new ResourceMissingException("converters");
    }
    for (ValueConverter<?, ?> converter : this.converters) {
      if (!(converter instanceof ComposedValueConverter)) {
        addConverterInternal(converter);
      }
    }
  }

  /**
   * This method registers the given {@code converter} to this composed converter.
   *
   * @param converter is the converter to add.
   */
  public void addConverter(ValueConverter<?, ?> converter) {

    getInitializationState().requireNotInitilized();
    if (this.converters == null) {
      this.converters = new ArrayList<>();
    }
    this.converters.add(converter);
  }

  /**
   * This method registers the given {@code converter} to this composed converter.
   *
   * @param converter is the converter to add.
   * @return the converter with the same {@link ValueConverter#getSourceType() source-type} and
   *         {@link ValueConverter#getTargetType() target-type} that has been replaced by {@code converter} or
   *         {@code null} if no converter has been replaced.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  private ValueConverter<?, ?> addConverterInternal(ValueConverter<?, ?> converter) {

    getInitializationState().requireNotInitilized();
    Class<?> targetType = converter.getTargetType();
    TargetClass2ConverterMap map;
    if (targetType.isArray()) {
      map = this.targetArrayClass2converterMap;
      targetType = targetType.getComponentType();
    } else {
      map = this.targetClass2converterMap;
    }
    ComposedTargetTypeConverter targetConverter = map.get(targetType);
    if ((targetConverter == null) || !targetType.equals(targetConverter.getTargetType())) {
      // if targetConverter is NOT null here, then a ValueConverter for a more
      // specific (e.g. String or Integer) type than targetType (e.g. Object or
      // Number) is currently registered here and will therefore be replaced by
      // the new more general ValueConverter
      targetConverter = new ComposedTargetTypeConverter(targetType);
      map.put(targetType, targetConverter);
    }
    return targetConverter.addConverter(converter);
  }

  /**
   * This method injects a {@link List} of {@link ValueConverter}s to {@link #addConverter(ValueConverter)
   * add}.
   *
   * @param converterList is the list of converters to register.
   */
  @Inject
  public void setConverters(List<ValueConverter<?, ?>> converterList) {

    getInitializationState().requireNotInitilized();
    this.converters = converterList;
  }

  /**
   * @return the converters
   */
  List<ValueConverter<?, ?>> getConverters() {

    return this.converters;
  }

  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public <T> T convert(Object value, Object valueSource, GenericType<T> targetType) {

    if (value == null) {
      return null;
    }
    if (getLogger().isTraceEnabled()) {
      Class<?> valueClass = value.getClass();
      String valueClassName;
      if (valueClass.isArray()) {
        valueClassName = valueClass.getComponentType().getName() + "[]";
      } else {
        valueClassName = value.getClass().getName();
      }
      getLogger().trace("starting conversion of '" + value + "' from '" + valueClassName + "' to '" + targetType + "'");
    }
    Class<?> targetClass = targetType.getRetrievalClass();
    if (targetClass.isInstance(value)) {
      // generic collections or maps might need converting of their items...
      boolean conversionRequired = false;
      if (value instanceof Collection<?>) {
        Collection<?> collection = (Collection<?>) value;
        // find first non-null item...
        Iterator<?> iterator = collection.iterator();
        while (iterator.hasNext()) {
          Object item = iterator.next();
          if (item != null) {
            GenericType<?> itemType = getReflectionUtil().createGenericType(item.getClass());
            GenericType<?> componentType = targetType.getComponentType();
            if (!componentType.isAssignableFrom(itemType)) {
              conversionRequired = true;
            }
            break;
          }
        }
      } else if (value instanceof Map<?, ?>) {
        conversionRequired = true;
        // bug: does not compile if unbound wildcards are used
        Map map = (Map) value;
        // find first non-null item...
        if (map.isEmpty()) {
          Iterator<Map.Entry> iterator = map.entrySet().iterator();
          while (iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            Object item = entry.getValue();
            if (item != null) {
              Object key = entry.getKey();
              GenericType<?> itemType = getReflectionUtil().createGenericType(item.getClass());
              GenericType<?> componentType = targetType.getComponentType();
              if (!componentType.isAssignableFrom(itemType)) {
                conversionRequired = true;
              }
              if (key != null) {
                // currently we do not care about the key...
              }
              break;
            }
          }
        }
      }
      if (!conversionRequired) {
        getLogger().trace("Value is already an instance of expected type.");
        return (T) value;
      }
    }
    TargetClass2ConverterMap converterMap;
    if (targetClass.isArray()) {
      converterMap = this.targetArrayClass2converterMap;
      targetClass = targetClass.getComponentType();
    } else {
      converterMap = this.targetClass2converterMap;
      if (targetClass.isPrimitive()) {
        targetClass = getReflectionUtil().getNonPrimitiveType(targetClass);
      }
    }
    return (T) convertRecursive(value, valueSource, targetType, targetClass, null, converterMap);
  }

  /**
   * This method determines if the given {@code converter} is applicable for the given {@code targetType}.
   *
   * @see ValueConverter#getTargetType()
   *
   * @param converter is the {@link ValueConverter} to check.
   * @param targetType is the {@link GenericType} to match with {@link ValueConverter#getTargetType()}.
   * @return {@code true} if the given {@code converter} is applicable, {@code false} otherwise.
   */
  protected boolean isApplicable(ValueConverter<?, ?> converter, GenericType<?> targetType) {

    Class<?> expectedTargetClass = targetType.getRetrievalClass();
    if (expectedTargetClass.isArray()) {
      expectedTargetClass = expectedTargetClass.getComponentType();
    }
    return isApplicable(converter.getTargetType(), expectedTargetClass);
  }

  /**
   * This method determines if the given {@code converterTargetClass} is applicable for the
   * {@code expectedTargetClass}.
   *
   * @param converterTargetClass is the {@link ValueConverter#getTargetType() target-class} of the
   *        {@link ValueConverter} to check.
   * @param expectedTargetClass is the target-class to convert to.
   * @return {@code true} if the conversion is applicable.
   */
  protected boolean isApplicable(Class<?> converterTargetClass, Class<?> expectedTargetClass) {

    if (converterTargetClass.isAssignableFrom(expectedTargetClass)) {
      return true;
    } else if (expectedTargetClass.isPrimitive()) {
      Class<?> expectedNonPrimitiveClass = getReflectionUtil().getNonPrimitiveType(expectedTargetClass);
      return converterTargetClass.isAssignableFrom(expectedNonPrimitiveClass);
      // } else if ((converterTargetClass.isArray()) &&
      // (expectedTargetClass.isArray())) {
      // return isApplicable(converterTargetClass.getComponentType(),
      // expectedTargetClass
      // .getComponentType());
    }
    return false;
  }

  /**
   * This method determines if the given {@code type} is accepted as significant type for registration and
   * lookup of {@link ValueConverter}s. E.g. interfaces such as {@link Cloneable} or
   * {@link java.io.Serializable} are not more significant than {@link Object} in order to choose the
   * appropriate {@link ValueConverter} and should therefore be skipped when the {@link Class}-hierarchy is
   * recursively traversed. <br>
   * <b>ATTENTION:</b><br>
   * If this method returns {@code false} the behaviour differs between {@link Class#isInterface() interfaces}
   * and regular classes. For an interface the entire traversal of super-interfaces is skipped, while for a
   * regular class, just that class is skipped, but {@link Class#getSuperclass() super-classes} are
   * recursively traversed.
   *
   * @param type is the {@link Class} reflecting the type to check.
   * @return {@code true} if the given {@code type} is acceptable, {@code false} if the given {@code type}
   *         should be ignored.
   */
  protected boolean isAccepted(Class<?> type) {

    if (getReflectionUtil().isMarkerInterface(type)) {
      return false;
    }
    if (type == Comparable.class) {
      return false;
    }
    return true;
  }

  /**
   * This method performs the {@link #convert(Object, Object, GenericType) conversion} recursive.
   *
   * @param value is the value to convert.
   * @param valueSource describes the source of the value. This may be the filename where the value was read
   *        from, an XPath where the value was located in an XML document, etc. It is used in exceptions
   *        thrown if something goes wrong. This will help to find the problem easier.
   * @param targetType is the {@link GenericType} to convert the {@code value} to.
   * @param currentTargetClass is the current {@link ValueConverter#getTargetType() target-type} to try.
   * @param previousConverter is the converter that has been tried last time without success. It is used to
   *        avoid trying the same converter again. Will initially be {@code null}.
   * @param converterMap is the {@link TargetClass2ConverterMap}.
   * @return the converted {@code value} or {@code null} if the conversion is NOT possible. The returned value
   *         has to be an {@link Class#isInstance(Object) instance} of the given {@code targetType}.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected Object convertRecursive(Object value, Object valueSource, GenericType<?> targetType,
      Class<?> currentTargetClass, ValueConverter previousConverter, TargetClass2ConverterMap converterMap) {

    boolean traceEnabled = getLogger().isTraceEnabled();
    ValueConverter lastConverter = previousConverter;
    Class<?> currentClass = currentTargetClass;
    Object result = null;
    try {
      while (currentClass != null) {
        if (isAccepted(currentClass)) {
          if (traceEnabled) {
            getLogger().trace("searching converter for target-type '" + currentClass + "'");
          }
          ValueConverter converter = converterMap.get(currentClass);
          if ((converter != null) && (converter != lastConverter) && (isApplicable(converter, targetType))) {
            if (traceEnabled) {
              StringWriter sw = new StringWriter(50);
              sw.append("trying converter for target-type '");
              sw.append(converter.getTargetType().toString());
              sw.append("'");
              if (!converter.getTargetType().equals(currentClass)) {
                sw.append(" for current-type '");
                sw.append(currentClass.toString());
                sw.append("'");
              }
              getLogger().trace(sw.toString());
            }
            result = converter.convert(value, valueSource, targetType);
            if (result != null) {
              return result;
            }
            lastConverter = converter;
          }
        }
        for (Class<?> superInterface : currentClass.getInterfaces()) {
          if (isAccepted(superInterface)) {
            result = convertRecursive(value, valueSource, targetType, superInterface, lastConverter, converterMap);
            if (result != null) {
              return result;
            }
          }
        }
        if ((currentClass.isInterface()) && (targetType.getRetrievalClass() == currentClass)) {
          currentClass = Object.class;
        } else {
          currentClass = currentClass.getSuperclass();
        }
      }
    } catch (ValueException e) {
      throw e;
    } catch (RuntimeException e) {
      throw new NlsParseException(e, value, targetType, valueSource);
    }
    return null;
  }

  /**
   * This inner class is a composed converter for all {@link ValueConverter}s with the same
   * {@link ValueConverter#getTargetType() target-type}.
   *
   * @param <TARGET> is the generic {@link #getTargetType() target-type}.
   */
  protected class ComposedTargetTypeConverter<TARGET> implements ValueConverter<Object, TARGET> {

    private final Class<TARGET> targetType;

    private final Map<Class<?>, ValueConverter<?, TARGET>> sourceClass2converterMap;

    /**
     * The constructor.
     *
     * @param targetType is the {@link #getTargetType() target-type} of this converter.
     */
    public ComposedTargetTypeConverter(Class<TARGET> targetType) {

      super();
      this.sourceClass2converterMap = new HashMap<>();
      this.targetType = targetType;
    }

    @Override
    public Class<Object> getSourceType() {

      return Object.class;
    }

    @Override
    public Class<TARGET> getTargetType() {

      return this.targetType;
    }

    /**
     * This method registers the given {@code converter} to this composed converter.
     *
     * @param converter is the converter to add.
     * @return the converter with the same {@link ValueConverter#getSourceType() source-type} that has been
     *         replaced by {@code converter} or {@code null} if no converter has been replaced.
     */
    public ValueConverter<?, TARGET> addConverter(ValueConverter<?, TARGET> converter) {

      return this.sourceClass2converterMap.put(converter.getSourceType(), converter);
    }

    @Override
    public <T extends TARGET> T convert(Object value, Object valueSource, Class<T> targetClass) throws ValueException {

      return convert(value, valueSource, getReflectionUtil().createGenericType(targetClass));
    }

    @Override
    public <T extends TARGET> T convert(Object value, Object valueSource, GenericType<T> genericTargetType) {

      if (value == null) {
        return null;
      }
      return convertRecursive(value, valueSource, genericTargetType, value.getClass());
    }

    /**
     * This method performs the {@link #convert(Object, Object, GenericType) conversion} recursive.
     *
     * @param <T> is the generic type of {@code genericTargetType}.
     * @param value is the value to convert.
     * @param valueSource describes the source of the value. This may be the filename where the value was read
     *        from, an XPath where the value was located in an XML document, etc. It is used in exceptions
     *        thrown if something goes wrong. This will help to find the problem easier.
     * @param genericTargetType is the {@link GenericType} to convert the {@code value} to.
     * @param sourceClass is the current {@link ValueConverter#getSourceType() source-type} to try.
     * @return the converted {@code value} or {@code null} if the conversion is NOT possible. The returned
     *         value has to be an {@link Class#isInstance(Object) instance} of the given {@code targetType}.
     */
    @SuppressWarnings("unchecked")
    protected <T extends TARGET> T convertRecursive(Object value, Object valueSource, GenericType<T> genericTargetType,
        Class<?> sourceClass) {

      boolean traceEnabled = getLogger().isTraceEnabled();
      Class<?> currentClass = sourceClass;
      while (currentClass != null) {
        if (isAccepted(currentClass)) {
          if (traceEnabled) {
            getLogger().trace("searching converter for source-type '" + currentClass + "'");
          }
          ValueConverter<Object, TARGET> converter = (ValueConverter<Object, TARGET>) this.sourceClass2converterMap
              .get(currentClass);
          if (converter != null) {
            if (traceEnabled) {
              getLogger().trace(
                  "trying converter for source-type '" + currentClass + "': " + converter.getClass().getSimpleName());
            }
            T result = converter.convert(value, valueSource, genericTargetType);
            if (result != null) {
              if (traceEnabled) {
                getLogger().trace("conversion successful using '" + converter.getClass().getName() + "'");
              }
              return result;
            }
          }
        }
        for (Class<?> superInterface : currentClass.getInterfaces()) {
          if (isAccepted(superInterface)) {
            T result = convertRecursive(value, valueSource, genericTargetType, superInterface);
            if (result != null) {
              return result;
            }
          }
        }
        if (currentClass.isInterface() && (value.getClass() == currentClass)) {
          currentClass = Object.class;
        } else {
          currentClass = currentClass.getSuperclass();
        }
      }
      return null;
    }
  }

  /**
   * This inner class is an {@link AdvancedClassHierarchyMap} for {@link ComposedTargetTypeConverter}s.
   */
  protected class TargetClass2ConverterMap extends AdvancedClassHierarchyMap<ComposedTargetTypeConverter<?>> {

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
    @SuppressWarnings({ "rawtypes" })
    public TargetClass2ConverterMap(MapFactory<Map> mapFactory) {

      super(mapFactory);
    }

    @Override
    protected Class<?> getClass(ComposedTargetTypeConverter<?> element) {

      return element.getTargetType();
    }

    @Override
    public ComposedTargetTypeConverter<?> put(Class<?> type, ComposedTargetTypeConverter<?> element) {

      return super.put(type, element);
    }

    @Override
    protected boolean isAccepted(Class<?> type) {

      if (!ComposedValueConverterImpl.this.isAccepted(type)) {
        return false;
      }
      return super.isAccepted(type);
    }

  }

}
