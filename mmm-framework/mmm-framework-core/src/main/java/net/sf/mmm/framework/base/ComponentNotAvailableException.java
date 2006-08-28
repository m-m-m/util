/* $Id$ */
package net.sf.mmm.framework.base;

import net.sf.mmm.framework.NlsResourceBundle;
import net.sf.mmm.framework.api.ComponentException;

/**
 * A {@link ComponentNotAvailableException} is thrown if a
 * {@link net.sf.mmm.framework.api.ComponentProviderIF component} was
 * {@link net.sf.mmm.framework.api.ComponentManagerIF#requestComponent(Class) requested}
 * that is NOT
 * {@link net.sf.mmm.framework.api.ComponentManagerIF#hasComponent(Class) available}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ComponentNotAvailableException extends ComponentException {

    /** UID for serialization */
    private static final long serialVersionUID = -4615431837761683276L;

    /**
     * The constructor.
     * 
     * @param specification
     *        is the
     *        {@link net.sf.mmm.framework.api.ComponentManagerIF#requestComponent(Class) requested}
     *        {@link net.sf.mmm.framework.api.ComponentDescriptorIF#getSpecification() specification}
     *        that is NOT
     *        {@link net.sf.mmm.framework.api.ComponentManagerIF#hasComponent(Class) available}.
     */
    public ComponentNotAvailableException(Class specification) {

        super(NlsResourceBundle.ERR_COMPONENT_NOT_AVAILABLE, specification);
    }

}
