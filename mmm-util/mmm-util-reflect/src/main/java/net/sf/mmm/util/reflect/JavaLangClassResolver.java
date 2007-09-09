/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a {@link MappedClassResolver} that contains the mapping for typical
 * {@link Class classes} located in the package <code>java.lang</code>.<br>
 * <b>ATTENTION:</b><br>
 * This class does NOT contain the mapping for all possible types in
 * <code>java.lang</code> but only common data-types and important classes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class JavaLangClassResolver extends MappedClassResolver {

  /** @see #resolveClass(String) */
  private final Map<String, Class> name2classMap;

  /**
   * The constructor.
   */
  public JavaLangClassResolver() {

    super();
    this.name2classMap = new HashMap<String, Class>();
    register(Boolean.class);
    register(Byte.class);
    register(Character.class);
    register(CharSequence.class);
    register(Class.class);
    register(ClassLoader.class);
    register(Cloneable.class);
    register(Comparable.class);
    register(Compiler.class);
    register(Double.class);
    register(Enum.class);
    register(Error.class);
    register(Float.class);
    register(Integer.class);
    register(Iterable.class);
    register(Long.class);
    register(Number.class);
    register(Object.class);
    register(Package.class);
    register(Process.class);
    register(RuntimeException.class);
    register(Short.class);
    register(String.class);
    register(StringBuffer.class);
    register(StringBuilder.class);
    register(Thread.class);
    register(ThreadGroup.class);
    register(Throwable.class);
    register(Void.class);
  }

  /**
   * This method adds the given type to the internal cache.
   * 
   * @param type is the type to cache.
   */
  private void register(Class type) {

    this.name2classMap.put(type.getSimpleName(), type);
  }

  /**
   * {@inheritDoc}
   */
  public Class resolveClass(String name) throws ClassNotFoundException {

    Class result = this.name2classMap.get(name);
    if (result == null) {
      result = Class.forName(name);
    }
    return result;
  }

}
