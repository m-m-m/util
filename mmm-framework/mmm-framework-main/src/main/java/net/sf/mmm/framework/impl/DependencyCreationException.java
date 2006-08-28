/* $Id$ */
package net.sf.mmm.framework.impl;

import net.sf.mmm.framework.NlsResourceBundle;
import net.sf.mmm.framework.api.ComponentDescriptorIF;
import net.sf.mmm.framework.api.DependencyException;
import net.sf.mmm.framework.api.DependencyIF;

/**
 * This is the exception thrown if a
 * {@link net.sf.mmm.framework.api.ExtendedComponentDescriptorIF#getDependencies() dependency}
 * could NOT be resolved.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DependencyCreationException extends DependencyException {

    /** UID for serialization */
    private static final long serialVersionUID = 6421581325810304694L;

    /**
     * The constructor.
     * 
     * @param sourceDescriptor
     *        is the {@link ComponentDescriptorIF descriptor} of the component
     *        that requested the
     *        {@link net.sf.mmm.framework.api.ExtendedComponentDescriptorIF#getDependencies() dependency}.
     * @param sourceInstanceId
     *        is the
     *        {@link net.sf.mmm.framework.api.ComponentManagerIF#requestComponent(Class, String) instance-ID}
     *        of the component causing this request.
     * @param dependency
     *        is the {@link DependencyIF descriptor} of the dependency that
     *        could NOT be created.
     * @param cause
     *        is the exception that caused this error.
     */
    public DependencyCreationException(ComponentDescriptorIF<?> sourceDescriptor,
            String sourceInstanceId, DependencyIF dependency, Exception cause) {

        super(cause, NlsResourceBundle.ERR_DEPENDENCY_CREATION, sourceDescriptor, sourceInstanceId,
                dependency);
    }

}
