/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.base;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.sf.mmm.util.io.api.StreamClosedException;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

/**
 * This is an {@link InputStream} that wraps an existing {@link InputStream} and prevents {@link #close()
 * closing} the original {@link InputStream}.<br/>
 * This can be useful as workaround for bugs like <a
 * href="http://bugs.sun.com/view_bug.do?bug_id=6539065">6539065</a>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class NonClosingInputStream extends FilterInputStream {

  /**
   * The constructor.
   * 
   * @param delegate is the {@link InputStream} to adapt.
   */
  public NonClosingInputStream(InputStream delegate) {

    super(delegate);
    NlsNullPointerException.checkNotNull(InputStream.class, delegate);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() throws IOException {

    this.in = ClosedInputStream.INSTANCE;
  }

  /**
   * This inner class is a stream that will always throw a {@link StreamClosedException}.
   */
  protected static class ClosedInputStream extends FailingInputStream {

    /** The singleton instance. */
    protected static final ClosedInputStream INSTANCE = new ClosedInputStream();

    /**
     * The constructor.
     */
    public ClosedInputStream() {

      super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void causeException() throws RuntimeException {

      throw new StreamClosedException();
    }

  }

}
