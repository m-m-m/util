/* $Id$ */
package net.sf.mmm.framework.impl;

import net.sf.mmm.framework.NlsResourceBundle;
import net.sf.mmm.framework.api.ComponentDescriptorIF;
import net.sf.mmm.framework.api.DependencyException;
import net.sf.mmm.framework.base.AbstractDependency;
import net.sf.mmm.framework.base.ComponentNotAvailableException;

/**
 * This is the exception thrown if a
 * {@link net.sf.mmm.framework.api.ExtendedComponentDescriptorIF#getDependencies() dependency}
 * could NOT be resolved.
 * 
 * @see ComponentNotAvailableException
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DependencyNotAvailableException extends DependencyException {

    /** UID for serialization */
    private static final long serialVersionUID = -177207919686785274L;

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
     * @param dependencySpecification
     *        is the {@link AbstractDependency#getSpecification() specification}
     *        of the dependency that is NOT available.
     */
    public DependencyNotAvailableException(ComponentDescriptorIF<?> sourceDescriptor,
            String sourceInstanceId, Class dependencySpecification) {

        super(NlsResourceBundle.ERR_DEPENDENCY_NOT_AVAILABLE, sourceDescriptor, sourceInstanceId,
                dependencySpecification);
    }

}
