/* $Id$ */
package net.sf.mmm.configuration.api;

import java.util.Collection;

import net.sf.mmm.value.api.MutableGenericValue;

/**
 * This is the interface for a
 * {@link net.sf.mmm.configuration.api.Configuration configuration} that can be
 * changed actively.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface MutableConfiguration extends Configuration {

  /**
   * @see net.sf.mmm.configuration.api.Configuration#getValue()
   */
  MutableGenericValue getValue();

  /**
   * @see net.sf.mmm.configuration.api.Configuration#getDescendant(java.lang.String)
   */
  MutableConfiguration getDescendant(String path);

  /**
   * @see net.sf.mmm.configuration.api.Configuration#getDescendant(java.lang.String,
   *      java.lang.String)
   */
  MutableConfiguration getDescendant(String path, String namespaceUri);

  /**
   * @see net.sf.mmm.configuration.api.Configuration#getDescendants(java.lang.String)
   */
  Collection<? extends MutableConfiguration> getDescendants(String path);

  /**
   * @see net.sf.mmm.configuration.api.Configuration#getDescendants(java.lang.String,
   *      java.lang.String)
   */
  Collection<? extends MutableConfiguration> getDescendants(String path, String namespaceUri);

  /**
   * This method creates the child with the given {@link #getName() name} and
   * {@link #getNamespaceUri() namespace}.<br>
   * If the child to create is an {@link Configuration.Type#ATTRIBUTE attribute}
   * that already exists, this method will NOT create a new attribute and return
   * the existing one instead.
   * 
   * @param name
   *        is the {@link #getName() name} of the child to create. If the
   *        <code>name</code> starts with the
   *        {@link #NAME_PREFIX_ATTRIBUTE "attribute prefix"}, the requested
   *        child has the {@link #getType() type}
   *        {@link Configuration.Type#ATTRIBUTE attribute}, else
   *        {@link Configuration.Type#ELEMENT element}.
   * @param namespace
   *        is the {@link #getNamespaceUri() namespace} of the child to create
   *        or <code>null</code> for default namespace.
   * @return the created child.
   * @throws ConfigurationException
   *         if the operation failed (e.g. NOT editable).
   */
  MutableConfiguration createChild(String name, String namespace) throws ConfigurationException;

  /**
   * This method removes the this configuration and all its
   * {@link #getDescendants(String, String) descendants} from the configuration
   * tree.<br>
   * After the configuration is {@link ConfigurationDocumentIF#save() saved}
   * this information is lost.
   * 
   * @see #disable()
   * 
   * @throws ConfigurationException
   *         if the operation failed (e.g. NOT editable).
   */
  void remove() throws ConfigurationException;

  /**
   * This method disables the this configuration so itself and all its
   * {@link #getDescendants(String, String) descendants} will dissappear from
   * the configuration tree.<br>
   * Instead of {@link #remove()} this method will only out-comment the
   * according configuration but no information is lost.
   * 
   * @throws ConfigurationException
   *         if the operation failed (e.g. NOT editable).
   */
  void disable() throws ConfigurationException;

  /**
   * This method determines if this configuration can be edited by the end-user.
   * By default a configuration is NOT editable. The status is inherited by the
   * {@link #getValue() value} and {@link #getDescendant(String) descendants}
   * but can be {@link ConfigurationDocumentIF#NAME_EDITABLE overriden}. This
   * flag is independent of {@link #isAddDefaults()}. This especially means
   * that a configuration that is NOT {@link #isEditable() editable} is NOT
   * necessarily immutable. If {@link #isAddDefaults()} is set to
   * <code>true</code>, it can be modified indirectly (but an assigned value
   * can NOT be changed or removed).
   * 
   * @see #remove()
   * @see #disable()
   * @see #createChild(String, String)
   * @see MutableGenericValue#isEditable()
   * 
   * @return <code>true</code> if this node is editable, <code>false</code>
   *         otherwise.
   */
  boolean isEditable();

}
