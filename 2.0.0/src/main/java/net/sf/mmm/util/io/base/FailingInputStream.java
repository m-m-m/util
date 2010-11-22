/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.base;

import java.io.IOException;
import java.io.InputStream;

/**
 * This is an implementation of {@link InputStream} that will throw an exception
 * for every method of {@link InputStream}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class FailingInputStream extends InputStream {

  /**
   * The constructor.
   */
  public FailingInputStream() {

    super();
  }

  /**
   * This method is called by any method declared by {@link InputStream}.
   * 
   * @throws RuntimeException is the actual error.
   */
  protected void causeException() throws RuntimeException {

    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int read() throws IOException {

    causeException();
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int read(byte[] b) throws IOException {

    causeException();
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int read(byte[] b, int off, int len) throws IOException {

    causeException();
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long skip(long n) throws IOException {

    causeException();
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int available() throws IOException {

    causeException();
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() throws IOException {

    causeException();
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void mark(int readlimit) {

    causeException();
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void reset() throws IOException {

    causeException();
    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean markSupported() {

    causeException();
    throw new UnsupportedOperationException();
  }

}
