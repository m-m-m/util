/* $Id$ */
package net.sf.mmm.configuration.base;

import java.util.List;

import net.sf.mmm.configuration.api.ConfigurationDocumentIF;
import net.sf.mmm.configuration.api.ConfigurationIF;
import net.sf.mmm.configuration.api.access.ConfigurationAccessFactoryIF;
import net.sf.mmm.configuration.api.access.ConfigurationAccessIF;
import net.sf.mmm.configuration.base.access.ConfigurationFactoryIF;
import net.sf.mmm.configuration.impl.access.file.FileAccessFactory;
import net.sf.mmm.configuration.impl.access.resource.ResourceAccessFactory;
import net.sf.mmm.configuration.impl.access.url.UrlAccessFactory;
import net.sf.mmm.configuration.impl.format.properties.PropertiesFactory;
import net.sf.mmm.configuration.impl.format.xml.dom.XmlFactory;
import net.sf.mmm.context.api.ContextIF;
import net.sf.mmm.context.api.MutableContextIF;
import net.sf.mmm.context.impl.MutableContext;

/**
 * This class is a collection of utility methods to resolve
 * {@link net.sf.mmm.configuration.api.ConfigurationDocumentIF#NAME_INCLUDE included}
 * {@link net.sf.mmm.configuration.api.ConfigurationIF configurations}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class ConfigurationUtil {

    /**
     * The forbidden constructor.
     */
    private ConfigurationUtil() {

        super();
    }

    /**
     * This method creates a new and
     * {@link #initializeContext(MutableContextIF) initialized}
     * {@link MutableContextIF environment}.
     * 
     * @return a default environment.
     */
    public static MutableContextIF createDefaultContext() {

        MutableContextIF env = new MutableContext();
        initializeContext(env);
        return env;
    }

    /**
     * 
     * @param context
     * @param type
     * @param factoryClass
     */
    public static void setAccessFactory(MutableContextIF context, String type,
            Class<? extends ConfigurationAccessFactoryIF> factoryClass) {

        String key = ConfigurationAccessFactoryIF.CONTEXT_VARIABLE_PREFIX + type
                + ConfigurationAccessFactoryIF.CONTEXT_VARIABLE_SUFFIX_FACTORY;
        context.setVariable(key, factoryClass.getName());
    }

    /**
     * 
     * @param context
     * @param type
     * @param factoryClass
     */
    public static void setFormatFactory(MutableContextIF context, String type,
            Class<? extends ConfigurationFactoryIF> factoryClass) {

        String key = ConfigurationFactoryIF.CONTEXT_VARIABLE_PREFIX + type
                + ConfigurationFactoryIF.CONTEXT_VARIABLE_SUFFIX_FACTORY;
        context.setVariable(key, factoryClass.getName());
    }

    /**
     * This method initializes the given {@link MutableContextIF environment}
     * with the defaults for the root {@link ConfigurationDocumentIF document}.
     * 
     * @param context
     *        is the environment to modify.
     */
    public static void initializeContext(MutableContextIF context) {

        // ### access ###
        // default
        context.setVariable(ConfigurationAccessFactoryIF.CONTEXT_VARIABLE_DEFAULT,
                ResourceAccessFactory.CONTEXT_DEFAULT_NAME);

        // resource
        setAccessFactory(context, ResourceAccessFactory.CONTEXT_DEFAULT_NAME,
                ResourceAccessFactory.class);
        // file
        setAccessFactory(context, FileAccessFactory.CONTEXT_DEFAULT_NAME, FileAccessFactory.class);
        // url
        setAccessFactory(context, UrlAccessFactory.CONTEXT_DEFAULT_NAME, UrlAccessFactory.class);

        // ### formats ###
        // default
        context.setVariable(ConfigurationFactoryIF.CONTEXT_VARIABLE_DEFAULT,
                XmlFactory.CONTEXT_DEFAULT_NAME);
        // xml
        setFormatFactory(context, XmlFactory.CONTEXT_DEFAULT_NAME, XmlFactory.class);
        // properties
        setFormatFactory(context, PropertiesFactory.CONTEXT_DEFAULT_NAME, PropertiesFactory.class);
        // flat properties
        String type = "flat-" + PropertiesFactory.CONTEXT_DEFAULT_NAME;
        setFormatFactory(context, type, PropertiesFactory.class);
        String key = ConfigurationFactoryIF.CONTEXT_VARIABLE_PREFIX + type
                + PropertiesFactory.CONTEXT_VARIABLE_SUFFIX_FLAT;
        context.setVariable(key, Boolean.TRUE.toString());
        key = ConfigurationFactoryIF.CONTEXT_VARIABLE_PREFIX
                + PropertiesFactory.CONTEXT_DEFAULT_NAME
                + PropertiesFactory.CONTEXT_VARIABLE_SUFFIX_FLAT;
        context.setVariable(key, XmlFactory.class.getName());

    }

    /**
     * This method gets the factory used to interpret the format of the
     * configuration to include.
     * 
     * @param includeConfiguration
     * @param context
     * @return the configuration factory.
     */
    public static ConfigurationFactoryIF getDocumentFactory(ConfigurationIF includeConfiguration,
            ContextIF context) {

        String format = includeConfiguration.getDescendant(
                ConfigurationDocumentIF.NAME_INCLUDE_FORMAT).getValue().getString(null);
        if (format == null) {
            format = context.getValue(ConfigurationFactoryIF.CONTEXT_VARIABLE_DEFAULT).getString();
        }
        String varPrefix = ConfigurationFactoryIF.CONTEXT_VARIABLE_PREFIX + format;
        String varFactory = varPrefix + ConfigurationFactoryIF.CONTEXT_VARIABLE_SUFFIX_FACTORY;
        ConfigurationFactoryIF factory = context.getValue(varFactory).getJavaClassInstance(
                ConfigurationFactoryIF.class);
        factory.configure(varPrefix, context, includeConfiguration);
        return factory;
    }

    /**
     * 
     * @param includeConfiguration
     * @param env
     * @return the access factory.
     */
    public static ConfigurationAccessFactoryIF getAccessFactory(
            ConfigurationIF includeConfiguration, ContextIF env) {

        String access = includeConfiguration.getDescendant(
                ConfigurationDocumentIF.NAME_INCLUDE_ACCESS).getValue().getString(null);
        if (access == null) {
            access = env.getValue(ConfigurationAccessFactoryIF.CONTEXT_VARIABLE_DEFAULT)
                    .getString();
        }
        String varPrefix = ConfigurationAccessFactoryIF.CONTEXT_VARIABLE_PREFIX + access;
        String varFactory = varPrefix
                + ConfigurationAccessFactoryIF.CONTEXT_VARIABLE_SUFFIX_FACTORY;
        ConfigurationAccessFactoryIF factory = env.getValue(varFactory).getJavaClassInstance(
                ConfigurationAccessFactoryIF.class);
        factory.configure(varPrefix, env, includeConfiguration);
        return factory;
    }

    /**
     * This method resolves the included configurations for the given
     * <code>includeElement</code>.
     * 
     * @param includeElement
     *        is the {@link ConfigurationDocumentIF#NAME_INCLUDE include}
     *        element to resolve.
     * @param targetList
     *        is the list where to add the included child nodes.
     */
    public static void resolveInclude(AbstractConfiguration includeElement,
            List<AbstractConfiguration> targetList) {

        assert ConfigurationDocumentIF.NAME_INCLUDE.equals(includeElement.getName());
        assert ConfigurationDocumentIF.NAMESPACE_URI_CONFIGURATION.equals(includeElement
                .getNamespaceUri());

        ContextIF context = includeElement.getOwnerDocument().getContext();
        ConfigurationFactoryIF documentFactory = getDocumentFactory(includeElement, context);
        ConfigurationAccessFactoryIF accessFactory = getAccessFactory(includeElement, context);
        ConfigurationAccessIF[] accessors = accessFactory.getAccessors();
        String descendantPath = includeElement.getDescendant(
                ConfigurationDocumentIF.NAME_INCLUDE_DESCENDANTS).getValue().getString(null);
        for (int i = 0; i < accessors.length; i++) {
            AbstractConfigurationDocument document = documentFactory.create(accessors[i],
                    includeElement);
            AbstractConfiguration result = document.getConfiguration();
            if (descendantPath == null) {
                targetList.add(result);
            } else {
                for (AbstractConfiguration descendant : result.getDescendants(descendantPath)) {
                    targetList.add(new EntryPointConfiguration(includeElement, descendant));
                }
            }
        }
    }
}
