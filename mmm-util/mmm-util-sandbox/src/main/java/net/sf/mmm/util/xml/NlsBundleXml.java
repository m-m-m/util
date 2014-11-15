/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml;

import net.sf.mmm.util.nls.base.AbstractResourceBundle;

/**
 * This class holds the internationalized messages for the XML subproject.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsBundleXml extends AbstractResourceBundle {

  /**
   * exception message if element was closed with wrong tagname.
   */
  public static final String ERR_CLOSE_TAGNAME = "Current element was opened with \"{0}\" but closed with \"{1}\"!";

  /**
   * exception message if element was closed with wrong namespace prefix.
   *
   * @see net.sf.mmm.xml.api.XmlWriterIF#writeEndElement(String, String)
   */

  /**
   * exception message if a namespace prefix was used that has not been declared before.
   */
  public static final String ERR_NAMESPACE_NOT_DECLARED = "Namespace prefix \"{0}\" has not been declared yet!";

  /**
   * exception message if closeElement was called after top-level element is already closed.
   */
  public static final String ERR_CLOSE_TOPLEVEL = "Root element is already closed!";

  /**
   * exception message if no element is open (and element was closed or attribut/content was written).
   */
  public static final String ERR_NOT_OPEN = "No XML Element is open!";

  /**
   * exception message if a second root element was opened.
   */
  public static final String ERR_SECOND_ROOT = "Can not open more than one root tag!";

  /**
   * exception message if XML is invalid (XML serializer abuse).
   */
  public static final String ERR_INVALID_XML = "Invalid XML!";

  /**
   * exception message if an IO error occurred while streaming XML.
   */
  public static final String ERR_IO = "Input/Output error while streaming XML!";

  /**
   * The constructor.
   */
  public NlsBundleXml() {

    super();
  }

}
