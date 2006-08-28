/* $Id$ */
package net.sf.mmm.xml.api;

import java.io.Serializable;

/**
 * This is the interface for an object that can be serialized to XML.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface XmlSerializableIF extends Serializable {

    /**
     * This method searializes this object to XML using the given searializer.<br>
     * The class implementing this method is responsible to produce well-formed
     * XML. The given serializer may NOT neccessarily validate the produced XML.
     * 
     * @param xmlWriter
     *        is the receiver of the serialized XML data.
     * @throws XmlException
     *         if the serialization fails (I/O error, invalid XML, etc.).
     */
    void toXml(XmlWriterIF xmlWriter) throws XmlException;

}