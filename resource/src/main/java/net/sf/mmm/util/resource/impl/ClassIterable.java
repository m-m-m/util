/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.impl;

import java.util.Collection;
import java.util.Iterator;

import net.sf.mmm.util.filter.api.Filter;

/**
 * An implementation of {@link Iterable} that iterates the {@link Class}es filtered according to
 * {@link ClasspathScannerImpl#getClasspathResourceClasses(Filter, Filter)}.
 *
 * @author hohwille
 * @since 7.3.0
 */
class ClassIterable implements Iterable<Class<?>> {

  private final Iterable<ClasspathFile> classpathFiles;

  private final Filter<String> classnameFilter;

  private final Filter<Class<?>> classFilter;

  /**
   * The constructor.
   *
   * @param classpathFiles is the {@link Iterable} to adapt.
   * @param classnameFilter the {@link Class#getName() classname} {@link Filter}.
   * @param classFilter the {@link Class} {@link Filter}.
   */
  public ClassIterable(Collection<ClasspathFile> classpathFiles, Filter<String> classnameFilter,
      Filter<Class<?>> classFilter) {

    super();
    this.classpathFiles = classpathFiles;
    this.classnameFilter = classnameFilter;
    this.classFilter = classFilter;
  }

  @Override
  public Iterator<Class<?>> iterator() {

    return new ClassIterator(this.classpathFiles.iterator(), this.classnameFilter, this.classFilter);
  }
}