/* $Id$ */
package net.sf.mmm.framework.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;

import net.sf.mmm.framework.api.MutableIocContainer;
import net.sf.mmm.framework.base.AbstractIocContainer;

/**
 * This is the default implementation of the
 * {@link net.sf.mmm.framework.api.MutableIocContainer} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class IocContainerImpl extends AbstractIocContainer {

    /** the logger instance */
    private Log logger;

    /**
     * The constructor for a toplevel container.
     */
    public IocContainerImpl() {

        super();
    }

    /**
     * The constructor for a
     * {@link #createChildContainer(String) child-container}.
     * 
     * @param parentContainer
     *        is the {@link #getParentContainer() parent-container} that created
     *        this one.
     * @param name
     *        is the {@link #getName() name} of this container.
     */
    public IocContainerImpl(IocContainerImpl parentContainer, String name) {

        super(parentContainer, name);
    }

    /**
     * This method gets the logger instance.
     * 
     * @return the logger.
     */
    public Log getLogger() {

        return this.logger;
    }

    /**
     * This method sets the logger.
     * 
     * @param myLogger
     *        is the logger instance.
     */
    @Resource
    public void setLogger(Log myLogger) {

        this.logger = myLogger;
    }

    /**
     * @see net.sf.mmm.framework.api.IocContainer#createChildContainer(java.lang.String)
     * 
     */
    public MutableIocContainer createChildContainer(String name) {

        return new IocContainerImpl(this, name);
    }

    /**
     * @see net.sf.mmm.framework.base.AbstractIocContainer#debug(java.lang.String)
     * 
     */
    @Override
    protected void debug(String message) {

        this.logger.debug(message);
    }

    /**
     * @see net.sf.mmm.framework.base.AbstractIocContainer#info(java.lang.String)
     * 
     */
    @Override
    protected void info(String message) {

        this.logger.info(message);
    }

    /**
     * @see net.sf.mmm.framework.base.AbstractIocContainer#warning(java.lang.String)
     * 
     */
    @Override
    protected void warning(String message) {

        this.logger.warn(message);
    }

}
