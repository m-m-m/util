/* $Id$ */
package net.sf.mmm.framework.base;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.framework.api.ComponentDescriptorIF;
import net.sf.mmm.framework.api.ComponentManagerIF;
import net.sf.mmm.framework.api.ExtendedComponentDescriptorIF;

/**
 * This is the implementation of the {@link ComponentInstantiationManagerIF}
 * interface for multiple singletons with different
 * {@link net.sf.mmm.framework.api.ComponentManagerIF#requestComponent(Class, String) instance-IDs}.
 * 
 * @param <S>
 *        is the {@link ComponentDescriptorIF#getSpecification() specification}
 *        of the component.
 * @param <I>
 *        is the
 *        {@link ExtendedComponentDescriptorIF#getImplementation() implementation}
 *        of the component.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SingletonComponentInstantiationManager<S, I extends S> extends
        AbstractComponentInstantiationManager<S, I> {

    /** @see #getNewInstanceContainer(String) */
    private final Map<String, ExtendedComponentInstanceContainer<S, I>> instanceContainers;

    /**
     * The constructor.
     */
    public SingletonComponentInstantiationManager() {

        super();
        this.instanceContainers = new HashMap<String, ExtendedComponentInstanceContainer<S, I>>();
    }

    /**
     * @see net.sf.mmm.framework.base.AbstractComponentInstantiationManager#createInstanceContainer(String)
     * 
     * @param instanceId
     *        is the
     *        {@link ComponentManagerIF#requestComponent(Class, String) instance-ID}
     *        of the requested component.
     * @return the new instance container for the requested component.
     */
    protected ExtendedComponentInstanceContainer<S, I> getNewInstanceContainer(String instanceId) {

        ExtendedComponentInstanceContainer<S, I> instanceContainer = createInstanceContainer(instanceId);
        this.instanceContainers.put(instanceId, instanceContainer);
        return instanceContainer;
    }

    /**
     * @see net.sf.mmm.framework.base.ComponentInstantiationManagerIF#release(net.sf.mmm.framework.base.ExtendedComponentInstanceContainer)
     * {@inheritDoc}
     */
    public boolean release(ExtendedComponentInstanceContainer<S, I> instanceContainer) {

        return false;
    }

    /**
     * @see net.sf.mmm.framework.base.ComponentInstantiationManagerIF#request(String)
     * {@inheritDoc}
     */
    public ExtendedComponentInstanceContainer<S, I> request(String instanceId) {

        ExtendedComponentInstanceContainer<S, I> instanceContainer = this.instanceContainers
                .get(instanceId);
        if (instanceContainer == null) {
            instanceContainer = getNewInstanceContainer(instanceId);
        }
        return instanceContainer;
    }

    /**
     * @see net.sf.mmm.framework.base.ComponentInstantiationManagerIF#dispose()
     * {@inheritDoc}
     */
    public ExtendedComponentInstanceContainer[] dispose() {
    
        Collection<ExtendedComponentInstanceContainer<S, I>> values = this.instanceContainers.values();
        ExtendedComponentInstanceContainer[] containers = new ExtendedComponentInstanceContainer[values.size()];
        return this.instanceContainers.values().toArray(containers);
    }
}
