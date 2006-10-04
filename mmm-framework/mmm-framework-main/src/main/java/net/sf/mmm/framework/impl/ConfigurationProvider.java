/* $Id$ */
package net.sf.mmm.framework.impl;

import net.sf.mmm.configuration.api.ConfigurationIF;
import net.sf.mmm.framework.api.ComponentDescriptorIF;
import net.sf.mmm.framework.api.ComponentException;
import net.sf.mmm.framework.api.ComponentInstanceContainerIF;
import net.sf.mmm.framework.api.ComponentManagerIF;
import net.sf.mmm.framework.api.ComponentProviderIF;
import net.sf.mmm.framework.base.provider.AbstractComponentProvider;

/**
 * This is the implementation of the {@link ComponentProviderIF} interface
 * providing {@link ConfigurationIF configuration}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ConfigurationProvider extends AbstractComponentProvider<ConfigurationIF> {

    /** the root configuration */
    private ConfigurationIF rootConfiguration;

    /**
     * The constructor.
     */
    public ConfigurationProvider() {

        super(ConfigurationIF.class);
        this.rootConfiguration = null;
    }

    /**
     * This method gets the rootConfiguration.
     * 
     * @return the rootConfiguration.
     */
    public ConfigurationIF getRootConfiguration() {

        return this.rootConfiguration;
    }

    /**
     * This method sets the rootConfiguration.
     * 
     * @param rootConfig
     *        is the rootConfiguration to set.
     */
    public void setRootConfiguration(ConfigurationIF rootConfig) {

        this.rootConfiguration = rootConfig;
    }

    /**
     * @see net.sf.mmm.framework.api.ComponentProviderIF#release(net.sf.mmm.framework.api.ComponentInstanceContainerIF, net.sf.mmm.framework.api.ComponentManagerIF)
     * 
     */
    public boolean release(ComponentInstanceContainerIF<ConfigurationIF> instanceContainer, ComponentManagerIF componentManager) {

        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @see net.sf.mmm.framework.api.ComponentProviderIF#request(java.lang.String, net.sf.mmm.framework.api.ComponentDescriptorIF, java.lang.String, net.sf.mmm.framework.api.ComponentManagerIF)
     * 
     */
    public ComponentInstanceContainerIF<ConfigurationIF> request(String instanceId, ComponentDescriptorIF<?> sourceDescriptor, String sourceInstanceId, ComponentManagerIF componentManager) throws ComponentException {

        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @see net.sf.mmm.framework.api.ComponentProviderIF#dispose(net.sf.mmm.framework.api.ComponentInstanceContainerIF, net.sf.mmm.framework.api.ComponentManagerIF)
     * 
     */
    public void dispose(ComponentInstanceContainerIF<ConfigurationIF> instanceContainer, ComponentManagerIF componentManager) {
        
    }

}
