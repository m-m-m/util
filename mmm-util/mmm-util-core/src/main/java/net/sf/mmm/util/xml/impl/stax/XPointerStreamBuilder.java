/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.impl.stax;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamReader;

import net.sf.mmm.util.xml.api.XmlException;

/**
 * This is the interface used to create an {@link XMLStreamReader} facade that adapts a given
 * {@link XMLStreamReader} only selecting the contents identified by a given XPointer expression.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface XPointerStreamBuilder {

  /**
   * This method creates a {@link XMLStreamReader} facade that adapts the given <code>xmlReader</code> so only
   * the contents pointed by the given <code>xpointerExpression</code> are visible.
   * 
   * @param xmlReader is the original reader to adapt.
   * @param xpointerExpression is the XPointer expression.
   * @param namespaceContext is the namespace-context to inherit from when parsing the XPointer expression.
   * @return the requested {@link XMLStreamReader} facade.
   * @throws XmlException if the given <code>xpointerExpression</code> is illegal or NOT supported.
   */
  XMLStreamReader createStreamReader(XMLStreamReader xmlReader, String xpointerExpression,
      NamespaceContext namespaceContext) throws XmlException;

}
