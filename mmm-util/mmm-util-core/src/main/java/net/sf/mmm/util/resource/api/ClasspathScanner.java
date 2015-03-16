/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.api;

import net.sf.mmm.util.component.api.ComponentSpecification;
import net.sf.mmm.util.filter.api.Filter;

/**
 * This is the interface for a component that scans the classpath for resources. It makes your classpath a
 * browsable filesystem that allows to walk through all packages like directories and list their content. <br/>
 * <b>ATTENTION:</b><br/>
 * On the first call of any of the {@code getClasspathResource*()} methods the entire classpath will be
 * scanned and the result will be cached. This is an expensive operation. However, after the results are
 * cached further invocations are relatively cheap. Please note that only the "directory structure" is kept in
 * cache but not any content. Invocations to read file contents from the classpath will always return the
 * recent data without caching. If you have a dynamic classpath where resources can be added at runtime see
 * {@link #clearCaches()}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.0.0
 */
@ComponentSpecification
public interface ClasspathScanner {

  /**
   * @return the {@link BrowsableResource} for the root (or default) package.
   */
  BrowsableResource getClasspathResource();

  /**
   * @param classpath is the classpath location pointing to a {@link Package} or
   *        {@link net.sf.mmm.util.resource.base.ClasspathResource}.
   * @return the {@link BrowsableResource} for the given <code>classpath</code>.
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
   * Evicts all cache data so that any further invocation of the other methods will rebuild the cached data
   * from scratch. <br/>
   * <b>ATTENTION:</b><br/>
   * Using this method can be very expensive for subsequent calls. A regular Java application has a static
   * classpath so this method does not make any sense. Only use this method if you have dynamic class-loaders
   * and you are fully aware of performance implications.
   */
  void clearCaches();

}
