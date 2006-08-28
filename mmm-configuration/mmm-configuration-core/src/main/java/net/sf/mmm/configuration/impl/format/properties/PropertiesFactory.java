/* $Id: PropertiesFactory.java 200 2006-08-01 21:30:00Z hohwille $ */
package net.sf.mmm.configuration.impl.format.properties;

import net.sf.mmm.configuration.api.ConfigurationDocumentIF;
import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.ConfigurationIF;
import net.sf.mmm.configuration.api.access.ConfigurationAccessIF;
import net.sf.mmm.configuration.base.AbstractConfiguration;
import net.sf.mmm.configuration.base.AbstractConfigurationDocument;
import net.sf.mmm.configuration.base.access.AbstractConfigurationFactory;
import net.sf.mmm.context.api.ContextIF;
import net.sf.mmm.context.api.MutableContextIF;
import net.sf.mmm.value.api.ValueException;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.configuration.base.access.ConfigurationFactoryIF} interface
 * using {@link java.util.Properties} as representation for the configuration.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PropertiesFactory extends AbstractConfigurationFactory {

    /**
     * this is the default
     * {@link ConfigurationDocumentIF#NAME_INCLUDE_FORMAT format} name for this
     * implementation.
     * 
     * @see net.sf.mmm.configuration.base.access.ConfigurationFactoryIF#CONTEXT_VARIABLE_PREFIX
     */
    public static final String CONTEXT_DEFAULT_NAME = "properties";

    /** the suffix for the {@link PropertiesDocument#getSeparator() separator} */
    public static final String CONTEXT_VARIABLE_SUFFIX_SEPARATOR = ".separator";

    /** the suffix for the {@link PropertiesDocument#isFlat() flat} flag */
    public static final String CONTEXT_VARIABLE_SUFFIX_FLAT = ".flat";

    /** the default {@link PropertiesDocument#getSeparator() separator} */
    public static final String DEFAULT_SEPARATOR = ".";

    /** the default {@link PropertiesDocument#getSeparator() separator} */
    public static final boolean DEFAULT_FLAT = false;

    /** the {@link PropertiesDocument#isFlat() flat} flag */
    private boolean flat;

    /** the {@link PropertiesDocument#getSeparator() separator} */
    private String propertyKeySeparator;

    /**
     * The constructor.
     */
    public PropertiesFactory() {

        super();
        this.propertyKeySeparator = DEFAULT_SEPARATOR;
    }

    /**
     * @see net.sf.mmm.configuration.base.access.ConfigurationFactoryIF#configure(java.lang.String,
     *      net.sf.mmm.context.api.ContextIF,
     *      net.sf.mmm.configuration.api.ConfigurationIF)
     */
    public void configure(String prefix, ContextIF context, ConfigurationIF include)
            throws ConfigurationException, ValueException {

        this.propertyKeySeparator = context.getValue(prefix + CONTEXT_VARIABLE_SUFFIX_SEPARATOR)
                .getString(DEFAULT_SEPARATOR);
        this.flat = context.getValue(prefix + CONTEXT_VARIABLE_SUFFIX_FLAT)
                .getBoolean(DEFAULT_FLAT);
    }

    /**
     * @see net.sf.mmm.configuration.base.access.ConfigurationFactoryIF#create(net.sf.mmm.configuration.api.access.ConfigurationAccessIF,
     *      net.sf.mmm.context.api.MutableContextIF) {@inheritDoc}
     */
    public AbstractConfigurationDocument create(ConfigurationAccessIF access, MutableContextIF context)
            throws ConfigurationException {

        return new PropertiesDocument(access, context, this.propertyKeySeparator, this.flat);
    }

    /**
     * @see net.sf.mmm.configuration.base.access.ConfigurationFactoryIF#create(net.sf.mmm.configuration.api.access.ConfigurationAccessIF,
     *      net.sf.mmm.configuration.base.AbstractConfiguration) {@inheritDoc}
     */
    public AbstractConfigurationDocument create(ConfigurationAccessIF access,
            AbstractConfiguration parentConfiguration) throws ConfigurationException {

        return new PropertiesDocument(access, parentConfiguration, this.propertyKeySeparator,
                this.flat);
    }

}
