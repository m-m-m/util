/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

/**
 * This enum holds the possible modes of I/O.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public enum IoMode {

  /** Indicates that data is read from a source. */
  READ,

  /** Indicates that data is written to a target. */
  WRITE,

  /** Indicates that a handle is {@link java.io.Closeable#close() closed}. */
  CLOSE,

  /** Indicates that a handle is {@link java.io.Flushable#flush() flushed}. */
  FLUSH,

  /** Indicates that data is copied. */
  COPY,

  /** Indicates that a handle is opened. */
  OPEN,

}
