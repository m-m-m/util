/* $Id$ */
package net.sf.mmm.configuration.base.access;

import net.sf.mmm.configuration.api.access.ConfigurationAccessIF;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.configuration.api.access.ConfigurationAccessIF} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractConfigurationAccess implements ConfigurationAccessIF {

    /** @see #getContextPrefix() */
    private String prefix;
    
    /**
     * The constructor.
     */
    public AbstractConfigurationAccess() {

        super();
        this.prefix = null;
    }

    /**
     * @see #getContextPrefix()
     * 
     * @param contextPrefix
     */
    public void setContextPrefix(String contextPrefix) {
        this.prefix = contextPrefix;
    }
    
    /**
     * @see net.sf.mmm.configuration.api.access.ConfigurationAccessIF#getContextPrefix()
     * {@inheritDoc}
     */
    public String getContextPrefix() {
    
        return this.prefix;
    }
    
    
    /**
     * @see java.lang.Object#toString() {@inheritDoc}
     */
    @Override
    public String toString() {

        return getUniqueUri();
    }

}
