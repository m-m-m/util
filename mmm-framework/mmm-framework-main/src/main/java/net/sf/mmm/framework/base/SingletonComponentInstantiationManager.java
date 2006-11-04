/* $Id$ */
package net.sf.mmm.framework.base;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.framework.api.ComponentDescriptor;
import net.sf.mmm.framework.api.ComponentManager;
import net.sf.mmm.framework.api.ExtendedComponentDescriptor;

/**
 * This is the implementation of the {@link ComponentInstantiationManager}
 * interface for multiple singletons with different
 * {@link net.sf.mmm.framework.api.ComponentManager#requestComponent(Class, String) instance-IDs}.
 * 
 * @param <S>
 *        is the {@link ComponentDescriptor#getSpecification() specification}
 *        of the component.
 * @param <I>
 *        is the
 *        {@link ExtendedComponentDescriptor#getImplementation() implementation}
 *        of the component.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SingletonComponentInstantiationManager<S, I extends S> extends
        AbstractComponentInstantiationManager<S, I> {

    /** @see #getNewInstanceContainer(String) */
    private final Map<String, ExtendedComponentInstanceContainerImpl<S, I>> instanceContainers;

    /**
     * The constructor.
     */
    public SingletonComponentInstantiationManager() {

        super();
        this.instanceContainers = new HashMap<String, ExtendedComponentInstanceContainerImpl<S, I>>();
    }

    /**
     * @see net.sf.mmm.framework.base.AbstractComponentInstantiationManager#createInstanceContainer(String)
     * 
     * @param instanceId
     *        is the
     *        {@link ComponentManager#requestComponent(Class, String) instance-ID}
     *        of the requested component.
     * @return the new instance container for the requested component.
     */
    protected ExtendedComponentInstanceContainerImpl<S, I> getNewInstanceContainer(String instanceId) {

        ExtendedComponentInstanceContainerImpl<S, I> instanceContainer = createInstanceContainer(instanceId);
        this.instanceContainers.put(instanceId, instanceContainer);
        return instanceContainer;
    }

    /**
     * @see net.sf.mmm.framework.base.ComponentInstantiationManager#release(net.sf.mmm.framework.base.ExtendedComponentInstanceContainerImpl)
     * 
     */
    public boolean release(ExtendedComponentInstanceContainerImpl<S, I> instanceContainer) {

        return false;
    }

    /**
     * @see net.sf.mmm.framework.base.ComponentInstantiationManager#request(String)
     * 
     */
    public ExtendedComponentInstanceContainerImpl<S, I> request(String instanceId) {

        ExtendedComponentInstanceContainerImpl<S, I> instanceContainer = this.instanceContainers
                .get(instanceId);
        if (instanceContainer == null) {
            instanceContainer = getNewInstanceContainer(instanceId);
        }
        return instanceContainer;
    }

    /**
     * @see net.sf.mmm.framework.base.ComponentInstantiationManager#dispose()
     * 
     */
    public ExtendedComponentInstanceContainerImpl[] dispose() {
    
        Collection<ExtendedComponentInstanceContainerImpl<S, I>> values = this.instanceContainers.values();
        ExtendedComponentInstanceContainerImpl[] containers = new ExtendedComponentInstanceContainerImpl[values.size()];
        return this.instanceContainers.values().toArray(containers);
    }
}
