/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.api;

/**
 * This is the interface for a factory used to create instances of {@link BrowsableResource}.
 *
 * @see DataResourceFactory
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public interface BrowsableResourceFactory extends DataResourceFactory {

  /**
   * This method creates a new {@link BrowsableResource} from the given {@code resourceUri}. The following
   * URI-schemes are guaranteed to be supported:
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
   * </table>
   *
   * @param resourceUri is the {@link DataResource#getUri() absolute URI} pointing to the location of the
   *        requested {@link BrowsableResource}.
   * @return the requested {@link BrowsableResource}.
   * @throws ResourceUriUndefinedException if the given {@code resourceUri} is undefined (e.g. the scheme is
   *         NOT supported).
   */
  BrowsableResource createBrowsableResource(String resourceUri) throws ResourceUriUndefinedException;

}
