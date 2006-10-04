/* $ Id: $ */
package net.sf.mmm.configuration.api;

import net.sf.mmm.configuration.NlsResourceBundle;

/**
 * This is the exception thrown if a
 * {@link net.sf.mmm.configuration.api.ConfigurationIF configuration-node} does
 * not support
 * {@link net.sf.mmm.configuration.api.ConfigurationIF#getNamespaceUri() namespaces}
 * and a namespace-aware method was called.
 * 
 * @see net.sf.mmm.configuration.api.ConfigurationIF#getDescendant(String,
 *      String)
 * @see net.sf.mmm.configuration.api.ConfigurationIF#getDescendants(String,
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
  public NamespacesUnsupportedException(ConfigurationIF node, Class implementation) {

    super(NlsResourceBundle.ERR_NAMESPACES_UNSUPPORTED, node, implementation);
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
  public NamespacesUnsupportedException(ConfigurationIF node, Class implementation, Throwable nested) {

    super(nested, NlsResourceBundle.ERR_NAMESPACES_UNSUPPORTED, node, implementation);
  }

  /**
   * This method gets the configuration that does NOT support
   * {@link ConfigurationIF#getNamespaceUri() namepsaces}.
   * 
   * @return the associated configuration node.
   */
  public ConfigurationIF getConfiguration() {

    return (ConfigurationIF) getNlsMessage().getArgument(0);
  }

}
