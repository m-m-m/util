/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.api;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import net.sf.mmm.util.xml.XmlException;

/**
 * TODO: this class ...
 * 
 * @param <O>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface XmlDeserializer<O> {

  /**
   * This method de-serializes the templated object from the given
   * <code>xmlReader</code>.
   * 
   * @param xmlReader is where to read from.
   * @return the de-serialized object.
   * @throws XmlException if the serialization fails (e.g. caused by
   *         {@link XMLStreamException}).
   */
  O fromXml(XMLStreamReader xmlReader) throws XMLStreamException;

}
