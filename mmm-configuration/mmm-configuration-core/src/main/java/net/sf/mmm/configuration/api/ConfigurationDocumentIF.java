/* $ Id: $ */
package net.sf.mmm.configuration.api;

import net.sf.mmm.context.api.Context;

/**
 * This is the interface for a
 * {@link net.sf.mmm.configuration.api.Configuration configuration} document.
 * The configuration is represented as a tree. The configuration document holds
 * the {@link #getConfiguration() root-node} of that tree. <br>
 * An implementation of this API can use any format and any data-source to read
 * and write the configuration data. The easiest way to understand the API is to
 * think of the configuration as XML file on the disk. <br>
 * Further there is also the ability to save the configuration. This is useful
 * for a configuration editor but also to save the defaults generated while
 * reading the configuration.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ConfigurationDocumentIF {

  /**
   * This is the URI of the {@link Configuration#getNamespaceUri() namespace}
   * used for internal features in the representation of configuration.
   * 
   * @see #NAME_EXCLUDE
   * @see #NAME_INCLUDE
   */
  String NAMESPACE_URI_CONFIGURATION = "http://m-m-m.sf.net/namespaces/configuration";

  /**
   * This is the suggested prefix for the
   * {@link Configuration#getNamespaceUri() namespace} defined by the URI
   * {@link #NAMESPACE_URI_CONFIGURATION} .
   */
  String NAMESPACE_PREFIX_CONFIGURATION = "cfg";

  /**
   * This is the {@link Configuration#getName() name} of an
   * {@link Configuration.Type#ELEMENT element} that is omitted with all its
   * {@link Configuration#getDescendants(String, String) descendants}. It
   * must have the {@link Configuration#getNamespaceUri() namespace}
   * {@link #NAMESPACE_URI_CONFIGURATION}.<br>
   * Did you ever worry that you can NOT out-comment parts of XML that contain
   * comments? Ever thought why comments could not be special elements? A node
   * as specified above fills this gap.
   */
  String NAME_EXCLUDE = "exclude";

  /**
   * This is the {@link Configuration#getName() name} of an
   * {@link Configuration.Type#ELEMENT element} that is used to include the
   * configuration from another source into the configuration tree (like a
   * symbolic link). Such node must have the namespace
   * {@link #NAMESPACE_URI_CONFIGURATION}.<br>
   * Instead of XML XInclude (http://www.w3.org/XInclude/...) this technology
   * allows to include configurations in different formats from various sources.
   * Further saving the configuration will keep the fragmented structure of
   * included configurations.
   */
  String NAME_INCLUDE = "include";

  /**
   * This is the {@link Configuration#getName() name} of an optional
   * {@link Configuration.Type#ATTRIBUTE attribute} that defines the format of
   * the {@link Configuration configuration} to {@link #NAME_INCLUDE include}.
   * This corresponds to the
   * {@link net.sf.mmm.configuration.base.access.ConfigurationFactory factory}
   * used to interpret the raw configuration data.
   */
  String NAME_INCLUDE_FORMAT = "@format";

  /**
   * This is the {@link Configuration#getName() name} of an optional
   * {@link Configuration.Type#ATTRIBUTE attribute} that defines how to access
   * the {@link Configuration configuration} to {@link #NAME_INCLUDE include}.
   * This corresponds to the
   * {@link net.sf.mmm.configuration.api.access.ConfigurationAccessFactory access-list}
   * used to read and write the raw configuration data.
   */
  String NAME_INCLUDE_ACCESS = "@access";

  /**
   * This is the {@link Configuration#getName() name} of a required
   * {@link Configuration.Type#ATTRIBUTE attribute} that defines where to
   * {@link #NAME_INCLUDE_ACCESS access} the
   * {@link Configuration configuration} to {@link #NAME_INCLUDE include}.
   * This corresponds to the
   * {@link net.sf.mmm.configuration.api.access.ConfigurationAccessFactory access-list}
   * used to read and write the raw configuration data.
   */
  String NAME_INCLUDE_HREF = "@href";

  /**
   * This is the {@link Configuration#getName() name} of an optional
   * {@link Configuration.Type#ATTRIBUTE attribute} that defines the relative
   * {@link Configuration#getPath() path} of the
   * {@link Configuration#getDescendants(String, String) descendants} to
   * {@link #NAME_INCLUDE include}.
   */
  String NAME_INCLUDE_DESCENDANTS = "@descendants";

  /**
   * This is the {@link Configuration#getName() name} of an
   * {@link Configuration.Type#ATTRIBUTE attribute} of a further unspecified
   * {@link Configuration.Type#ELEMENT element}. If defined, the attribute
   * contains the classname of an existing
   * {@link net.sf.mmm.value.api.GenericValue#getJavaClass() java-class}.
   */
  String NAME_CLASS = "@class";

  /**
   * This is the {@link Configuration#getName() name} of an
   * {@link Configuration.Type#ATTRIBUTE attribute} that can be applied to any
   * {@link Configuration.Type#ELEMENT element}. It must have the
   * {@link Configuration#getNamespaceUri() namespace}
   * {@link #NAMESPACE_URI_CONFIGURATION}.<br>
   * 
   * @see Configuration#isAddDefaults()
   */
  String NAME_ADD_DEFAULTS = "@addDefaults";

  /**
   * This is the {@link Configuration#getName() name} of an
   * {@link Configuration.Type#ATTRIBUTE attribute} that can be applied to any
   * {@link Configuration.Type#ELEMENT element}. It must have the
   * {@link Configuration#getNamespaceUri() namespace}
   * {@link #NAMESPACE_URI_CONFIGURATION}.<br>
   * 
   * @see MutableConfiguration#isEditable()
   */
  String NAME_EDITABLE = "@editable";

  /**
   * This is the {@link Configuration#getName() name} of an
   * {@link Configuration.Type#ELEMENT element} that defines
   * {@link net.sf.mmm.context.api.Context#getObject(String) context variables}.
   */
  String NAME_CONTEXT = "context";

  /**
   * This is the {@link Configuration#getName() name} of an
   * {@link Configuration.Type#ELEMENT element} that defines a single
   * {@link net.sf.mmm.context.api.Context#getObject(String) context variable}.
   * The {@link Context#getVariableNames() name} of the valiable is defined by
   * the child {@link Configuration.Type#ATTRIBUTE attribute}
   * {@link #NAME_CONTEXT_VARIABLE_NAME}, the
   * {@link Context#getObject(String) value} by its
   * {@link Configuration#getValue() value}. It is used as child of
   * {@link #NAME_CONTEXT}.
   */
  String NAME_CONTEXT_VARIABLE = "variable";

  /**
   * This is the {@link Configuration#getName() name} of an
   * {@link Configuration.Type#ATTRIBUTE attribute} that defines the
   * {@link Context#getVariableNames() name} of the variable to
   * {@link net.sf.mmm.context.api.MutableContext#setObject(String, Object) set}.
   * It is used as child of {@link #NAME_CONTEXT_VARIABLE}.
   */
  String NAME_CONTEXT_VARIABLE_NAME = "@name";

  /**
   * This method gets the root node of the configuration tree containing the
   * actual configuration.
   * 
   * @return the configuration root-node.
   */
  MutableConfiguration getConfiguration();

  /**
   * This method saves the unsaved changes of this configuration document.
   * 
   * @throws ConfigurationException
   *         if the configuration data is illegal or writing failed (e.g. I/O
   *         problem).
   */
  void save() throws ConfigurationException;

  /**
   * This method reloads the configuration.
   * 
   * @throws ConfigurationException
   *         if the reload failed.
   */
  void reload() throws ConfigurationException;

  /**
   * This method determines if this document has been modified. It is modified
   * if its configuration data has been modified without being saved. The flag
   * will be reseted (set <code>false</code>) if the document is
   * {@link #save() saved}.
   * 
   * @return <code>true</code> if this document has been modified,
   *         <code>false</code> otherwise.
   */
  boolean isModified();

  /**
   * This method determines if this document is immutable. Immutable means that
   * it can NOT be {@link #isModified() modified} in any way.<br>
   * It is inherited by the {@link #getConfiguration() root-node} but can
   * potentially be overriden.
   * 
   * @return <code>true</code> if this document is immutable,
   *         <code>false</code> otherwise.
   */
  boolean isImmutable();

  /**
   * This method gets a string representation of this object identifying the
   * source where this document comes from (absolute file-path, db-connection
   * string, etc.).
   * 
   * @see Object#toString()
   * 
   * @return the string representation of this object.
   */
  String toString();

  /**
   * This method gets the context of with this document. This is a set of
   * variables that can be defined within the configuration. The plain
   * {@link Configuration#getValue() value} of a
   * {@link Configuration configuration} may contain a variable expression
   * (e.g. "http://localhost:${com.foo.server.port}") that will be resolved when
   * {@link net.sf.mmm.value.api.GenericValue#getObject() read} (e.g.
   * "http://localhost:8080").
   * 
   * @return the context of this node.
   */
  Context getContext();

}
