/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.impl;

import net.sf.mmm.framework.NlsBundleFrameworkCore;
import net.sf.mmm.framework.api.ComponentException;
import net.sf.mmm.framework.api.ComponentInstanceContainer;
import net.sf.mmm.framework.api.LifecycleManager;
import net.sf.mmm.framework.api.LifecycleMethod;

/**
 * This is the exception thrown if a {@link LifecycleMethod lifecycle phase}
 * failed.
 * 
 * @see LifecycleManager
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LifecycleException extends ComponentException {

    /** UID for serialization */
    private static final long serialVersionUID = -6987247060118666045L;

    /**
     * The constructor.
     * 
     * @param instanceContainer
     *        is the container of the component instance.
     * @param lifecyclePhase
     *        is the lifecycle phase that failed.
     */
    public LifecycleException(ComponentInstanceContainer instanceContainer, String lifecyclePhase) {

        super(NlsBundleFrameworkCore.ERR_LIFECYCLE, lifecyclePhase, instanceContainer);
    }

    /**
     * The constructor.
     * 
     * @param instanceContainer
     *        is the container of the component instance.
     * @param lifecyclePhase
     *        is the lifecycle phase that failed.
     * @param nested
     *        is the exception that caused this error.
     */
    public LifecycleException(ComponentInstanceContainer instanceContainer,
            String lifecyclePhase, Throwable nested) {

        super(nested, NlsBundleFrameworkCore.ERR_LIFECYCLE, lifecyclePhase, instanceContainer);
    }

}
