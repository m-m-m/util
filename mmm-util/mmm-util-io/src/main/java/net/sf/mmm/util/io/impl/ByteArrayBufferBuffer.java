/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.impl;

import java.io.IOException;
import java.io.InputStream;

/**
 * This is the regular implementation of the {@link AbstractByteArrayBufferBuffer}.<br>
 * <b>ATTENTION:</b><br>
 * This class is NOT intended to be exposed. It should only be used internally by some class or component.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class ByteArrayBufferBuffer extends AbstractByteArrayBufferBuffer {

  /**
   * The constructor.
   * 
   * @param buffers are the buffers to concat.
   */
  public ByteArrayBufferBuffer(ByteArrayBufferImpl... buffers) {

    super(buffers);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean fill(InputStream inputStream) throws IOException {

    return super.fill(inputStream);
  }
}
