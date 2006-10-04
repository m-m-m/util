/* $ Id: $ */
package net.sf.mmm.configuration.api.access;

import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.ConfigurationIF;
import net.sf.mmm.context.api.ContextIF;
import net.sf.mmm.value.api.ValueException;

/**
 * This is the interface for a factory of
 * {@link net.sf.mmm.configuration.api.access.ConfigurationAccessIF accessors}
 * for
 * {@link net.sf.mmm.configuration.api.ConfigurationDocumentIF#NAME_INCLUDE included}
 * {@link net.sf.mmm.configuration.api.ConfigurationDocumentIF configuration-documents}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ConfigurationAccessFactoryIF {

  /**
   * This is the prefix for all context variables related to configuration
   * access. It is followed by a specific access type and one of the suffix
   * constants. E.g. if you define the custom access type 'foo', your
   * implementation (classname) will be configured by the context variable
   * 'net.sf.mmm.configuration.access.foo.factory'.
   */
  String CONTEXT_VARIABLE_PREFIX = "net.sf.mmm.configuration.access.";

  /**
   * This is the suffix for a context variable pointing to an implementation of
   * this interface for the according type.
   * 
   * @see #CONTEXT_VARIABLE_PREFIX
   */
  String CONTEXT_VARIABLE_SUFFIX_FACTORY = ".factory";

  /**
   * This is the suffix for a context variable containg the parent access used
   * if the
   * {@link net.sf.mmm.configuration.api.ConfigurationDocumentIF#NAME_INCLUDE_HREF}
   * is a relative path.
   */
  String CONTEXT_VARIABLE_SUFFIX_PARENT = ".parent";

  /**
   * This is the suffix for a context variable containg the current href used as
   * base if the
   * {@link net.sf.mmm.configuration.api.ConfigurationDocumentIF#NAME_INCLUDE_HREF}
   * is a relative path.
   */
  String CONTEXT_VARIABLE_SUFFIX_HREF = ".basehref";

  /**
   * TODO This is the name of an optional
   * {@link net.sf.mmm.value.api.GenericValueIF#getBoolean(boolean) boolean}
   * {@link ContextIF#getValue(String) value} of that determines if the
   * {@link #getAccessors() accessors} are statically set to
   * {@link ConfigurationAccessIF#isReadOnly() read-only}. The default value is
   * <code>false</code>.
   */
  String CONTEXT_VARIABLE_SUFFIX_READONLY = ".readonly";

  /**
   * TODO
   */
  String CONTEXT_VARIABLE_DEFAULT = CONTEXT_VARIABLE_PREFIX + "default";

  /**
   * This method configures this access-list.
   * 
   * @param prefix
   *        is the prefix for the {@link ContextIF#getValue(String) "variables"}
   *        containing the factory
   *        {@link #configure(String, ContextIF, ConfigurationIF) configuration}.
   *        It will start with {@link #CONTEXT_VARIABLE_PREFIX}.
   * @param context
   *        is the context (potentially) containing the required configuration
   *        for this factory. TODO: explain more
   * @param include
   *        is the
   *        {@link net.sf.mmm.configuration.api.ConfigurationDocumentIF#NAME_INCLUDE include}
   *        configuration.
   * @throws ConfigurationException
   *         if the configuration is illegal for this implementation.
   * @throws ValueException
   *         if a configuration value is missing or has the wrong type.
   */
  void configure(String prefix, ContextIF context, ConfigurationIF include)
      throws ConfigurationException, ValueException;

  /**
   * This method determines if this access-list will always
   * {@link #getAccessors() contain} a single
   * {@link ConfigurationAccessIF access}. If this access-list can contain
   * potentially none or more than one {@link ConfigurationAccessIF accessors},
   * according to its configuration (even if it currently has exaclty one single
   * access) it should return <code>false</code>.<br>
   * For example an include of the file "configuration/include.xml" will
   * represent an access-list that will return <code>true</code> here while
   * "configuration/*.xml" will return <code>false</code> even if there is
   * exaclty one matching file.
   * 
   * @return <code>true</code> if this access-list always contains
   */
  boolean isSingleAccess();

  /**
   * This method gets the {@link ConfigurationAccessIF accessors} for the
   * {@link net.sf.mmm.configuration.api.ConfigurationDocumentIF#NAME_INCLUDE included}
   * {@link net.sf.mmm.configuration.api.ConfigurationDocumentIF configuration-documents}.
   * The method is called exactly once after this access-list is
   * {@link #configure(String, ContextIF, ConfigurationIF) configured}.
   * 
   * @return an array of the accessors.
   */
  ConfigurationAccessIF[] getAccessors();

}
