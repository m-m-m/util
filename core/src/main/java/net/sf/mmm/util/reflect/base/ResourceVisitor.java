/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

/**
 * This is the interface for a visitor of resources by their absolute classpath.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public interface ResourceVisitor {

  /**
   * This method is invoked for each traversed package.
   *
   * @param classpath is the absolute classpath of the traversed package (e.g. "net/sf/mmm/util/reflect").
   * @return {@code true} if the package should be traversed recursively, {@code false} if the package and all its
   *         content (including sub-packages) should be skipped.
   */
  boolean visitPackage(String classpath);

  /**
   * This method is invoked for each resource.
   *
   * @param classpath is the absolute classpath of the resource (e.g. "net/sf/mmm/util/reflect/api/ReflectionUtil.class"
   *        or "net/sf/mmm/util/reflect/beans-reflect.xml").
   */
  void visitResource(String classpath);

}
