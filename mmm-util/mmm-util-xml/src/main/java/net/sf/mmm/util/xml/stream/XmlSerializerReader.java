/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.stream;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * This is the interface for a de-serializer of values of a specific type
 * <code>V</code>.
 * 
 * @param <V> is the generic type of the value to de-serialize.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface XmlSerializerReader<V> {

  /**
   * This method de-serializes a value from XML using the given
   * <code>xmlReader</code>.<br>
   * This is the inverse operation of
   * {@link XmlSerializerWriter#toXml(javax.xml.stream.XMLStreamWriter, Object)}
   * if an according {@link XmlSerializerWriter} exists for the same type
   * <code>V</code>.
   * 
   * @param xmlReader is where to read the XML from. It has to point to the
   *        {@link javax.xml.stream.XMLStreamConstants#START_ELEMENT start-element}
   *        of the
   *        {@link XmlSerializerWriter#toXml(javax.xml.stream.XMLStreamWriter, Object) serialized}
   *        value.
   * @return the de-serialized value. May potentially be <code>null</code>.
   * @throws XMLStreamException if the <code>xmlReader</code> produced such
   *         error while reading.
   */
  V fromXml(XMLStreamReader xmlReader) throws XMLStreamException;

}
