/* $Id: AbstractConfigurationFactory.java 197 2006-07-31 21:00:03Z hohwille $ */
package net.sf.mmm.configuration.base.access;

import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.access.ConfigurationAccessIF;
import net.sf.mmm.configuration.base.AbstractConfigurationDocument;
import net.sf.mmm.configuration.base.ConfigurationUtil;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.configuration.base.access.ConfigurationFactoryIF}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractConfigurationFactory implements ConfigurationFactoryIF {

    /**
     * The constructor.
     */
    public AbstractConfigurationFactory() {

        super();
    }

    /**
     * @see net.sf.mmm.configuration.base.access.ConfigurationFactoryIF#create(net.sf.mmm.configuration.api.access.ConfigurationAccessIF)
     * {@inheritDoc}
     */
    public AbstractConfigurationDocument create(ConfigurationAccessIF access)
            throws ConfigurationException {

        return create(access, ConfigurationUtil.createDefaultContext());
    }

}
