/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * This is the abstract base implementation of the {@link DataResource}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractResource implements DataResource {

  /**
   * The constructor.
   */
  public AbstractResource() {

    super();
  }

  /**
   * {@inheritDoc}
   * 
   * This is a default implementation. Override if there is a more performing
   * way to implement this.
   */
  public long getSize() throws ResourceNotAvailableException {

    try {
      return getUrl().openConnection().getContentLength();
    } catch (IOException e) {
      throw new ResourceNotAvailableException(e, getPath());
    }
  }

  /**
   * {@inheritDoc}
   * 
   * This is a default implementation. Override if there is a more performing
   * way to implement this.
   */
  public InputStream openStream() throws ResourceNotAvailableException, IOException {

    return getUrl().openStream();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    String simpleName = getClass().getSimpleName();
    String path = getPath();
    StringBuilder result = new StringBuilder(simpleName.length() + path.length() + 2);
    result.append(simpleName);
    result.append('[');
    result.append(path);
    result.append(']');
    return result.toString();
  }

}
