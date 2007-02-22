/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * This is the interface for a resource. You can think of a {@link DataResource}
 * as a {@link java.io.File file} but it may come from other sources than the
 * filesystem.<br>
 * The major reason for naming it {@link DataResource} is that
 * <code>Resource</code> is a very general name already occupied by
 * {@link javax.annotation.Resource}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface DataResource {

  /**
   * This method determines if this resource is available. Available simply
   * means that it exists.
   * 
   * @return <code>true</code> if this resource is available,
   *         <code>false</code> otherwise.
   */
  boolean isAvailable();

  /**
   * This method gets the path of this resource.
   * 
   * @return the path that was used to identify this resource when creating.
   */
  String getPath();

  /**
   * This method gets the size (content-length) of this resource.
   * 
   * @return the size of this resource.
   * @throws ResourceNotAvailableException
   *         if this resource is NOT {@link #isAvailable() available}.
   */
  long getSize() throws ResourceNotAvailableException;

  /**
   * This method gets this resource as {@link URL}.
   * 
   * @return the url that represents this resource.
   * @throws ResourceNotAvailableException
   *         if this resource is NOT {@link #isAvailable() available}.
   */
  URL getUrl() throws ResourceNotAvailableException;

  /**
   * This method opens this resource for reading.
   * 
   * @see URL#openStream()
   * 
   * @return the input stream where to read from.
   * @throws ResourceNotAvailableException
   *         if this resource is NOT {@link #isAvailable() available}.
   * @throws IOException
   */
  InputStream openStream() throws ResourceNotAvailableException, IOException;

}
