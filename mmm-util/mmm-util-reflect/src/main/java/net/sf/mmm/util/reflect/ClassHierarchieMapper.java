/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect;

import java.util.Map;

import net.sf.mmm.util.collection.MapFactory;
import net.sf.mmm.util.component.AbstractLoggable;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class ClassHierarchieMapper<E> extends AbstractLoggable {

  /** @see #get(Class) */
  private final Map<Class<?>, E> classMap;

  /**
   * The constructor.
   */
  public ClassHierarchieMapper() {

    this(MapFactory.INSTANCE_HASH_MAP);
  }

  /**
   * The constructor.
   * 
   * @param mapFactory is the factory used to create the internal {@link Map}.
   */
  @SuppressWarnings("unchecked")
  public ClassHierarchieMapper(MapFactory mapFactory) {

    super();
    this.classMap = mapFactory.create();
  }

  protected abstract Class<?> getClass(E element);

  protected E get(Class<?> type) {

    return this.classMap.get(type);
  }

  protected E register(E element) {

    return register(element, getClass(element));
  }

  protected E register(E element, Class<?> type) {

    E old = this.classMap.get(type);
    registerRecursive(type, element, type);
    return old;
  }

  /**
   * This method determines if the given <code>element</code> should be
   * {@link #get(Class) associated} with <code>currentType</code> in
   * preference to the element <code>existing</code> that is already
   * registered and will be replaced according to the result of this method.
   * 
   * @param element is the element to register.
   * @param elementType is the type for which the given <code>element</code>
   *        is to be registered originally.
   * @param existing is the element that has already been registered before and
   *        is {@link #get(Class) associated} with <code>currentType</code>.
   * @param currentType is the registration type.
   * @return <code>true</code> if the given <code>element</code> is
   *         preferable and should replace <code>existing</code> for
   *         <code>currentType</code>, <code>false</code> otherwise (if
   *         <code>existing</code> should remain
   *         {@link #get(Class) associated} with <code>currentType</code>).
   */
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

  private void registerRecursive(Class<?> type, E element, Class<?> elementType) {

    Class<?> clazz = type;
    while (clazz != null) {
      E existing = this.classMap.get(clazz);
      boolean add = (existing == null);
      if (existing != null) {
        if (existing == element) {
          return;
        }
        add = isPreferable(element, elementType, existing, clazz);
      }
      if (add) {
        if (getLogger().isDebugEnabled()) {
          getLogger().debug("registered '" + element + "' for '" + clazz + "'");
        }
        this.classMap.put(clazz, element);
      }
      for (Class<?> clazzInterface : clazz.getInterfaces()) {
        registerRecursive(clazzInterface, element, elementType);
      }
      clazz = clazz.getSuperclass();
    }
  }
}
