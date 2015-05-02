/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

/**
 * This is a {@link MappedClassResolver} that contains the mapping for typical {@link Class classes} located
 * in the package <code>java.lang</code>. <br>
 * <b>ATTENTION:</b><br>
 * This class does NOT contain the mapping for all possible types in <code>java.lang</code> but only common
 * data-types and important classes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class JavaLangClassResolver extends MappedClassResolver {

  /** The singleton instance. */
  public static final JavaLangClassResolver INSTANCE = new JavaLangClassResolver();

  /**
   * The constructor.
   */
  public JavaLangClassResolver() {

    super();
    addClassMapping(Boolean.class);
    addClassMapping(Byte.class);
    addClassMapping(Character.class);
    addClassMapping(CharSequence.class);
    addClassMapping(Class.class);
    addClassMapping(ClassLoader.class);
    addClassMapping(Cloneable.class);
    addClassMapping(Comparable.class);
    addClassMapping(Compiler.class);
    addClassMapping(Double.class);
    addClassMapping(Enum.class);
    addClassMapping(Error.class);
    addClassMapping(Float.class);
    addClassMapping(Integer.class);
    addClassMapping(Iterable.class);
    addClassMapping(Long.class);
    addClassMapping(Number.class);
    addClassMapping(Object.class);
    addClassMapping(Package.class);
    addClassMapping(Process.class);
    addClassMapping(RuntimeException.class);
    addClassMapping(Short.class);
    addClassMapping(String.class);
    addClassMapping(StringBuffer.class);
    addClassMapping(StringBuilder.class);
    addClassMapping(Thread.class);
    addClassMapping(ThreadGroup.class);
    addClassMapping(Throwable.class);
    addClassMapping(Void.class);
  }

}
