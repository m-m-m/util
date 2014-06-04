/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.path.api;

/**
 * This is the interface for a parser of XPointer expressions.<br>
 * See <a href="http://www.w3.org/TR/WD-xptr">XML Pointer Language (XPointer)</a>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface XPointerParser {

  /**
   * This method parses the given <code>xpointer</code> expression.
   * 
   * @param xpointer is the XPointer expression as string.
   * @return the parsed <code>xpointer</code> expression.
   * @throws XmlParseException if the given <code>xpointer</code> expression
   *         is illegal.
   */
  XmlPath parseXPointer(String xpointer) throws XmlParseException;

}
