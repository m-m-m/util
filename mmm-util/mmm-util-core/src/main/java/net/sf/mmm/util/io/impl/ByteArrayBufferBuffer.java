/* $Id: ByteArrayBufferBuffer.java 518 2008-04-12 20:49:15Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.impl;

import java.io.IOException;
import java.io.InputStream;

/**
 * This is the regular implementation of the
 * {@link AbstractByteArrayBufferBuffer}.<br>
 * <b>NOTE:</b><br>
 * This class is NOT public visible, because further releases might break it's
 * compatibility. Feel free to review and give feedback on the mailing list if
 * you want to use it directly.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ByteArrayBufferBuffer extends AbstractByteArrayBufferBuffer {

  /**
   * The constructor.
   * 
   * @param buffers are the buffers to concat.
   */
  public ByteArrayBufferBuffer(ByteArrayBuffer... buffers) {

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
