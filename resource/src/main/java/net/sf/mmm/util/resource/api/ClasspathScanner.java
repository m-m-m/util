/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.api;

import net.sf.mmm.util.filter.api.Filter;

/**
 * This is the interface for a component that scans the classpath for resources. It makes your classpath a
 * browsable filesystem that allows to walk through all packages like directories and list their content.
 * <br/>
 * <b>ATTENTION:</b><br/>
 * On the first call of any of the {@code getClasspathResource*()} methods the entire classpath will be
 * scanned and the result will be cached. This is an expensive operation. However, after the results are
 * cached further invocations are relatively cheap. Please note that only the "directory structure" is kept in
 * cache but not any content. Invocations to read file contents from the classpath will always return the
 * recent data without caching. If you have a dynamic classpath where resources can be added at runtime see
 * {@link #clearCaches()}. If you create and initialize the implementation (manually or via
 * dependency-injection) it shall not scan the classpath or build any caches before any of the actual
 * {@code getClasspathResource*()} methods are invoked.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.0.0
 */
public interface ClasspathScanner {

  /**
   * @return the {@link BrowsableResource} for the root (or default) package.
   */
  BrowsableResource getClasspathResource();

  /**
   * @param classpath is the classpath location pointing to a {@link Package} or
   *        {@link net.sf.mmm.util.resource.base.ClasspathResource}.
   * @return the {@link BrowsableResource} for the given {@code classpath}.
   */
  BrowsableResource getClasspathResource(String classpath);

  /**
   * @param pkg is the {@link Package} to get.
   * @return the {@link BrowsableResource} pointing to the given package.
   */
  BrowsableResource getClasspathResource(Package pkg);

  /**
   * @return an {@link Iterable} with ALL existing {@link BrowsableResource#isData() file} resources on the
   *         classpath.
   */
  Iterable<? extends BrowsableResource> getClasspathResourceFiles();

  /**
   * @param filter decides which {@link #getClasspathResourceFiles() file resources} to
   *        {@link Filter#accept(Object) accept}. Please note that the {@link BrowsableResource#getName()
   *        name} of the filtered resources will end with ".class" in case of a {@link Class}.
   * @return an {@link Iterable} with all {@link #getClasspathResourceFiles() resource files} on the classpath
   *         {@link Filter#accept(Object) accepted} by the given {@code filter}.
   */
  Iterable<? extends BrowsableResource> getClasspathResourceFiles(Filter<? super BrowsableResource> filter);

  /**
   * @param classnameFilter a {@link Filter} used to {@link Filter#accept(Object) filter} by
   *        {@link Class#getName() qualified classname} (to prevent unnecessary classloading).
   * @param classFilter a {@link Filter} used to {@link Filter#accept(Object) filter} the {@link Class}es that
   *        have passed the {@code classnameFilter} and should be returned.
   * @return an {@link Iterable} with the {@link Filter#accept(Object) accepted} classes.
   * @since 7.3.0
   */
  Iterable<Class<?>> getClasspathResourceClasses(Filter<String> classnameFilter, Filter<Class<?>> classFilter);

  /**
   * @param classResource a {@link DataResource} that identifies a Java *.{@link Class class} file or a
   *        {@link Package} from your classpath. Should be retrieved from this {@link ClasspathScanner}.
   * @return the corresponding qualified {@link Class#getName() class name} or {@link Package#getName()
   *         package name}. Will be {@code null} in case the given {@link DataResource} is not pointing to a
   *         {@link Class} or {@link Package}.
   * @since 7.3.0
   */
  String getQualifiedName(DataResource classResource);

  /**
   * @param <T> the generic type of the returned {@link Class} for simplistic usage.
   * @param classResource a {@link BrowsableResource} that identifies a Java *.class file from your classpath.
   *        Should be retrieved from this {@link ClasspathScanner}.
   * @return the loaded {@link Class}.
   * @throws IllegalArgumentException if the given {@link BrowsableResource} does not point to a *.class file.
   * @since 7.3.0
   */
  <T> Class<T> loadClass(BrowsableResource classResource) throws IllegalArgumentException;

  /**
   * Evicts all cache data so that any further invocation of the other methods will rebuild the cached data
   * from scratch. <br/>
   * <b>ATTENTION:</b><br/>
   * Using this method can be very expensive for subsequent calls. A regular Java application has a static
   * classpath so this method does not make any sense. Only use this method if you have dynamic class-loaders
   * and you are fully aware of performance implications.
   */
  void clearCaches();

}
