/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.datatype.base;

import java.io.InputStream;
import java.io.OutputStream;

import net.sf.mmm.content.datatype.api.Blob;
import net.sf.mmm.util.io.api.RuntimeIoException;

/**
 * This is an implementation of {@link Blob} that delegates to another instance
 * of {@link Blob} in order to make it immutable. This is useful to create an
 * unmodifiable view on a {@link net.sf.mmm.content.datatype.api.MutableBlob}.
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

    return toString();
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
  @Override
  public String toString() {

    return ImmutableBlob.class.getSimpleName() + ":" + this.delegate;
  }

}
