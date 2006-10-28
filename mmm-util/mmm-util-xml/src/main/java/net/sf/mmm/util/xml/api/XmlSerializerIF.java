/* $Id$ */
package net.sf.mmm.util.xml.api;

import net.sf.mmm.util.xml.XmlException;

/**
 * This is the interface for an serializer that can convert objects of a
 * specific type to XML.
 * 
 * @see net.sf.mmm.util.xml.api.XmlSerializableIF
 * 
 * @param <O>
 *        is the templated type of the objects to serialize.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface XmlSerializerIF<O> {

  /**
   * This method creates an XML representation of the given value. <br>
   * 
   * @see XmlSerializableIF#toXml(XmlWriterIF)
   * 
   * @param xmlWriter
   *        is where the XML is written to.
   * @param object
   *        is the object to serialize. May be <code>null</code>.
   * @throws XmlException
   *         if the serialization fails (I/O error, invalid XML, etc.).
   */
  void toXml(XmlWriterIF xmlWriter, O object) throws XmlException;

}
