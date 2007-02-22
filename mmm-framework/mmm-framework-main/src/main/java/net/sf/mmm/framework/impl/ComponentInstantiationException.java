/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.impl;

import net.sf.mmm.framework.NlsResourceBundle;
import net.sf.mmm.framework.api.ComponentException;

/**
 * This is the exception thrown if a
 * {@link net.sf.mmm.framework.api.ExtendedComponentDescriptor#getImplementation() component implementation}
 * could NOT be instantiated.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ComponentInstantiationException extends ComponentException {

    /** UID for serialization */
    private static final long serialVersionUID = 8168893735381325200L;

    /**
     * The constructor.
     * 
     * @param specification
     *        is the component
     *        {@link net.sf.mmm.framework.api.ComponentDescriptor#getSpecification() specification}.
     * @param implementation
     *        is the component
     *        {@link net.sf.mmm.framework.api.ExtendedComponentDescriptor#getImplementation() implementation}.
     * @param instanceId
     *        is the
     *        {@link net.sf.mmm.framework.api.ComponentManager#requestComponent(Class, String) instance-ID}
     *        of the component.
     * @param cause
     *        is the exception that caused this error.
     */
    public ComponentInstantiationException(Class specification, Class implementation,
            String instanceId, Exception cause) {

        super(cause, NlsResourceBundle.ERR_INSTANTIATION, specification, implementation, instanceId);
    }

}
