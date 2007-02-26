/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml;

import net.sf.mmm.nls.base.AbstractResourceBundle;

/**
 * This class holds the internationalized messages for the XML subproject.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsBundleXml extends AbstractResourceBundle {

  /**
   * The constructor.
   */
  public NlsBundleXml() {

    super();
  }

  /**
   * exception message if element was closed with wrong tagname.
   * 
   * @see net.sf.mmm.util.xml.api.XmlWriter#writeEndElement(String)
   */
  public static final String ERR_CLOSE_TAGNAME = "Current element was openend with \"{0}\" but closed with \"{1}\"!";

  /**
   * exception message if element was closed with wrong namespace prefix.
   * 
   * @see net.sf.mmm.xml.api.XmlWriterIF#writeEndElement(String, String)
   */
  // public static final String ERR_CLOSE_NAMESPACE_PREFIX = "Current element
  // has namespace prefix {'0} and not {'1}!";
  /**
   * exception message if a namespace prefix was used that has not been declared
   * before.
   * 
   * @see net.sf.mmm.util.xml.api.XmlWriter#writeStartElement(String, String)
   * @see net.sf.mmm.util.xml.api.XmlWriter#writeAttribute(String, String)
   */
  public static final String ERR_NAMESPACE_NOT_DECLARED = "Namespace prefix \"{0}\" has not been declared yet!";

  /**
   * exception message if closeElement was called after toplevel element is
   * already closed.
   * 
   * @see net.sf.mmm.util.xml.api.XmlWriter#writeEndElement(String, String)
   */
  public static final String ERR_CLOSE_TOPLEVEL = "Root element is already closed!";

  /**
   * exception message if no element is open (and element was closed or
   * attribut/content was written).
   * 
   * @see net.sf.mmm.util.xml.api.XmlWriter#writeStartElement(String, String,
   *      String)
   */
  public static final String ERR_NOT_OPEN = "No XML Element is open!";

  /**
   * exception message if a second root element was opened.
   * 
   * @see net.sf.mmm.util.xml.api.XmlWriter#writeStartElement(String, String,
   *      String)
   */
  public static final String ERR_SECOND_ROOT = "Can not open more than one root tag!";

  /**
   * exception message if XML is invalid (XML serializer abuse).
   * 
   * @see net.sf.mmm.util.xml.api.XmlWriter#writeEndElement(String, String)
   */
  public static final String ERR_INVALID_XML = "Inalid XML!";

  /**
   * exception message if an IO error occured while streaming XML.
   * 
   * @see net.sf.mmm.util.xml.api.XmlWriter
   */
  public static final String ERR_IO = "Input/Output error while streaming XML!";

}
