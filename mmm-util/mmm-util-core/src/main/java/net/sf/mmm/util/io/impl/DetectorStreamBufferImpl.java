/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.impl;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;

import net.sf.mmm.util.io.api.ByteArray;
import net.sf.mmm.util.io.api.spi.DetectorStreamBuffer;
import net.sf.mmm.util.io.api.spi.DetectorStreamProcessor;
import net.sf.mmm.util.io.base.AbstractByteProvider;
import net.sf.mmm.util.io.base.ByteArrayImpl;
import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;

/**
 * This is the implementation of the {@link DetectorStreamBuffer} interface.<br>
 * It is based on the idea that each {@link DetectorStreamProcessor} in the
 * chain has its own {@link DetectorStreamBuffer} instance. Therefore it holds
 * the according {@link DetectorStreamProcessor} building a pair of
 * buffer+processor. Further it holds an instance of the predecessor and thereby
 * represents the chain itself.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DetectorStreamBufferImpl extends AbstractByteProvider implements DetectorStreamBuffer {

  /** The actual processor served by this buffer. */
  private DetectorStreamProcessor processor;

  /** The predecessor in the chain or <code>null</code> if this is the first. */
  private DetectorStreamBufferImpl chainPredecessor2;

  /** The successor in the chain or <code>null</code> if this is the last. */
  private DetectorStreamBufferImpl chainSuccessor;

  /** @see #getStreamPosition() */
  private long streamPosition;

  /** @see #seek(long, SeekMode) */
  private long seekCount;

  /** @see #seek(long, SeekMode) */
  private SeekMode seekMode;

  /** The current {@link ByteArray} to work on. */
  private byte[] currentArray;

  /** The start-index in {@link #currentArray}. */
  private int currentArrayMin;

  /** The {@link ByteArray#getCurrentIndex() index} in {@link #currentArray}. */
  private int currentArrayIndex;

  /**
   * The {@link ByteArray#getMaximumIndex() maximum index} in
   * {@link #currentArray}.
   */
  private int currentArrayMax;

  /**
   * The {@link Queue} of available {@link ByteArray}s that have NOT yet been
   * processed.
   */
  private final LinkedList<ByteArray> arrayQueue;

  private final ByteArray currentArrayView;

  /**
   * The constructor.
   * 
   * @param processor is the {@link DetectorStreamProcessor} served by this
   *        buffer.
   * @param successor is the successor in the chain or <code>null</code> if this
   *        is the last buffer/processor pair in the chain.
   */
  public DetectorStreamBufferImpl(DetectorStreamProcessor processor,
      DetectorStreamBufferImpl successor) {

    super();
    this.arrayQueue = new LinkedList<ByteArray>();
    this.chainSuccessor = successor;
    // this.chainPredecessor = predecessor;
    // if (this.chainPredecessor != null) {
    // this.chainPredecessor.chainSuccessor = this;
    // }
    this.currentArrayView = new CurrentByteArray();
  }

  /**
   * {@inheritDoc}
   */
  public long skip(long byteCount) {

    seek(byteCount, SeekMode.SKIP);
    return byteCount;
  }

  /**
   * {@inheritDoc}
   */
  public ByteArray getByteArray(int index) {

    if (index == 0) {
      return this.currentArrayView;
    } else {
      return this.arrayQueue.get(index - 1);
    }
  }

  /**
   * {@inheritDoc}
   */
  public int getByteArrayCount() {

    return this.arrayQueue.size() + 1;
  }

  /**
   * {@inheritDoc}
   */
  public int getBytesAvailable() {

    if (this.currentArray == null) {
      return 0;
    }
    int bytesAvailable = this.currentArrayMax - this.currentArrayIndex + 1;
    for (ByteArray array : this.arrayQueue) {
      bytesAvailable = bytesAvailable + array.getBytesAvailable();
    }
    return bytesAvailable;
  }

  /**
   * {@inheritDoc}
   */
  public boolean hasNext() {

    if (this.currentArray == null) {
      boolean okay = nextArray();
      if (!okay) {
        return false;
      }
    }
    return true;
  }

  /**
   * TODO: javadoc
   * 
   * @return <code>true</code> if a new buffer is available, <code>false</code>
   *         if the buffer queue is empty.
   */
  private boolean nextArray() {

    if ((this.currentArray != null) && (this.currentArrayMin < this.currentArrayMax)) {
      this.chainSuccessor.append(new ByteArrayImpl(this.currentArray, this.currentArrayMin,
          this.currentArrayMax));
    }
    if (this.arrayQueue.isEmpty()) {
      this.currentArray = null;
      return false;
    } else {
      ByteArray nextArray = this.arrayQueue.remove();
      int offsetMin = 0;
      int offsetIndex = 0;
      while (this.seekCount > 0) {
        long bytesAvailable = nextArray.getBytesAvailable();
        if (this.seekCount >= bytesAvailable) {
          this.seekCount = this.seekCount - bytesAvailable;
          this.streamPosition = this.streamPosition + bytesAvailable;
          if (this.seekMode == SeekMode.SKIP) {
            this.chainSuccessor.append(nextArray);
          }
          if (this.arrayQueue.isEmpty()) {
            this.currentArray = null;
            return false;
          }
          nextArray = this.arrayQueue.remove();
        } else {
          offsetIndex = (int) this.seekCount;
          if (this.seekMode == SeekMode.REMOVE) {
            offsetMin = offsetIndex;
          }
          this.streamPosition = this.streamPosition + this.seekCount;
          this.seekCount = 0;
          this.seekMode = null;
          // break;
        }
      }
      this.currentArray = nextArray.getBytes();
      int currentIndex = nextArray.getCurrentIndex();
      this.currentArrayMin = currentIndex + offsetMin;
      this.currentArrayIndex = currentIndex + offsetIndex;
      this.currentArrayMax = nextArray.getMaximumIndex();
      if (this.currentArrayIndex > this.currentArrayMax) {
        // array already empty...
        return nextArray();
      }
      return true;
    }
  }

  /**
   * {@inheritDoc}
   */
  public byte next() throws NoSuchElementException {

    if (this.currentArray == null) {
      throw new NoSuchElementException();
    }
    byte result = this.currentArray[this.currentArrayIndex++];
    this.streamPosition++;
    if (this.currentArrayIndex > this.currentArrayMax) {
      nextArray();
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  public byte peek() throws NoSuchElementException {

    if (this.currentArray == null) {
      throw new NoSuchElementException();
    }
    byte result = this.currentArray[this.currentArrayIndex];
    return result;
  }

  /**
   * {@inheritDoc}
   */
  public void insert(ByteArray data) {

    if (this.currentArray != null) {
      this.chainSuccessor.append(new ByteArrayImpl(this.currentArray, this.currentArrayMin,
          this.currentArrayIndex));
      this.currentArrayMin = this.currentArrayIndex;
    }
    this.chainSuccessor.append(data);
  }

  /**
   * This method {@link #remove(long) removes} or {@link #skip(long) skips} the
   * given number of bytes.
   * 
   * @param byteCount is the number of bytes to seek.
   * @param mode is the {@link SeekMode}.
   */
  protected void seek(long byteCount, SeekMode mode) {

    if (this.seekMode == null) {
      this.seekMode = mode;
    } else if (this.seekMode != mode) {
      throw new NlsIllegalArgumentException("remove and skip can NOT be mixed!");
    }
    this.seekCount = this.seekCount + byteCount;
    if (this.currentArray != null) {
      // if removeCount was > 0 before, then currentArray had been null.
      if (mode == SeekMode.REMOVE) {
        if (this.currentArrayMin < this.currentArrayIndex) {
          this.chainSuccessor.append(new ByteArrayImpl(this.currentArray, this.currentArrayMin,
              this.currentArrayIndex - 1));
        }
      }
      long currentBytesAvailable = this.currentArrayMax - this.currentArrayIndex + 1;
      if (currentBytesAvailable < this.seekCount) {
        this.currentArrayIndex = (int) (this.currentArrayIndex + this.seekCount);
        this.seekCount = 0;
        this.seekMode = null;
        if (mode == SeekMode.REMOVE) {
          this.currentArrayMin = this.currentArrayIndex;
        }
      } else {
        this.seekCount = this.seekCount - currentBytesAvailable;
        if (mode == SeekMode.SKIP) {
          this.chainSuccessor.append(new ByteArrayImpl(this.currentArray, this.currentArrayMin,
              this.currentArrayMax));
        }
        this.currentArray = null;
        nextArray();
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public void remove(long byteCount) {

    seek(byteCount, SeekMode.REMOVE);
  }

  /**
   * {@inheritDoc}
   */
  public long getStreamPosition() {

    return this.streamPosition;
  }

  /**
   * This method queues the given {@link ByteArray} at the end of this buffer.
   * 
   * @param nextArray is the {@link ByteArray} to append.
   */
  protected void append(ByteArray nextArray) {

    this.arrayQueue.add(nextArray);
    if (this.currentArray == null) {
      nextArray();
    }
  }

  /**
   * @see DetectorStreamProcessor#process(DetectorStreamBuffer, Map, boolean)
   * 
   * @param metadata
   * @param eos
   * @throws IOException
   */
  public void process(Map<String, Object> metadata, boolean eos) throws IOException {

    this.processor.process(this, metadata, eos);
    if (this.chainSuccessor != null) {
      this.chainSuccessor.process(metadata, eos);
      // TODO: sync this buffer...
    }
  }

  /**
   * This inner class is a view on the current {@link ByteArray}.
   * 
   * @see DetectorStreamBufferImpl#getByteArray(int)
   */
  protected class CurrentByteArray extends AbstractByteProvider implements ByteArray {

    /**
     * {@inheritDoc}
     */
    public byte[] getBytes() {

      return DetectorStreamBufferImpl.this.currentArray;
    }

    /**
     * {@inheritDoc}
     */
    public int getBytesAvailable() {

      return DetectorStreamBufferImpl.this.currentArrayMax
          - DetectorStreamBufferImpl.this.currentArrayIndex + 1;
    }

    /**
     * {@inheritDoc}
     */
    public int getCurrentIndex() {

      return DetectorStreamBufferImpl.this.currentArrayIndex;
    }

    /**
     * {@inheritDoc}
     */
    public int getMaximumIndex() {

      return DetectorStreamBufferImpl.this.currentArrayMax;
    }
  }

  /**
   * Enum with available modes for a
   * {@link DetectorStreamBufferImpl#seek(long, SeekMode) seek}.
   */
  protected static enum SeekMode {

    /** @see DetectorStreamBufferImpl#skip(long) */
    SKIP,

    /** @see DetectorStreamBufferImpl#remove(long) */
    REMOVE
  }

}
