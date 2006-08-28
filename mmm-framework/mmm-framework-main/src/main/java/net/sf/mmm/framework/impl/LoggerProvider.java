/* $Id: LoggerProvider.java 197 2006-07-31 21:00:03Z hohwille $ */
package net.sf.mmm.framework.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.Log4JLogger;

import net.sf.mmm.framework.api.ComponentDescriptorIF;
import net.sf.mmm.framework.api.ComponentException;
import net.sf.mmm.framework.api.ComponentManagerIF;
import net.sf.mmm.framework.api.ComponentProviderIF;
import net.sf.mmm.framework.base.provider.AbstractPerRequestComponentProvider;

/**
 * This is the implementation of the {@link ComponentProviderIF} interface
 * providing {@link Log loggers} for components.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LoggerProvider extends AbstractPerRequestComponentProvider<Log> {

    /**
     * The constructor.
     */
    public LoggerProvider() {

        super(Log.class);
    }

    /**
     * @see net.sf.mmm.framework.base.provider.AbstractPerRequestComponentProvider#requestDefault(net.sf.mmm.framework.api.ComponentDescriptorIF, java.lang.String, net.sf.mmm.framework.api.ComponentManagerIF)
     * {@inheritDoc}
     */
    @Override
    protected Log requestDefault(ComponentDescriptorIF<?> sourceDescriptor, String sourceInstanceId, ComponentManagerIF componentManager) throws ComponentException {
    
        return new Log4JLogger(sourceDescriptor.getCategory());
    }

}
