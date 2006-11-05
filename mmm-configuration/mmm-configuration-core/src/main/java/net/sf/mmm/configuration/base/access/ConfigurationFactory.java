/* $ Id: $ */
package net.sf.mmm.configuration.base.access;

import net.sf.mmm.configuration.api.ConfigurationDocument;
import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.access.ConfigurationAccess;
import net.sf.mmm.configuration.base.AbstractConfiguration;
import net.sf.mmm.configuration.base.AbstractConfigurationDocument;
import net.sf.mmm.context.api.Context;
import net.sf.mmm.context.api.MutableContext;
import net.sf.mmm.value.api.ValueException;

/**
 * This is the interface for factory that creates (loads) a
 * {@link net.sf.mmm.configuration.api.ConfigurationDocument configuration-document}.<br>
 * A legal implementation must have a public non-arg constructor.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ConfigurationFactory {

  /**
   * The prefix for the {@link Context#getValue(String) variables} containing
   * the factory
   * {@link #configure(String, Context, Configuration) configuration}.<br>
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
   * The suffix for the {@link Context#getValue(String) variable} containing
   * the
   * {@link net.sf.mmm.value.api.GenericValue#getJavaClass(Class) factory implementation}.<br>
   * E.g. <code>{@link #CONTEXT_VARIABLE_PREFIX} + "xml" +
   * {@link #CONTEXT_VARIABLE_SUFFIX_FACTORY} =
   * {@link net.sf.mmm.configuration.impl.format.xml.dom.XmlFactory}</code>
   */
  String CONTEXT_VARIABLE_SUFFIX_FACTORY = ".factory";

  /**
   * The name of the the {@link Context#getValue(String) variable}
   * {@link net.sf.mmm.value.api.GenericValue#getString() containing} the
   * default format. This default is used if
   * {@link ConfigurationDocument#NAME_INCLUDE_FORMAT} is not set.<br>
   */
  String CONTEXT_VARIABLE_DEFAULT = CONTEXT_VARIABLE_PREFIX + "default";

  /**
   * This method configures the configuration factory.
   * 
   * @param prefix
   *        is the prefix for the {@link Context#getValue(String) variables}
   *        containing the factory configuration. It is build by the
   *        {@link #CONTEXT_VARIABLE_PREFIX} with
   *        {@link ConfigurationDocument#NAME_INCLUDE_FORMAT format} and "."
   *        appended.
   * @param context
   *        is the context (potentially) containing the required configuration
   *        for this factory. The implementation should only read
   *        {@link Context#getValue(String) variables} that start with the
   *        given <code>prefix</code>.
   * @param include
   *        is the {@link ConfigurationDocument#NAME_INCLUDE include}
   *        configuration.
   * @throws ConfigurationException
   *         if the configuration is illegal for this implementation.
   * @throws ValueException
   *         if a configuration value is missing or has the wrong type.
   */
  void configure(String prefix, Context context, Configuration include)
      throws ConfigurationException, ValueException;

  /**
   * This method creates the root
   * {@link net.sf.mmm.configuration.api.ConfigurationDocument configuration-document}
   * via the given {@link ConfigurationAccess access}.
   * 
   * @param access
   *        gives read/write access to the configuration data.
   * @return the loaded configuration.
   * @throws ConfigurationException
   *         if the configuration data could not be read or is illegal.
   */
  AbstractConfigurationDocument create(ConfigurationAccess access) throws ConfigurationException;

  /**
   * This method creates the root
   * {@link net.sf.mmm.configuration.api.ConfigurationDocument configuration-document}
   * via the given {@link ConfigurationAccess access}.
   * 
   * @see net.sf.mmm.configuration.base.ConfigurationUtil#initializeContext(MutableContext)
   * 
   * @param access
   *        gives read/write access to the configuration data.
   * @param context
   *        is the {@link ConfigurationDocument#getContext() context} for the
   *        document.
   * @return the loaded configuration.
   * @throws ConfigurationException
   *         if the configuration data could not be read or is illegal.
   */
  AbstractConfigurationDocument create(ConfigurationAccess access, MutableContext context)
      throws ConfigurationException;

  /**
   * This method creates the
   * {@link net.sf.mmm.configuration.api.ConfigurationDocument configuration-document}
   * via the given {@link ConfigurationAccess access}.
   * 
   * @param access
   *        gives read/write access to the configuration data.
   * @param parentConfiguration
   *        is the parent-configuration for the
   *        {@link ConfigurationDocument#getConfiguration() "document configuration"}.
   * @return the loaded configuration.
   * @throws ConfigurationException
   *         if the configuration data could not be read or is illegal.
   */
  AbstractConfigurationDocument create(ConfigurationAccess access,
      AbstractConfiguration parentConfiguration) throws ConfigurationException;

}
