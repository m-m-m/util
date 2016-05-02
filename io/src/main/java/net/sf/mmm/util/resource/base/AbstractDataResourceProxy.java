/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.base;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;

import net.sf.mmm.util.resource.api.DataResource;

/**
 * This is an abstract implementation of the {@link DataResource} interface that {@link #getDelegate()
 * delegates to another} {@link DataResource}.
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

  /**
   * {@inheritDoc}
   */
  public String getPath() {

    return getDelegate().getPath();
  }

  /**
   * {@inheritDoc}
   */
  public String getName() {

    return getDelegate().getName();
  }

  /**
   * {@inheritDoc}
   */
  public long getSize() {

    return getDelegate().getSize();
  }

  /**
   * {@inheritDoc}
   */
  public URL getUrl() {

    return getDelegate().getUrl();
  }

  /**
   * {@inheritDoc}
   */
  public String getUri() {

    return getDelegate().getUri();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isAvailable() {

    return getDelegate().isAvailable();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isData() {

    return getDelegate().isData();
  }

  /**
   * {@inheritDoc}
   */
  public DataResource navigate(String relativePath) {

    return getDelegate().navigate(relativePath);
  }

  /**
   * {@inheritDoc}
   */
  public InputStream openStream() {

    return getDelegate().openStream();
  }

  /**
   * {@inheritDoc}
   */
  public OutputStream openOutputStream() {

    return getDelegate().openOutputStream();
  }

  /**
   * {@inheritDoc}
   */
  public Boolean isModifiedSince(Date date) {

    return getDelegate().isModifiedSince(date);
  }

  /**
   * {@inheritDoc}
   */
  public Date getLastModificationDate() {

    return getDelegate().getLastModificationDate();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getDelegate().toString();
  }

}
