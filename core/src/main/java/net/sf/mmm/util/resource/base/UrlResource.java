/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.base;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.file.api.FileUtilLimited;
import net.sf.mmm.util.file.base.FileUtilLimitedImpl;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.ResourceNotAvailableException;
import net.sf.mmm.util.resource.api.ResourceUri;
import net.sf.mmm.util.resource.api.ResourceUriUndefinedException;

/**
 * This is the implementation of the {@link DataResource} interface for a resource that comes from an {@link URL}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class UrlResource extends AbstractDataResource {

  /** The {@link #getSchemePrefix() scheme-prefix} for HTTP. */
  public static final String SCHEME_PREFIX_HTTP = ResourceUri.SCHEME_PREFIX_HTTP;

  /** The {@link #getSchemePrefix() scheme-prefix} for HTTPS. */
  public static final String SCHEME_PREFIX_HTTPS = ResourceUri.SCHEME_PREFIX_HTTPS;

  /** The {@link #getSchemePrefix() scheme-prefix} for FTP. */
  public static final String SCHEME_PREFIX_FTP = ResourceUri.SCHEME_PREFIX_FTP;

  private final URL url;

  /** The {@link FileUtilLimited} instance. */
  private final FileUtilLimited fileUtil;

  /**
   * The constructor.
   *
   * @param url is the URL to the resource. E.g. "http://foo.bar/index.html".
   */
  public UrlResource(String url) {

    this(url, FileUtilLimitedImpl.getInstance());
  }

  /**
   * The constructor.
   *
   * @param absolutePath is the absolute path to the resource. E.g. "http://foo.bar/index.html".
   * @param fileUtil is the {@link FileUtilLimited} to use.
   */
  public UrlResource(String absolutePath, FileUtilLimited fileUtil) {

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

    this(url, FileUtilLimitedImpl.getInstance());
  }

  /**
   * The constructor.
   *
   * @param url is the {@link URL}.
   * @param fileUtil is the {@link FileUtilLimited} to use.
   */
  public UrlResource(URL url, FileUtilLimited fileUtil) {

    super();
    if (url == null) {
      throw new NlsNullPointerException("url");
    }
    this.url = url;
    this.fileUtil = fileUtil;
  }

  @Override
  public Date getLastModificationDate() {

    return null;
  }

  @Override
  public String getSchemePrefix() {

    if (this.url == null) {
      return "url:";
    } else {
      return this.url.getProtocol() + "://";
    }
  }

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

  @Override
  public boolean isData() {

    return (this.url != null);
  }

  @Override
  public String getPath() {

    if (this.url == null) {
      return null;
    }
    return this.url.getPath();
  }

  @Override
  public URL getUrl() throws ResourceNotAvailableException {

    if (this.url == null) {
      throw new ResourceNotAvailableException(getPath());
    }
    return this.url;
  }

  @Override
  public String getName() {

    if (this.url == null) {
      return null;
    }
    String path = this.url.getPath();
    return this.fileUtil.getBasename(path);
  }

  @Override
  public DataResource navigate(String relativePath) {

    try {
      return new UrlResource(new URL(this.url, relativePath));
    } catch (MalformedURLException e) {
      throw new ResourceUriUndefinedException(e, relativePath);
    }
  }

}
