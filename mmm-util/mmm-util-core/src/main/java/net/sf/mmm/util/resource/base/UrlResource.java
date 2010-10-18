/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.base;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.mmm.util.file.api.FileUtil;
import net.sf.mmm.util.file.base.FileUtilImpl;
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

  /** The {@link #getSchemePrefix() scheme-prefix} for HTTP. */
  public static final String SCHEME_PREFIX_HTTP = "http://";

  /** The {@link #getSchemePrefix() scheme-prefix} for HTTPS. */
  public static final String SCHEME_PREFIX_HTTPS = "https://";

  /** The {@link #getSchemePrefix() scheme-prefix} for FTP. */
  public static final String SCHEME_PREFIX_FTP = "ftp://";

  /** @see #getUrl() */
  private final URL url;

  /** The {@link FileUtil} instance. */
  private final FileUtil fileUtil;

  /**
   * The constructor.
   * 
   * @param url is the URL to the resource. E.g. "http://foo.bar/index.html".
   */
  public UrlResource(String url) {

    this(url, FileUtilImpl.getInstance());
  }

  /**
   * The constructor.
   * 
   * @param absolutePath is the absolute path to the resource. E.g.
   *        "http://foo.bar/index.html".
   * @param fileUtil is the {@link FileUtil} to use.
   */
  public UrlResource(String absolutePath, FileUtil fileUtil) {

    super();
    try {
      this.url = new URL(absolutePath);
      this.fileUtil = fileUtil;
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

    this(url, FileUtilImpl.getInstance());
  }

  /**
   * The constructor.
   * 
   * @param url is the {@link URL}.
   * @param fileUtil is the {@link FileUtil} to use.
   */
  public UrlResource(URL url, FileUtil fileUtil) {

    super();
    if (url == null) {
      throw new NlsNullPointerException("url");
    }
    this.url = url;
    this.fileUtil = fileUtil;
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
  @Override
  public boolean isAvailable() {

    if (this.url == null) {
      return false;
    }
    try {
      long length = this.url.openConnection().getContentLength();
      return (length != -1);
    } catch (IOException e) {
      return false;
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean isData() {

    return (this.url != null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getPath() {

    if (this.url == null) {
      return null;
    }
    return this.url.getPath();
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
  @Override
  public String getName() {

    if (this.url == null) {
      return null;
    }
    String path = this.url.getPath();
    return this.fileUtil.getBasename(path);
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
