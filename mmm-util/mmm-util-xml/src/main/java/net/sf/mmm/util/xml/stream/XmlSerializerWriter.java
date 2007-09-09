/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.stream;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * This is the interface for a serializer of values of a specific type
 * <code>V</code>.
 * 
 * @param <V> is the generic type of the value to serialize.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface XmlSerializerWriter<V> {

  /**
   * This method serializes a given <code>value</code> to XML using the given
   * <code>xmlWriter</code>.<br>
   * This is the inverse operation of
   * {@link XmlSerializerReader#fromXml(javax.xml.stream.XMLStreamReader)} if an
   * according {@link XmlSerializerReader} exists for the same type <code>V</code>.<br>
   * E.g. the string "foo" may be represented as
   * 
   * <pre>&lt;value type="String"&gt;foo&lt;/value&gt;</pre>
   * 
   * or
   * 
   * <pre>&lt;string value="foo"/&gt;</pre>
   * 
   * @param xmlWriter is where to write the XML to. The document will already
   *        have been {@link XMLStreamWriter#writeStartDocument() started}.
   * @param value is the value to serialize. It may be <code>null</code>.
   * @throws XMLStreamException if the serialization fails (I/O error, invalid
   *         XML, etc.).
   */
  void toXml(XMLStreamWriter xmlWriter, V value) throws XMLStreamException;

}
