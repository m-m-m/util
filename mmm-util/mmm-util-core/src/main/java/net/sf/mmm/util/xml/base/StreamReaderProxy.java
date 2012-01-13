/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;

/**
 * This is an implementation of {@link XMLStreamReader} interface that adapts an
 * {@link XMLStreamReader}. It therefore extends {@link StreamReaderDelegate}
 * but adds the default implementation to {@link #nextTag()} to allow overriding
 * {@link #next()} properly.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class StreamReaderProxy extends StreamReaderDelegate {

  /**
   * The constructor.
   */
  protected StreamReaderProxy() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param reader is the {@link #getParent() delegate} to adapt.
   */
  public StreamReaderProxy(XMLStreamReader reader) {

    super(reader);
  }

  /**
   * {@inheritDoc}
   * 
   * We override this method to get sure that it delegates to our
   * {@link #next()} instead of the {@link #next()} of the {@link #getParent()
   * delegate}.
   */
  @Override
  public int nextTag() throws javax.xml.stream.XMLStreamException {

    int eventType = next();
    while ((eventType == XMLStreamConstants.CHARACTERS && isWhiteSpace())
        || (eventType == XMLStreamConstants.CDATA && isWhiteSpace())
        || eventType == XMLStreamConstants.SPACE
        || eventType == XMLStreamConstants.PROCESSING_INSTRUCTION
        || eventType == XMLStreamConstants.COMMENT) {
      eventType = next();
    }

    if (eventType != XMLStreamConstants.START_ELEMENT
        && eventType != XMLStreamConstants.END_ELEMENT) {

      throw new XMLStreamException("expected start or end tag (but found " + eventType + ")",
          getLocation());
    }

    return eventType;
  }

}
