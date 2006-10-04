/* $ Id: $ */
package net.sf.mmm.configuration.base.access;

import net.sf.mmm.configuration.api.ConfigurationDocumentIF;
import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.ConfigurationIF;
import net.sf.mmm.configuration.api.access.ConfigurationAccessIF;
import net.sf.mmm.configuration.base.AbstractConfiguration;
import net.sf.mmm.configuration.base.AbstractConfigurationDocument;
import net.sf.mmm.context.api.ContextIF;
import net.sf.mmm.context.api.MutableContextIF;
import net.sf.mmm.value.api.ValueException;

/**
 * This is the interface for factory that creates (loads) a
 * {@link net.sf.mmm.configuration.api.ConfigurationDocumentIF configuration-document}.<br>
 * A legal implementation must have a public non-arg constructor.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ConfigurationFactoryIF {

  /**
   * The prefix for the {@link ContextIF#getValue(String) variables} containing
   * the factory
   * {@link #configure(String, ContextIF, ConfigurationIF) configuration}.<br>
   * Here an example in XML notation (to make it obvious we use "foo" instead of
   * "xml"):
   * 
   * <pre>
   * &lt;myRoot xmlns:mmm="http://m-m-m.sf.net/namespaces/configuration"&gt;
   *   &lt;mmm:context&gt;
   *     &lt;mmm:variable name="net.sf.mmm.configuration.format.default"&gt;foo&lt;/mmm:variable&gt;
   *     &lt;mmm:variable name="net.sf.mmm.configuration.format.foo.factory"&gt;net.sf.mmm.configuration.impl.format.xml.dom.XmlFactory&lt;/mmm:variable&gt;
   *     &lt;mmm:variable name="net.sf.mmm.configuration.format.foo.encoding"&gt;UTF-8&lt;/mmm:variable&gt;
   *   &lt;/mmm:context&gt;
   *   &lt;myContent&gt;
   *     ...
   *     &lt;!-- the type attribute could also be omitted in this case --&gt;
   *     &lt;mmm:include type="foo" href="inlcude.xml"&gt;
   *     ...
   *   &lt;/myContent&gt;
   * &lt;/myRoot&gt;
   * </pre>
   */
  String CONTEXT_VARIABLE_PREFIX = "net.sf.mmm.configuration.format.";

  /**
   * The suffix for the {@link ContextIF#getValue(String) variable} containing
   * the
   * {@link net.sf.mmm.value.api.GenericValueIF#getJavaClass(Class) factory implementation}.<br>
   * E.g. <code>{@link #CONTEXT_VARIABLE_PREFIX} + "xml" +
   * {@link #CONTEXT_VARIABLE_SUFFIX_FACTORY} =
   * {@link net.sf.mmm.configuration.impl.format.xml.dom.XmlFactory}</code>
   */
  String CONTEXT_VARIABLE_SUFFIX_FACTORY = ".factory";

  /**
   * The name of the the {@link ContextIF#getValue(String) variable}
   * {@link net.sf.mmm.value.api.GenericValueIF#getString() containing} the
   * default format. This default is used if
   * {@link ConfigurationDocumentIF#NAME_INCLUDE_FORMAT} is not set.<br>
   */
  String CONTEXT_VARIABLE_DEFAULT = CONTEXT_VARIABLE_PREFIX + "default";

  /**
   * This method configures the configuration factory.
   * 
   * @param prefix
   *        is the prefix for the {@link ContextIF#getValue(String) variables}
   *        containing the factory configuration. It is build by the
   *        {@link #CONTEXT_VARIABLE_PREFIX} with
   *        {@link ConfigurationDocumentIF#NAME_INCLUDE_FORMAT format} and "."
   *        appended.
   * @param context
   *        is the context (potentially) containing the required configuration
   *        for this factory. The implementation should only read
   *        {@link ContextIF#getValue(String) variables} that start with the
   *        given <code>prefix</code>.
   * @param include
   *        is the {@link ConfigurationDocumentIF#NAME_INCLUDE include}
   *        configuration.
   * @throws ConfigurationException
   *         if the configuration is illegal for this implementation.
   * @throws ValueException
   *         if a configuration value is missing or has the wrong type.
   */
  void configure(String prefix, ContextIF context, ConfigurationIF include)
      throws ConfigurationException, ValueException;

  /**
   * This method creates the root
   * {@link net.sf.mmm.configuration.api.ConfigurationDocumentIF configuration-document}
   * via the given {@link ConfigurationAccessIF access}.
   * 
   * @param access
   *        gives read/write access to the configuration data.
   * @return the loaded configuration.
   * @throws ConfigurationException
   *         if the configuration data could not be read or is illegal.
   */
  AbstractConfigurationDocument create(ConfigurationAccessIF access) throws ConfigurationException;

  /**
   * This method creates the root
   * {@link net.sf.mmm.configuration.api.ConfigurationDocumentIF configuration-document}
   * via the given {@link ConfigurationAccessIF access}.
   * 
   * @see net.sf.mmm.configuration.base.ConfigurationUtil#initializeContext(MutableContextIF)
   * 
   * @param access
   *        gives read/write access to the configuration data.
   * @param context
   *        is the {@link ConfigurationDocumentIF#getContext() context} for the
   *        document.
   * @return the loaded configuration.
   * @throws ConfigurationException
   *         if the configuration data could not be read or is illegal.
   */
  AbstractConfigurationDocument create(ConfigurationAccessIF access, MutableContextIF context)
      throws ConfigurationException;

  /**
   * This method creates the
   * {@link net.sf.mmm.configuration.api.ConfigurationDocumentIF configuration-document}
   * via the given {@link ConfigurationAccessIF access}.
   * 
   * @param access
   *        gives read/write access to the configuration data.
   * @param parentConfiguration
   *        is the parent-configuration for the
   *        {@link ConfigurationDocumentIF#getConfiguration() "document configuration"}.
   * @return the loaded configuration.
   * @throws ConfigurationException
   *         if the configuration data could not be read or is illegal.
   */
  AbstractConfigurationDocument create(ConfigurationAccessIF access,
      AbstractConfiguration parentConfiguration) throws ConfigurationException;

}
