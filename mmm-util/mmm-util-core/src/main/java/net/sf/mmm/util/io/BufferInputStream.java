/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * This class represents a {@link InputStream} that works like a
 * {@link java.io.BufferedInputStream} but exposes its internal state to be used
 * for lookahead operations.<br>
 * <b>ATTENTION:</b><br>
 * This class is intended for class-internal usage only.<br>
 * <b>NOTE:</b><br>
 * This class is NOT public visible, because further releases might break it's
 * compatibility. Feel free to review and give feedback on the mailing list if
 * you want to use it directly.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
class BufferInputStream extends InputStream {

  /** The default capacity used by {@link #BufferInputStream(InputStream)}. */
  private static final int DEFAULT_CAPACITY = 2048;

  /**
   * The adapted {@link InputStream} to buffer or <code>null</code> if
   * {@link #close() closed}.
   */
  private InputStream inStream;

  /** @see #getCurrentBuffer() */
  private ByteArrayBuffer currentBuffer;

  /** @see #getLookaheadBuffer() */
  private ByteArrayBuffer lookaheadBuffer;

  /**
   * <code>true</code> if the end of the stream has been reached,
   * <code>false</code> otherwise.
   */
  private boolean eos;

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
   * @param capacity is the capacity for each of the two {@link ByteArrayBuffer}s.
   *        Please note that therefore the double amount of memory is allocated.
   */
  public BufferInputStream(InputStream inStream, int capacity) {

    super();
    this.inStream = inStream;
    this.currentBuffer = new ByteArrayBuffer(capacity);
    this.lookaheadBuffer = new ByteArrayBuffer(capacity);
  }

  /**
   * This is the current buffer where to {@link #read() read} from. It is
   * initially empty until the first call of {@link #fill()} is made (what is
   * also called from the <code>read</code>-methods). Whenever it gets
   * <em>consumed</em> ({@link ByteArrayBuffer#hasNext() has} no next byte),
   * the {@link #fill()}-method will swap it with the
   * {@link #getLookaheadBuffer() lookahead-buffer} and refill it. It remains
   * {@link ByteArrayBuffer#hasNext() consumed} after {@link #fill()}, if the
   * end of the stream has been reached.
   * 
   * @return the current buffer.
   */
  public ByteArrayBuffer getCurrentBuffer() {

    return this.currentBuffer;
  }

  /**
   * This is the lookahead buffer. It allows to do lookahead reads even if the
   * {@link #getCurrentBuffer() current-buffer} is almost consumed. This will
   * guarantee you to do a lookahead of the buffers capacity given at
   * {@link #BufferInputStream(InputStream, int) construction}.<br>
   * This buffer is initially empty until the first call of {@link #fill()} is
   * made (what is also called from the <code>read</code>-methods).<br>
   * <b>ATTENTION:</b><br>
   * This buffer should never be consumed externally or the result will be
   * unpredictable so be very careful NOT to modify this buffer.
   * 
   * @return the lookahead buffer.
   */
  public ByteArrayBuffer getLookaheadBuffer() {

    return this.lookaheadBuffer;
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
    this.inStream = null;
    stream.close();
  }

  /**
   * This method (re-)fills the {@link #getCurrentBuffer() current} and
   * {@link #getLookaheadBuffer() lookahead} buffer as far as data is available
   * from the underlying {@link InputStream} without {@link #read() consuming}
   * data from the stream.<br>
   * 
   * @return <code>true</code> if the
   *         {@link #getCurrentBuffer() current buffer} is (or has been) filled,
   *         <code>false</code> if the end of the stream was encountered and
   *         all data is consumed.
   * @throws IOException if the operation fails.
   */
  public boolean fill() throws IOException {

    if (!this.currentBuffer.hasNext()) {
      if (this.lookaheadBuffer.hasNext()) {
        // this is the first read at all or we reached the end of the stream...
        // fill current buffer...
        if (this.eos) {
          return false;
        }
        int bytes = this.inStream.read(this.currentBuffer.getBytes());
        if (bytes == 0) {
          // strange API but however give it another try...
          bytes = this.inStream.read(this.currentBuffer.getBytes());
        }
        if (bytes > 0) {
          this.currentBuffer.setCurrentIndex(0);
          this.currentBuffer.setMaximumIndex(bytes - 1);
        } else {
          this.eos = true;
          return false;
        }
      } else {
        // swap the two buffers...
        ByteArrayBuffer swap = this.currentBuffer;
        this.currentBuffer = this.lookaheadBuffer;
        this.lookaheadBuffer = swap;
      }
      if (!this.eos) {
        int bytes = this.inStream.read(this.lookaheadBuffer.getBytes());
        if (bytes == 0) {
          // strange API but however give it another try...
          bytes = this.inStream.read(this.lookaheadBuffer.getBytes());
        }
        if (bytes > 0) {
          this.lookaheadBuffer.setCurrentIndex(0);
          this.lookaheadBuffer.setMaximumIndex(bytes - 1);
        } else if (bytes == -1) {
          this.eos = true;
        }
      }
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int read() throws IOException {

    ensureOpen();
    boolean end = fill();
    if (end) {
      return -1;
    }
    return this.currentBuffer.getNext();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int read(byte[] buffer, int offset, int len) throws IOException {

    ensureOpen();
    int length = len;
    int offPlusLen = offset + length;
    if ((offset < 0) || (length < 0) || (offPlusLen < 0) || (buffer.length < offPlusLen)) {
      throw new IndexOutOfBoundsException();
    } else if (length == 0) {
      return 0;
    }
    boolean end = fill();
    if (end) {
      return -1;
    }
    // currentBuffer NOT empty, lookahead should be filled as far as data left
    int index = this.currentBuffer.getCurrentIndex();
    int count = this.currentBuffer.getMaximumIndex() - index + 1;
    if (count > length) {
      count = length;
    }
    System.arraycopy(this.currentBuffer.getBytes(), index, buffer, offset, count);
    this.currentBuffer.setCurrentIndex(index + count);
    length = length - count;
    if (length > 0) {
      end = fill();
      if (!end) {
        index = this.currentBuffer.getCurrentIndex();
        int count2 = this.currentBuffer.getMaximumIndex() - index + 1;
        if (count2 > length) {
          count2 = length;
        }
        System.arraycopy(this.currentBuffer.getBytes(), index, buffer, offset + count, count2);
        this.currentBuffer.setCurrentIndex(index + count2);
        count = count + count2;
      }
    }
    return count;
  }
}
