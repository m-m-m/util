/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.base;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Date;

import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.ResourceNotAvailableException;
import net.sf.mmm.util.resource.api.ResourceUriUndefinedException;

/**
 * This is the implementation of {@link DataResource} for a resource that comes from an arbitrary {@link InputStream}.
 *
 * @author hohwille
 * @since 7.4.0
 */
public class StreamResource extends AbstractDataResource {

  private static final String PROTOCOL = "stream";

  /**
   * The {@link #getSchemePrefix() scheme-prefix} for this {@link StreamResource}.
   */
  public static final String SCHEME_PREFIX = PROTOCOL + ":";

  private final InputStream stream;

  private final String name;

  private final long size;

  private final String path;

  private final Date modificationDate;

  private URL url;

  /**
   * The constructor.
   *
   * @param stream the {@link InputStream} to wrap.
   * @param name the {@link #getName() name} of the resource.
   * @param size the {@link #getSize() size} of the resource.
   */
  public StreamResource(InputStream stream, String name, long size) {

    this(stream, name, size, null);
  }

  /**
   * The constructor.
   *
   * @param stream the {@link InputStream} to wrap.
   * @param name the {@link #getName() name} of the resource.
   * @param size the {@link #getSize() size} of the resource.
   * @param parentPath the parent {@link #getPath() path} of the resource.
   */
  public StreamResource(InputStream stream, String name, long size, String parentPath) {

    this(stream, name, size, parentPath, new Date());
  }

  /**
   * The constructor.
   *
   * @param stream the {@link InputStream} to wrap.
   * @param name the {@link #getName() name} of the resource.
   * @param size the {@link #getSize() size} of the resource.
   * @param parentPath the parent {@link #getPath() path} of the resource.
   * @param modificationDate the {@link #getLastModificationDate() last modification data} of the resource.
   */
  public StreamResource(InputStream stream, String name, long size, String parentPath, Date modificationDate) {
    super();
    this.stream = stream;
    this.name = name;
    this.size = size;
    if ((parentPath == null) || (parentPath.isEmpty())) {
      this.path = this.name;
    } else {
      this.path = parentPath + "/" + this.name;
    }
    this.modificationDate = modificationDate;
  }

  @Override
  public InputStream openStream() {

    return this.stream;
  }

  @Override
  public long getSize() throws ResourceNotAvailableException {

    return this.size;
  }

  @Override
  public boolean isData() {

    return true;
  }

  @Override
  public String getName() {

    return this.name;
  }

  @Override
  public String getPath() {

    return this.path;
  }

  @Override
  public String getSchemePrefix() {

    return SCHEME_PREFIX;
  }

  @Override
  public URL getUrl() throws ResourceNotAvailableException {

    if (this.url == null) {
      String uri = getSchemePrefix() + getPath();
      try {
        this.url = new URL(null, uri, new StreamHandler());
      } catch (MalformedURLException e) {
        throw new IllegalStateException(e);
      }
    }
    return this.url;
  }

  @Override
  public DataResource navigate(String resourcePath) throws ResourceUriUndefinedException {

    throw new UnsupportedOperationException();
  }

  @Override
  public Date getLastModificationDate() {

    return this.modificationDate;
  }

  private class StreamHandler extends URLStreamHandler {

    @Override
    protected URLConnection openConnection(URL url2open) throws IOException {

      if (url2open == StreamResource.this.url) {
        return new Connection();
      }
      throw new IllegalArgumentException(url2open.toString());
    }

  }

  private class Connection extends URLConnection {

    /**
     * The constructor.
     */
    public Connection() {
      super(StreamResource.this.url);
    }

    @Override
    public void connect() throws IOException {

      // nothing to do...
    }

    @Override
    public InputStream getInputStream() throws IOException {

      return StreamResource.this.stream;
    }

  }

}
