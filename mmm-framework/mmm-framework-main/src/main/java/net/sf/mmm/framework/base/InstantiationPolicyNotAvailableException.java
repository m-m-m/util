/* $Id: InstantiationPolicyNotAvailableException.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.framework.base;

import net.sf.mmm.framework.NlsResourceBundle;
import net.sf.mmm.framework.api.ExtendedComponentDescriptorIF;
import net.sf.mmm.framework.api.IocException;

/**
 * An {@link InstantiationPolicyNotAvailableException} is thrown if a
 * {@link ExtendedComponentDescriptorIF#getInstantiationPolicy() instantiation-policy}
 * is not available. This means that is is eigther NOT supported at all or is
 * NOT available for the specific
 * {@link ExtendedComponentDescriptorIF#getSpecification() component}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class InstantiationPolicyNotAvailableException extends IocException {

    /** UID for serialization */
    private static final long serialVersionUID = 3922899083538442911L;

    /**
     * The constructor.
     * 
     * @param descriptor
     *        is the descriptor for which the instantiation policy is NOT
     *        available.
     */
    public InstantiationPolicyNotAvailableException(ExtendedComponentDescriptorIF<?, ?> descriptor) {

        super(NlsResourceBundle.ERR_INSTANTIATION_POLICY_NOT_AVAILABLE, descriptor
                .getInstantiationPolicy(), descriptor.getSpecification());
    }

}
