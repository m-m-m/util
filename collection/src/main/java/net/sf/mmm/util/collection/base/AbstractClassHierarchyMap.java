/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.Map;

import net.sf.mmm.util.collection.api.MapFactory;

/**
 * This is the abstract base class for a simplified {@link Map} that associates elements ({@literal <E>}) with
 * {@link Class} objects.
 *
 * @param <E> is the generic type of the elements contained in this map.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1 (renamed in 4.0.0)
 */
public abstract class AbstractClassHierarchyMap<E> {

  private final Map<Class<?>, E> classMap;

  /**
   * The constructor.
   */
  public AbstractClassHierarchyMap() {

    this(HashMapFactory.INSTANCE);
  }

  /**
   * The constructor.
   *
   * @param mapFactory is the factory used to create the internal {@link Map}.
   */
  @SuppressWarnings("rawtypes")
  public AbstractClassHierarchyMap(MapFactory<Map> mapFactory) {

    super();
    this.classMap = mapFactory.create();
  }

  /**
   * This method gets the element that has been {@link #put(Class, Object) registered} for the given {@code type} or a
   * sub-type.
   *
   * @param type is the {@link Class} for which the associated element is requested.
   * @return the element for the given type or {@code null} if no element is {@link #put(Class, Object) registered} for
   *         the given {@code type}.
   */
  public E get(Class<?> type) {

    return this.classMap.get(type);
  }

  /**
   * This method determines if the given {@code element} should be {@link #get(Class) associated} with
   * {@code currentType} in preference to the element {@code existing} that is already registered and will be replaced
   * according to the result of this method.
   *
   * @param element is the element to register.
   * @param elementType is the type for which the given {@code element} is to be registered originally.
   * @param existing is the element that has already been registered before and is {@link #get(Class) associated} with
   *        {@code currentType}.
   * @param currentType is the registration type.
   * @return {@code true} if the given {@code element} is preferable and should replace {@code existing} for
   *         {@code currentType}, {@code false} otherwise (if {@code existing} should remain {@link #get(Class)
   *         associated} with {@code currentType} ).
   */
  protected boolean isPreferable(E element, Class<?> elementType, E existing, Class<?> currentType) {

    return false;
  }

  /**
   * This method registers the given {@code element} for the given {@code type}. Unlike a regular {@link Map} this
   * method will also recursively put the given {@code element} for all {@link Class#getSuperclass() super-classes} and
   * {@link Class#getInterfaces() super-interfaces} of the given {@code type}.
   *
   * @see Map#put(Object, Object)
   *
   * @param type is the {@link Class} used as key to associate the given {@code element}.
   * @param element is the element to put.
   * @return the element that was associated directly with the given {@code type} and has NOW been replaced with
   *         {@code element}.
   */
  protected E put(Class<?> type, E element) {

    E old = this.classMap.get(type);
    putRecursive(type, element, type);
    return old;
  }

  /**
   * This method performs the {@link #put(Class, Object) put} recursively.
   *
   * @param type is the current {@link Class} used as key to associate the given {@code element}.
   * @param element is the element to put.
   * @param elementType is the the {@code type} of the original {@link #put(Class, Object) put}.
   */
  private void putRecursive(Class<?> type, E element, Class<?> elementType) {

    Class<?> clazz = type;
    while (clazz != null) {
      if (isAccepted(clazz)) {
        E existing = this.classMap.get(clazz);
        boolean add = (existing == null);
        if (existing != null) {
          if (existing == element) {
            return;
          }
          add = isPreferable(element, elementType, existing, clazz);
        }
        if (add) {
          this.classMap.put(clazz, element);
        }
      }
      for (Class<?> interfaceClass : clazz.getInterfaces()) {
        if (isAccepted(interfaceClass)) {
          putRecursive(interfaceClass, element, elementType);
        }
      }
      clazz = clazz.getSuperclass();
    }
  }

  /**
   * This method determines whether the given {@code type} is accepted in the hierarchy. <br>
   * This implementation accepts any type. Override this method to ignore specific types (e.g.
   * {@link net.sf.mmm.util.reflect.api.ReflectionUtil#isMarkerInterface(Class) marker-interfaces}).
   *
   * @param type is the {@link Class} reflecting the type to check.
   * @return {@code true} if the given {@code type} should be accepted, {@code false} to ignore {@code type}.
   */
  protected boolean isAccepted(Class<?> type) {

    return true;
  }
}
