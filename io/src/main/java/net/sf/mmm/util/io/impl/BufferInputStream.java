/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.impl;

import java.io.IOException;
import java.io.InputStream;

import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;
import net.sf.mmm.util.io.api.ByteProcessable;
import net.sf.mmm.util.io.api.ByteProcessor;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.ProcessableByteArrayBuffer;
import net.sf.mmm.util.io.api.RuntimeIoException;

/**
 * This class represents a {@link InputStream} that works like a {@link java.io.BufferedInputStream} but
 * exposes its internal state to be used for lookahead operations. <br>
 * <b>ATTENTION:</b><br>
 * This class is intended for internal usage only. <br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class BufferInputStream extends InputStream implements ByteProcessable {

  /** The default capacity used by {@link #BufferInputStream(InputStream)}. */
  private static final int DEFAULT_CAPACITY = 2048;

  /**
   * The adapted {@link InputStream} to buffer or {@code null} if {@link #close() closed}.
   */
  private InputStream inStream;

  /** the internal buffer. */
  private final ByteArrayBufferBuffer buffer;

  /**
   * {@code true} if the end of the stream has been reached, {@code false} otherwise.
   */
  private boolean eos;

  /**
   * The processor used to copy the bytes from the {@link #buffer} to the readers buffer.
   */
  private CopyProcessor copyProcessor;

  /**
   * The constructor.
   * 
   * @param inStream is the {@link InputStream} to adapt.
   */
  public BufferInputStream(InputStream inStream) {

    this(inStream, DEFAULT_CAPACITY);
  }

  /**
   * The constructor.
   * 
   * @param inStream is the {@link InputStream} to adapt.
   * @param capacity is the capacity for each of the two {@link ByteArrayBufferImpl} s. Please note that
   *        therefore the double amount of memory is allocated.
   */
  public BufferInputStream(InputStream inStream, int capacity) {

    this(inStream, capacity, 2);
  }

  /**
   * The constructor.
   * 
   * @param inStream is the {@link InputStream} to adapt.
   * @param capacity is the capacity for each of the two {@link ByteArrayBufferImpl} s. Please note that
   *        therefore the double amount of memory is allocated.
   * @param bufferCount is the number of buffers to use.
   */
  private BufferInputStream(InputStream inStream, int capacity, int bufferCount) {

    super();
    this.inStream = inStream;
    ByteArrayBufferImpl[] buffers = new ByteArrayBufferImpl[bufferCount];
    for (int i = 0; i < bufferCount; i++) {
      buffers[i] = new ByteArrayBufferImpl(capacity);
    }
    this.buffer = new ByteArrayBufferBuffer(buffers);
    this.copyProcessor = new CopyProcessor();
  }

  /**
   * This method ensures that this stream has NOT been {@link #close() closed}.
   * 
   * @throws IOException if this stream has been {@link #close() closed}.
   */
  protected void ensureOpen() throws IOException {

    if (this.inStream == null) {
      throw new IOException("Stream closed!");
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() throws IOException {

    InputStream stream = this.inStream;
    if (stream != null) {
      this.inStream = null;
      stream.close();
    }
  }

  /**
   * This method (re-)fills the internal buffer as far as data is available from the underlying
   * {@link InputStream} without {@link #read() consuming} data from the stream. If the internal buffer is
   * already filled, the call of this method will have no effect. <br>
   * 
   * @return {@code true} if the end of the stream was encountered while (re)filling the internal buffer,
   *         {@code false} otherwise.
   * @throws IOException if the operation fails.
   */
  public boolean fill() throws IOException {

    if (!this.eos) {
      this.eos = this.buffer.fill(this.inStream);
    }
    return this.eos;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long skip(long skipCount) throws IOException {

    if (skipCount < 0) {
      throw new NlsIllegalArgumentException(Long.valueOf(skipCount));
    }
    if (skipCount == 0) {
      return 0;
    }
    long skipped = this.buffer.skip(skipCount);
    if ((skipped < skipCount) && (!this.eos)) {
      skipped = skipped + this.inStream.skip(skipCount - skipped);
    }
    fill();
    return skipped;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int read() throws IOException {

    ensureOpen();
    if (!this.buffer.hasNext()) {
      fill();
      if (this.eos) {
        return -1;
      }
    }
    int result = this.buffer.next();
    fill();
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int read(byte[] bytes, int offset, int length) throws IOException {

    ensureOpen();
    int offPlusLen = offset + length;
    if ((offset < 0) || (length < 0) || (offPlusLen < 0) || (bytes.length < offPlusLen)) {
      throw new IndexOutOfBoundsException();
    } else if (length == 0) {
      return 0;
    }
    this.copyProcessor.targetBuffer = bytes;
    this.copyProcessor.targetOffset = offset;
    int processed = (int) this.buffer.process(this.copyProcessor, length);
    int len = length - processed;
    if (len > 0) {
      int bytesRead = this.inStream.read(bytes, offset + processed, len);
      if (bytesRead == -1) {
        if (processed == 0) {
          return -1;
        }
      } else {
        processed = processed + bytesRead;
      }
    }
    fill();
    return processed;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int available() throws IOException {

    return this.buffer.getBytesAvailable() + this.inStream.available();
  }

  /**
   * {@inheritDoc}
   */
  public long process(ByteProcessor processor, long length) {

    try {
      long len = length;
      while (len > 0) {
        fill();
        long processed = this.buffer.process(processor, length);
        if (processed <= 0) {
          assert (processed == 0);
          break;
        }
        len = len - processed;
      }
      return length - len;
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
    }
  }

  /**
   * This method creates a new {@link ProcessableByteArrayBuffer buffer} for lookahead operations on the data
   * from the underlying stream. That buffer is a view on the internal buffer of this stream with its own
   * state for the read position. Consuming data from that buffer will NOT influence the state of this buffer,
   * while consuming data from this stream will refill the returned buffer.
   * 
   * @return the lookahead buffer.
   */
  public ProcessableByteArrayBuffer createLookaheadBuffer() {

    return new LookaheadByteArrayBufferBuffer(this.buffer);
  }

  /**
   * This inner class is the {@link ByteProcessor} used to copy bytes from the buffer to the caller consuming
   * data from this stream.
   */
  protected static class CopyProcessor implements ByteProcessor {

    /** The buffer to copy to. */
    private byte[] targetBuffer;

    /** The offset in {@link #targetBuffer}. */
    private int targetOffset;

    /**
     * The constructor.
     */
    public CopyProcessor() {

      super();
    }

    /**
     * The constructor.
     * 
     * @param targetBuffer the buffer to copy to.
     * @param targetOffset the offset in {@code targetBuffer}.
     */
    public CopyProcessor(byte[] targetBuffer, int targetOffset) {

      super();
      this.targetBuffer = targetBuffer;
      this.targetOffset = targetOffset;
    }

    /**
     * {@inheritDoc}
     */
    public int process(byte[] buffer, int offset, int length) {

      System.arraycopy(buffer, offset, this.targetBuffer, this.targetOffset, length);
      this.targetOffset = this.targetOffset + length;
      return length;
    }
  }
}
