/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.api;

import java.io.Serializable;

import net.sf.mmm.util.xml.XmlException;

/**
 * This is the interface for an object that can be serialized to XML.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface XmlSerializable extends Serializable {

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
  void toXml(XmlWriter xmlWriter) throws XmlException;

}
