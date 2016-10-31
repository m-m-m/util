/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.api;

import net.sf.mmm.util.property.api.WritableProperty;

/**
 * This is the interface for a builder of {@link Bean} prototypes. A prototype is used as template to
 * {@link BeanFactory#create(Bean) create} regular {@link Bean}s. Such beans will inherit the defaults from the
 * prototype what are the {@link BeanAccess#getProperties() available properties} as well as their default
 * {@link WritableProperty#getValue() value}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public interface BeanPrototypeBuilder extends AbstractBeanFactory {

  /**
   * @return the {@link BeanAccess#isDynamic() dynamic-flag} of this builder given as it was
   *         {@link BeanFactory#createPrototypeBuilder(boolean) created}.
   */
  boolean isDynamic();

  /**
   * @see BeanFactory#createPrototype(Class)
   *
   * @param <BEAN> the generic type of the {@link Bean}.
   * @param type the {@link Class} reflecting the {@link Bean}.
   * @return the {@link #getPrototype(Bean) prototype instance} of the specified {@link Bean}. Will be cached and
   *         subsequent calls will return the same instance. For polymorphism this method will create the prototype for
   *         all super {@link Bean} interfaces and connect them so dynamic properties will also be inherited.
   */
  <BEAN extends Bean> BEAN getOrCreatePrototype(Class<BEAN> type);

  /**
   * @param qualifiedName the {@link BeanAccess#getQualifiedName() qualified name} of the requested prototype.
   * @return the {@link #getPrototype(Bean) prototype instance} for the given {@code qualifiedName} or {@code null} if
   *         no such prototype exists.
   */
  Bean getPrototype(String qualifiedName);

  /**
   * Creates a {@link BeanAccess#isVirtual() virtual} prototype that allows dynamic beans at runtime (without an
   * corresponding sub-interface existing as Java code).
   *
   * @see BeanFactory#createPrototype(Class)
   *
   * @param <BEAN> the generic type of the {@link Bean}.
   * @param prototype the main prototype of the {@link Bean}.
   * @param name the explicit {@link BeanAccess#getSimpleName() name} of the {@link Bean}.
   * @param superBeanPrototypes the additional prototypes of the {@link Bean}s (mixins) that will also be inherited from
   *        the prototype to create in addition to the given {@code prototype}. They have to be
   *        {@link #getPrototype(Bean) prototypes}. The prototype to create must not result in cyclic inheritance or
   *        inconsistent properties in case of multi-inheritance.
   * @return the prototype instance of the specified {@link Bean}.
   */
  <BEAN extends Bean> BEAN createPrototype(BEAN prototype, String name, Bean... superBeanPrototypes);

  /**
   * Creates a {@link BeanAccess#isVirtual() virtual} prototype that allows dynamic beans at runtime (without an
   * corresponding sub-interface existing as Java code).
   *
   * @see BeanFactory#createPrototype(Class)
   *
   * @param <BEAN> the generic type of the {@link Bean}.
   * @param type the {@link Class} reflecting the {@link Bean}.
   * @param name the explicit {@link BeanAccess#getSimpleName() name} of the {@link Bean}.
   * @param superBeanPrototypes the prototypes of the {@link Bean}s to inherit the prototype to create. Have to be
   *        {@link #getPrototype(Bean) prototypes}. Further one of these prototypes has to correspond to the given
   *        {@code type} (may be a sub-prototype). Finally the prototype to create must not result in cyclic inheritance
   *        or inconsistent properties in case of multi-inheritance.
   * @return the prototype instance of the specified {@link Bean}.
   */
  default <BEAN extends Bean> BEAN createPrototype(Class<BEAN> type, String name, Bean... superBeanPrototypes) {

    BEAN superPrototypeBean = getOrCreatePrototype(type);
    return createPrototype(superPrototypeBean, name, superBeanPrototypes);
  }

}
