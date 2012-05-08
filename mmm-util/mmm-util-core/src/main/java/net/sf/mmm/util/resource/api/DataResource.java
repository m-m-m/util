/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.api;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;

import net.sf.mmm.util.io.api.RuntimeIoException;

/**
 * This is the interface for a resource {@link #isData() potentially} containing data. You can think of a
 * {@link DataResource} as a {@link java.io.File file} but it may come from other sources than the filesystem.<br>
 * The major reason for naming it {@link DataResource} is that <code>Resource</code> is a very general name
 * already occupied by {@link javax.annotation.Resource}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public interface DataResource {

  /**
   * This method determines if this resource is available. Available simply means that it exists and
   * {@link #openStream() data can be read}.<br/>
   * <b>ATTENTION:</b><br>
   * Depending on the underlying implementation this can be a relatively expensive operation. E.g. if this
   * resource points to a remote URL this method has to open a network connection in order to verify if the
   * resource is available. Use {@link #isData()} to prevent such expensive operaitons.
   * 
   * @return <code>true</code> if this resource is available, <code>false</code> otherwise.
   */
  boolean isAvailable();

  /**
   * This method determines if this resource has potentially data {@link #isAvailable() available}. Unlike
   * {@link #isAvailable()} this method will not invoke expensive operations like connecting to remote URLs.
   * If this method will return <code>false</code>, then {@link #isAvailable()} would also have returned
   * <code>false</code>. However in case of <code>true</code> only {@link #isAvailable()} can guarantee if a
   * resource really exists and contains data. E.g. if the resource points to a {@link java.io.File} then this
   * method can check if it is a {@link java.io.File#isFile() data-file}. So in case it points to a directory
   * or does not exist at all in the filesystem, this method will return <code>false</code>. Please also note
   * that this may invoke expensive operations if the according directory path points to something like a
   * network share. You should also be aware that the state of {@link #isData()} and {@link #isAvailable()}
   * can change at any time so you never have a full guarantee if some data exists or NOT. However in most
   * cases it is very improbable that this status changes when you {@link #openStream() read} the resource
   * immediately after the check.
   * 
   * @return <code>true</code> if this resource points to potential data, <code>false</code> otherwise.
   * @since 2.0.0
   */
  boolean isData();

  /**
   * This method gets the path of this resource. Please note that the path is including the {@link #getName()
   * name} of the resource.<br/>
   * <b>ATTENTION:</b><br>
   * The result of this method may differ to the path used in the URL when this resource has been
   * {@link DataResourceFactory#createDataResource(String) created}.
   * 
   * @return the path that was used to identify this resource when creating.
   */
  String getPath();

  /**
   * This method gets the name of the resource. It is analog to a {@link java.io.File#getName() filename}.
   * 
   * @return the name of the resource.
   * @since 2.0.0
   */
  String getName();

  /**
   * This method gets the size (content-length) of this resource.
   * 
   * @return the size of this resource.
   * @throws ResourceNotAvailableException if this resource is NOT {@link #isAvailable() available}.
   */
  long getSize() throws ResourceNotAvailableException;

  /**
   * This method gets this resource as {@link URL}.
   * 
   * @return the URL that represents this resource.
   * @throws ResourceNotAvailableException if an URL can NOT be created because the represented resource does
   *         not exist.
   */
  URL getUrl() throws ResourceNotAvailableException;

  /**
   * This method gets a string identifying this {@link DataResource}. In most cases this will be the same as
   * {@link Object#toString() string-representation} of the {@link #getUrl() URL}. However this method will
   * not throw an exception.
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
   * @throws ResourceNotAvailableException if this resource is NOT {@link #isAvailable() available}.
   * @throws RuntimeIoException if an input/output error occurred.
   */
  InputStream openStream() throws ResourceNotAvailableException, RuntimeIoException;

  /**
   * This method opens an output-stream in order to write data to the resource.
   * 
   * @return the {@link OutputStream} to write to the resource.
   * @throws ResourceNotAvailableException if this resource is NOT {@link #isAvailable() available}.
   * @throws ResourceNotWritableException if the resource is NOT writable (e.g. read-only).
   * @throws RuntimeIoException if an input/output error occurred.
   * @since 2.0.0
   */
  OutputStream openOutputStream() throws ResourceNotAvailableException, ResourceNotWritableException,
      RuntimeIoException;

  /**
   * This method creates a new {@link DataResource} pointing to the given <code>resourcePath</code> based on
   * this resource.<br>
   * E.g. if this resource points to the file "/etc/init.d/rc" and <code>relativePath</code> would be
   * "../apt/sources.list" the resulting resource would point to "/etc/apt/sources.list".
   * 
   * @param resourcePath is the absolute or relative path pointing to a new resource. If it is a relative
   *        path, it is interpreted relative to the parent URI (directory) of this resource.
   * @return is the resource pointing to the given path (relative to this resource).
   * @throws ResourceUriUndefinedException if the given <code>resourcePath</code> leads to an undefined or
   *         illegal URI.
   */
  DataResource navigate(String resourcePath) throws ResourceUriUndefinedException;

  /**
   * This method gets the last modification date of the {@link DataResource} if {@link #isAvailable()
   * available} and supported.
   * 
   * @return the last modification {@link Date} or <code>null</code> if not available or supported.
   * @since 2.0.0
   */
  Date getLastModificationDate();

  /**
   * This method determines if this resource has been been modified since the given <code>data</code>.
   * 
   * @param date is the {@link Date} to check for.
   * @return <code>true</code> if the resource has been modified after the given <code>date</code>,
   *         <code>false</code> if it has NOT been modified after the given <code>date</code> and
   *         <code>null</code> if this can NOT be determined (resource not {@link #isAvailable() available} or
   *         operation NOT supported by this resource).
   * @since 2.0.0
   */
  Boolean isModifiedSince(Date date);

}
