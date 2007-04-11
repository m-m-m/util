/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.base;

import net.sf.mmm.framework.NlsBundleFrameworkCore;
import net.sf.mmm.framework.api.ExtendedComponentDescriptor;
import net.sf.mmm.framework.api.IocException;

/**
 * An {@link InstantiationPolicyNotAvailableException} is thrown if a
 * {@link ExtendedComponentDescriptor#getInstantiationPolicy() instantiation-policy}
 * is not available. This means that is is eigther NOT supported at all or is
 * NOT available for the specific
 * {@link ExtendedComponentDescriptor#getSpecification() component}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class InstantiationPolicyNotAvailableException extends IocException {

    /** UID for serialization. */
    private static final long serialVersionUID = 3922899083538442911L;

    /**
     * The constructor.
     * 
     * @param descriptor
     *        is the descriptor for which the instantiation policy is NOT
     *        available.
     */
    public InstantiationPolicyNotAvailableException(ExtendedComponentDescriptor<?, ?> descriptor) {

        super(NlsBundleFrameworkCore.ERR_INSTANTIATION_POLICY_NOT_AVAILABLE, descriptor
                .getInstantiationPolicy(), descriptor.getSpecification());
    }

}
