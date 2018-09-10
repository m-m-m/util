/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.api;

import java.util.Set;

import net.sf.mmm.util.filter.api.Filter;

/**
 * This is the interface for a collection of utility functions to deal with {@link DataResource resources}. It
 * allows to scan the classpath to find arbitrary resources.<br/>
 *
 * @author hohwille
 * @since 7.6.0
 */
public interface ResourceUtil {

  /**
   * This method finds all resources that are located in the package identified by the given
   * {@code packageName}. <br>
   * <b>ATTENTION:</b><br>
   * This is a relative expensive operation. Depending on your classpath multiple directories, JAR-, and
   * WAR-files may need to be scanned.
   *
   * @param packageName is the name of the {@link Package} to scan. Both "." and "/" are accepted as separator
   *        (e.g. "net.sf.mmm.util.reflect).
   * @param includeSubPackages - if {@code true} all sub-packages of the specified {@link Package} will be
   *        included in the search.
   * @param filter is used to {@link Filter#accept(Object) filter} the resources. Typically you will exclude
   *        resources that end with ".class" or only accept resources that end with ".xml".
   * @return a {@link Set} with all requested {@link DataResource resources}.
   * @throws IllegalStateException if the operation failed with an I/O error.
   * @since 1.1.0
   */
  Set<DataResource> findResources(String packageName, boolean includeSubPackages, Filter<? super String> filter);

  /**
   * This method finds all resources that match to the given {@code absoluteClasspath}. Unlike
   * {@link net.sf.mmm.util.resource.base.ClasspathResource#ClasspathResource(String)} this method will return
   * all resources that with the given classpath (e.g. out of multiple JAR-files). <br>
   *
   * @param absoluteClasspath is the absolute path to the resource. E.g.
   *        "net/sf/mmm/util/resource/ClasspathResource.txt".
   * @return a {@link Set} with all requested {@link DataResource resources}.
   * @throws IllegalStateException if the operation failed with an I/O error.
   * @since 1.1.0
   */
  Set<DataResource> findResources(String absoluteClasspath);

  /**
   * This method finds all resources that are located in the package identified by the given
   * {@code packageName}. <br>
   * <b>ATTENTION:</b><br>
   * This is a relative expensive operation. Depending on your classpath multiple directories, JAR-, and
   * WAR-files may need to be scanned.
   *
   * @param packageName is the name of the {@link Package} to scan. Both "." and "/" are accepted as separator
   *        (e.g. "net.sf.mmm.util.reflect).
   * @param includeSubPackages - if {@code true} all sub-packages of the specified {@link Package} will be
   *        included in the search.
   * @param filter is used to {@link Filter#accept(Object) filter} the resources. Typically you will exclude
   *        resources that end with ".class" or only accept resources that end with ".xml".
   * @param classLoader is the explicit {@link ClassLoader} to use.
   * @return a {@link Set} with all requested {@link DataResource resources}.
   * @throws IllegalStateException if the operation failed with an I/O error.
   * @since 1.1.0
   */
  Set<DataResource> findResources(String packageName, boolean includeSubPackages, Filter<? super String> filter, ClassLoader classLoader);

}
