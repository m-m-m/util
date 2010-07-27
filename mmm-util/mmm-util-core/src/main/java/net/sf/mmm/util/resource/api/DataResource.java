/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.api;

import java.io.InputStream;
import java.net.URL;

import net.sf.mmm.util.io.api.RuntimeIoException;

/**
 * This is the interface for a resource containing data. You can think of a
 * {@link DataResource} as a {@link java.io.File file} but it may come from
 * other sources than the filesystem.<br>
 * The major reason for naming it {@link DataResource} is that
 * <code>Resource</code> is a very general name already occupied by
 * {@link javax.annotation.Resource}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public interface DataResource {

  /**
   * This method determines if this resource is available. Available simply
   * means that it exists and {@link #openStream() data can be read}.
   * 
   * @return <code>true</code> if this resource is available, <code>false</code>
   *         otherwise.
   */
  boolean isAvailable();

  /**
   * This method gets the path of this resource. Please note that the path is
   * including the {@link #getName() name} of the resource.
   * 
   * @return the path that was used to identify this resource when creating.
   */
  String getPath();

  /**
   * This method gets the name of the resource. It is analog to a
   * {@link java.io.File#getName() filename}.
   * 
   * @return the name of the resource.
   * @since 2.0.0
   */
  String getName();

  /**
   * This method gets the size (content-length) of this resource.
   * 
   * @return the size of this resource.
   * @throws ResourceNotAvailableException if this resource is NOT
   *         {@link #isAvailable() available}.
   */
  long getSize() throws ResourceNotAvailableException;

  /**
   * This method gets this resource as {@link URL}.
   * 
   * @return the URL that represents this resource.
   * @throws ResourceNotAvailableException if an URL can NOT be created because
   *         the represented resource does not exist.
   */
  URL getUrl() throws ResourceNotAvailableException;

  /**
   * This method gets a string identifying this {@link DataResource}. In most
   * cases this will be the same as {@link Object#toString()
   * string-representation} of the {@link #getUrl() URL}. However this method
   * will not throw an exception.
   * 
   * @return the URI as string.
   */
  String getUri();

  /**
   * This method opens this resource for reading.
   * 
   * @see java.net.URL#openStream()
   * 
   * @return the input stream where to read from.
   * @throws ResourceNotAvailableException if this resource is NOT
   *         {@link #isAvailable() available}.
   * @throws RuntimeIoException if an input/output error occurred.
   */
  InputStream openStream() throws ResourceNotAvailableException, RuntimeIoException;

  /**
   * This method creates a new {@link DataResource} pointing to the given
   * <code>resourcePath</code> based on this resource.<br>
   * E.g. if this resource points to the file "/etc/init.d/rc" and
   * <code>relativePath</code> would be "../apt/sources.list" the resulting
   * resource would point to "/etc/apt/sources.list".
   * 
   * @param resourcePath is the absolute or relative path pointing to a new
   *        resource. If it is a relative path, it is interpreted relative to
   *        the parent URI (directory) of this resource.
   * @return is the resource pointing to the given path (relative to this
   *         resource).
   * @throws ResourceUriUndefinedException if the given
   *         <code>resourcePath</code> leads to an undefined or illegal URI.
   */
  DataResource navigate(String resourcePath) throws ResourceUriUndefinedException;

}
