/* $Id$ */
package net.sf.mmm.framework.impl;

import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.framework.api.ComponentDescriptor;
import net.sf.mmm.framework.api.ComponentException;
import net.sf.mmm.framework.api.ComponentInstanceContainer;
import net.sf.mmm.framework.api.ComponentManager;
import net.sf.mmm.framework.api.ComponentProvider;
import net.sf.mmm.framework.base.provider.AbstractComponentProvider;

/**
 * This is the implementation of the {@link ComponentProvider} interface
 * providing {@link Configuration configuration}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ConfigurationProvider extends AbstractComponentProvider<Configuration> {

    /** the root configuration */
    private Configuration rootConfiguration;

    /**
     * The constructor.
     */
    public ConfigurationProvider() {

        super(Configuration.class);
        this.rootConfiguration = null;
    }

    /**
     * This method gets the rootConfiguration.
     * 
     * @return the rootConfiguration.
     */
    public Configuration getRootConfiguration() {

        return this.rootConfiguration;
    }

    /**
     * This method sets the rootConfiguration.
     * 
     * @param rootConfig
     *        is the rootConfiguration to set.
     */
    public void setRootConfiguration(Configuration rootConfig) {

        this.rootConfiguration = rootConfig;
    }

    /**
     * @see net.sf.mmm.framework.api.ComponentProvider#release(net.sf.mmm.framework.api.ComponentInstanceContainer, net.sf.mmm.framework.api.ComponentManager)
     */
    public boolean release(ComponentInstanceContainer<Configuration> instanceContainer, ComponentManager componentManager) {

        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @see net.sf.mmm.framework.api.ComponentProvider#request(java.lang.String, net.sf.mmm.framework.api.ComponentDescriptor, java.lang.String, net.sf.mmm.framework.api.ComponentManager)
     */
    public ComponentInstanceContainer<Configuration> request(String instanceId, ComponentDescriptor<?> sourceDescriptor, String sourceInstanceId, ComponentManager componentManager) throws ComponentException {

        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see net.sf.mmm.framework.api.ComponentProvider#dispose(net.sf.mmm.framework.api.ComponentInstanceContainer, net.sf.mmm.framework.api.ComponentManager)
     */
    public void dispose(ComponentInstanceContainer<Configuration> instanceContainer, ComponentManager componentManager) {
        
    }

}
