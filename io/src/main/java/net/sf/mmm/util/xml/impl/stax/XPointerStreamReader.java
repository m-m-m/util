/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.impl.stax;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import net.sf.mmm.util.exception.api.NlsUnsupportedOperationException;
import net.sf.mmm.util.xml.base.StreamReaderProxy;

/**
 * This is an implementation of the {@link XMLStreamReader} interface that adapts an {@link XMLStreamReader}
 * adding limited XPointer support. <br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class XPointerStreamReader extends StreamReaderProxy {

  /** The depths of elements. */
  private int depth;

  /**
   * The constructor.
   * 
   * @param delegate is the reader to adapt.
   * @param xpointer is the XPointer expression.
   */
  public XPointerStreamReader(XMLStreamReader delegate, String xpointer) {

    super(delegate);
    // TODO: add proper XPointer support
    // figure out the actual syntax to include all children as this is not
    // standard compliant / invalid
    if (!"element(/1/*)".equals(xpointer)) {
      throw new NlsUnsupportedOperationException(xpointer);
    }
    this.depth = 0;
  }

  @Override
  public int next() throws XMLStreamException {

    int eventType = super.next();
    if (eventType == XMLStreamConstants.START_ELEMENT) {
      this.depth++;
      if (this.depth == 1) {
        // skip root
        return next();
      }
    } else if (eventType == XMLStreamConstants.END_ELEMENT) {
      this.depth--;
      if (this.depth <= 0) {
        if (hasNext()) {
          return next();
        }
      }
    }
    return eventType;
  }
}
