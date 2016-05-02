/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.base;

import java.io.IOException;
import java.io.Writer;

/**
 * This is a wrapper for a writer that allows to map specific characters to a escape sequences given a
 * strings. <br>
 * The mapping table for escaping is given as string array.
 * 
 * @see EscapeWriter#EscapeWriter(Object, String[], Writer)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class EscapeWriter extends Writer {

  /**
   * A table that maps chars (as index) to escape sequences.
   */
  private final String[] characterEscapingTable;

  /** the actual writer */
  private final Writer writer;

  /**
   * The constructor.
   * 
   * @see EscapeWriter#EscapeWriter(Object, String[], Writer)
   * 
   * @param charEscapeTable is a String array that maps characters by to escape sequences. If the numeric
   *        representation of a character is a legal index for the given array and the String at that index is
   *        <code>null</code> that string is written instead of the character. In all other cases, the
   *        character is written without change.
   * @param plainWriter is the writer that is wrapped.
   */
  public EscapeWriter(String[] charEscapeTable, Writer plainWriter) {

    super();
    this.writer = plainWriter;
    this.characterEscapingTable = charEscapeTable;
  }

  /**
   * The constructor.
   * 
   * @param syncLock is an explicit lock object used for synchronization (see
   *        {@link Writer#Writer(java.lang.Object)}).
   * @param charEscapeTable is a String array that maps characters by to escape sequences. If the numeric
   *        representation of a character is a legal index for the given array and the String at that index is
   *        NOT <code>null</code> that string is written instead of the character. In all other cases, the
   *        character is written without change.
   * @param plainWriter is the writer that is wrapped.
   */
  public EscapeWriter(Object syncLock, String[] charEscapeTable, Writer plainWriter) {

    super(syncLock);
    this.writer = plainWriter;
    this.characterEscapingTable = charEscapeTable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {

    int start = off;
    int end = off + len;
    int index = start;
    int count = 0;
    while (index < end) {
      char c = cbuf[index++];
      // escaped representation of character c or null if no escaping
      // needed.
      if (c < this.characterEscapingTable.length) {
        String escapeSequence = this.characterEscapingTable[c];
        if (escapeSequence != null) {
          // some characters left that where not escaped?
          if (count > 0) {
            // flush them
            this.writer.write(cbuf, start, count);
            count = 0;
          }
          start = index;
          this.writer.write(escapeSequence);
        } else {
          count++;
        }
      } else {
        count++;
      }
    }
    // flush rest of the buffer
    if (count > 0) {
      this.writer.write(cbuf, start, count);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void flush() throws IOException {

    this.writer.flush();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void close() throws IOException {

    this.writer.close();
  }

}
