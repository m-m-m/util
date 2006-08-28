/* $Id: UrlAccess.java 205 2006-08-10 19:04:59Z hohwille $ */
package net.sf.mmm.configuration.impl.access.url;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.base.ConfigurationReadException;
import net.sf.mmm.configuration.base.ConfigurationWriteException;
import net.sf.mmm.configuration.base.access.AbstractConfigurationAccess;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.configuration.api.access.ConfigurationAccessIF} interface
 * using {@link java.net.URL} to read and write data.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UrlAccess extends AbstractConfigurationAccess {

    /** the URL to access */
    private final URL url;

    /** @see #getUniqueUri() */
    private final String canonicalPath;

    /** @see #getName() */
    private final String name;

    /**
     * The constructor.
     * 
     * 
     * @param unifiedResourceLocator
     *        is the URL to access.
     */
    public UrlAccess(URL unifiedResourceLocator) {

        super();
        this.url = unifiedResourceLocator;
        this.canonicalPath = this.url.toExternalForm();
        String path = this.url.getPath();
        int endIndex = path.length() - 1;
        while ((endIndex > 0) && (path.charAt(endIndex) == '/')) {
            endIndex--;
        }
        int startIndex = path.lastIndexOf('/', endIndex);
        if (startIndex == -1) {
            startIndex = 0;
        } else {
            startIndex++;
        }
        String filename = path.substring(startIndex, endIndex);
        String query = this.url.getQuery();
        if (query != null) {
            filename += query;
        }
        if (filename.length() == 0) {
            filename = "noname";
        }
        this.name = filename;
    }

    /**
     * @see net.sf.mmm.configuration.api.access.ConfigurationAccessIF#getUniqueUri()
     * {@inheritDoc}
     */
    public String getUniqueUri() {

        return this.canonicalPath;
    }
    
    /**
     * This method gets the url.
     *
     * @return the access url.
     */
    public URL getUrl() {

        return this.url;
    }
    
    /**
     * @see net.sf.mmm.configuration.api.access.ConfigurationAccessIF#getName()
     * {@inheritDoc}
     */
    public String getName() {

        return this.name;
    }

    /**
     * @see net.sf.mmm.configuration.api.access.ConfigurationAccessIF#getReadAccess()
     * {@inheritDoc}
     */
    public InputStream getReadAccess() throws ConfigurationException {

        try {
            return this.url.openStream();
        } catch (IOException e) {
            throw new ConfigurationReadException(this, e);
        }
    }

    /**
     * @see net.sf.mmm.configuration.api.access.ConfigurationAccessIF#getWriteAccess()
     * {@inheritDoc}
     */
    public OutputStream getWriteAccess() throws ConfigurationException {

        //Web-DAV?
        throw new ConfigurationWriteException(this);
    }

    /**
     * @see net.sf.mmm.configuration.api.access.ConfigurationAccessIF#isReadOnly()
     * {@inheritDoc}
     */
    public boolean isReadOnly() {

        return true;
    }

}
