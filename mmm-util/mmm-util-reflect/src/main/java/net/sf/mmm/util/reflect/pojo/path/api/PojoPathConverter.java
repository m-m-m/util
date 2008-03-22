/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.path.api;

import java.lang.reflect.Type;

/**
 * This is the call-back interface for a converter that
 * {@link #convert(Object, Class, Type, PojoPath) converts}
 * {@link net.sf.mmm.util.reflect.pojo.api.Pojo}s of a
 * {@link #getSourceType() source-type} to a specific
 * {@link #getTargetType() target-type}.<br>
 * <b>ATTENTION:</b><br>
 * An implementation of this interface should be stateless and thread-safe.
 * 
 * @param <SOURCE> is the generic {@link #getSourceType() source-type}.
 * @param <TARGET> is the generic {@link #getTargetType() target.type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PojoPathConverter<SOURCE, TARGET> {

  /**
   * This the type of the {@link net.sf.mmm.util.reflect.pojo.api.Pojo}s
   * accepted by this converter. Use {@link Object} if you want to accept any
   * {@link net.sf.mmm.util.reflect.pojo.api.Pojo}. A very common
   * {@link #getSourceType() source-type} is {@link String}.
   * 
   * @return the source-type.
   */
  Class<SOURCE> getSourceType();

  /**
   * Is the guaranteed return-type of the
   * {@link #convert(Object, Class, Type, PojoPath) conversion}. This
   * information is used externally to choose the most specific
   * {@link PojoPathConverter} that is
   * {@link Class#isAssignableFrom(Class) appropriate} for the conversion.<br>
   * E.g. a generic converter can have {@link Object} as
   * {@link #getTargetType() target-type} while a specific converter may have
   * {@link java.util.Collection} as {@link #getTargetType() target-type}. Now
   * if an object (compliant with the {@link #getSourceType() source-type})
   * needs to be converted to a {@link java.util.Collection} or
   * {@link java.util.List}, the specific converter is used while for other
   * objects the generic converter is chosen.<br>
   * Please note that the {@link #getTargetType() target-type} is often more
   * general than the actual
   * {@link #convert(Object, Class, Type, PojoPath) returned result}. So a
   * {@link PojoPathConverter} that converts a comma-separated {@link String} to
   * an {@link java.util.ArrayList} will typically declare
   * {@link java.util.List} as {@link #getTargetType() target-type}.
   * 
   * @return the target-type.
   */
  Class<TARGET> getTargetType();

  /**
   * This method converts the given <code>pojo</code> to the
   * &lt;TARGET&gt;-type.
   * 
   * @param pojo is the {@link net.sf.mmm.util.reflect.pojo.api.Pojo} to
   *        convert.
   * @param targetClass is the type to convert the <code>pojo</code> to. It is
   *        the
   *        {@link net.sf.mmm.util.reflect.ReflectionUtil#toClass(Type) raw-type}
   *        of the given <code>targetType</code>.
   * @param targetType is the type to convert the <code>pojo</code> to. It is
   *        potentially generic and therefore contains more detailed information
   *        than <code>targetClass</code>. E.g. the <code>targetClass</code>
   *        may be <code>java.util.List</code> while this
   *        <code>targetType</code> could be
   *        <code>java.util.List&lt;Long&gt;</code>. This could help if the
   *        <code>pojo</code> is a string like <code>"2, 47, 4252525"</code>.
   * @param path is the {@link PojoPath} that lead to the given
   *        <code>pojo</code>. E.g. the {@link PojoPath#getSegment() segment}
   *        can be used to influence the conversion.
   * @return the converted <code>pojo</code> or <code>null</code> if the
   *         conversion is NOT possible. The returned value has to be an
   *         {@link Class#isInstance(Object) instance} of the given
   *         <code>targetType</code>.
   */
  TARGET convert(SOURCE pojo, Class<? extends TARGET> targetClass, Type targetType, PojoPath path);

}
