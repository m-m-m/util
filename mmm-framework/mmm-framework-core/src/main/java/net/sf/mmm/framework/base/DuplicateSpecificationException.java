/* $Id$ */
package net.sf.mmm.framework.base;

import net.sf.mmm.framework.NlsResourceBundle;
import net.sf.mmm.framework.api.ContainerException;

/**
 * A {@link DuplicateSpecificationException} is thrown if a
 * {@link net.sf.mmm.framework.api.ComponentProviderIF component} was
 * {@link net.sf.mmm.framework.api.MutableIocContainerIF#addComponentProvider(net.sf.mmm.framework.api.ComponentProviderIF) added}
 * but another component with the same
 * {@link net.sf.mmm.framework.api.ComponentDescriptorIF#getSpecification() specification}
 * is already registered.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DuplicateSpecificationException extends ContainerException {

    /** UID for serialization */
    private static final long serialVersionUID = 536566649568067962L;

    /**
     * The constructor.
     * 
     * @param specification
     *        is the specification
     */
    public DuplicateSpecificationException(Class specification) {

        super(NlsResourceBundle.ERR_COMPONENT_DUPLICATE_SPECIFICATION, specification);
    }

}
