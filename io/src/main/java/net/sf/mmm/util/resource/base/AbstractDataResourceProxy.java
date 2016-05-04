/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.base;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;

import net.sf.mmm.util.resource.api.DataResource;

/**
 * This is an abstract implementation of the {@link DataResource} interface that {@link #getDelegate() delegates to
 * another} {@link DataResource}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public abstract class AbstractDataResourceProxy implements DataResource {

  /**
   * The constructor.
   */
  public AbstractDataResourceProxy() {

    super();
  }

  /**
   * This method gets the {@link DataResource} this proxy delegates to.
   *
   * @return the delegate.
   */
  protected abstract DataResource getDelegate();

  @Override
  public String getPath() {

    return getDelegate().getPath();
  }

  @Override
  public String getName() {

    return getDelegate().getName();
  }

  @Override
  public long getSize() {

    return getDelegate().getSize();
  }

  @Override
  public URL getUrl() {

    return getDelegate().getUrl();
  }

  @Override
  public String getUri() {

    return getDelegate().getUri();
  }

  @Override
  public boolean isAvailable() {

    return getDelegate().isAvailable();
  }

  @Override
  public boolean isData() {

    return getDelegate().isData();
  }

  @Override
  public DataResource navigate(String relativePath) {

    return getDelegate().navigate(relativePath);
  }

  @Override
  public InputStream openStream() {

    return getDelegate().openStream();
  }

  @Override
  public OutputStream openOutputStream() {

    return getDelegate().openOutputStream();
  }

  @Override
  public Boolean isModifiedSince(Date date) {

    return getDelegate().isModifiedSince(date);
  }

  @Override
  public Date getLastModificationDate() {

    return getDelegate().getLastModificationDate();
  }

  @Override
  public String toString() {

    return getDelegate().toString();
  }

}
