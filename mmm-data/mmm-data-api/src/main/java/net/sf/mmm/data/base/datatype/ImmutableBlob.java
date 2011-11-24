/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.datatype;

import java.io.InputStream;
import java.io.OutputStream;

import net.sf.mmm.data.api.datatype.Blob;
import net.sf.mmm.util.io.api.RuntimeIoException;

/**
 * This is an implementation of {@link Blob} that delegates to another instance
 * of {@link Blob} in order to make it immutable. This is useful to create an
 * unmodifiable view on a {@link net.sf.mmm.data.api.datatype.MutableBlob}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ImmutableBlob implements Blob {

  /** UID for serialization. */
  private static final long serialVersionUID = 7945848361875728502L;

  /** The {@link Blob} to delegate to. */
  private final Blob delegate;

  /**
   * The constructor.
   * 
   * @param delegate is the {@link Blob} to adapt as immutable view.
   */
  public ImmutableBlob(Blob delegate) {

    super();
    this.delegate = delegate;
  }

  /**
   * {@inheritDoc}
   */
  public long getSize() {

    return this.delegate.getSize();
  }

  /**
   * {@inheritDoc}
   */
  public InputStream getReadAccess() throws RuntimeIoException {

    return this.delegate.getReadAccess();
  }

  /**
   * {@inheritDoc}
   */
  public InputStream getReadAccess(long offset) throws RuntimeIoException {

    return this.delegate.getReadAccess(offset);
  }

  /**
   * {@inheritDoc}
   */
  public int getRevision() {

    return this.delegate.getRevision();
  }

  /**
   * {@inheritDoc}
   */
  public Blob getValue() {

    return this;
  }

  /**
   * {@inheritDoc}
   */
  public String getTitle() {

    return ImmutableBlob.class.getSimpleName() + ":" + this.delegate;
  }

  /**
   * {@inheritDoc}
   */
  public void streamData(OutputStream outStream) throws RuntimeIoException {

    this.delegate.streamData(outStream);
  }

  /**
   * {@inheritDoc}
   */
  public void streamData(OutputStream outStream, long offset) throws RuntimeIoException {

    this.delegate.streamData(outStream, offset);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final String toString() {

    return getTitle();
  }

}
