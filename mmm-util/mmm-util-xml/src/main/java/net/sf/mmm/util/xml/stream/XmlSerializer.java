/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.stream;

/**
 * This is the interface for a bidirectional serializer of values of a specific
 * type <code>V</code>. It can serialize
 * {@link #toXml(javax.xml.stream.XMLStreamWriter, Object) to XML} and
 * de-serialize {@link #fromXml(javax.xml.stream.XMLStreamReader) from XML}.
 * 
 * @param <V> is the generic type of the value to (de-)serialize.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface XmlSerializer<V> extends XmlSerializerWriter<V>, XmlSerializerReader<V> {

}
