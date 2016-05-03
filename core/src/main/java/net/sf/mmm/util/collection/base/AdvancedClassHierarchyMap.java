/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.Map;

import net.sf.mmm.util.collection.api.MapFactory;

/**
 * This class extends {@link AbstractClassHierarchyMap} with the ability to replace elements ({@literal <E>}) on
 * recursive {@link #put(Class, Object) puts} if the new element has a {@link Class#isAssignableFrom(Class) more
 * general} {@link Class type}. <br>
 * Therefore an according sub-class has to implement the abstract method {@link #getClass(Object)}.
 *
 * @param <E> is the generic type of the elements contained in this map.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1 (renamed in 4.0.0)
 */
public abstract class AdvancedClassHierarchyMap<E> extends AbstractClassHierarchyMap<E> {

  /**
   * The constructor.
   */
  public AdvancedClassHierarchyMap() {

    super();
  }

  /**
   * The constructor.
   *
   * @param mapFactory is the factory used to create the internal {@link Map}.
   */
  @SuppressWarnings("rawtypes")
  public AdvancedClassHierarchyMap(MapFactory<Map> mapFactory) {

    super(mapFactory);
  }

  /**
   * This method gets the associated {@link Class type} for the given {@code element}.
   *
   * @param element is the element for which the {@link Class type} is requested.
   * @return the {@link Class} of the given {@code element}.
   */
  protected abstract Class<?> getClass(E element);

  /**
   * This method {@link #put(Class, Object) puts} the given {@code element} using its {@link #getClass(Object)
   * associated class}.
   *
   * @param element is the element to put.
   * @return the element that has been replaced or {@code null} if none has been replaced.
   */
  protected E put(E element) {

    return put(getClass(element), element);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean isPreferable(E element, Class<?> elementType, E existing, Class<?> currentType) {

    Class<?> currentClass = getClass(existing);
    if (currentClass != null) {
      if (elementType.isAssignableFrom(currentClass)) {
        // element is associated with a more general type than existing
        return true;
      }
    }
    return false;
  }
}
