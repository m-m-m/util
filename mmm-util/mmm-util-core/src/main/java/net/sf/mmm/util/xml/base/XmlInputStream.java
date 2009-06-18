/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * This class is an input-stream that detects the encoding from the potential
 * XML-header of a stream.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.3
 */
final class XmlInputStream extends InputStream {

  /** the start of the XML header (<code>"<?xml"</code>). */
  private static final byte[] XML_HEADER_START = new byte[] { '<', '?', 'x', 'm', 'l', ' ' };

  /** the start of the XML encoding attribute (<code>"encoding="</code>). */
  private static final byte[] XML_ENCODING_ATRIBUTE = new byte[] { 'e', 'n', 'c', 'o', 'd', 'i',
      'n', 'g', '=' };

  /** The original input-stream to adapt. */
  private final InputStream delegate;

  /** The buffer read to lookahead the encoding from the XML header. */
  private final byte[] buffer;

  /** the length of the buffered data (may be less than the array length). */
  private final int length;

  /** The current index position in the {@link #buffer}. */
  private int index;

  /**
   * The encoding detected from the XML header or <code>null</code> if it was
   * NOT specified.
   */
  private Charset charset;

  /**
   * The constructor.
   * 
   * @param delegate is the input-stream to adapt.
   * @param defaultCharset is the {@link Charset} used if NO encoding was
   *        specified via an XML header.
   * @throws IOException if an I/O error was caused by <code>delegate</code>
   *         when trying to read the XML header.
   */
  public XmlInputStream(InputStream delegate, Charset defaultCharset) throws IOException {

    super();
    this.index = 0;
    this.delegate = delegate;
    this.buffer = new byte[128];
    this.length = this.delegate.read(this.buffer);
    String encoding = null;
    int bufferIndex = 0;
    if (this.length > 20) {
      boolean okay = true;
      for (byte c : XML_HEADER_START) {
        if (c != this.buffer[bufferIndex++]) {
          okay = false;
          break;
        }
      }
      if (okay) {
        // XML header present...
        int encodingIndex = 0;
        while (bufferIndex < this.length) {
          if (this.buffer[bufferIndex++] == XML_ENCODING_ATRIBUTE[encodingIndex]) {
            encodingIndex++;
            if (encodingIndex == XML_ENCODING_ATRIBUTE.length) {
              if (bufferIndex < this.length) {
                byte quote = this.buffer[bufferIndex++];
                if ((quote == '"') || (quote == '\'')) {
                  // encoding declaration detected
                  int encodingStartIndex = bufferIndex;
                  while (bufferIndex < this.length) {
                    if (quote == this.buffer[bufferIndex++]) {
                      encoding = new String(this.buffer, encodingStartIndex, bufferIndex
                          - encodingStartIndex - 1);
                    }
                  }
                }
              }
              break;
            }
          } else {
            encodingIndex = 0;
          }
        }
      }
    }
    if (encoding == null) {
      this.charset = defaultCharset;
    } else {
      try {
        this.charset = Charset.forName(encoding);
      } catch (RuntimeException e) {
        this.charset = defaultCharset;
      }
    }
  }

  /**
   * This method gets the {@link Charset} that was detected.
   * 
   * @return the charset.
   */
  public Charset getCharset() {

    return this.charset;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int read() throws IOException {

    if (this.index < this.length) {
      return this.buffer[this.index++];
    } else {
      return this.delegate.read();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int read(byte[] b, int off, int len) throws IOException {

    if (this.index < this.length) {
      // keep it simple ;)
      return super.read(b, off, len);
    } else {
      return this.delegate.read(b, off, len);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() throws IOException {

    this.delegate.close();
  }

}
