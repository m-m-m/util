/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;

import net.sf.mmm.util.io.api.ByteProcessor;

/**
 * This class is a <code>byte</code>-Buffer that represents the concatenation of
 * multiple {@link ByteArrayBufferImpl}s. The resulting
 * {@link LookaheadByteArrayBufferBuffer} has its own state and does NOT modify
 * a contained {@link ByteArrayBufferImpl}.<br>
 * <b>ATTENTION:</b><br>
 * This class is NOT intended to be exposed. It should only be used internally
 * by some class or component.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LookaheadByteArrayBufferBuffer extends AbstractByteArrayBufferBuffer {

  /** The master buffer adapted by this buffer. */
  private final AbstractByteArrayBufferBuffer master;

  /**
   * The constructor.
   * 
   * @param master is the master-buffer to copy for lookahead reads.
   */
  public LookaheadByteArrayBufferBuffer(AbstractByteArrayBufferBuffer master) {

    super(master);
    this.master = master;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public byte next() throws NoSuchElementException {

    sync(this.master);
    return super.next();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasNext() {

    sync(this.master);
    return super.hasNext();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long skip(long byteCount) {

    sync(this.master);
    return super.skip(byteCount);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long process(ByteProcessor processor, long byteCount) {

    sync(this.master);
    return super.process(processor, byteCount);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean fill(InputStream inputStream) throws IOException {

    throw new UnsupportedOperationException();
  }
}
