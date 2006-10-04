/* $ Id: $ */
package net.sf.mmm.configuration.impl.format.xml.dom;

import net.sf.mmm.configuration.api.ConfigurationDocumentIF;
import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.ConfigurationIF;
import net.sf.mmm.configuration.api.access.ConfigurationAccessIF;
import net.sf.mmm.configuration.base.AbstractConfiguration;
import net.sf.mmm.configuration.base.AbstractConfigurationDocument;
import net.sf.mmm.configuration.base.access.AbstractConfigurationFactory;
import net.sf.mmm.context.api.ContextIF;
import net.sf.mmm.context.api.MutableContextIF;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.configuration.base.access.ConfigurationFactoryIF} interface
 * using {@link org.w3c.dom XML DOM} as representation for the configuration.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class XmlFactory extends AbstractConfigurationFactory {

  /**
   * this is the default
   * {@link ConfigurationDocumentIF#NAME_INCLUDE_FORMAT format} name for this
   * implementation.
   * 
   * @see net.sf.mmm.configuration.base.access.ConfigurationFactoryIF#CONTEXT_VARIABLE_PREFIX
   */
  public static final String CONTEXT_DEFAULT_NAME = "file";

  /**
   * The constructor.
   * 
   */
  public XmlFactory() {

    super();
  }

  /**
   * @see net.sf.mmm.configuration.base.access.ConfigurationFactoryIF#configure(String,
   *      ContextIF, ConfigurationIF) 
   */
  public void configure(String prefix, ContextIF environment, ConfigurationIF include)
      throws ConfigurationException {

  // TODO: parser factory configuration
  }

  /**
   * @see net.sf.mmm.configuration.base.access.ConfigurationFactoryIF#create(net.sf.mmm.configuration.api.access.ConfigurationAccessIF,
   *      net.sf.mmm.context.api.MutableContextIF) 
   */
  public AbstractConfigurationDocument create(ConfigurationAccessIF access, MutableContextIF env)
      throws ConfigurationException {

    return new XmlDocument(access, env);
  }

  /**
   * @see net.sf.mmm.configuration.base.access.ConfigurationFactoryIF#create(net.sf.mmm.configuration.api.access.ConfigurationAccessIF,
   *      net.sf.mmm.configuration.base.AbstractConfiguration) 
   */
  public AbstractConfigurationDocument create(ConfigurationAccessIF access,
      AbstractConfiguration parentConfiguration) throws ConfigurationException {

    return new XmlDocument(access, parentConfiguration);
  }
}
