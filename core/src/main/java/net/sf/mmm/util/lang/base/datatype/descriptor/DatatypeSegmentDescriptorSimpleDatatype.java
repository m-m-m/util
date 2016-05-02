/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.descriptor;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import net.sf.mmm.util.lang.api.DatatypeSegmentDescriptor;
import net.sf.mmm.util.lang.api.SimpleDatatype;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.api.ReflectionUtilLimited;

/**
 * This is the implementation of {@link net.sf.mmm.util.lang.api.DatatypeSegmentDescriptor} for a
 * {@link SimpleDatatype}.
 *
 * @param <T> is the generic type of the described {@link SimpleDatatype}.
 * @param <V> is the generic type of the datatypes {@link SimpleDatatype#getValue() value}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
public class DatatypeSegmentDescriptorSimpleDatatype<T extends SimpleDatatype<V>, V> extends
    AbstractDatatypeSegmentDescriptor<T, V> {

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   */
  protected DatatypeSegmentDescriptorSimpleDatatype(String name, Class<V> type) {

    super(name, type);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected V doGetSegment(T datatype) {

    return datatype.getValue();
  }

  /**
   * Creates a new instance of {@link DatatypeSegmentDescriptorSimpleDatatype} for the given
   * {@link SimpleDatatype} {@link Class}.
   *
   * @param <T> is the generic type of the {@link SimpleDatatype}.
   * @param <V> is the generic type of the datatypes {@link SimpleDatatype#getValue() value}.
   * @param datatype is the {@link Class} reflecting the {@link SimpleDatatype}.
   * @param reflectionUtil is the instance of {@link ReflectionUtil} to use.
   * @return the new {@link DatatypeSegmentDescriptorSimpleDatatype} instance.
   */
  public static <T extends SimpleDatatype<V>, V> DatatypeSegmentDescriptorSimpleDatatype<T, V> newInstance(
      Class<T> datatype, ReflectionUtil reflectionUtil) {

    try {
      Method method = datatype.getMethod("getValue", ReflectionUtilLimited.NO_PARAMETERS);
      Type returnType = method.getGenericReturnType();
      GenericType<?> genericType = reflectionUtil.createGenericType(returnType, datatype);
      @SuppressWarnings({ "unchecked", "rawtypes" })
      Class<V> type = (Class) genericType.getRetrievalClass();
      return new DatatypeSegmentDescriptorSimpleDatatype<>(DatatypeSegmentDescriptor.DEFAULT_NAME, type);
    } catch (Exception e) {
      throw new IllegalStateException("Failed to reflect type of " + datatype.getName(), e);
    }
  }

}
