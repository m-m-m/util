/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.base;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.ResourceNotAvailableException;
import net.sf.mmm.util.resource.api.ResourceNotWritableException;

/**
 * This is the abstract base implementation of the {@link DataResource} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public abstract class AbstractDataResource implements DataResource {

  /**
   * The constructor.
   */
  public AbstractDataResource() {

    super();
  }

  /**
   * This method gets the <em>scheme-prefix</em> of absolute {@link #getUri() URIs} for this type of
   * {@link DataResource}. The scheme-prefix has the following form:
   * <code>{@link java.net.URI#getScheme() &lt;scheme&gt;}:&lt;suffix&gt;</code> where
   * <code>&lt;suffix&gt;</code> is the empty string or something like <code>//</code>.
   * 
   * @return the scheme-prefix of this resource.
   */
  public abstract String getSchemePrefix();

  /**
   * {@inheritDoc}
   * 
   * This is a default implementation. Override if there is a more performing way to implement this.
   */
  public long getSize() throws ResourceNotAvailableException {

    try {
      // return getUrl().openConnection().getContentLengthLong();
      return getUrl().openConnection().getContentLength();
    } catch (IOException e) {
      throw new ResourceNotAvailableException(e, getPath());
    }
  }

  /**
   * {@inheritDoc}
   * 
   * This is a default implementation. Override if there is a more performing way to implement this.
   */
  public InputStream openStream() {

    try {
      return getUrl().openStream();
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
    }
  }

  /**
   * {@inheritDoc}
   */
  public String getPath() {

    return getUrl().getPath();
  }

  /**
   * {@inheritDoc}
   */
  public String getUri() {

    return getUrl().toString();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isAvailable() {

    return isData();
  }

  /**
   * {@inheritDoc}
   */
  public Boolean isModifiedSince(Date date) {

    Date lastModified = getLastModificationDate();
    if (lastModified != null) {
      return Boolean.valueOf(lastModified.after(date));
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public OutputStream openOutputStream() throws ResourceNotAvailableException, ResourceNotWritableException,
      RuntimeIoException {

    throw new ResourceNotWritableException(getUri());
  }

  /**
   * {@inheritDoc}
   */
  public String getName() {

    String path = getPath();
    int length = path.length();
    if (length == 0) {
      return "";
    }
    int endIndex = length - 1;
    char c = path.charAt(endIndex);
    while ((c == '/') || (c == '\\')) {
      endIndex--;
      if (endIndex < 0) {
        return "";
      }
      c = path.charAt(endIndex);
    }
    int startIndex = endIndex;
    while (startIndex > 0) {
      c = path.charAt(startIndex - 1);
      if ((c == '/') || (c == '\\')) {
        break;
      }
      startIndex--;
    }
    return path.substring(startIndex, endIndex + 1);
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
