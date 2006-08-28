/* $Id: FunctionService.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.term.impl;

import javax.annotation.Resource;

import net.sf.mmm.configuration.api.ConfigurationIF;
import net.sf.mmm.term.base.AbstractFunctionService;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.term.api.FunctionServiceIF} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Resource(shareable = true)
public class FunctionService extends AbstractFunctionService {

    /**
     * The constructor.
     */
    public FunctionService() {

        super();
    }

    /**
     * This method configures this service.
     * 
     * @param configuration
     *        is the configuration to inject.
     */
    @Resource
    public void configure(ConfigurationIF configuration) {

    }

}
