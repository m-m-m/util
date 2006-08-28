/* $Id: IocContainer.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.framework.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;

import net.sf.mmm.framework.api.MutableIocContainerIF;
import net.sf.mmm.framework.base.AbstractIocContainer;

/**
 * This is the default implementation of the
 * {@link net.sf.mmm.framework.api.MutableIocContainerIF} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class IocContainer extends AbstractIocContainer {

    /** the logger instance */
    private Log logger;

    /**
     * The constructor for a toplevel container.
     */
    public IocContainer() {

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
    public IocContainer(IocContainer parentContainer, String name) {

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
     * @see net.sf.mmm.framework.api.IocContainerIF#createChildContainer(java.lang.String)
     * {@inheritDoc}
     */
    public MutableIocContainerIF createChildContainer(String name) {

        return new IocContainer(this, name);
    }

    /**
     * @see net.sf.mmm.framework.base.AbstractIocContainer#debug(java.lang.String)
     * {@inheritDoc}
     */
    @Override
    protected void debug(String message) {

        this.logger.debug(message);
    }

    /**
     * @see net.sf.mmm.framework.base.AbstractIocContainer#info(java.lang.String)
     * {@inheritDoc}
     */
    @Override
    protected void info(String message) {

        this.logger.info(message);
    }

    /**
     * @see net.sf.mmm.framework.base.AbstractIocContainer#warning(java.lang.String)
     * {@inheritDoc}
     */
    @Override
    protected void warning(String message) {

        this.logger.warn(message);
    }

}
