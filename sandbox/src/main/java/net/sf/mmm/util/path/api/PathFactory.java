/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.path.api;

import java.nio.file.Path;

/**
 * This is the interface for a factory used to create instance of {@link Path} across multiple
 * {@link java.nio.file.FileSystem}s. <br>
 * If you want to read resources from configurable locations, you should use this factory. In situations where
 * you want to load a resource, for which you already know the location (e.g. from a specific classpath
 * location) and you are going to wire the location into your code, you can bypass this factory and simply
 * create a new instance of the according {@link Path} directly (e.g.
 * {@link java.nio.file.Paths#get(java.net.URI)}).
 *
 * @see java.nio.file.Paths#get(java.net.URI)
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 5.0.0
 */
public interface PathFactory {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  String CDI_NAME = "net.sf.mmm.util.file.api.PathFactory";

  /**
   * This method creates a new {@link Path} for the given {@code resourceUri}. The following URI-schemes are
   * guaranteed to be supported:
   * <table border="1">
   * <tr>
   * <th>scheme</th>
   * <th>example URI</th>
   * </tr>
   * <tr>
   * <td>{@link PathUri#SCHEME_PREFIX_FILE file}</td>
   * <td>file:///tmp/foo.xml</td>
   * </tr>
   * <tr>
   * <td>{@link PathUri#SCHEME_PREFIX_CLASSPATH classpath}</td>
   * <td>classpath:net/sf/mmm/util/beans-core.xml</td>
   * </tr>
   * <tr>
   * <td>{@link PathUri#SCHEME_PREFIX_HTTP http}</td>
   * <td>http://m-m-m.sourceforge.net/maven/</td>
   * </tr>
   * <tr>
   * <td>{@link PathUri#SCHEME_PREFIX_HTTPS https}</td>
   * <td>https://github.com/m-m-m/mmm/</td>
   * </tr>
   * </table>
   *
   * @param resourceUri is the {@link Path#toUri() absolute URI} pointing to the location of the requested
   *        {@link Path}.
   * @return the requested {@link Path}.
   */
  Path createPath(String resourceUri);

  /**
   * This method creates a new {@link Path} for the given parameters. If the given {@code resourcePath} is
   * absolute (including {@link PathUri#getSchemePrefix() scheme}), then this method returns the same result
   * as {@link #createPath(String) createPath}{@code (resourcePath)}. Otherwise the resource path will be
   * treated relative to the given {@code basePath}.
   *
   * @param basePath is the {@link Path} to use if {@code resourcePath} is relative. You typically want to
   *        provide the {@link Path#getParent() parent directory} of a resource to navigate from.
   * @param resourcePath is the absolute or relative path to the requested resource.
   * @return the new path.
   */
  Path createPath(Path basePath, String resourcePath);

}
