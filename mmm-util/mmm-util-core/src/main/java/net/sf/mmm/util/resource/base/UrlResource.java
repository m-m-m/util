/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.base;

import java.net.MalformedURLException;
import java.net.URL;

import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.ResourceNotAvailableException;
import net.sf.mmm.util.resource.api.ResourceUriUndefinedException;

/**
 * This is the implementation of the {@link DataResource} interface for a
 * resource that comes from an {@link URL}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class UrlResource extends AbstractDataResource {

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
      throw new ResourceUriUndefinedException(e, absolutePath);
    }
  }

  /**
   * The constructor.
   * 
   * @param url is the {@link URL}.
   */
  public UrlResource(URL url) {

    super();
    if (url == null) {
      throw new NlsNullPointerException("url");
    }
    this.url = url;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getSchemePrefix() {

    if (this.url == null) {
      return "url:";
    } else {
      return this.url.getProtocol() + "://";
    }
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
      throw new ResourceUriUndefinedException(e, relativePath);
    }
  }

}
