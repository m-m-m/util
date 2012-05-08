/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import java.util.Set;

import net.sf.mmm.util.filter.api.Filter;

/**
 * This is an implementation of the {@link ResourceVisitor} interface that collects the names of all classes
 * {@link Filter#accept(Object) accepted} by a given filter in a {@link Set}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class ClassNameCollector implements ResourceVisitor {

  /** Suffix for classes as resource. */
  private static final String SUFFIX_CLASS = ".class";

  /** {@link Set} to collect class-names. */
  private final Set<String> classNameSet;

  /** {@link Filter} to accept class-names. */
  private final Filter<String> filter;

  /**
   * The constructor.
   * 
   * @param classNameSet is the {@link Set} where collected {@link Class}-names will be added.
   * @param filter is used to {@link Filter#accept(Object) filter} classes by name.
   */
  public ClassNameCollector(Set<String> classNameSet, Filter<String> filter) {

    super();
    this.classNameSet = classNameSet;
    this.filter = filter;
  }

  /**
   * {@inheritDoc}
   */
  public boolean visitPackage(String classpath) {

    // return this.filter.accept(classpath);
    return true;
  }

  /**
   * {@inheritDoc}
   */
  public void visitResource(String classpath) {

    if (classpath.endsWith(SUFFIX_CLASS)) {
      String classname = classpath.substring(0, classpath.length() - SUFFIX_CLASS.length()).replace('/', '.');
      if (this.filter.accept(classname)) {
        this.classNameSet.add(classname);
      }
    }
  }
}
