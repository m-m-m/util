/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base.datatype.descriptor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import net.sf.mmm.util.exception.api.IllegalCaseException;
import net.sf.mmm.util.lang.api.SimpleDatatype;
import net.sf.mmm.util.reflect.api.AccessFailedException;
import net.sf.mmm.util.reflect.api.InstantiationFailedException;
import net.sf.mmm.util.reflect.api.InvocationFailedException;
import net.sf.mmm.util.reflect.api.ReflectionUtil;

/**
 * This is the implementation of {@link net.sf.mmm.util.lang.api.DatatypeDescriptor} for a
 * {@link SimpleDatatype}.
 *
 * @param <T> is the generic type of the {@link SimpleDatatype} to describe.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 6.0.0
 */
public class DatatypeDescriptorSimpleDatatype<T extends SimpleDatatype<?>> extends AbstractDatatypeDescriptor<T> {

  private  final Constructor<T> constructor;

  /**
   * The constructor.
   *
   * @param datatype is the {@link Class} reflecting the {@link SimpleDatatype}.
   * @param reflectionUtil is the {@link ReflectionUtil} instance.
   */
  // generic type of Java/Eclipse is very poor...
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public DatatypeDescriptorSimpleDatatype(Class<T> datatype, ReflectionUtil reflectionUtil) {

    super(datatype, DatatypeSegmentDescriptorSimpleDatatype.newInstance((Class) datatype, reflectionUtil));
    if (datatype.isEnum()) {
      this.constructor = null;
    } else {
      assert !(datatype.isInterface());
      assert !Modifier.isAbstract(datatype.getModifiers());
      Class<?> type = getSegmentDescriptors().get(0).getType();
      try {
        this.constructor = datatype.getConstructor(type);
      } catch (Exception e) {
        throw new IllegalArgumentException("Invalid SimpleDatatype '" + datatype.getName()
            + "' - has to be an Enum or non-abstract class with constructor compatible to getValue()!");
      }
    }
  }

  @Override
  protected T doCreate(Object... segments) {

    if (this.constructor == null) {
      Object value = segments[0];
      if (value == null) {
        return null;
      }
      for (T instance : getDatatype().getEnumConstants()) {
        if (instance.getValue().equals(value)) {
          return instance;
        }
      }
      throw new IllegalCaseException(value.toString() + "@" + getDatatype().getName());
    } else {
      try {
        return this.constructor.newInstance(segments);
      } catch (IllegalArgumentException | InstantiationException e) {
        throw new InstantiationFailedException(e, getDatatype());
      } catch (IllegalAccessException e) {
        throw new AccessFailedException(e, this.constructor);
      } catch (InvocationTargetException e) {
        throw new InvocationFailedException(e, this.constructor);
      }
    }
  }
}
