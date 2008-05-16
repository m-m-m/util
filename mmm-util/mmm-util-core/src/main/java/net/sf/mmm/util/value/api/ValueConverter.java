/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;

import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is the interface for a converter that
 * {@link #convert(Object, Object, GenericType) converts} a value from a
 * {@link #getSourceType() source-type} to a specific
 * {@link #getTargetType() target-type}.<br>
 * <b>ATTENTION:</b><br>
 * An implementation of this interface should be stateless and thread-safe.
 * 
 * @param <SOURCE> is the generic {@link #getSourceType() source-type}.
 * @param <TARGET> is the generic {@link #getTargetType() target-type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ValueConverter<SOURCE, TARGET> {

  /**
   * This the type of the value accepted by this converter. Use {@link Object}
   * if you want to accept any value. A very common
   * {@link #getSourceType() source-type} is {@link String}.
   * 
   * @return the source-type.
   */
  Class<SOURCE> getSourceType();

  /**
   * Is the guaranteed return-type of the
   * {@link #convert(Object, Object, GenericType) conversion}. This information
   * is used externally to choose the most specific {@link ValueConverter} that
   * is {@link Class#isAssignableFrom(Class) appropriate} for the conversion.<br>
   * E.g. a generic converter can have {@link Object} as
   * {@link #getTargetType() target-type} while a specific converter may have
   * {@link java.util.Collection} as {@link #getTargetType() target-type}. Now
   * if an object (compliant with the {@link #getSourceType() source-type})
   * needs to be converted to a {@link java.util.Collection} or
   * {@link java.util.List}, the specific converter is used while for other
   * objects the generic converter is chosen.<br>
   * Please note that the {@link #getTargetType() target-type} is often more
   * general than the actual
   * {@link #convert(Object, Object, GenericType) returned result}. So a
   * {@link ValueConverter} that converts a comma-separated {@link String} to an
   * {@link java.util.ArrayList} will typically declare {@link java.util.List}
   * as {@link #getTargetType() target-type}.
   * 
   * @return the target-type.
   */
  Class<TARGET> getTargetType();

  /**
   * This method converts the given <code>pojo</code> to the
   * &lt;TARGET&gt;-type.
   * 
   * @see #convert(Object, Object, GenericType)
   * 
   * @param value is the value to convert.
   * @param valueSource describes the source of the value. This may be the
   *        filename where the value was read from, an XPath where the value was
   *        located in an XML document, etc. It is used in exceptions thrown if
   *        something goes wrong. This will help to find the problem easier.
   * @param targetClass is the type to convert the <code>value</code> to.
   * @return the converted <code>value</code> or <code>null</code> if the
   *         conversion is NOT possible. The returned value has to be an
   *         {@link Class#isInstance(Object) instance} of the given
   *         <code>targetType</code>.
   * @throws ValueException if the conversion failed (e.g. the given
   *         <code>value</code> is illegal for the given
   *         <code>targetClass</code>).
   */
  TARGET convert(SOURCE value, Object valueSource, Class<? extends TARGET> targetClass)
      throws ValueException;

  /**
   * This method converts the given <code>pojo</code> to the
   * &lt;TARGET&gt;-type.
   * 
   * @param value is the value to convert.
   * @param valueSource describes the source of the value. This may be the
   *        filename where the value was read from, an XPath where the value was
   *        located in an XML document, etc. It is used in exceptions thrown if
   *        something goes wrong. This will help to find the problem easier.
   * @param targetType is the {@link GenericType} to convert the
   *        <code>value</code> to. It is potentially generic and therefore
   *        contains more detailed information than a {@link Class}. E.g. the
   *        <code>targetType</code> could be
   *        <code>java.util.List&lt;Long&gt;</code>. This could help e.g. if
   *        the <code>value</code> is a string like
   *        <code>"2, 47, 4252525"</code>.
   * @return the converted <code>value</code> or <code>null</code> if the
   *         conversion is NOT possible. The returned value has to be an
   *         {@link Class#isInstance(Object) instance} of the given
   *         <code>targetType</code>.
   * @throws ValueException if the conversion failed (e.g. the given
   *         <code>value</code> is illegal for the given
   *         <code>targetClass</code>).
   */
  TARGET convert(SOURCE value, Object valueSource, GenericType<? extends TARGET> targetType)
      throws ValueException;

}
