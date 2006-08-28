/* $Id$ */
package net.sf.mmm.framework.impl;

import net.sf.mmm.framework.NlsResourceBundle;
import net.sf.mmm.framework.api.ComponentException;
import net.sf.mmm.framework.api.ComponentInstanceContainerIF;
import net.sf.mmm.framework.api.LifecycleManagerIF;
import net.sf.mmm.framework.api.LifecycleMethod;

/**
 * This is the exception thrown if a {@link LifecycleMethod lifecycle phase}
 * failed.
 * 
 * @see LifecycleManagerIF
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
    public LifecycleException(ComponentInstanceContainerIF instanceContainer, String lifecyclePhase) {

        super(NlsResourceBundle.ERR_LIFECYCLE, lifecyclePhase, instanceContainer);
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
    public LifecycleException(ComponentInstanceContainerIF instanceContainer,
            String lifecyclePhase, Throwable nested) {

        super(nested, NlsResourceBundle.ERR_LIFECYCLE, lifecyclePhase, instanceContainer);
    }

}
