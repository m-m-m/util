/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.api;

/**
 * This {@link Enum} contains the available modes for using a {@link PojoPathNavigator}. The
 * {@link PojoPathMode} has influence on how the {@link PojoPathNavigator} deals with (intermediate)
 * <code>null</code> values.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public enum PojoPathMode {

  /**
   * This mode causes the {@link PojoPathNavigator} to
   * {@link net.sf.mmm.util.pojo.api.PojoFactory#newInstance(Class) create}
   * {@link net.sf.mmm.util.pojo.api.Pojo}s if they are <code>null</code>. This applies to intermediate
   * {@link net.sf.mmm.util.pojo.api.Pojo}s as well as the final result of the {@link PojoPath}.<br>
   * <ul>
   * <li>For a &#171;Property&#187; this is done via the {@link Class type} of the according setter.</li>
   * <li>For an &#171;Index&#187; this is done via the
   * {@link net.sf.mmm.util.reflect.api.GenericType#getComponentType() component-type} of the according
   * getter.<br>
   * Therfore {@link java.util.List}s need to be declared using generics. If the &#171;Index&#187; is greater
   * or equal to the {@link net.sf.mmm.util.reflect.api.CollectionReflectionUtil#getSize(Object) size} of the
   * ordered container, its size is increased as necessary. For {@link java.util.List}s this is done by
   * {@link java.util.List#add(Object) adding} <code>null</code>-values. For arrays a compliant setter has to
   * be present. Then a {@link System#arraycopy(Object, int, Object, int, int) copy} of the original array
   * will be created with the required size.</li>
   * <li>Finally for a &#171;Function&#187; this is done via
   * {@link PojoPathFunction#create(Object, String, PojoPathContext)}.</li>
   * </ul>
   * The creation in case of &#171;Property&#187; or &#171;Index&#187; will happen via the
   * {@link net.sf.mmm.util.pojo.api.PojoFactory} provided by the {@link PojoPathContext}.
   */
  CREATE_IF_NULL,

  /**
   * This mode causes the {@link PojoPathNavigator} to fail with an {@link PojoPathSegmentIsNullException} if
   * an intermediate {@link net.sf.mmm.util.pojo.api.Pojo} is <code>null</code>. However if only the last
   * segment of the {@link PojoPath} evaluates to <code>null</code>, then <code>null</code> is returned. If an
   * &#171;Index&#187; is greater or equal to the
   * {@link net.sf.mmm.util.reflect.api.CollectionReflectionUtil#getSize(Object) size} of the ordered
   * container,a NlsIndexOutOfBoundsException is thrown.
   */
  FAIL_IF_NULL,

  /**
   * This mode causes the {@link PojoPathNavigator} to return <code>null</code> if an intermediate
   * {@link net.sf.mmm.util.pojo.api.Pojo} is <code>null</code>. This also applies for ordered containers if
   * &#171;Index&#187; is greater or equal to the containers
   * {@link net.sf.mmm.util.reflect.api.CollectionReflectionUtil#getSize(Object) size}.
   */
  RETURN_IF_NULL,

}
