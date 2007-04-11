/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.impl;

import net.sf.mmm.framework.NlsBundleFrameworkCore;
import net.sf.mmm.framework.api.ComponentException;
import net.sf.mmm.framework.api.Dependency;

/**
 * A {@link ComponentInjectionException} is thrown if a
 * {@link net.sf.mmm.framework.api.ExtendedComponentDescriptor#getDependencies() dependency}
 * injection failed.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ComponentInjectionException extends ComponentException {

    /** UID for serialization. */
    private static final long serialVersionUID = 4474708630553870559L;

    /**
     * The constructor.
     * 
     * @param cause
     *        is the original cause of the exception.
     * @param dependecyInstance
     *        is the dependend component that could NOT be injected.
     * @param dependency
     *        is the dependency descriptor.
     * @param targetComponent
     *        is the target component where the dependency could NOT be injected
     *        to.
     */
    public ComponentInjectionException(Exception cause, Object dependecyInstance,
            Dependency<?> dependency, Object targetComponent) {

        super(cause, NlsBundleFrameworkCore.ERR_INJECTION, "" + dependecyInstance + "["
                + dependency.getSpecification() + "]", dependency, targetComponent);
    }
}
