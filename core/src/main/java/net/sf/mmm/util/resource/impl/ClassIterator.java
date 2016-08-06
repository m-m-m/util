/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.impl;

import java.util.Iterator;

import net.sf.mmm.util.collection.base.AbstractIterator;
import net.sf.mmm.util.filter.api.Filter;

/**
 * An implementation of {@link Iterator} that iterates the {@link Class}es filtered according to
 * {@link ClasspathScannerImpl#getClasspathResourceClasses(Filter, Filter)}.
 *
 * @author hohwille
 * @since 7.3.0
 */
class ClassIterator extends AbstractIterator<Class<?>> {

  private final Iterator<ClasspathFile> classpathFiles;

  private final Filter<String> classnameFilter;

  private final Filter<Class<?>> classFilter;

  /**
   * The constructor.
   *
   * @param classpathFiles is the {@link Iterator} to adapt.
   * @param classnameFilter the {@link Class#getName() classname} {@link Filter}.
   * @param classFilter the {@link Class} {@link Filter}.
   */
  public ClassIterator(Iterator<ClasspathFile> classpathFiles, Filter<String> classnameFilter,
      Filter<Class<?>> classFilter) {

    super();
    this.classpathFiles = classpathFiles;
    this.classnameFilter = classnameFilter;
    this.classFilter = classFilter;
    findFirst();
  }

  @Override
  protected Class<?> findNext() {

    ClasspathFile classpathFile = null;
    while (this.classpathFiles.hasNext()) {
      classpathFile = this.classpathFiles.next();
      String qualifiedName = classpathFile.getQualifiedName();
      if ((qualifiedName != null) && this.classnameFilter.accept(qualifiedName)) {
        Class<?> javaClass = classpathFile.getJavaClass();
        if (this.classFilter.accept(javaClass)) {
          return javaClass;
        }
      }
    }
    return null;
  }
}