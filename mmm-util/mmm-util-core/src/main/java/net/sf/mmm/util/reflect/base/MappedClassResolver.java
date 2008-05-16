/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import java.util.Map;

import net.sf.mmm.util.collection.MapFactory;
import net.sf.mmm.util.reflect.api.ClassResolver;

/**
 * This is an implementation of the {@link ClassResolver} interface that uses an
 * internal {@link Map} to define specific mappings. If no mapping is found for
 * the {@link #resolveClass(String) requested} type the default strategy
 * {@link Class#forName(String)} is used.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class MappedClassResolver implements ClassResolver {

  /** @see #resolveClass(String) */
  private final Map<String, Class<?>> name2classMap;

  /**
   * The constructor.
   */
  public MappedClassResolver() {

    this(MapFactory.INSTANCE_HASH_MAP);
  }

  /**
   * The constructor.
   * 
   * @param mapFactory the factory used to create the underlying {@link Map}.
   */
  @SuppressWarnings("unchecked")
  public MappedClassResolver(MapFactory<? extends Map> mapFactory) {

    super();
    this.name2classMap = mapFactory.create();
  }

  /**
   * This method adds a mapping for the given <code>type</code> using the
   * {@link Class#getSimpleName() simple-name}.
   * 
   * @param type is the type to add.
   */
  public void addClassMapping(Class<?> type) {

    addClassMapping(type.getSimpleName(), type);
  }

  /**
   * This method adds a mapping for the given <code>type</code> using the
   * given <code>name</code>.
   * 
   * @param name is the name under which the class is
   *        {@link #resolveClass(String) mapped}.
   * @param type is the mapped class.
   */
  public void addClassMapping(String name, Class<?> type) {

    this.name2classMap.put(name, type);
  }

  /**
   * {@inheritDoc}
   */
  public Class<?> resolveClass(String name) throws ClassNotFoundException {

    Class<?> result = this.name2classMap.get(name);
    if (result == null) {
      result = Class.forName(name);
    }
    return result;
  }

}
