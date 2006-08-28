/* $Id$ */
package net.sf.mmm.framework.impl;

import net.sf.mmm.framework.NlsResourceBundle;
import net.sf.mmm.framework.api.ComponentException;
import net.sf.mmm.framework.api.DependencyIF;

/**
 * A {@link ComponentInjectionException} is thrown if a
 * {@link net.sf.mmm.framework.api.ExtendedComponentDescriptorIF#getDependencies() dependency}
 * injection failed.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ComponentInjectionException extends ComponentException {

    /** UID for serialization */
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
            DependencyIF<?> dependency, Object targetComponent) {

        super(cause, NlsResourceBundle.ERR_INJECTION, "" + dependecyInstance + "["
                + dependency.getSpecification() + "]", dependency, targetComponent);
    }
}
