/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.path.api;

/**
 * This is the interface for a parser of XPath expressions.<br>
 * See <a href="http://www.w3.org/TR/xpath">XML Path Language (XPath)</a>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface XPathParser {

  /**
   * This method parses the given <code>xpath</code> expression.
   * 
   * @param xpath is the XPath expression as string.
   * @return the parsed <code>xpath</code> expression.
   * @throws XmlParseException if the given <code>xpath</code> expression is
   *         illegal.
   */
  XmlSelector parseXPath(String xpath) throws XmlParseException;

}
