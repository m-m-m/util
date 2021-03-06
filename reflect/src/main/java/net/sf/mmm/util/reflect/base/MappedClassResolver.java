/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.reflect.api.ClassResolver;
import net.sf.mmm.util.reflect.api.TypeNotFoundException;

/**
 * This is an implementation of the {@link ClassResolver} interface that uses an internal {@link Map} to
 * define specific mappings. If no mapping is found for the {@link #resolveClass(String) requested} type the
 * default strategy {@link Class#forName(String)} is used.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class MappedClassResolver implements ClassResolver {

  private final Map<String, Class<?>> name2classMap;

  /**
   * The constructor.
   */
  public MappedClassResolver() {

    this(new HashMap<String, Class<?>>());
  }

  /**
   * The constructor.
   *
   * @param map the empty {@link Map} to use (for choosing the implementation).
   */
  public MappedClassResolver(Map<String, Class<?>> map) {

    super();
    this.name2classMap = map;
  }

  /**
   * This method adds a mapping for the given {@code type} using the {@link Class#getSimpleName()
   * simple-name}.
   *
   * @param type is the type to add.
   */
  public void addClassMapping(Class<?> type) {

    addClassMapping(type.getSimpleName(), type);
  }

  /**
   * This method adds a mapping for the given {@code type} using the given {@code name}.
   *
   * @param name is the name under which the class is {@link #resolveClass(String) mapped}.
   * @param type is the mapped class.
   */
  public void addClassMapping(String name, Class<?> type) {

    this.name2classMap.put(name, type);
  }

  @Override
  public Class<?> resolveClass(String name) {

    try {
      Class<?> result = this.name2classMap.get(name);
      if (result == null) {
        result = Class.forName(name);
      }
      return result;
    } catch (ClassNotFoundException e) {
      throw new TypeNotFoundException(e, name);
    }
  }

}
