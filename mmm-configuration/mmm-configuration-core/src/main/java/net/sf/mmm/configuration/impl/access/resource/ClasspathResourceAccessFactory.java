/* $Id$ */
package net.sf.mmm.configuration.impl.access.resource;

import java.net.URL;

import net.sf.mmm.configuration.api.ConfigurationDocumentIF;
import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.ConfigurationIF;
import net.sf.mmm.configuration.api.access.ConfigurationAccessFactoryIF;
import net.sf.mmm.configuration.api.access.ConfigurationAccessIF;
import net.sf.mmm.configuration.base.ConfigurationReadException;
import net.sf.mmm.configuration.impl.access.url.UrlAccess;
import net.sf.mmm.context.api.ContextIF;
import net.sf.mmm.value.api.ValueException;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.configuration.api.access.ConfigurationAccessFactoryIF} for
 * {@link ClassLoader#getResource(java.lang.String) classpath-resource} access.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ClasspathResourceAccessFactory implements ConfigurationAccessFactoryIF {

    /** the file accessors */
    private UrlAccess[] accessors;

    /**
     * The constructor.
     */
    public ClasspathResourceAccessFactory() {

        super();
    }

    /**
     * @see net.sf.mmm.configuration.api.access.ConfigurationAccessFactoryIF#configure(java.lang.String,
     *      net.sf.mmm.context.api.ContextIF,
     *      net.sf.mmm.configuration.api.ConfigurationIF)
     */
    public void configure(String prefix, ContextIF environment, ConfigurationIF include)
            throws ConfigurationException, ValueException {

        String resourceName = include.getDescendant(ConfigurationDocumentIF.NAME_INCLUDE_HREF)
                .getValue().getString();
        URL url = Thread.currentThread().getContextClassLoader().getResource(resourceName);
        if (url == null) {
            throw new ConfigurationReadException(resourceName);
        }
        UrlAccess access = new UrlAccess(url);
        this.accessors = new UrlAccess[] {access};
    }

    /**
     * @see net.sf.mmm.configuration.api.access.ConfigurationAccessFactoryIF#getAccessors()
     *      {@inheritDoc}
     */
    public ConfigurationAccessIF[] getAccessors() {

        return this.accessors;
    }

    /**
     * @see net.sf.mmm.configuration.api.access.ConfigurationAccessFactoryIF#isSingleAccess()
     *      {@inheritDoc}
     */
    public boolean isSingleAccess() {

        return true;
    }

}
