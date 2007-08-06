/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.path.base;

import javax.xml.namespace.NamespaceContext;

import net.sf.mmm.util.xml.path.api.XmlSelector;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractXmlSelector extends AbstractXmlItem implements XmlSelector {

  /** @see #getNamespaceContext() */
  private final NamespaceContext namespaceContext;

  /**
   * The constructor.
   * 
   * @param namespaceContext is the
   *        {@link #getNamespaceContext() namespace-context}.
   */
  public AbstractXmlSelector(final NamespaceContext namespaceContext) {

    super();
    assert (namespaceContext != null);
    this.namespaceContext = namespaceContext;
  }

  /**
   * {@inheritDoc}
   */
  public NamespaceContext getNamespaceContext() {

    return this.namespaceContext;
  }

}
