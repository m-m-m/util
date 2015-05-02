/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import java.util.Set;

import net.sf.mmm.util.filter.api.Filter;

/**
 * This is an implementation of the {@link ResourceVisitor} interface that collects the names of all resources
 * {@link Filter#accept(Object) accepted} by a given filter in a {@link Set}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class ResourceNameCollector implements ResourceVisitor {

  /** {@link Set} to collect resource-names. */
  private final Set<String> resourceSet;

  /** {@link Filter} to accept resource-names. */
  private final Filter<? super String> filter;

  /**
   * The constructor.
   *
   * @param resourceSet is the {@link Set} where collected resource-names will be added.
   * @param filter is used to {@link Filter#accept(Object) filter} resources by name.
   */
  public ResourceNameCollector(Set<String> resourceSet, Filter<? super String> filter) {

    super();
    this.resourceSet = resourceSet;
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

    if (this.filter.accept(classpath)) {
      this.resourceSet.add(classpath);
    }
  }
}
