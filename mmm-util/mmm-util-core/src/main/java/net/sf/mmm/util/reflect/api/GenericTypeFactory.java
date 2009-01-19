/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

import java.lang.reflect.Type;

/**
 * This is the interface for a factory with ability to create
 * {@link GenericType}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface GenericTypeFactory {

  /**
   * This method creates the {@link GenericType} representing the given
   * <code>type</code>.<br>
   * The {@link GenericType#getType() type},
   * {@link GenericType#getAssignmentClass() lower bound} and
   * {@link GenericType#getRetrievalClass() upper bound} of the returned
   * {@link GenericType} will all be identical to the given <code>type</code>.<br>
   * <b>ATTENTION:</b><br>
   * If you know the {@link Type} where the given <code>type</code> was
   * {@link net.sf.mmm.util.reflect.base.AbstractGenericType#getDefiningType()
   * defined} you should use {@link #createGenericType(Type, GenericType)}
   * instead to get a more precise result. <br>
   * 
   * @param <T> is the templated type of the {@link Class} to convert.
   * 
   * @param type is the {@link Type} to represent.
   * @return the according {@link GenericType}.
   */
  <T> GenericType<T> createGenericType(Class<T> type);

  /**
   * This method creates the {@link GenericType} representing the given
   * <code>type</code>.<br>
   * If the given <code>type</code> is a {@link Class}, the methods behaves like
   * {@link #createGenericType(Class)}.<br>
   * <b>ATTENTION:</b><br>
   * If you know the {@link Type} where the given <code>type</code> was defined
   * (e.g. the {@link Class} where you retrieved the given <code>type</code>
   * from as parameter, return-type or field-type) you should use
   * {@link #createGenericType(Type, GenericType)} instead to get a more precise
   * result.
   * 
   * @param type is the {@link Type} to represent.
   * @return the according {@link GenericType}.
   */
  GenericType<?> createGenericType(Type type);

  /**
   * This method creates the {@link GenericType} representing the given
   * <code>type</code> in the context of the given <code>definingType</code>.<br>
   * Here is some typical example of how to use this:
   * 
   * <pre>
   * {@link ReflectionUtil} util = {@link net.sf.mmm.util.reflect.base.ReflectionUtilImpl#getInstance()};
   * Class&lt;?&gt; myClass = getSomeClass();
   * GenericType definingType = util.{@link #createGenericType(Type) createGenericType}(myClass);
   * {@link java.lang.reflect.Method} myMethod = findSomeMethod(myClass);
   * Type returnType = myMethod.{@link java.lang.reflect.Method#getGenericReturnType() getGenericReturnType()};
   * GenericType type = util.{@link #createGenericType(Type, GenericType) createGenericType}(returnType, definingType);
   * Class&lt;?&gt; returnClass = type.{@link GenericType#getRetrievalClass()};
   * </pre>
   * 
   * Now if you ask your self my all this instead of just using
   * <code>myMethod.{@link java.lang.reflect.Method#getReturnType() getReturnType()}</code>
   * ? Read the javadoc of {@link GenericType} to get the answer.<br>
   * <b>NOTE:</b><br>
   * Please look at <code>mmm-util-pojo</code> which allows to use this features
   * at a higher level and therefore much easier.
   * 
   * @see #createGenericType(Type, Class)
   * 
   * @param type is the {@link Type} to represent.
   * @param definingType is the {@link GenericType} where the given
   *        <code>type</code> is defined in. It is needed to resolve
   *        {@link java.lang.reflect.TypeVariable}s.
   * @return the according {@link GenericType}.
   */
  GenericType<?> createGenericType(Type type, GenericType<?> definingType);

  /**
   * This method creates the {@link GenericType} representing the given
   * <code>type</code> in the context of the given <code>definingType</code>.<br>
   * It is a convenience method for
   * <code>{@link #createGenericType(Type, GenericType) createGenericType}(type, 
   * {@link #createGenericType(Type) createGenericType}(definingType))</code>
   * 
   * @param type is the {@link Type} to represent.
   * @param definingType is the {@link Class} where the given <code>type</code>
   *        is defined in. It is needed to resolve
   *        {@link java.lang.reflect.TypeVariable}s.
   * @return the according {@link GenericType}.
   */
  GenericType<?> createGenericType(Type type, Class<?> definingType);

}
