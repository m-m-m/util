/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.base;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Objects;

import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.ResourceNotAvailableException;
import net.sf.mmm.util.resource.api.ResourceUri;
import net.sf.mmm.util.resource.api.ResourceUriUndefinedException;

/**
 * This is the implementation of the {@link DataResource} interface for a resource that comes from an
 * {@link URL}.
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

  /**
   * The constructor.
   *
   * @param url is the URL to the resource. E.g. "http://foo.bar/index.html".
   */
  public UrlResource(String url) {

    super();
    try {
      this.url = new URL(url);
    } catch (MalformedURLException e) {
      throw new ResourceUriUndefinedException(e, url);
    }
  }

  /**
   * The constructor.
   *
   * @param url is the {@link URL}.
   */
  public UrlResource(URL url) {

    super();
    Objects.requireNonNull(url, "url");
    this.url = url;
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
    return getBasename(path);
  }

  @Override
  public DataResource navigate(String relativePath) {

    try {
      return new UrlResource(new URL(this.url, relativePath));
    } catch (MalformedURLException e) {
      throw new ResourceUriUndefinedException(e, relativePath);
    }
  }

  private static String getBasename(String filename) {

    // copied from FileUtilLimitedImpl to avoid dependency on util.io
    int len = filename.length();
    if (len == 0) {
      return filename;
    }
    // remove trailing slashes
    int end = len - 1;
    char last = filename.charAt(end);
    while ((last == '/') || (last == '\\')) {
      end--;
      if (end < 0) {
        return Character.toString(last);
      }
      last = filename.charAt(end);
    }
    int start = filename.lastIndexOf('/', end);
    if (start < 0) {
      start = filename.lastIndexOf('\\', end);
    }
    if ((last == ':') && (start < 0)) {
      return "";
    }
    return filename.substring(start + 1, end + 1);
  }

}
