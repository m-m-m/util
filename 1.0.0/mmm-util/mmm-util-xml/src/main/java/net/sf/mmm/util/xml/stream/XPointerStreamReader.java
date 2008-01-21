/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.stream;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;

/**
 * This is an implementation of the {@link XMLStreamReader} interface that
 * adapts an {@link XMLStreamReader} adding limited XPointer support.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class XPointerStreamReader extends StreamReaderDelegate {

  /**
   * The constructor.
   * 
   * @param delegate is the reader to adapt.
   */
  public XPointerStreamReader(XMLStreamReader delegate) {

    super(delegate);
  }

}
