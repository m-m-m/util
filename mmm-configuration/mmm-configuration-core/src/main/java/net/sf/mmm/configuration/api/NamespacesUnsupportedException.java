/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.api;

import net.sf.mmm.configuration.NlsBundleConfigCore;

/**
 * This is the exception thrown if a
 * {@link net.sf.mmm.configuration.api.Configuration configuration-node} does
 * not support
 * {@link net.sf.mmm.configuration.api.Configuration#getNamespaceUri() namespaces}
 * and a namespace-aware method was called.
 * 
 * @see net.sf.mmm.configuration.api.Configuration#getDescendant(String,
 *      String)
 * @see net.sf.mmm.configuration.api.Configuration#getDescendants(String,
 *      String)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NamespacesUnsupportedException extends ConfigurationException {

  /** uid for serialization */
  private static final long serialVersionUID = -6131430745876832213L;

  /**
   * The constructor.
   * 
   * @param node
   *        is the node that does not support namespaces.
   * @param implementation
   *        is the specific implementation that does not support namespaces.
   */
  public NamespacesUnsupportedException(Configuration node, Class<?> implementation) {

    super(NlsBundleConfigCore.ERR_NAMESPACES_UNSUPPORTED, node, implementation);
  }

  /**
   * The constructor.
   * 
   * @param node
   *        is the node that does not support namespaces.
   * @param implementation
   *        is the specific implementation that does not support namespaces.
   * @param nested
   *        is the throwable that caused this exception.
   */
  public NamespacesUnsupportedException(Configuration node, Class<?> implementation, Throwable nested) {

    super(nested, NlsBundleConfigCore.ERR_NAMESPACES_UNSUPPORTED, node, implementation);
  }

  /**
   * This method gets the configuration that does NOT support
   * {@link Configuration#getNamespaceUri() namepsaces}.
   * 
   * @return the associated configuration node.
   */
  public Configuration getConfiguration() {

    return (Configuration) getNlsMessage().getArgument(0);
  }

}
