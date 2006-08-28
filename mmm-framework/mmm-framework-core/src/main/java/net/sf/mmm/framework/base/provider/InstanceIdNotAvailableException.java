/* $Id: InstanceIdNotAvailableException.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.framework.base.provider;

import net.sf.mmm.framework.NlsResourceBundle;
import net.sf.mmm.framework.api.ComponentDescriptorIF;
import net.sf.mmm.framework.api.ComponentException;
import net.sf.mmm.framework.api.ComponentManagerIF;

/**
 * This exception is thrown if a {@link ComponentDescriptorIF component} for a
 * specific instance-ID was
 * {@link ComponentManagerIF#requestComponent(Class, String) requested} but is
 * NOT available.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class InstanceIdNotAvailableException extends ComponentException {

    /** UID for serialization */
    private static final long serialVersionUID = 5117938605875157668L;

    /**
     * The constructor.
     * 
     * @param instanceId
     *        is the
     *        {@link ComponentManagerIF#requestComponent(Class, String) requested}
     *        instance-ID that is NOT available.
     * @param descriptor
     *        is the descriptor of the {@link ComponentDescriptorIF component}
     *        that was requested.
     */
    public InstanceIdNotAvailableException(String instanceId, ComponentDescriptorIF<?> descriptor) {

        super(NlsResourceBundle.ERR_COMPONENT_ID_NOT_AVAILABLE, descriptor.getSpecification(),
                instanceId);
    }

}
