/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.api;

/**
 * This is the interface for a factory used to create {@link DataResource}s.<br>
 * If you want to read resources from configurable locations, you should use
 * this factory. In situations where you want to load a resource, for which you
 * already know the location (e.g. from a specific classpath location) and you
 * are going to wire the location into your code, you can bypass this factory
 * and simply create a new instance of the according {@link DataResource}
 * -implementation (e.g. {@link net.sf.mmm.util.resource.base.ClasspathResource}
 * ).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface DataResourceFactory {

  /**
   * This method creates a new {@link DataResource} from the given
   * <code>resourceUri</code>. The following URI-schemes are guaranteed to be
   * supported:
   * <table border="1">
   * <tr>
   * <th>scheme</th>
   * <th>example URI</th>
   * <th>default implementation</th>
   * </tr>
   * <tr>
   * <td>file</td>
   * <td>file:///tmp/foo.xml</td>
   * <td>{@link net.sf.mmm.util.resource.base.FileResource}</td>
   * </tr>
   * <tr>
   * <td>classpath</td>
   * <td>classpath:net/sf/mmm/util/beans-core.xml</td>
   * <td>{@link net.sf.mmm.util.resource.base.ClasspathResource}</td>
   * </tr>
   * <tr>
   * <td>ftp|http|https|... (whatever supported by {@link java.net.URL})</td>
   * <td>http://m-m-m.sourceforge.net/maven/</td>
   * <td>{@link net.sf.mmm.util.resource.base.UrlResource}</td>
   * </tr>
   * </table>
   * 
   * @param resourceUri is the absolute URI pointing to the location of the
   *        requested {@link DataResource}.
   * @return the requested {@link DataResource}.
   * @throws ResourceUriUndefinedException if the given <code>resourceUri</code>
   *         is undefined (e.g. the scheme is NOT supported).
   */
  DataResource createDataResource(String resourceUri) throws ResourceUriUndefinedException;

}
