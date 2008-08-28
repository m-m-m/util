/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource;

import java.net.MalformedURLException;
import java.net.URL;

import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;

/**
 * This is the implementation of the {@link DataResource} interface for a
 * resource that comes from an {@link URL}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UrlResource extends AbstractResource {

  /** @see #getUrl() */
  private final URL url;

  /**
   * The constructor.
   * 
   * @param absolutePath is the absolute path to the resource. E.g.
   *        "http://foo.bar/index.html".
   */
  public UrlResource(String absolutePath) {

    super();
    try {
      this.url = new URL(absolutePath);
    } catch (MalformedURLException e) {
      throw new NlsIllegalArgumentException(e, NlsBundleResource.ERR_ILLEGAL_PATH, absolutePath);
    }
  }

  /**
   * The constructor.
   * 
   * @param url is the {@link URL}.
   */
  public UrlResource(URL url) {

    super();
    this.url = url;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isAvailable() {

    return (this.url != null);
  }

  /**
   * {@inheritDoc}
   */
  public String getPath() {

    if (this.url == null) {
      return null;
    }
    return this.url.toString();
  }

  /**
   * {@inheritDoc}
   */
  public URL getUrl() throws ResourceNotAvailableException {

    if (this.url == null) {
      throw new ResourceNotAvailableException(getPath());
    }
    return this.url;
  }

  /**
   * {@inheritDoc}
   */
  public DataResource navigate(String relativePath) {

    try {
      return new UrlResource(new URL(this.url, relativePath));
    } catch (MalformedURLException e) {
      throw new NlsIllegalArgumentException(e, NlsBundleResource.ERR_ILLEGAL_PATH, relativePath);
    }
  }

}
