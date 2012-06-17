/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.path.api;

import javax.xml.namespace.NamespaceContext;

/**
 * This is the interface for a selector of XML nodes. It is either a
 * {@link XmlPath} or a {@link XmlSet}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract interface XmlSelector {

  /**
   * This method gets the namespace context in the scope of this selector.
   * 
   * @return the namespace context.
   */
  NamespaceContext getNamespaceContext();

  /**
   * This method gets the string representation of this XML selector. This is an
   * XPath or XPointer expression representing the selector.
   * 
   * {@inheritDoc}
   */
  String toString();

}
