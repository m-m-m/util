/* $Id$ */
package net.sf.mmm.framework.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.Log4JLogger;

import net.sf.mmm.framework.api.ComponentDescriptor;
import net.sf.mmm.framework.api.ComponentException;
import net.sf.mmm.framework.api.ComponentManager;
import net.sf.mmm.framework.api.ComponentProvider;
import net.sf.mmm.framework.base.provider.AbstractPerRequestComponentProvider;

/**
 * This is the implementation of the {@link ComponentProvider} interface
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
     * @see net.sf.mmm.framework.base.provider.AbstractPerRequestComponentProvider#requestDefault(net.sf.mmm.framework.api.ComponentDescriptor, java.lang.String, net.sf.mmm.framework.api.ComponentManager)
     */
    @Override
    protected Log requestDefault(ComponentDescriptor<?> sourceDescriptor, String sourceInstanceId, ComponentManager componentManager) throws ComponentException {
    
        return new Log4JLogger(sourceDescriptor.getCategory());
    }

}
