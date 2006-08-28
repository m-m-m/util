/* $Id$ */
package net.sf.mmm.framework.base;

import java.util.HashSet;
import java.util.Set;

import net.sf.mmm.framework.api.ExtendedComponentDescriptorIF;
import net.sf.mmm.framework.api.ExtendedComponentInstanceContainerIF;
import net.sf.mmm.framework.api.LifecycleManagerIF;
import net.sf.mmm.framework.api.LifecycleMethod;
import net.sf.mmm.framework.impl.LifecycleException;
import net.sf.mmm.util.reflect.ReflectionUtil;

/**
 * This is the abstract base implementation of the {@link LifecycleManagerIF}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractLifecycleManager implements LifecycleManagerIF {

    /** @see #getLifecyclePhases() */
    private final Set<String> phases;

    /**
     * The constructor.
     */
    public AbstractLifecycleManager() {

        super();
        this.phases = new HashSet<String>();
    }

    /**
     * This method should be called from the constructor of sub-classes to
     * register the supported lifecycle phases. This method should NOT be called
     * after this lifecycle manager is set-up.
     * 
     * @see #getLifecyclePhases()
     * 
     * @param lifecyclePhase
     *        is the lifecycle phase to register.
     */
    public final void registerLifecyclePhase(String lifecyclePhase) {

        this.phases.add(lifecyclePhase);
    }

    /**
     * @see net.sf.mmm.framework.api.LifecycleManagerIF#getLifecyclePhases()
     * {@inheritDoc}
     */
    public final Set<String> getLifecyclePhases() {

        return this.phases;
    }

    /**
     * This method performs a regular lifecycle method.
     * 
     * @param instanceContainer
     *        is the container with the component instance.
     * @param phase
     *        is the
     *        {@link ComponentDescriptor#getLifecycleMethod(String) phase} to
     *        perform.
     * @throws LifecycleException
     *         if the phase failed.
     */
    protected void performLifecyclePhase(
            ExtendedComponentInstanceContainerIF<?, ?> instanceContainer, String phase)
            throws LifecycleException {

        try {
            ExtendedComponentDescriptorIF<?, ?> descriptor = instanceContainer.getDescriptor();
            LifecycleMethod method = descriptor.getLifecycleMethod(phase);
            if (method != null) {
                method.getLifecycleMethod().invoke(instanceContainer.getPrivateInstance(),
                        ReflectionUtil.NO_ARGUMENTS);
            }
            instanceContainer.setLifecycleState(phase);
        } catch (Exception e) {
            throw new LifecycleException(instanceContainer, phase, e);
        }
    }

}
