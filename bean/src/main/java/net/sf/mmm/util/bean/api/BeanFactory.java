/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.api;

import javax.inject.Named;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the factory used to {@link #create(Class) create} instances of {@link Bean}.
 *
 * @author hohwille
 * @since 8.4.0
 */
@ComponentSpecification
public interface BeanFactory extends AbstractBeanFactory {

  /**
   * @param type the {@link Class} reflecting the {@link Bean}.
   * @return the {@link BeanAccess#getQualifiedName() qualified name}.
   */
  default String getQualifiedName(Class<? extends Bean> type) {

    return getQualifiedName(type, null);
  }

  /**
   * @param type the {@link Class} reflecting the {@link Bean}.
   * @param name the optional name given at runtime or {@code null}.
   * @return the {@link BeanAccess#getQualifiedName() qualified name}.
   */
  default String getQualifiedName(Class<? extends Bean> type, String name) {

    String overridenName = name;
    if (overridenName == null) {
      Named named = type.getAnnotation(Named.class);
      if (named != null) {
        overridenName = named.value();
      }
    }
    if (overridenName == null) {
      return type.getName();
    } else if (overridenName.indexOf('.') >= 0) {
      return overridenName;
    } else {
      return type.getPackage().getName() + "." + overridenName;
    }
  }

  /**
   * @param dynamic the {@link BeanAccess#isDynamic() dynamic flag} of the {@link Bean}s to build.
   * @return a new {@link BeanPrototypeBuilder} instance.
   */
  BeanPrototypeBuilder createPrototypeBuilder(boolean dynamic);

  /**
   * Creates a prototype of the given {@link Bean}. A prototype is used as template to {@link #create(Bean) create}
   * regular {@link Bean}s. Such beans will inherit the defaults from the prototype what are the
   * {@link BeanAccess#getProperties() available properties} as well as their default
   * {@link net.sf.mmm.util.property.api.WritableProperty#getValue() value}.<br>
   * While in regular Java there is a separation between a {@link Class} for reflection and its {@link Object} instance,
   * this approach makes it a lot easier. The prototype is like the {@link Class} where {@link Bean}-instances can be
   * {@link #create(Bean) created} of that are of the same type.
   *
   * @see BeanPrototypeBuilder#createPrototype(Class, String, Bean...)
   *
   * @param <BEAN> the generic type of the {@link Bean}.
   * @param type the {@link Class} reflecting the {@link Bean}.
   * @return the prototype instance of the specified {@link Bean}.
   */
  <BEAN extends Bean> BEAN createPrototype(Class<BEAN> type);

  /**
   * Creates a new simple instance of the specified {@link Bean}.
   *
   * @param <BEAN> the generic type of the {@link Bean}.
   * @param type the {@link Class} reflecting the {@link Bean}.
   * @return the new {@link Bean} instance.
   */
  default <BEAN extends Bean> BEAN create(Class<BEAN> type) {

    return create(createPrototype(type));
  }

}
